package br.com.sysdesc.ui;

import static br.com.sysdesc.pesquisa.enumeradores.PesquisaEnum.PES_ENTRADAS;
import static br.com.sysdesc.util.resources.Resources.FRMDEPARTAMENTO_TITLE;
import static br.com.sysdesc.util.resources.Resources.translate;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.mysema.query.BooleanBuilder;
import com.toedter.calendar.JDateChooser;

import br.com.sysdesc.components.AbstractInternalFrame;
import br.com.sysdesc.components.ButtonColumn;
import br.com.sysdesc.components.JMoneyField;
import br.com.sysdesc.components.JNumericField;
import br.com.sysdesc.components.JmoneyFieldColumn;
import br.com.sysdesc.pesquisa.components.CampoPesquisa;
import br.com.sysdesc.pesquisa.components.PanelActions;
import br.com.sysdesc.pesquisa.enumeradores.PesquisaEnum;
import br.com.sysdesc.pesquisa.ui.FrmPesquisa;
import br.com.sysdesc.repository.model.EntradaCabecalho;
import br.com.sysdesc.repository.model.Fornecedor;
import br.com.sysdesc.repository.model.OperacaoEstoque;
import br.com.sysdesc.repository.model.PermissaoPrograma;
import br.com.sysdesc.repository.model.Produto;
import br.com.sysdesc.service.entradas.EntradasService;
import br.com.sysdesc.service.fornecedor.FornecedorService;
import br.com.sysdesc.service.operacaoestoque.OperacaoEstoqueService;
import br.com.sysdesc.service.produto.ProdutoService;
import br.com.sysdesc.tablemodels.EntradaMercadoriasTableModel;
import br.com.sysdesc.util.exception.SysDescException;
import br.com.sysdesc.util.resources.Resources;
import net.miginfocom.swing.MigLayout;

public class FrmEntradaNota extends AbstractInternalFrame {

	private static final long serialVersionUID = 1L;
	private JNumericField txCodigo;
	private JNumericField txNumeroNota;
	private JDateChooser dtEmissao;
	private JDateChooser dtSaida;
	private JMoneyField txFrete;
	private JMoneyField txValorProdutos;
	private JMoneyField txValorNota;
	private JTable tbProdutos;
	private PanelActions<EntradaCabecalho> panelActions;
	private EntradaMercadoriasTableModel entradaMercadoriasTableModel;
	private CampoPesquisa<Fornecedor> cpFornecedor;
	private CampoPesquisa<OperacaoEstoque> cpNaturezaOperacao;

	private JmoneyFieldColumn colunaQuantidade;
	private JmoneyFieldColumn colunaValorUnitario;
	private JmoneyFieldColumn colunaValorTotal;

	private FornecedorService fornecedorService = new FornecedorService();
	private ProdutoService produtoService = new ProdutoService();
	private EntradasService entradasService = new EntradasService();
	private OperacaoEstoqueService operacaoEstoqueService = new OperacaoEstoqueService();

	public FrmEntradaNota(PermissaoPrograma permissaoPrograma, Long codigoUsuario) {
		super(permissaoPrograma, codigoUsuario);

		initComponents();
	}

