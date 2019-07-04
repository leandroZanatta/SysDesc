package br.com.sysdesc.ui;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import br.com.sysdesc.components.AbstractInternalFrame;
import br.com.sysdesc.repository.model.PermissaoPrograma;
import net.miginfocom.swing.MigLayout;

public class FrmProduto extends AbstractInternalFrame {
	private JTextField txCodigo;
	private JTextField txDescricao;
	private JTextField txCategoria;
	private JTextField txSubCategoria;
	private JTextField txFornecedor;
	private JTextField txMarca;
	private JTextField txMinimo;
	private JTextField txMaximo;

	public FrmProduto(PermissaoPrograma permissaoPrograma, Long codigoUsuario) {
		super(permissaoPrograma, codigoUsuario);

		setSize(800, 595);
		setClosable(Boolean.TRUE);
		getContentPane().setLayout(new MigLayout("", "[grow][250px:n:250px,grow]", "[][][][][][][][][][][][][][]"));

		JLabel lbCodigo = new JLabel("Código:");
		getContentPane().add(lbCodigo, "cell 0 0");

		txCodigo = new JTextField();
		getContentPane().add(txCodigo, "cell 0 1,alignx left");
		txCodigo.setColumns(10);

		JLabel lbDescricao = new JLabel("Descrição:");
		getContentPane().add(lbDescricao, "cell 0 2");

		txDescricao = new JTextField();
		getContentPane().add(txDescricao, "cell 0 3 2 1,growx");
		txDescricao.setColumns(10);

		JLabel lbDepartamento = new JLabel("Departamento");
		getContentPane().add(lbDepartamento, "cell 0 4");

		JLabel lbUnidade = new JLabel("Unidade:");
		getContentPane().add(lbUnidade, "cell 1 4");

		JComboBox cbDepartamento = new JComboBox();
		getContentPane().add(cbDepartamento, "flowx,cell 0 5,growx");

		JComboBox cbUnidade = new JComboBox();
		getContentPane().add(cbUnidade, "cell 1 5,growx");

		JLabel lbCategoria = new JLabel("Categoria:");
		getContentPane().add(lbCategoria, "cell 0 6");

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Estoque",
				TitledBorder.CENTER, TitledBorder.TOP, null, null));
		getContentPane().add(panel, "cell 1 6 1 6,grow");
		panel.setLayout(new MigLayout("", "[][grow]", "[][][][]"));

		JLabel lbMinimo = new JLabel("Mínimo:");
		panel.add(lbMinimo, "cell 0 0,alignx trailing");

		txMinimo = new JTextField();
		panel.add(txMinimo, "cell 1 0,growx");
		txMinimo.setColumns(10);

		JLabel lbMaximo = new JLabel("Máximo:");
		panel.add(lbMaximo, "cell 0 1,alignx trailing");

		txMaximo = new JTextField();
		panel.add(txMaximo, "cell 1 1,growx");
		txMaximo.setColumns(10);

		JCheckBox chQuantidadeFracionada = new JCheckBox("Quantidade Fracionada");
		panel.add(chQuantidadeFracionada, "cell 0 2 2 1,aligny baseline");

		JCheckBox chMovimentaEstoque = new JCheckBox("Movimenta Estoque");
		panel.add(chMovimentaEstoque, "cell 0 3 2 1");

		txCategoria = new JTextField();
		getContentPane().add(txCategoria, "cell 0 7");
		txCategoria.setColumns(10);

		JLabel lbSubcategoria = new JLabel("Sub-Categoria:");
		getContentPane().add(lbSubcategoria, "cell 0 8");

		txSubCategoria = new JTextField();
		getContentPane().add(txSubCategoria, "cell 0 9");
		txSubCategoria.setColumns(10);

		JLabel lbFornecedor = new JLabel("Fornecedor:");
		getContentPane().add(lbFornecedor, "cell 0 10");

		txFornecedor = new JTextField();
		getContentPane().add(txFornecedor, "cell 0 11,growx,aligny top");
		txFornecedor.setColumns(10);

		JLabel lbMarca = new JLabel("Marca:");
		getContentPane().add(lbMarca, "cell 0 12");

		JLabel lbTipo = new JLabel("Tipo:");
		getContentPane().add(lbTipo, "cell 1 12");

		txMarca = new JTextField();
		getContentPane().add(txMarca, "cell 0 13,growx");
		txMarca.setColumns(10);

		JComboBox cbTipo = new JComboBox();
		getContentPane().add(cbTipo, "cell 1 13,growx");

	}

}
