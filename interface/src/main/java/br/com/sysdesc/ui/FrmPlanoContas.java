package br.com.sysdesc.ui;

import javax.swing.JPanel;

import br.com.sysdesc.components.AbstractInternalFrame;
import br.com.sysdesc.repository.model.PermissaoPrograma;
import net.miginfocom.swing.MigLayout;

public class FrmPlanoContas extends AbstractInternalFrame {

	private JPanel painelContent;

	public FrmPlanoContas(PermissaoPrograma permissaoPrograma, Long codigoUsuario) {
		super(permissaoPrograma, codigoUsuario);

		InitComponents();
	}

	private void InitComponents() {

		setSize(450, 230);
		setClosable(Boolean.TRUE);
		setTitle("Cadastro de plano de contas");
		painelContent = new JPanel();
		painelContent.setLayout(new MigLayout("", "", ""));
		getContentPane().add(painelContent);

	}

}