	private void initComponents() {

		setSize(800, 500);
		setClosable(Boolean.TRUE);
		setTitle(translate(FRMDEPARTAMENTO_TITLE));

		getContentPane().setLayout(new MigLayout("", "[grow][grow][grow][grow][grow]", "[][][][][][][][][grow][]"));

		JLabel lblCdigo = new JLabel(Resources.translate(Resources.FRMENTRADANOTA_LB_CODIGO));
		getContentPane().add(lblCdigo, "cell 0 0");

		txCodigo = new JNumericField();
		getContentPane().add(txCodigo, "cell 0 1,width 50:100:100");

		JLabel lblNewLabel = new JLabel(Resources.translate(Resources.FRMENTRADANOTA_LB_NATUREZA_OPERACAO));
		getContentPane().add(lblNewLabel, "cell 0 2");

		JLabel lblNmeroNota = new JLabel(Resources.translate(Resources.FRMENTRADANOTA_LB_NUMERO_NOTA));
		getContentPane().add(lblNmeroNota, "cell 4 2");

		cpNaturezaOperacao = new CampoPesquisa<OperacaoEstoque>(operacaoEstoqueService,
				PesquisaEnum.PES_OPERACOES_ESTOQUE, getCodigoUsuario()) {

			@Override
			public String formatarValorCampo(OperacaoEstoque objeto) {
				return String.format("%d - %s", objeto.getIdOperacoesEstoque(), objeto.getDescricao());
			}
		};
		getContentPane().add(cpNaturezaOperacao, "cell 0 3 4 1,growx");

		txNumeroNota = new JNumericField();
		getContentPane().add(txNumeroNota, "cell 4 3,growx");

		JLabel lblEmitente = new JLabel(Resources.translate(Resources.FRMENTRADANOTA_LB_EMITENTE));
		getContentPane().add(lblEmitente, "cell 0 4");

		cpFornecedor = new CampoPesquisa<Fornecedor>(fornecedorService, PesquisaEnum.PES_FORNECEDORES,
				getCodigoUsuario()) {

			private static final long serialVersionUID = 1L;

			@Override
			public String formatarValorCampo(Fornecedor objeto) {
				return String.format("%s - %s", objeto.getIdfornecedor(), objeto.getCliente().getNome());
			}

		};
		getContentPane().add(cpFornecedor, "cell 0 5 5 1,growx");

		JLabel lblDataEmisso = new JLabel(Resources.translate(Resources.FRMENTRADANOTA_LB_DATA_EMISSAO));
		getContentPane().add(lblDataEmisso, "cell 0 6");

		JLabel lblData = new JLabel(Resources.translate(Resources.FRMENTRADANOTA_LB_DATA_SAIDA));
		getContentPane().add(lblData, "cell 1 6");

		JLabel lblFrete = new JLabel("Frete:");
		getContentPane().add(lblFrete, "cell 2 6");

		JLabel lblValorProdutos = new JLabel("Valor Produtos:");
		getContentPane().add(lblValorProdutos, "cell 3 6");

		JLabel lblValorNota = new JLabel("Valor Nota:");
		getContentPane().add(lblValorNota, "cell 4 6");

		dtEmissao = new JDateChooser("dd/MM/yyyy", "##/##/####", '_');
		getContentPane().add(dtEmissao, "cell 0 7,growx");

		dtSaida = new JDateChooser("dd/MM/yyyy", "##/##/####", '_');
		getContentPane().add(dtSaida, "cell 1 7,growx");

		txFrete = new JMoneyField();
		getContentPane().add(txFrete, "cell 2 7,growx");

		txValorProdutos = new JMoneyField();
		getContentPane().add(txValorProdutos, "cell 3 7,growx");

		txValorNota = new JMoneyField();
		getContentPane().add(txValorNota, "cell 4 7,growx");

		JScrollPane scrollPane = new JScrollPane();
		getContentPane().add(scrollPane, "cell 0 8 5 1,grow");

		entradaMercadoriasTableModel = new EntradaMercadoriasTableModel();

		tbProdutos = new JTable(entradaMercadoriasTableModel);

		scrollPane.setViewportView(tbProdutos);

		panelActions = new PanelActions<EntradaCabecalho>(this, entradasService, PES_ENTRADAS) {

			private static final long serialVersionUID = 1L;

			@Override
			public void carregarObjeto(EntradaCabecalho objeto) {
			}

			@Override
			public Boolean preencherObjeto(EntradaCabecalho objetoPesquisa) {

				objetoPesquisa.setOperacaoEstoque(cpNaturezaOperacao.getObjetoPesquisado());
				objetoPesquisa.setNumeroNota(txNumeroNota.getValue());
				objetoPesquisa.setEmitente(cpFornecedor.getObjetoPesquisado());
				objetoPesquisa.setDataEmissao(dtEmissao.getDate());
				objetoPesquisa.setDataSaida(dtSaida.getDate());
				objetoPesquisa.setValorFrete(txFrete.getValue());
				objetoPesquisa.setValorProdutos(txValorProdutos.getValue());
				objetoPesquisa.setValorNota(txValorNota.getValue());

				objetoPesquisa.setEntradaDetalhes(entradaMercadoriasTableModel.getRows());

				objetoPesquisa.getEntradaDetalhes().forEach(detalhe -> detalhe.setEntradaCabecalho(objetoPesquisa));

				return Boolean.TRUE;
			}
		};

		ButtonColumn buttonColumn = new ButtonColumn(tbProdutos, 0);

		colunaQuantidade = new JmoneyFieldColumn(tbProdutos, 5, 0);
		colunaValorUnitario = new JmoneyFieldColumn(tbProdutos, 6, 4);
		colunaValorTotal = new JmoneyFieldColumn(tbProdutos, 7);

		buttonColumn.addButtonListener(this::selecionarProduto);

		tbProdutos.getColumnModel().getColumn(0).setPreferredWidth(30);
		tbProdutos.getColumnModel().getColumn(1).setPreferredWidth(60);
		tbProdutos.getColumnModel().getColumn(2).setPreferredWidth(100);
		tbProdutos.getColumnModel().getColumn(3).setPreferredWidth(250);
		tbProdutos.getColumnModel().getColumn(4).setPreferredWidth(50);
		tbProdutos.getColumnModel().getColumn(5).setPreferredWidth(80);
		tbProdutos.getColumnModel().getColumn(6).setPreferredWidth(80);
		tbProdutos.getColumnModel().getColumn(7).setPreferredWidth(80);

		panelActions.addSaveListener((objeto) -> txCodigo.setValue(objeto.getIdEntradaCabecalho()));

		getContentPane().add(panelActions, "cell 0 9 5 1,grow");
	}

	private void selecionarProduto(int selectedRow) {

		try {
			FrmPesquisa<Produto> pesquisaProduto = new FrmPesquisa<>(null, PesquisaEnum.PES_PRODUTOS,
					new BooleanBuilder(), produtoService, getCodigoUsuario());

			pesquisaProduto.setVisible(true);

			if (pesquisaProduto.getOk()) {
				entradaMercadoriasTableModel.getRow(selectedRow).setProduto(pesquisaProduto.getObjeto());

				entradaMercadoriasTableModel.addRow();
			}

		} catch (SysDescException e) {
			JOptionPane.showMessageDialog(null, e.getMensagem());
		}
	}

}
