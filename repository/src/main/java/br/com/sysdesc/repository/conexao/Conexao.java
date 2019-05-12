package br.com.sysdesc.repository.conexao;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.Charset;
import java.util.Properties;

import javax.naming.ConfigurationException;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import org.apache.commons.io.FileUtils;

import br.com.sysdesc.util.classes.CryptoUtil;
import br.com.sysdesc.util.resources.Configuracoes;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Conexao {

	private static EntityManager entityManager;

	private static Boolean isconfigured() {
		return new File(Configuracoes.CONEXAO).exists();
	}

	public static File getConfiguracaoBanco() throws ConfigurationException {

		if (!isconfigured()) {

			throw new ConfigurationException("Configuração de banco de dados não encontrada");
		}

		return new File(Configuracoes.CONEXAO);
	}

	public static void buildEntityManager() throws ConfigurationException {

		entityManager = Persistence.createEntityManagerFactory("SysDesc", buscarPropertiesConexao())
				.createEntityManager();
	}

	public static EntityManager getEntityManager() {

		return entityManager;
	}

	private static Properties buscarPropertiesConexao() throws ConfigurationException {

		try {
			String arquivoConfiguracao = CryptoUtil
					.fromBlowfish(FileUtils.readFileToString(getConfiguracaoBanco(), Charset.forName("UTF-8")));

			if (arquivoConfiguracao == null) {
				throw new ConfigurationException("Configuração de conexão inválida");
			}

			Properties properties = new Properties();

			properties.load(new StringReader(arquivoConfiguracao));

			return properties;

		} catch (IOException e) {

			log.error("");
			e.printStackTrace();

			return null;
		}
	}

}
