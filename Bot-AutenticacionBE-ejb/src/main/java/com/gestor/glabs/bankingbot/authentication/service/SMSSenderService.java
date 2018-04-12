/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.glabs.bankingbot.authentication.service;

import com.sun.security.auth.login.ConfigFile;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Singleton;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

/**
 *
 * @author Hendrix
 */
@Singleton
public class SMSSenderService {

    private static final Logger LOG = Logger.getLogger(SMSSenderService.class.getName());
    
    public boolean sendSMS(String host, String numeroTelefono, String text, String password) {
        LOG.info("Send confirmation code: "+text+", to phone:"+numeroTelefono);
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(UriBuilder.fromUri("http://"+host).build());
        try {
            String response = target.path("sendsms")
                    .queryParam("phone", numeroTelefono)
                    .queryParam("text", text)
                    .queryParam("password", password)
                    .request().accept(MediaType.TEXT_PLAIN).get(String.class);
            client.close();
            return response.contains("SENT");
        } catch (Exception e) {
            LOG.log(Level.WARNING, "Error al invocar servicio que obtiene infomracion del cliente.",e);
            return false;
        }
    }
    
}
