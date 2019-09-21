package br.com.sysdesc.ui;

import javax.swing.JPanel;

import br.com.sysdesc.components.AbstractInternalFrame;
import br.com.sysdesc.repository.model.PermissaoPrograma;

public class FrmOperacaoEstoque extends AbstractInternalFrame {

	private JPanel painelCotent;

	public FrmOperacaoEstoque(PermissaoPrograma permissaoPrograma, Long codigoUsuario) {
		super(permissaoPrograma, codigoUsuario);

		initComponents();

	}

	private void initComponents() {

		setSize(450, 230);
		setClosable(Boolean.TRUE);
		setTitle("Cadastro DE Operação De Estoque");

		painelCotent = new JPanel();

		painelCotent.setLayout(new net.miginfocom.swing.MigLayout("", "[grow][grow]", "[][][][][][grow]"));

		getContentPane().add(painelCotent);

	}

}
