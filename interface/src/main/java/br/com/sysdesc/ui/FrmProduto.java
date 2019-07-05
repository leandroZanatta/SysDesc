package br.com.sysdesc.ui;

import static br.com.sysdesc.pesquisa.enumeradores.PesquisaEnum.PES_CATEGORIAS;
import static br.com.sysdesc.pesquisa.enumeradores.PesquisaEnum.PES_CLIENTES;
import static br.com.sysdesc.pesquisa.enumeradores.PesquisaEnum.PES_MARCAS;
import static br.com.sysdesc.pesquisa.enumeradores.PesquisaEnum.PES_PRODUTOS;
import static br.com.sysdesc.pesquisa.enumeradores.PesquisaEnum.PES_SUBCATEGORIAS;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import br.com.sysdesc.components.AbstractInternalFrame;
import br.com.sysdesc.components.JMoneyField;
import br.com.sysdesc.components.JNumericField;
import br.com.sysdesc.components.JTextFieldMaiusculo;
import br.com.sysdesc.components.adapters.PanelEventAdapter;
import br.com.sysdesc.pesquisa.components.CampoPesquisa;
import br.com.sysdesc.pesquisa.components.PanelActions;
import br.com.sysdesc.repository.dao.CategoriaDAO;
import br.com.sysdesc.repository.dao.ClienteDAO;
import br.com.sysdesc.repository.dao.DepartamentoDAO;
import br.com.sysdesc.repository.dao.MarcaDAO;
import br.com.sysdesc.repository.dao.ProdutoDAO;
import br.com.sysdesc.repository.dao.SubcategoriaDAO;
import br.com.sysdesc.repository.dao.UnidadeDAO;
import br.com.sysdesc.repository.model.Categoria;
import br.com.sysdesc.repository.model.Cliente;
import br.com.sysdesc.repository.model.Departamento;
import br.com.sysdesc.repository.model.Marca;
import br.com.sysdesc.repository.model.PermissaoPrograma;
import br.com.sysdesc.repository.model.Produto;
import br.com.sysdesc.repository.model.Subcategoria;
import br.com.sysdesc.repository.model.Unidade;
import net.miginfocom.swing.MigLayout;

public class FrmProduto extends AbstractInternalFrame {

	private static final long serialVersionUID = 1L;

	private JPanel painelContent;

	private JLabel lbCodigo;
	private JLabel lbDescricao;
	private JLabel lbDepartamento;
	private JLabel lbUnidade;
	private JLabel lbCategoria;
	private JLabel lbFornecedor;
	private JLabel lbMarca;
	private JLabel lbTipo;
	private JLabel lbMinimo;
	private JLabel lbMaximo;
	private JLabel lbSubcategoria;
	private JLabel lbStatus;

	private JPanel panel;

	private JNumericField txCodigo;

	private JTextFieldMaiusculo txDescricao;

	private JCheckBox chQuantidadeFracionada;
	private JCheckBox chMovimentaEstoque;

	private JComboBox<Departamento> cbDepartamento;
	private JComboBox<Unidade> cbUnidade;
	private JComboBox cbTipo;
	private JComboBox cbStatus;

	private CampoPesquisa<Categoria> cpCategoria;
	private CampoPesquisa<Subcategoria> cpSubCategoria;
	private CampoPesquisa<Cliente> cpFornecedor;
	private CampoPesquisa<Marca> cpMarca;

	private JMoneyField txMinimo;
	private JMoneyField txMaximo;

	private DepartamentoDAO departamentoDAO = new DepartamentoDAO();
	private CategoriaDAO categoriaDAO = new CategoriaDAO();
	private SubcategoriaDAO subcategoriaDAO = new SubcategoriaDAO();
	private UnidadeDAO unidadeDAO = new UnidadeDAO();
	private ClienteDAO clienteDAO = new ClienteDAO();
	private MarcaDAO marcaDAO = new MarcaDAO();
	private ProdutoDAO produtoDAO = new ProdutoDAO();

	private PanelActions<Produto> panelActions;

