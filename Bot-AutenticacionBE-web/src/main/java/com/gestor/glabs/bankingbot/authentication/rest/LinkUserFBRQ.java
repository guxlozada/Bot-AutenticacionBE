/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.glabs.bankingbot.authentication.rest;

/**
 * Objeto con informacion de la request del webhook de FB Messenger cuando se dispara el evento 'account_linking'. La informacion debe ser utlizada
 * para realizar la autenticacion del usuario.
 *
 * @author Hendrix
 */
public class LinkUserFBRQ {
	/** Codigo de autenticacion enviado por FB Messenger. */
	private String authCode;
	/** PSID del usurio */
	private String senderId;

	public String getAuthCode() {
		return this.authCode;
	}

	public String getSenderId() {
		return this.senderId;
	}

	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}

	public void setSenderId(String senderId) {
		this.senderId = senderId;
	}

	@Override
	public String toString() {
		return "LinkUserFBRQ{" + "authCode=" + this.authCode + ", senderId=" + this.senderId + '}';
	}

}
