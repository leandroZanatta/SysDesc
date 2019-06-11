package br.com.sysdesc.ui;

import static br.com.sysdesc.util.resources.Resources.FRMCATEGORIA_LB_CODIGO;
import static br.com.sysdesc.util.resources.Resources.FRMCATEGORIA_LB_DEPARTAMENTO;
import static br.com.sysdesc.util.resources.Resources.FRMCATEGORIA_TITLE;
import static br.com.sysdesc.util.resources.Resources.translate;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import br.com.sysdesc.components.AbstractInternalFrame;
import br.com.sysdesc.components.JNumericField;
import br.com.sysdesc.components.JTextFieldMaiusculo;
import br.com.sysdesc.repository.model.Departamento;
import br.com.sysdesc.repository.model.PermissaoPrograma;
import net.miginfocom.swing.MigLayout;

public class FrmCategoria extends AbstractInternalFrame {

	private static final long serialVersionUID = 1L;

	private JPanel painelContent;

	private JLabel lbDepartamento;
	private JComboBox<Departamento> cbDepartamento;

	private JLabel lblCodigo;
	private JLabel lblDescricao;

	private JNumericField txCodigo;
	private JTextFieldMaiusculo txDescricao;

	public FrmCategoria(PermissaoPrograma permissaoPrograma) {
		super(permissaoPrograma);

		setSize(450, 160);
		setClosable(Boolean.TRUE);
		setTitle(translate(FRMCATEGORIA_TITLE));

		painelContent = new JPanel();

		txCodigo = new JNumericField();

		lblCodigo = new JLabel(translate(FRMCATEGORIA_LB_CODIGO));

		cbDepartamento = new JComboBox<Departamento>();

		lbDepartamento = new JLabel(translate(FRMCATEGORIA_LB_DEPARTAMENTO));

		lbCategoria = new JLabel(translate(FRMCATEGORIA_LB_DESCRICAOCATEGORIA));

		txDescricao = new JTextFieldMaiusculo();

		painelContent.setLayout(new MigLayout("", "[grow]", "[][][][][][][grow]"));
		getContentPane().add(painelContent);

		painelContent.add(lblCodigo, "cell 0 0");
		painelContent.add(txCodigo, "cell 0 1,growx");
		painelContent.add(lbDepartamento, "cell 0 2");
		painelContent.add(cbDepartamento, "cell 0 3,growx");

	}

}
