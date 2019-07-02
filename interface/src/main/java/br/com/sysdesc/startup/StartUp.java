package br.com.sysdesc.startup;

import br.com.sysdesc.atualizacao.changelog.core.Changelog;
import br.com.sysdesc.atualizacao.util.classes.LookAndFeelUtil;
import br.com.sysdesc.repository.conexao.Conexao;
import br.com.sysdesc.ui.FrmApplication;

public class StartUp {

	public static void main(String[] args) throws Exception {

		LookAndFeelUtil.configureLayout();

		Changelog.runChangelog(br.com.sysdesc.atualizacao.changelog.core.Conexao.buscarConexao());

		Conexao.buildEntityManager();

		FrmApplication.getInstance();
	}

}
