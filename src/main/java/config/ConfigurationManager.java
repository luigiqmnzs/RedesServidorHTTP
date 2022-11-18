package config;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;

import util.Json;

public class ConfigurationManager {
	private static ConfigurationManager myConfigurationManager;
	private static Configuration myCurrentConfiguration;

	private ConfigurationManager() {

	}

	public static ConfigurationManager getInstance() {
		if (myConfigurationManager == null)
			myConfigurationManager = new ConfigurationManager();
		return myConfigurationManager;
	}

	public void loadConfigurationFile(String filePath) {
		FileReader fileReader;
		try {
			fileReader = new FileReader(filePath);
		} catch (FileNotFoundException e) {
			throw new HttpConfigurationException(e);
		}
		StringBuffer sb = new StringBuffer();
		int i;
		try {
			while ((i = fileReader.read()) != -1) {
				sb.append((char) i);
			}
		} catch (IOException e) {
			throw new HttpConfigurationException(e);
		}
		JsonNode conf;
		try {
			conf = Json.parse(sb.toString());
		} catch (IOException e) {
			throw new HttpConfigurationException("Erro no parse do arquivo de configuracao...", e);
		}
		try {
			myCurrentConfiguration = Json.fromJson(conf, Configuration.class);
		} catch (JsonProcessingException e) {
			throw new HttpConfigurationException("Erro no parse do arquivo de configuracao (dentro)...",e);
		}
	}

	/**
	 * retorna o arquivo de configuracao atual
	 */
	public Configuration getCurrentConfiguration() {
		if(myCurrentConfiguration == null) {
			throw new HttpConfigurationException("Nao ha configuracao atual...");
		}
		return myCurrentConfiguration;
	}
}
