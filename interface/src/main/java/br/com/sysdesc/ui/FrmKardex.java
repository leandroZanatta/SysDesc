package br.com.sysdesc.ui;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import org.apache.commons.lang.NotImplementedException;

import com.toedter.calendar.JDateChooser;

import br.com.sysdesc.components.AbstractInternalFrame;
import br.com.sysdesc.enumerator.OperacaoEnum;
import br.com.sysdesc.pesquisa.components.CampoPesquisa;
import br.com.sysdesc.pesquisa.enumeradores.PesquisaEnum;
import br.com.sysdesc.repository.model.Empresa;
import br.com.sysdesc.repository.model.Kardex;
import br.com.sysdesc.repository.model.PermissaoPrograma;
import br.com.sysdesc.repository.model.Produto;
import br.com.sysdesc.service.empresa.EmpresaService;
import br.com.sysdesc.service.kardex.KardexService;
import br.com.sysdesc.service.produto.ProdutoService;
import br.com.sysdesc.tablemodels.KardexTableModel;
import br.com.sysdesc.util.classes.DateUtil;
import br.com.sysdesc.util.vo.KardexVO;
import net.miginfocom.swing.MigLayout;

public class FrmKardex extends AbstractInternalFrame {

	private static final long serialVersionUID = 1L;

	private JPanel painelContent;
	private CampoPesquisa<Empresa> txEmpresa;
	private CampoPesquisa<Produto> txProduto;
	private JDateChooser txDatainicial;
	private JDateChooser txDatafinal;
	private JTable tbEstoque;
	private JLabel lbEmpresa;
	private JLabel lbProduto;
	private JLabel lbTipoDeEstoque;
	private JLabel lbDataInicial;
	private JLabel lbDataFinal;
	private JPanel panel;
	private JComboBox<OperacaoEnum> cbTipoestoque;
	private JButton btCancelar;
	private JButton btFiltrar;
	private JScrollPane scrollPane;

	private KardexTableModel kardexTableModel;

	private ProdutoService produtoService = new ProdutoService();
	private EmpresaService empresaService = new EmpresaService();
	private KardexService kardexService = new KardexService();

	public FrmKardex(PermissaoPrograma permissaoPrograma, Long codigoUsuario, Long codigoEmpresa) {
		super(permissaoPrograma, codigoUsuario, codigoEmpresa);

		initComponents();
	}

	private void initComponents() {

		setSize(600, 480);
		setClosable(Boolean.TRUE);
		setTitle("Consulta Kardex");

		painelContent = new JPanel();

		painelContent.setLayout(new MigLayout("", "[grow][grow][grow][]", "[][][][][][][][grow][]"));
		getContentPane().add(painelContent);

		lbEmpresa = new JLabel("Empresa:");
		lbProduto = new JLabel("Produto:");
		lbTipoDeEstoque = new JLabel("Tipo de estoque:");
		lbDataInicial = new JLabel("Data inicial:");
		lbDataFinal = new JLabel("Data final:");
		panel = new JPanel();

		txEmpresa = new CampoPesquisa<Empresa>(empresaService, PesquisaEnum.PES_EMPRESAS, getCodigoUsuario()) {

			private static final long serialVersionUID = 1L;

			@Override
			public String formatarValorCampo(Empresa objeto) {

				return String.format("%d - %s", objeto.getIdEmpresa(), objeto.getNomeFantasia());
			}
		};

		txEmpresa.setValueById(getCodigoEmpresa());

		txProduto = new CampoPesquisa<Produto>(produtoService, PesquisaEnum.PES_PRODUTOS, getCodigoUsuario()) {

			private static final long serialVersionUID = 1L;

			@Override
			public String formatarValorCampo(Produto objeto) {

				return String.format("%d - %s", objeto.getIdProduto(), objeto.getDescricao());
			}
		};
		txDatainicial = new JDateChooser("dd/MM/yyyy", "##/##/#####", '_');
		txDatafinal = new JDateChooser("dd/MM/yyyy", "##/##/#####", '_');

		Date dataAtual = new Date();

		txDatainicial.setDate(dataAtual);
		txDatafinal.setDate(dataAtual);

		kardexTableModel = new KardexTableModel();
		tbEstoque = new JTable(kardexTableModel);

		btCancelar = new JButton("Cancelar");
		btFiltrar = new JButton("Filtrar");

		scrollPane = new JScrollPane(tbEstoque);

		cbTipoestoque = new JComboBox<>(OperacaoEnum.values());

		btCancelar.addActionListener((e) -> dispose());
		btFiltrar.addActionListener((e) -> filtrarKardex());
		painelContent.add(lbEmpresa, "cell 0 0");
		painelContent.add(txEmpresa, "cell 0 1 4 1,growx");
		painelContent.add(lbProduto, "cell 0 2");
		painelContent.add(txProduto, "cell 0 3 4 1,growx");
		painelContent.add(lbTipoDeEstoque, "cell 0 4");
		painelContent.add(lbDataInicial, "cell 1 4");
		painelContent.add(lbDataFinal, "cell 2 4");
		painelContent.add(cbTipoestoque, "cell 0 5,growx");
		painelContent.add(txDatainicial, "cell 1 5,growx");
		painelContent.add(txDatafinal, "cell 2 5,growx");
		painelContent.add(btFiltrar, "cell 3 5");
		painelContent.add(scrollPane, "cell 0 6 4 1,grow");
		painelContent.add(panel, "cell 0 7 4 1,grow");

		panel.add(btCancelar);

	}

