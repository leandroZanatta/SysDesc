package br.com.sysdesc.startup;

import static br.com.sysdesc.util.resources.Resources.APPLICATION_JAR;
import static br.com.sysdesc.util.resources.Resources.translate;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;

import javax.naming.ConfigurationException;

import br.com.sysdesc.changelog.core.Changelog;
import br.com.sysdesc.changelog.core.Conexao;
import br.com.sysdesc.ui.FrmConexao;
import br.com.sysdesc.util.classes.LookAndFeelUtil;

public class StartUp {

	private Connection criarConnection() throws Exception {

		try {

			return Conexao.buscarConexao();

		} catch (ConfigurationException e) {

			LookAndFeelUtil.configureLayout();

			new FrmConexao().setVisible(Boolean.TRUE);

			return criarConnection();
		}

	}

	private void iniciarAplicacao() throws IOException, InterruptedException {
		Desktop.getDesktop().open(new File(translate(APPLICATION_JAR, "interface.jar")));
	}

	private void atualizarAplicacao(Connection connection) {
// modificar os Jars e o arquivo upgrade.

		Changelog.runChangelog(connection);
	}

	private boolean versaoValida(Connection connection) {
// Conectar na internet e verificar o arquivo de versao.
		return Boolean.TRUE;
	}

	public static void main(String[] args) throws Exception {

		StartUp startUp = new StartUp();

		Connection connection = startUp.criarConnection();

		if (!startUp.versaoValida(connection)) {

			startUp.atualizarAplicacao(connection);

		}

		startUp.iniciarAplicacao();

		System.exit(0);

	}

}
