package br.com.sysdesc.ui;

import static br.com.sysdesc.util.resources.Resources.FRMDEPARTAMENTO_LB_CODIGO;
import static br.com.sysdesc.util.resources.Resources.FRMDEPARTAMENTO_LB_DESCRICAO;
import static br.com.sysdesc.util.resources.Resources.FRMDEPARTAMENTO_TITLE;
import static br.com.sysdesc.util.resources.Resources.translate;

import javax.swing.JLabel;
import javax.swing.JPanel;

import br.com.sysdesc.components.JNumericField;
import br.com.sysdesc.components.JTextFieldMaiusculo;
import net.miginfocom.swing.MigLayout;

public class FrmDepartamento extends AbstractInternalFrame {

	private JTextFieldMaiusculo txDescricao;
	private JNumericField txCodigo;

	private JLabel lblCodigo;

	private JPanel painelContent;

	private JLabel lblDescricao;

	public FrmDepartamento(FrmApplication frmApplication) {
		super(frmApplication);

		setSize(450, 210);
		setClosable(Boolean.TRUE);
		setTitle(translate(FRMDEPARTAMENTO_TITLE));

		painelContent = new JPanel();

		txCodigo = new JNumericField();

		lblCodigo = new JLabel(translate(FRMDEPARTAMENTO_LB_CODIGO));

		lblDescricao = new JLabel(translate(FRMDEPARTAMENTO_LB_DESCRICAO));

		txDescricao = new JTextFieldMaiusculo();
		
		painelContent.setLayout(new MigLayout("", "[grow]", "[][][][][][][grow]"));
		getContentPane().add(painelContent);

		painelContent.add(lblCodigo, "cell 0 0");
		painelContent.add(txCodigo, "cell 0 1");
		painelContent.add(lblDescricao, "cell 0 2");
		painelContent.add(txDescricao, "cell 0 3");
	}

}
