package br.com.sysdesc.gerenciador.inicializacao.util;

import static br.com.sysdesc.gerenciador.inicializacao.util.GerenciadorInicializacaoConstants.FILE_APPLICATION;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ApplicationProperies {

	private static ApplicationProperies applicationProperies;

	private Properties properties;

	private ApplicationProperies() {

		loadProperties();
	}

	private void loadProperties() {

		properties = new Properties();

		File arquivoPropriedades = new File(FILE_APPLICATION);

		if (arquivoPropriedades.exists()) {

			try (FileInputStream fileInputStream = new FileInputStream(arquivoPropriedades)) {

				properties.load(fileInputStream);

			} catch (IOException e) {

				log.error(GerenciadorInicializacaoLogConstants.ERRO_ARQUIVO_CONFIGURACOES_NAO_ENCONTRADO, e);
			}

		}
	}

	public static ApplicationProperies getInstance() {

		if (applicationProperies == null) {

			applicationProperies = new ApplicationProperies();
		}

		return applicationProperies;
	}

	public String getProperty(String key, String defaultValue) {

		return properties.getProperty(key, defaultValue);
	}

	public void setPropertie(String key, String value) {

		properties.setProperty(key, value);

		File arquivoPropriedades = new File(FILE_APPLICATION);

		try (FileOutputStream fileOutputStream = new FileOutputStream(arquivoPropriedades)) {

			properties.store(fileOutputStream, null);

		} catch (Exception e) {
			log.error(GerenciadorInicializacaoLogConstants.ERRO_ARQUIVO_CONFIGURACOES_NAO_ENCONTRADO, e);
		}

	}

}
