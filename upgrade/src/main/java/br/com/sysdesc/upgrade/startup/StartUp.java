package br.com.sysdesc.upgrade.startup;

import java.sql.Connection;

import javax.naming.ConfigurationException;
import javax.swing.JOptionPane;

import br.com.sysdesc.upgrade.changelog.core.Changelog;
import br.com.sysdesc.upgrade.changelog.core.Conexao;
import br.com.sysdesc.upgrade.ui.FrmConexao;
import br.com.sysdesc.upgrade.util.classes.LookAndFeelUtil;

public class StartUp {

	public static void main(String[] args) {

		try {
			Connection conexao = criarConnection();

			Changelog.runChangelog(conexao);

		} catch (Exception e) {

			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}

	private static Connection criarConnection() throws Exception {

		try {

			return Conexao.buscarConexao();

		} catch (ConfigurationException e) {

			LookAndFeelUtil.configureLayout();

			new FrmConexao().setVisible(Boolean.TRUE);

			return criarConnection();
		}

	}

}
