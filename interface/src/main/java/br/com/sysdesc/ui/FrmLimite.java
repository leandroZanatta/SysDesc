package br.com.sysdesc.ui;

import javax.swing.JPanel;

import br.com.sysdesc.components.AbstractInternalFrame;
import br.com.sysdesc.repository.model.PermissaoPrograma;
import net.miginfocom.swing.MigLayout;

public class FrmLimite extends AbstractInternalFrame {

	private JPanel panelContent;

	public FrmLimite(PermissaoPrograma permissaoPrograma, Long codigoUsuario) {
		super(permissaoPrograma, codigoUsuario);

		initComponentes();
	}

	private void initComponentes() {
		setSize(450, 230);

		setClosable(Boolean.TRUE);

		setTitle("Cadastro de Limite");

		panelContent = new JPanel();
		panelContent.setLayout(new MigLayout("", "", ""));
		getContentPane().add(panelContent);

	}

}
