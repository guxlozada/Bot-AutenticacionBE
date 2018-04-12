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
public class Company extends BaseEntity{
    
    private String code;
    private String name;
    private String baseURL;
    private String hostSMS;
    private String passwordSMS;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBaseURL() {
        return baseURL;
    }

    public void setBaseURL(String baseURL) {
        this.baseURL = baseURL;
    }

    public String getPasswordSMS() {
        return passwordSMS;
    }

    public void setPasswordSMS(String passwordSMS) {
        this.passwordSMS = passwordSMS;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getHostSMS() {
        return hostSMS;
    }

    public void setHostSMS(String hostSMS) {
        this.hostSMS = hostSMS;
    }

    @Override
    public String toString() {
        return "Company{" + "code=" + code + ", name=" + name + ", baseURL=" + baseURL + '}';
    }

    
}