	public FrmProduto(PermissaoPrograma permissaoPrograma, Long codigoUsuario) {
		super(permissaoPrograma, codigoUsuario);
		setTitle("CADASTRO DE PRODUTOS");

		setSize(650, 420);
		setClosable(Boolean.TRUE);

		painelContent = new JPanel();

		lbCodigo = new JLabel("Código:");
		lbDescricao = new JLabel("Descrição:");
		txCodigo = new JNumericField();
		txDescricao = new JTextFieldMaiusculo();
		lbDepartamento = new JLabel("Departamento");
		lbUnidade = new JLabel("Unidade:");
		cbDepartamento = new JComboBox<>();
		cbUnidade = new JComboBox<>();
		lbCategoria = new JLabel("Categoria:");
		panel = new JPanel();
		lbSubcategoria = new JLabel("Sub-Categoria:");
		lbFornecedor = new JLabel("Fornecedor:");
		lbMarca = new JLabel("Marca:");
		lbMinimo = new JLabel("Mínimo:");
		txMinimo = new JMoneyField();
		lbMaximo = new JLabel("Máximo:");
		txMaximo = new JMoneyField();
		lbTipo = new JLabel("Tipo:");
		lbStatus = new JLabel("Status:");
		cbTipo = new JComboBox();
		cbStatus = new JComboBox();
		chQuantidadeFracionada = new JCheckBox("Quantidade Fracionada");
		chMovimentaEstoque = new JCheckBox("Movimenta Estoque");

		cpCategoria = new CampoPesquisa<Categoria>(categoriaDAO, PES_CATEGORIAS, codigoUsuario) {

			private static final long serialVersionUID = 1L;

			@Override
			public void carregarCampo(JTextField campoPesquisa, Categoria objeto) {
				campoPesquisa.setText(String.format("%s - %s", objeto.getIdCategoria(), objeto.getDescricao()));
			}

			@Override
			public Boolean validar() {

				if (cbDepartamento.getSelectedIndex() < 0) {

					JOptionPane.showMessageDialog(null, "SELECIONE UM DEPARTAMENTO");

					return Boolean.FALSE;
				}

				return Boolean.TRUE;
			}

		};

		cpSubCategoria = new CampoPesquisa<Subcategoria>(subcategoriaDAO, PES_SUBCATEGORIAS, codigoUsuario) {

			private static final long serialVersionUID = 1L;

			@Override
			public void carregarCampo(JTextField campoPesquisa, Subcategoria objeto) {
				campoPesquisa.setText(String.format("%s - %s", objeto.getIdSubcategoria(), objeto.getDescricao()));
			}

			@Override
			public Boolean validar() {

				if (cpCategoria.getObjetoPesquisado() == null) {

					JOptionPane.showMessageDialog(null, "SELECIONE UMA CATEGORIA");

					return Boolean.FALSE;
				}

				return Boolean.TRUE;
			}

		};

		cpFornecedor = new CampoPesquisa<Cliente>(clienteDAO, PES_CLIENTES, codigoUsuario) {

			private static final long serialVersionUID = 1L;

			@Override
			public void carregarCampo(JTextField campoPesquisa, Cliente objeto) {
				campoPesquisa.setText(String.format("%s - %s", objeto.getIdCliente(), objeto.getNome()));
			}

		};

		cpMarca = new CampoPesquisa<Marca>(marcaDAO, PES_MARCAS, codigoUsuario) {

			private static final long serialVersionUID = 1L;

			@Override
			public void carregarCampo(JTextField campoPesquisa, Marca objeto) {
				campoPesquisa.setText(String.format("%s - %s", objeto.getIdMarca(), objeto.getDescricao()));
			}

		};

		departamentoDAO.listar().forEach(cbDepartamento::addItem);
		unidadeDAO.listar().forEach(cbUnidade::addItem);

		painelContent.setLayout(new MigLayout("", "[grow][200px:n:200px]", "[][][][][][][][][][][][][][][][][grow]"));
		panel.setLayout(new MigLayout("", "[][grow]", "[grow][][][][][grow]"));
		panel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Estoque",
				TitledBorder.CENTER, TitledBorder.TOP, null, null));

		getContentPane().add(painelContent);

		painelContent.add(lbCodigo, "cell 0 0");
		painelContent.add(txCodigo, "cell 0 1,width 50:100:100");
		painelContent.add(lbDescricao, "cell 0 2");
		painelContent.add(txDescricao, "cell 0 3 2 1,growx");
		painelContent.add(lbDepartamento, "cell 0 4");
		painelContent.add(lbUnidade, "cell 1 4");
		painelContent.add(cbDepartamento, "cell 0 5,growx");
		painelContent.add(cbUnidade, "cell 1 5,growx");
		painelContent.add(lbCategoria, "cell 0 6");
		painelContent.add(cpCategoria, "cell 0 7,growx");
		painelContent.add(lbSubcategoria, "cell 0 8");
		painelContent.add(cpSubCategoria, "cell 0 9,growx");
		painelContent.add(lbFornecedor, "cell 0 10");
		painelContent.add(cpFornecedor, "cell 0 11,growx,aligny top");
		painelContent.add(lbMarca, "cell 0 12");
		painelContent.add(cpMarca, "cell 0 13,growx");
		painelContent.add(panel, "cell 1 6 1 8,grow");
		painelContent.add(lbTipo, "cell 0 14");
		painelContent.add(lbStatus, "cell 1 14");
		painelContent.add(cbTipo, "cell 0 15,growx");
		painelContent.add(cbStatus, "cell 1 15,growx");

		panel.add(lbMinimo, "cell 0 1,alignx trailing");
		panel.add(txMinimo, "cell 1 1,growx");
		panel.add(lbMaximo, "cell 0 2,alignx trailing");
		panel.add(txMaximo, "cell 1 2,growx");
		panel.add(chQuantidadeFracionada, "cell 0 3 2 1,aligny baseline");
		panel.add(chMovimentaEstoque, "cell 0 4 2 1");

		panelActions = new PanelActions<Produto>(this, Produto::getIdProduto, produtoDAO, PES_PRODUTOS) {

			private static final long serialVersionUID = 1L;

			@Override
			public void carregarObjeto(Produto objeto) {
			}

			@Override
			public void preencherObjeto(Produto objetoPesquisa) {
			}

			@Override
			public Boolean objetoValido() {

				return Boolean.TRUE;
			}
		};

		panelActions.addEventListener(new PanelEventAdapter<Produto>() {

			@Override
			public void saveEvent(Produto produto) {
				txCodigo.setValue(produto.getIdProduto());
			}
		});

		painelContent.add(panelActions, "cell 0 16 2 1,grow");
	}

}
