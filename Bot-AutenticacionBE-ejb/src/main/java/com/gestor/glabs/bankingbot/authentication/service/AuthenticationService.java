/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.glabs.bankingbot.authentication.service;

import com.gestor.glabs.bankingbot.model.Company;
import com.gestor.glabs.bankingbot.model.UserFB;
import com.gestor.glabs.mongopersistence.MongoPersistence;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;
import org.mongodb.morphia.query.UpdateResults;

/**
 *
 * @author Hendrix
 */
@Singleton
public class AuthenticationService {

    private static final Logger LOG = Logger.getLogger(AuthenticationService.class.getName());

    private static final String SMS_MESSAGE = "Estimado Cliente, su codigo de seguridad para registrarse en FRED Bot es: ${CODE}, duracion 2 minutos.";

    @EJB
    private MongoPersistence mp;
    @EJB
    private SMSSenderService smsSenderService;

    private Company company;

    @PostConstruct
    public void init() {
        String code = "001";
        Company cmp = this.mp.context().find(Company.class).field("code").equal(code).get();
        if (cmp != null) {
            this.company = cmp;
            LOG.info("SystemConfigured form company: " + this.company.toString());
        } else {
            LOG.severe("Can not load configuration for Company.." + code);
        }
    }

    public boolean linkUsertoFB(String senderIDFB, String authCode) {
        UserFB user = this.findUserAuthCode(authCode);
        if (user != null) {
            UpdateOperations ops = mp.context().createUpdateOperations(UserFB.class).set("userFB", senderIDFB);
            UpdateResults ur = mp.context().update(user, ops);
            if (ur.getUpdatedCount() > 0) {
                return true;
            }
        }
        return false;
    }

    public String validateClientInBank(String cedula, String accountLinkingToken) {
        UserFB us1 = this.findUserToken(accountLinkingToken);
        if (us1 == null || !"OK".equals(us1.getValidationCode())) {
            BankUserDTO userBank = this.validateUserInBank(cedula);
            if (userBank != null) {
                String code = System.currentTimeMillis() + "";
                code = code.substring(code.length() - 5);
                UserFB us = new UserFB();
                us.setCedula(cedula);
                us.setValidationCode(code);
                us.setAccountLinkingToken(accountLinkingToken);
                us.setTelefono(userBank.getNumeroTelefono());
                us.setEdad(userBank.getEdad());
                us.setGenero(userBank.getGenero());
                us.setMail(userBank.getMail());
                us.setNombre(userBank.getNombre());
                try {
                    String text = SMS_MESSAGE.replace("${CODE}", code);
                    //boolean sendSMS = this.smsSenderService.sendSMS(this.company.getHostSMS(), us.getTelefono(), text, this.company.getPasswordSMS());
                    boolean sendSMS = false;
                    if (sendSMS) {
                        Key<UserFB> key = this.mp.context().save(us);
                        LOG.info("User FB created, waiting for user confirmation..." + us.getCedula() + "," + us.getAccountLinkingToken());
                        return "OK";
                    } else {
                        //Se incluye esto para permitir login con 00000
                        us.setValidationCode("00000");
                        Key<UserFB> key = this.mp.context().save(us);
                        LOG.info("User FB created, waiting for user confirmation..." + us.getCedula() + "," + us.getAccountLinkingToken());
                        return "OK";
                        //return "NOSMS";
                    }
                } catch (Exception e) {
                    us.setValidationCode("00000");
                    Key<UserFB> key = this.mp.context().save(us);
                    LOG.info("User FB created, waiting for user confirmation..." + us.getCedula() + "," + us.getAccountLinkingToken());
                    return "OK";
                }
            }
        } else {
            LOG.info("User already registered: " + cedula + "|" + accountLinkingToken);
            return "LOGGED";
        }
        return null;
    }

    public String addUser(String code, String accountLinkingToken) {
        UserFB us1 = this.findUserToken(accountLinkingToken);
        if (us1 != null) {
            if ((System.currentTimeMillis() - us1.getCreationDate().getTime()) < 120000) {
                if (us1.getValidationCode().equals(code)) {
                    Query<UserFB> userRemoveQry = this.mp.context().createQuery(UserFB.class)
                            .filter("cedula", us1.getCedula())
                            .filter("validationCode", "OK");
                    this.mp.context().delete(userRemoveQry);
                    UpdateOperations ops = mp.context().createUpdateOperations(UserFB.class).set("validationCode", "OK");
                    mp.context().update(us1, ops);
                    return us1.getId().toHexString();
                } else {
                    return "BAD";
                }
            } else {
                Query<UserFB> userRemoveQry = this.mp.context().createQuery(UserFB.class)
                        .filter("accountLinkingToken", accountLinkingToken);
                this.mp.context().delete(userRemoveQry);
                return "EXP";
            }
        } else {
            return "INV";
        }
    }

    public UserFB findUserToken(String accountLinkingToken) {
        return this.mp.context().createQuery(UserFB.class).field("accountLinkingToken").equal(accountLinkingToken).get();
    }

    public UserFB findUserIDFB(String userIDFB) {
        return this.mp.context().createQuery(UserFB.class).field("userFB").equal(userIDFB).get();
    }

    public UserFB findUserAuthCode(String authCode) {
        ObjectId oId = new ObjectId(authCode);
        return this.mp.context().createQuery(UserFB.class).field("id").equal(oId).get();
    }

    private BankUserDTO validateUserInBank(String cedula) {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(UriBuilder.fromUri("http://192.9.200.8:8080/financialApi-web/webresources/").build());
        try {
            BankUserDTO textResponse = target.path("informacionCliente")
                    .queryParam("id", cedula)
                    .request().accept(MediaType.APPLICATION_JSON).get(BankUserDTO.class);

            client.close();
            return textResponse;
        } catch (Exception e) {
            LOG.log(Level.WARNING, "Error al invocar servicio que registra nuevo usuarios.", e);
            return null;
        }
    }
}
