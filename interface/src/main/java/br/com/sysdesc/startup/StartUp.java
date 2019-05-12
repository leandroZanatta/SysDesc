package br.com.sysdesc.startup;

import javax.naming.ConfigurationException;

import br.com.sysdesc.repository.conexao.Conexao;
import br.com.sysdesc.repository.core.Changelog;
import br.com.sysdesc.ui.FrmApplication;
import br.com.sysdesc.ui.FrmConexao;
import br.com.sysdesc.util.classes.LookAndFeelUtil;

public class StartUp {

	private static void createConnection() {

		try {

			Conexao.buildEntityManager();

		} catch (ConfigurationException e) {

			new FrmConexao().setVisible(Boolean.TRUE);

			createConnection();
		}

	}

	public static void main(String[] args) throws Exception {

		LookAndFeelUtil.configureLayout();

		createConnection();

		Changelog.runChangelog();

		FrmApplication.getInstance();
	}

}
