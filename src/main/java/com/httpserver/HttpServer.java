package com.httpserver;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import config.Configuration;
import config.ConfigurationManager;
import core.ServerListenerThread;

/**
 * Classe Driver para o Servidor Http
 * @author Igor, Luigi e Matela
 *
 */

public class HttpServer {
	
	private final static Logger LOGGER = LoggerFactory.getLogger(HttpServer.class);
	
	public static void main (String[] args) {
		
		LOGGER.info("Servidor Rodando...");
		ConfigurationManager.getInstance().loadConfigurationFile("src/main/resources/http.json");
		Configuration conf = ConfigurationManager.getInstance().getCurrentConfiguration();
		
		LOGGER.info("Usando porta: " + conf.getPort());
		LOGGER.info("Usando webroot: " + conf.getWebroot());
		
		try {
			ServerListenerThread serverListenerThread = new ServerListenerThread(conf.getPort(), conf.getWebroot());
			serverListenerThread.start();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}

}
