package br.com.sysdesc.ui;

import javax.swing.JPanel;

import br.com.sysdesc.components.AbstractInternalFrame;
import br.com.sysdesc.repository.model.PermissaoPrograma;
import net.miginfocom.swing.MigLayout;

public class FrmFornecedor extends AbstractInternalFrame {
	private JPanel painelContent;

	public FrmFornecedor(PermissaoPrograma permissaoPrograma, Long codigoUsuario) {
		super(permissaoPrograma, codigoUsuario);
		// TODO Auto-generated constructor stub
		initComponents();
	}

	private void initComponents() {
		setSize(450, 20);
		setClosable(Boolean.TRUE);
		setTitle("Cadastro de Fornecedores");
		painelContent = new JPanel();
		getContentPane().add(painelContent);

		painelContent.setLayout(new MigLayout("", "", ""));

	}

}