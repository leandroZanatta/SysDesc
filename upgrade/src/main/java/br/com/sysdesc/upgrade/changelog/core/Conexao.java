package br.com.sysdesc.upgrade.changelog.core;

import static br.com.sysdesc.upgrade.util.resources.Resources.MENSAGEM_CONFIGURACOES_INVALIDAS;
import static br.com.sysdesc.upgrade.util.resources.Resources.MENSAGEM_ERRO_BUSCAR_PROPRIEDADES_CONEXAO;
import static br.com.sysdesc.upgrade.util.resources.Resources.translate;
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

import br.com.sysdesc.upgrade.enumeradores.TipoConexaoEnum;
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

	public static Connection buscarConexao(String caminho) throws Exception {

		Properties propertiesConexao = buscarPropertiesConexao(caminho);

		String clazz = propertiesConexao.getProperty(TipoConexaoEnum.jdbcDriver);
		String url = propertiesConexao.getProperty(TipoConexaoEnum.jdbcUrl);
		String usuario = propertiesConexao.getProperty(TipoConexaoEnum.jdbcUser);
		String senha = propertiesConexao.getProperty(TipoConexaoEnum.jdbcPassword);

		Class.forName(clazz);

		return getConnection(url, usuario, senha);

	}

	private static Properties buscarPropertiesConexao(String caminho) throws ConfigurationException {

		try {

			File config = caminho != null ? new File(caminho) : getConfiguracaoBanco();

			String arquivoConfiguracao = CryptoUtil
					.fromBlowfish(FileUtils.readFileToString(config, Charset.forName("UTF-8")));

			if (arquivoConfiguracao == null) {
				throw new ConfigurationException(translate(MENSAGEM_CONFIGURACOES_INVALIDAS));
			}

			Properties properties = new Properties();

			properties.load(new StringReader(arquivoConfiguracao));

			return properties;

		} catch (IOException e) {

			log.log(Level.SEVERE, translate(MENSAGEM_ERRO_BUSCAR_PROPRIEDADES_CONEXAO), e);

			return null;
		}
	}

}
