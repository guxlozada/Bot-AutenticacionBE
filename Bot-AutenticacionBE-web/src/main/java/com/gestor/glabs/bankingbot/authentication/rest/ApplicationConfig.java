/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.glabs.bankingbot.authentication.rest;

import java.util.Set;

import javax.ws.rs.core.Application;

/**
 * clase utilizada para registrar los servicios RESTful y define el contexto base de los componentes para validar el usuario, autenticar en FB y
 * registrar la relacion tokenFB-usuario
 * @author Hendrix
 */
@javax.ws.rs.ApplicationPath("webresources")
public class ApplicationConfig extends Application {

	/**
	 * Do not modify addRestResourceClasses() method. It is automatically populated with all resources defined in the project. If required, comment
	 * out calling this method in getClasses().
	 */
	private void addRestResourceClasses(Set<Class<?>> resources) {
		resources.add(com.gestor.glabs.bankingbot.authentication.rest.LinkUserFBResource.class);
	}

	@Override
	public Set<Class<?>> getClasses() {
		Set<Class<?>> resources = new java.util.HashSet<>();
		this.addRestResourceClasses(resources);
		return resources;
	}

}
