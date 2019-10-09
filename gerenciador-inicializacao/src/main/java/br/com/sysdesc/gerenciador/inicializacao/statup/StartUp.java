package br.com.sysdesc.gerenciador.inicializacao.statup;

import br.com.sysdesc.gerenciador.inicializacao.ui.FrmPrincipal;
import br.com.sysdesc.util.classes.LookAndFeelUtil;

public class StartUp {

	public static void main(String[] args) throws Exception {

		LookAndFeelUtil.configureLayout();

		new FrmPrincipal().setVisible(Boolean.TRUE);
	}

}