	private void filtrarKardex() {

		kardexTableModel.clear();

		List<KardexVO> kardexVOs = new ArrayList<>();

		Date dataInicial = DateUtil.getInitialDate(txDatainicial.getDate());

		BigDecimal valorTotal = BigDecimal.ZERO;

		KardexVO kardexVO = this.retornarEstoqueInicial(dataInicial, valorTotal, "T", BigDecimal.ZERO, BigDecimal.ZERO);

		kardexVOs.add(kardexVO);

		List<Kardex> kardexes = kardexService.buscarRegistroskardex(getCodigoEmpresa(),
				txProduto.getObjetoPesquisado().getIdProduto(),
				((OperacaoEnum) cbTipoestoque.getSelectedItem()).getCodigo(), txDatainicial.getDate(),
				txDatafinal.getDate());

		for (Kardex kardex : kardexes) {

			kardexVOs.add(retornarEstoqueInicial(kardex.getDataMovimento(), valorTotal, kardex.getFlagOperacao(),
					kardex.getQuantidade(), kardex.getValorTotal()));
		}

		kardexTableModel.setRows(kardexVOs);

	}

	private KardexVO retornarEstoqueInicial(Date dataInicial, BigDecimal saldoInicial, String operacao,
			BigDecimal quantidade, BigDecimal valorOperacao) {

		KardexVO kardexVO = new KardexVO();
		kardexVO.setDataMovimento(dataInicial);
		kardexVO.setQuantidade(quantidade);
		kardexVO.setValorOperacao(valorOperacao);

		kardexVO.setTipoOperacao(getTipoOperacao(operacao));
		saldoInicial = calcularSaldo(saldoInicial, valorOperacao, operacao);

		kardexVO.setSaldo(saldoInicial);

		return kardexVO;
	}

	private BigDecimal calcularSaldo(BigDecimal saldoInicial, BigDecimal valorOperacao, String operacao) {

		switch (operacao) {
		case "E":
		case "T":
			return saldoInicial.add(valorOperacao);
		case "S":
			return saldoInicial.subtract(valorOperacao);

		default:
			throw new NotImplementedException("Tipo de operação não suportado");
		}
	}

	private String getTipoOperacao(String operacao) {

		switch (operacao) {
		case "T":
			return "Estoque Inicial";
		case "E":
			return "Entrada";
		case "S":
			return "Saída";

		default:
			throw new NotImplementedException("Tipo de operação não suportado");
		}

	}

}
