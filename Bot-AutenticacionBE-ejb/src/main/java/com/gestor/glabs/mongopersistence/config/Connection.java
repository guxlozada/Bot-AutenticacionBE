/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.glabs.mongopersistence.config;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.ServerAddress;
import com.mongodb.WriteConcern;
import java.util.logging.Logger;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

/**
 *
 * @author Henry Coral
 */
public class Connection {
    
    private static final Logger LOG = Logger.getLogger(Connection.class.getName());
	private static final Connection INSTANCE = new Connection();

	private final Datastore datastore;
	private final ConfigDTO config;

	private Connection() {
        System.err.println("--------------------------------------------------------");
        System.err.println("Creando conexion");
        System.err.println("--------------------------------------------------------");
        ConfigReader reader = new ConfigReader();
        config = reader.getConfiguration();
        if (config!=null) {
            MongoClientOptions mongoOptions = MongoClientOptions.builder().socketTimeout(config.getSocketTimeout())
                    .connectTimeout(config.getConnectionTimeout()).build(); // SocketTimeout: 60s, ConnectionTimeout: 20min
            MongoClient mongoClient;
            try{
                mongoClient = new MongoClient(new ServerAddress(config.getHost(), config.getPort()), mongoOptions);
            } catch(Exception e){
                throw new RuntimeException("Error initializing MongoDB", e);
            }
            mongoClient.setWriteConcern(WriteConcern.SAFE);
            Morphia morphia = new Morphia();
            for (String packageN : config.getPackages()) {
                morphia.mapPackage(packageN, true);
            }
            datastore = morphia.createDatastore(mongoClient, config.getDatabase());    
            datastore.ensureIndexes();
            LOG.info("Connection: " +config.toString()+ " initialized");
        } else {
            LOG.info("Configuracion invalida, persistencia no disponible.");
            datastore = null;
        }
	}

	public static Connection instance() {
		return INSTANCE;
	}

	// Creating the mongo connection is expensive - (re)use a singleton for performance reasons
	// Both the underlying Java driver and Datastore are thread safe
	public Datastore getDatabase() {
		return datastore;
	}
    
}
