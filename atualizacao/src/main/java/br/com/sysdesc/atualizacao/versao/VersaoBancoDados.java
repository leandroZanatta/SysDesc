package br.com.sysdesc.atualizacao.versao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.ConfigurationException;

import br.com.sysdesc.atualizacao.changelog.core.Changelog;
import br.com.sysdesc.atualizacao.changelog.core.Conexao;
import br.com.sysdesc.atualizacao.ui.FrmConexao;
import br.com.sysdesc.atualizacao.util.classes.LookAndFeelUtil;

public class VersaoBancoDados {

	private final Logger logger = Logger.getLogger(VersaoBancoDados.class.getName());

	public Long buscarVersaoBanco() {

		String sqlQuery = "select nr_versao from tb_versao";

		try (Connection connection = criarConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
				ResultSet rs = preparedStatement.executeQuery()) {

			if (rs.next()) {
				return rs.getLong(1);
			}

		} catch (Exception e) {

			logger.log(Level.SEVERE, "Erro ao buscar versão no banco de dados", e);
		}

		return 0L;
	}

	public void atualizarVersao(Long versao) {

		String sqlQuery = "update tb_versao set nr_versao=?";

		try (Connection connection = criarConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {

			connection.setAutoCommit(false);

			preparedStatement.setLong(1, versao);

			preparedStatement.executeUpdate();

			connection.commit();

		} catch (Exception e) {

			logger.log(Level.SEVERE, "Erro ao atualizar versão no banco de dados", e);
		}
	}

	public void upgradeDatabase(Long versao) {

		try (Connection connection = criarConnection()) {

			Changelog.runChangelog(connection);

			this.atualizarVersao(versao);

		} catch (Exception e) {
			logger.log(Level.SEVERE, "Erro ao atualizar versão no banco de dados", e);
		}
	}

	private Connection criarConnection() throws Exception {

		try {

			return Conexao.buscarConexao();

		} catch (ConfigurationException e) {

			LookAndFeelUtil.configureLayout();

			new FrmConexao().setVisible(Boolean.TRUE);

			return criarConnection();
		}

	}

}
