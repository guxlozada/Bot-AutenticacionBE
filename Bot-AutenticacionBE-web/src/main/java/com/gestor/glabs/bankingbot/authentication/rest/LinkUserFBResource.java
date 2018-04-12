/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.glabs.bankingbot.authentication.rest;

import com.gestor.glabs.bankingbot.authentication.service.AuthenticationService;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.POST;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * REST Web Service
 *
 * @author Hendrix
 */
@Path("linkUserFB")
@RequestScoped
public class LinkUserFBResource {

    @Context
    private UriInfo context;
    @EJB
    private AuthenticationService authenticationService;
    private static final Logger LOG = Logger.getLogger(LinkUserFBResource.class.getName());

    /**
     * Creates a new instance of LinkUserFBResource
     */
    public LinkUserFBResource() {
    }

    

    /**
     * PUT method for updating or creating an instance of LinkUserFBResource
     * @param content representation for the resource
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response postJson(LinkUserFBRQ content) {
        LOG.info("Request recieved to link FB User with data: "+content.toString());
        boolean resp = this.authenticationService.linkUsertoFB(content.getSenderId(), content.getAuthCode());
        if (resp) {
            return Response.ok().build();
        } else {
            return Response.serverError().build();
        }
    }
}
