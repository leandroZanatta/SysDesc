package br.com.sysdesc.upgrade.changelog.core;

import static java.sql.DriverManager.getConnection;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.ConfigurationException;

import org.apache.commons.io.FileUtils;

import br.com.sysdesc.upgrade.util.classes.CryptoUtil;
import br.com.sysdesc.upgrade.util.resources.Configuracoes;

public class Conexao {

	private static final Logger log = Logger.getLogger(Conexao.class.getName());

	private static Boolean isconfigured() {
		return new File(Configuracoes.CONEXAO).exists();
	}

	public static File getConfiguracaoBanco() throws ConfigurationException {

		if (!isconfigured()) {

			throw new ConfigurationException("Configuração de banco de dados não encontrada");
		}

		return new File(Configuracoes.CONEXAO);
	}

	public static Connection buscarConexao() throws Exception {

		Properties propertiesConexao = buscarPropertiesConexao();

		String clazz = propertiesConexao.getProperty("javax.persistence.jdbc.driver");
		String url = propertiesConexao.getProperty("javax.persistence.jdbc.url");
		String usuario = propertiesConexao.getProperty("javax.persistence.jdbc.user");
		String senha = propertiesConexao.getProperty("javax.persistence.jdbc.password");

		Class.forName(clazz);

		return getConnection(url, usuario, senha);

	}

	private static Properties buscarPropertiesConexao() throws ConfigurationException {

		try {
			String arquivoConfiguracao = CryptoUtil
					.fromBlowfish(FileUtils.readFileToString(getConfiguracaoBanco(), Charset.forName("UTF-8")));

			if (arquivoConfiguracao == null) {
				throw new ConfigurationException("Configurações inválidas");
			}

			Properties properties = new Properties();

			properties.load(new StringReader(arquivoConfiguracao));

			return properties;

		} catch (IOException e) {

			log.log(Level.SEVERE, "Erro ao Buscar Configurações", e);

			return null;
		}
	}

}
