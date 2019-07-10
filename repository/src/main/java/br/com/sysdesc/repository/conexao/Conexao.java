package br.com.sysdesc.repository.conexao;

import static java.sql.DriverManager.getConnection;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.util.Properties;

import javax.naming.ConfigurationException;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import org.apache.commons.io.FileUtils;

import com.mysema.query.sql.MySQLTemplates;
import com.mysema.query.sql.PostgresTemplates;
import com.mysema.query.sql.SQLTemplates;

import br.com.sysdesc.repository.enumeradores.TipoConexaoEnum;
import br.com.sysdesc.util.classes.CryptoUtil;
import br.com.sysdesc.util.resources.Configuracoes;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Conexao {

	private static EntityManager entityManager;

	private static SQLTemplates sqlTemplates;

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

		Properties propertiesConexao = buscarPropertiesConexao();

		entityManager = Persistence.createEntityManagerFactory("SysDesc", propertiesConexao).createEntityManager();

		sqlTemplates = createTemplate(propertiesConexao);

	}

	private static SQLTemplates createTemplate(Properties propertiesConexao) {

		TipoConexaoEnum conexao = TipoConexaoEnum.POSTGRES;

		if (propertiesConexao.get(conexao.getJdbcDriver()).equals(conexao.getDriver())) {
			return PostgresTemplates.DEFAULT;
		}

		return MySQLTemplates.DEFAULT;
	}

	public static EntityManager getEntityManager() {

		return entityManager;
	}

	public static SQLTemplates getSqlTemplates() {
		return sqlTemplates;
	}

	public static Connection buscarConexao() throws Exception {

		Properties propertiesConexao = buscarPropertiesConexao();

		String clazz = propertiesConexao.getProperty(TipoConexaoEnum.jdbcDriver);
		String url = propertiesConexao.getProperty(TipoConexaoEnum.jdbcUrl);
		String usuario = propertiesConexao.getProperty(TipoConexaoEnum.jdbcUser);
		String senha = propertiesConexao.getProperty(TipoConexaoEnum.jdbcPassword);

		Class.forName(clazz);

		return getConnection(url, usuario, senha);

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
