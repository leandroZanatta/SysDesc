package br.com.sysdesc.ui;

import static br.com.sysdesc.pesquisa.enumeradores.PesquisaEnum.PES_CATEGORIAS;
import static br.com.sysdesc.pesquisa.enumeradores.PesquisaEnum.PES_CLIENTES;
import static br.com.sysdesc.pesquisa.enumeradores.PesquisaEnum.PES_MARCAS;
import static br.com.sysdesc.pesquisa.enumeradores.PesquisaEnum.PES_PRODUTOS;
import static br.com.sysdesc.pesquisa.enumeradores.PesquisaEnum.PES_SUBCATEGORIAS;
import static br.com.sysdesc.util.constants.MensagemConstants.MENSAGEM_SELECIONE_CATEGORIA;
import static br.com.sysdesc.util.constants.MensagemConstants.MENSAGEM_SELECIONE_DEPARTAMENTO;
import static br.com.sysdesc.util.resources.Resources.FRMLOGIN_MSG_VERIFICACAO;
import static br.com.sysdesc.util.resources.Resources.FRMPRODUTO_LB_CATEGORIA;
import static br.com.sysdesc.util.resources.Resources.FRMPRODUTO_LB_CODIGO;
import static br.com.sysdesc.util.resources.Resources.FRMPRODUTO_LB_DEPARTAMENTO;
import static br.com.sysdesc.util.resources.Resources.FRMPRODUTO_LB_DESCRICAO;
import static br.com.sysdesc.util.resources.Resources.FRMPRODUTO_LB_FORNECEDOR;
import static br.com.sysdesc.util.resources.Resources.FRMPRODUTO_LB_MARCA;
import static br.com.sysdesc.util.resources.Resources.FRMPRODUTO_LB_MAXIMO;
import static br.com.sysdesc.util.resources.Resources.FRMPRODUTO_LB_MINIMO;
import static br.com.sysdesc.util.resources.Resources.FRMPRODUTO_LB_MOVIMENTAESTOQUE;
import static br.com.sysdesc.util.resources.Resources.FRMPRODUTO_LB_QUANTIDADEFRACIONADA;
import static br.com.sysdesc.util.resources.Resources.FRMPRODUTO_LB_STATUS;
import static br.com.sysdesc.util.resources.Resources.FRMPRODUTO_LB_SUBCATEGORIA;
import static br.com.sysdesc.util.resources.Resources.FRMPRODUTO_LB_TIPO;
import static br.com.sysdesc.util.resources.Resources.FRMPRODUTO_LB_UNIDADE;
import static br.com.sysdesc.util.resources.Resources.FRMPRODUTO_TITLE;
import static br.com.sysdesc.util.resources.Resources.translate;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import com.mysema.query.BooleanBuilder;

