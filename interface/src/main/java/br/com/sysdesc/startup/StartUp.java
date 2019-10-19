package br.com.sysdesc.startup;

import br.com.sysdesc.ftp.SysdescFTP;
import br.com.sysdesc.repository.conexao.Conexao;
import br.com.sysdesc.rest.SysdescRest;
import br.com.sysdesc.thread.PDVSchedule;
import br.com.sysdesc.ui.FrmApplication;
import br.com.sysdesc.util.classes.LookAndFeelUtil;

public class StartUp {

	public static void main(String[] args) throws Exception {

		LookAndFeelUtil.configureLayout();

		Conexao.buildEntityManager();

		PDVSchedule.getInstance().startScheduleAtualizacaoGerenciadores();

		SysdescRest.startServer();

		SysdescFTP.startServer();

		FrmApplication.getInstance();
	}

}
