package br.com.sysdesc.upgrade.startup;

import javax.swing.JOptionPane;

import br.com.sysdesc.upgrade.changelog.core.Changelog;
import br.com.sysdesc.upgrade.changelog.core.Conexao;

public class StartUp {

	public static void main(String[] args) {

		try {

			Changelog.runChangelog(Conexao.buscarConexao());

		} catch (Exception e) {

			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}

}
