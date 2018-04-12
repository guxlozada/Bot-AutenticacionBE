/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.glabs.bankingbot.model;

import com.gestor.glabs.mongopersistence.BaseEntity;
import org.mongodb.morphia.annotations.Entity;

/**
 *
 * @author Hendrix
 */
@Entity(noClassnameStored = true)
public class UserFB extends BaseEntity{
    
    private String userFB;
    private String cedula;
    private String nombre;
    private String mail;
    private String genero;
    private Integer edad;
    private String telefono;
    private String accountLinkingToken;
    private String validationCode;
    
    public String getAuthorizationCode() {
        return super.getId().toHexString();
    }
    
    public String getUserFB() {
        return userFB;
    }

    public void setUserFB(String userFB) {
        this.userFB = userFB;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }
    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getAccountLinkingToken() {
        return accountLinkingToken;
    }

    public void setAccountLinkingToken(String accountLinkingToken) {
        this.accountLinkingToken = accountLinkingToken;
    }

    public String getValidationCode() {
        return validationCode;
    }

    public void setValidationCode(String validationCode) {
        this.validationCode = validationCode;
    }

    @Override
    public String toString() {
        return "UserFB{" + "userFB=" + userFB + ", cedula=" + cedula + ", autorizationCode=" + this.getAuthorizationCode() + '}';
    }
    
    
}
