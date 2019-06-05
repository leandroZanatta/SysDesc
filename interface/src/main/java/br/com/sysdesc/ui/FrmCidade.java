package br.com.sysdesc.ui;

import static br.com.sysdesc.util.resources.Resources.FRMCIDADE_LB_CODIGO;
import static br.com.sysdesc.util.resources.Resources.FRMCIDADE_TITLE;
import static br.com.sysdesc.util.resources.Resources.FRMESTADO_LB_DESCRICAO;
import static br.com.sysdesc.util.resources.Resources.FRMESTADO_LB_UF;
import static br.com.sysdesc.util.resources.Resources.translate;

import javax.swing.JLabel;
import javax.swing.JPanel;

import br.com.sysdesc.components.AbstractInternalFrame;
import br.com.sysdesc.components.JNumericField;
import br.com.sysdesc.components.JTextFieldMaiusculo;
import br.com.sysdesc.repository.model.PermissaoPrograma;
import net.miginfocom.swing.MigLayout;

public class FrmCidade extends AbstractInternalFrame {

	private static final long serialVersionUID = 1L;

	private JPanel painelContent;
	private JNumericField txCodigo;
	private JLabel lblCodigo;
	private JLabel lblDescricao;
	private JLabel lblUF;
	private JTextFieldMaiusculo txDescricao;
	private JTextFieldMaiusculo txUF;

	public FrmCidade(PermissaoPrograma permissaoPrograma) {
		super(permissaoPrograma);

		setSize(450, 210);
		setClosable(Boolean.TRUE);
		setTitle(translate(FRMCIDADE_TITLE));

		painelContent = new JPanel();
		txCodigo = new JNumericField();
		lblCodigo = new JLabel(translate(FRMCIDADE_LB_CODIGO));
		lblDescricao = new JLabel(translate(FRMESTADO_LB_DESCRICAO));
		txDescricao = new JTextFieldMaiusculo();
		lblUF = new JLabel(translate(FRMESTADO_LB_UF));
		txUF = new JTextFieldMaiusculo();

		painelContent.setLayout(new MigLayout("", "[grow]", "[][][][][][][grow]"));
		getContentPane().add(painelContent);
		painelContent.add(lblCodigo, "cell 0 0");
		painelContent.add(txCodigo, "cell 0 1,growx");

		painelContent.add(lblDescricao, "cell 0 2");
		painelContent.add(txDescricao, "cell 0 3,growx");
		painelContent.add(lblUF, "cell 0 4,growx");
		painelContent.add(txUF, "cell 0 5,growx");

	}

}
