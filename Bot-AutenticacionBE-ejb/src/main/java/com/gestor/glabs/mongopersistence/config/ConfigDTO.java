/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.glabs.mongopersistence.config;

/**
 *
 * @author Henry Coral
 */
public class ConfigDTO {
    
    /**
     * Propiedad host, relacionada con la clave: en el archivo de configuracion.
     */
    private String host;
    /**
     * Propiedad port, relacionada con la clave: en el archivo de configuracion.
     */
    private Integer port;
    /**
     * Propiedad database, relacionada con la clave: en el archivo de configuracion.
     */
    private String database;
    /**
     * Propiedad socket-timeout, relacionada con la clave: en el archivo de configuracion.
     */
    private Integer socketTimeout;
    /**
     * Propiedad connection-timeout, relacionada con la clave: en el archivo de configuracion.
     */
    private Integer connectionTimeout;
    
    private String packages [];
    

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public Integer getSocketTimeout() {
        return socketTimeout;
    }

    public void setSocketTimeout(Integer socketTimeout) {
        this.socketTimeout = socketTimeout;
    }

    public Integer getConnectionTimeout() {
        return connectionTimeout;
    }

    public void setConnectionTimeout(Integer connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }

    public String[] getPackages() {
        return packages;
    }

    public void setPackages(String[] packages) {
        this.packages = packages;
    }

    @Override
    public String toString() {
        return "ConfigDTO{" + "host=" + host + ", port=" + port + ", database=" + database + '}';
    }
    
}
