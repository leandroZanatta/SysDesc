package br.com.sysdesc.startup;

import br.com.sysdesc.repository.conexao.Changelog;
import br.com.sysdesc.repository.conexao.Conexao;
import br.com.sysdesc.ui.FrmApplication;
import br.com.sysdesc.util.classes.LookAndFeelUtil;

public class StartUp {

	public static void main(String[] args) throws Exception {

		LookAndFeelUtil.configureLayout();

		Changelog.runChangelog(Conexao.buscarConexao());

		Conexao.buildEntityManager();

		FrmApplication.getInstance();
	}

}