import br.com.sysdesc.components.AbstractInternalFrame;
import br.com.sysdesc.components.JMoneyField;
import br.com.sysdesc.components.JNumericField;
import br.com.sysdesc.components.JTextFieldMaiusculo;
import br.com.sysdesc.components.adapters.PanelEventAdapter;
import br.com.sysdesc.enumerator.TipoProdutoEnum;
import br.com.sysdesc.enumerator.TipoStatusEnum;
import br.com.sysdesc.pesquisa.components.CampoPesquisa;
import br.com.sysdesc.pesquisa.components.PanelActions;
import br.com.sysdesc.repository.model.Categoria;
import br.com.sysdesc.repository.model.Cliente;
import br.com.sysdesc.repository.model.Departamento;
import br.com.sysdesc.repository.model.Marca;
import br.com.sysdesc.repository.model.PermissaoPrograma;
import br.com.sysdesc.repository.model.Produto;
import br.com.sysdesc.repository.model.Subcategoria;
import br.com.sysdesc.repository.model.Unidade;
import br.com.sysdesc.service.categoria.CategoriaService;
import br.com.sysdesc.service.cliente.ClienteService;
import br.com.sysdesc.service.departamento.DepartamentoService;
import br.com.sysdesc.service.marca.MarcaService;
import br.com.sysdesc.service.produto.ProdutoService;
import br.com.sysdesc.service.subcategoria.SubcategoriaService;
import br.com.sysdesc.service.unidade.UnidadeService;
import br.com.sysdesc.util.classes.LongUtil;
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
	private JComboBox<TipoProdutoEnum> cbTipo;
	private JComboBox<TipoStatusEnum> cbStatus;

	private CampoPesquisa<Categoria> cpCategoria;
	private CampoPesquisa<Subcategoria> cpSubCategoria;
	private CampoPesquisa<Cliente> cpFornecedor;
	private CampoPesquisa<Marca> cpMarca;

	private JMoneyField txMinimo;
	private JMoneyField txMaximo;

	private DepartamentoService departamentoService = new DepartamentoService();
	private CategoriaService categoriaService = new CategoriaService();
	private SubcategoriaService subcategoriaService = new SubcategoriaService();
	private UnidadeService unidadeService = new UnidadeService();
	private ClienteService clienteService = new ClienteService();
	private MarcaService marcaService = new MarcaService();
	private ProdutoService produtoService = new ProdutoService();

	private PanelActions<Produto> panelActions;

	public FrmProduto(PermissaoPrograma permissaoPrograma, Long codigoUsuario) {
		super(permissaoPrograma, codigoUsuario);
		setTitle(translate(FRMPRODUTO_TITLE));

		setSize(650, 420);
		setClosable(Boolean.TRUE);

		painelContent = new JPanel();

		lbCodigo = new JLabel(translate(FRMPRODUTO_LB_CODIGO));
		lbDescricao = new JLabel(translate(FRMPRODUTO_LB_DESCRICAO));
		txCodigo = new JNumericField();
		txDescricao = new JTextFieldMaiusculo();
		lbDepartamento = new JLabel(translate(FRMPRODUTO_LB_DEPARTAMENTO));
		lbUnidade = new JLabel(translate(FRMPRODUTO_LB_UNIDADE));
		cbDepartamento = new JComboBox<>();
		cbUnidade = new JComboBox<>();
		lbCategoria = new JLabel(translate(FRMPRODUTO_LB_CATEGORIA));
		lbSubcategoria = new JLabel(translate(FRMPRODUTO_LB_SUBCATEGORIA));
		lbFornecedor = new JLabel(translate(FRMPRODUTO_LB_FORNECEDOR));
		lbMarca = new JLabel(translate(FRMPRODUTO_LB_MARCA));
		lbMinimo = new JLabel(translate(FRMPRODUTO_LB_MINIMO));
		txMinimo = new JMoneyField();
		lbMaximo = new JLabel(translate(FRMPRODUTO_LB_MAXIMO));
		txMaximo = new JMoneyField();
		lbTipo = new JLabel(translate(FRMPRODUTO_LB_TIPO));
		lbStatus = new JLabel(translate(FRMPRODUTO_LB_STATUS));
		cbTipo = new JComboBox<TipoProdutoEnum>(TipoProdutoEnum.values());
		cbStatus = new JComboBox<TipoStatusEnum>(TipoStatusEnum.values());
		chQuantidadeFracionada = new JCheckBox(translate(FRMPRODUTO_LB_QUANTIDADEFRACIONADA));
		chMovimentaEstoque = new JCheckBox(translate(FRMPRODUTO_LB_MOVIMENTAESTOQUE));

		cpCategoria = new CampoPesquisa<Categoria>(categoriaService, PES_CATEGORIAS, codigoUsuario) {

			private static final long serialVersionUID = 1L;

			@Override
			public String formatarValorCampo(Categoria objeto) {
				return String.format("%s - %s", objeto.getIdCategoria(), objeto.getDescricao());
			}

			@Override
			public Boolean validar() {

				if (cbDepartamento.getSelectedIndex() < 0) {

					JOptionPane.showMessageDialog(null, translate(MENSAGEM_SELECIONE_DEPARTAMENTO),
							translate(FRMLOGIN_MSG_VERIFICACAO), JOptionPane.WARNING_MESSAGE);

					return Boolean.FALSE;
				}

				return Boolean.TRUE;
			}

			@Override
			public BooleanBuilder getPreFilter() {

				Long codigoDepartamento = ((Departamento) cbDepartamento.getSelectedItem()).getIdDepartamento();

				return categoriaService.getFilterDepartamento(codigoDepartamento);
			}

		};

		cpSubCategoria = new CampoPesquisa<Subcategoria>(subcategoriaService, PES_SUBCATEGORIAS, codigoUsuario) {

			private static final long serialVersionUID = 1L;

			@Override
			public String formatarValorCampo(Subcategoria objeto) {
				return String.format("%s - %s", objeto.getIdSubcategoria(), objeto.getDescricao());
			}

			@Override
			public Boolean validar() {

				if (cpCategoria.getObjetoPesquisado() == null) {

					JOptionPane.showMessageDialog(null, translate(MENSAGEM_SELECIONE_CATEGORIA),
							translate(FRMLOGIN_MSG_VERIFICACAO), JOptionPane.WARNING_MESSAGE);

					return Boolean.FALSE;
				}

				return Boolean.TRUE;
			}

			@Override
			public BooleanBuilder getPreFilter() {

				Long codigoCategoria = cpCategoria.getObjetoPesquisado().getIdCategoria();

				return subcategoriaService.getFilterCategoria(codigoCategoria);
			}

		};

		cpFornecedor = new CampoPesquisa<Cliente>(clienteService, PES_CLIENTES, codigoUsuario) {

			private static final long serialVersionUID = 1L;

			@Override
			public String formatarValorCampo(Cliente objeto) {
				return String.format("%s - %s", objeto.getIdCliente(), objeto.getNome());
			}

		};

		cpMarca = new CampoPesquisa<Marca>(marcaService, PES_MARCAS, codigoUsuario) {

			private static final long serialVersionUID = 1L;

			@Override
			public String formatarValorCampo(Marca objeto) {
				return String.format("%s - %s", objeto.getIdMarca(), objeto.getDescricao());
			}

		};

		departamentoService.listarDepartamentos().forEach(cbDepartamento::addItem);
		unidadeService.listarUnidades().forEach(cbUnidade::addItem);

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

		panelActions = new PanelActions<Produto>(this, produtoService, PES_PRODUTOS) {

			private static final long serialVersionUID = 1L;

			@Override
			public void carregarObjeto(Produto objeto) {

				txCodigo.setValue(objeto.getIdProduto());
				txDescricao.setText(objeto.getDescricao());
				cbDepartamento.setSelectedItem(objeto.getSubcategoria().getCategoria().getDepartamento());
				cpCategoria.setValue(objeto.getSubcategoria().getCategoria());
				cpSubCategoria.setValue(objeto.getSubcategoria());
				cpMarca.setValue(objeto.getMarca());
				cpFornecedor.setValue(objeto.getFornecedor());

				chMovimentaEstoque.setSelected(objeto.getFlagMovimentaEstoque());
				chQuantidadeFracionada.setSelected(objeto.getFlagQuantidadeFracionada());
				txMaximo.setValue(objeto.getValorEstoqueMaximo());
				txMinimo.setValue(objeto.getValorEstoqueMinimo());

				if (objeto.getUnidade() != null) {
					cbUnidade.setSelectedItem(objeto.getUnidade());
				}

				if (!LongUtil.isNullOrZero(objeto.getCodigoStatus())) {
					cbStatus.setSelectedItem(TipoStatusEnum.findByCodigo(objeto.getCodigoStatus()));
				}

				if (!LongUtil.isNullOrZero(objeto.getCodigoTipo())) {
					cbTipo.setSelectedItem(TipoProdutoEnum.findByCodigo(objeto.getCodigoTipo()));
				}

			}

			@Override
			public void preencherObjeto(Produto objetoPesquisa) {

				objetoPesquisa.setIdProduto(txCodigo.getValue());
				objetoPesquisa.setDescricao(txDescricao.getText());
				objetoPesquisa.setSubcategoria(cpSubCategoria.getObjetoPesquisado());
				objetoPesquisa.setMarca(cpMarca.getObjetoPesquisado());
				objetoPesquisa.setFornecedor(cpFornecedor.getObjetoPesquisado());

				objetoPesquisa.setFlagMovimentaEstoque(chMovimentaEstoque.isSelected());
				objetoPesquisa.setFlagQuantidadeFracionada(chQuantidadeFracionada.isSelected());
				objetoPesquisa.setValorEstoqueMaximo(txMaximo.getValue());
				objetoPesquisa.setValorEstoqueMinimo(txMinimo.getValue());

				if (cbUnidade.getSelectedIndex() >= 0) {
					objetoPesquisa.setUnidade((Unidade) cbUnidade.getSelectedItem());
				}

				if (cbStatus.getSelectedIndex() >= 0) {
					objetoPesquisa.setCodigoStatus(((TipoStatusEnum) cbStatus.getSelectedItem()).getCodigo());
				}

				if (cbTipo.getSelectedIndex() >= 0) {
					objetoPesquisa.setCodigoTipo(((TipoProdutoEnum) cbTipo.getSelectedItem()).getCodigo());
				}
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
