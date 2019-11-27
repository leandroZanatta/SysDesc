package br.com.sysdesc.ui;

import java.awt.BorderLayout;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.toedter.calendar.JDateChooser;

import br.com.sysdesc.components.AbstractInternalFrame;
import br.com.sysdesc.components.JTextFieldMaiusculo;
import br.com.sysdesc.enumerator.OperacaoEnum;
import br.com.sysdesc.pesquisa.components.CampoPesquisa;
import br.com.sysdesc.pesquisa.components.JTextFieldId;
import br.com.sysdesc.pesquisa.components.PanelActions;
import br.com.sysdesc.pesquisa.enumeradores.PesquisaEnum;
import br.com.sysdesc.repository.model.OperacaoEstoque;
import br.com.sysdesc.repository.model.PermissaoPrograma;
import br.com.sysdesc.repository.model.PlanoContas;
import br.com.sysdesc.service.operacaoestoque.OperacaoEstoqueService;
import br.com.sysdesc.service.planocontas.PlanoContasService;
import net.miginfocom.swing.MigLayout;

public class FrmOperacaoEstoque extends AbstractInternalFrame {

	private static final long serialVersionUID = 1L;

	private JLabel lbCodigo;
	private JLabel lbDescricao;
	private JLabel lbPlanoDeContas;
	private JLabel lbOperacao;
	private JLabel lbManutencao;
	private JLabel lbCadastro;

	private JPanel panelContent;
	private PanelActions<OperacaoEstoque> panelActions;

	private JTextFieldMaiusculo txDescricao;
	private CampoPesquisa<PlanoContas> txPanoDeContas;
	private JDateChooser txCadastro;
	private JDateChooser txManutencao;
	private JComboBox<OperacaoEnum> cbOperacao;
	private JCheckBox chAtualizaCusto;
	private JTextFieldId txCodigo;

	private OperacaoEstoqueService operacaoEstoqueService = new OperacaoEstoqueService();
	private PlanoContasService planoContasService = new PlanoContasService();

	public FrmOperacaoEstoque(PermissaoPrograma permissaoPrograma, Long codigoUsuario, Long codigoEmpresa) {
		super(permissaoPrograma, codigoUsuario, codigoEmpresa);

		initComponents();

	}

	private void initComponents() {

		setSize(450, 270);
		setClosable(Boolean.TRUE);
		setTitle(" Operação De Estoque");

		lbCodigo = new JLabel("código:");
		lbDescricao = new JLabel("Descrição:");
		lbPlanoDeContas = new JLabel("Plano de contas:");
		lbOperacao = new JLabel("Operação:");
		lbCadastro = new JLabel("Cadastro:");
		lbManutencao = new JLabel("Matutenção:");

		txCodigo = new JTextFieldId();
		txDescricao = new JTextFieldMaiusculo();
		txPanoDeContas = new CampoPesquisa<PlanoContas>(planoContasService, PesquisaEnum.PES_PLANOCONTAS,
				getCodigoUsuario()) {

			private static final long serialVersionUID = 1L;

			@Override
			public String formatarValorCampo(PlanoContas objeto) {
				return String.format("%d - %s", objeto.getIdPlanoContas(), objeto.getDescricao());
			}

		};
		txManutencao = new JDateChooser("dd/MM/yyyy", "##/##/####", '_');
		txCadastro = new JDateChooser("dd/MM/yyyy", "##/##/####", '_');

		cbOperacao = new JComboBox<>(OperacaoEnum.values());

		panelContent = new JPanel();

		chAtualizaCusto = new JCheckBox("Atualiza custo");

		panelContent.setLayout(new MigLayout("", "[grow][][grow][grow]", "[][][][][][][][][grow]"));

		panelContent.add(lbCodigo, "cell 0 0");
		panelContent.add(txCodigo, "cell 0 1,width 50:100:100");
		panelContent.add(lbDescricao, "cell 0 2");
		panelContent.add(txDescricao, "cell 0 3 4 1,growx");
		panelContent.add(lbPlanoDeContas, "cell 0 4");
		panelContent.add(txPanoDeContas, "cell 0 5 4 1,growx");
		panelContent.add(lbOperacao, "cell 0 6");
		panelContent.add(lbCadastro, "cell 2 6");
		panelContent.add(lbManutencao, "cell 3 6");
		panelContent.add(cbOperacao, "cell 0 7,growx");
		panelContent.add(chAtualizaCusto, "cell 1 7");
		panelContent.add(txCadastro, "cell 2 7,growx");
		panelContent.add(txManutencao, "cell 3 7,growx");

		getContentPane().add(panelContent, BorderLayout.CENTER);

		panelActions = new PanelActions<OperacaoEstoque>(this, operacaoEstoqueService,
				PesquisaEnum.PES_OPERACOES_ESTOQUE) {

			private static final long serialVersionUID = 1L;

			@Override
			public void carregarObjeto(OperacaoEstoque objeto) {
				txCodigo.setValue(objeto.getIdOperacoesEstoque());
				txDescricao.setText(objeto.getDescricao());
				cbOperacao.setSelectedItem(objeto.getOperacao());
				txCadastro.setDate(objeto.getCadastro());
				txManutencao.setDate(objeto.getManutencao());
				chAtualizaCusto.setSelected(objeto.getAtualizacusto());
				txPanoDeContas.setValue(objeto.getPlanoContas());

			}

			@Override
			public Boolean preencherObjeto(OperacaoEstoque objetoPesquisa) {
				objetoPesquisa.setIdOperacoesEstoque(txCodigo.getValue());
				objetoPesquisa.setDescricao(txDescricao.getText());

				OperacaoEnum operacao = (OperacaoEnum) cbOperacao.getSelectedItem();
				objetoPesquisa.setOperacao(operacao.getCodigo());

				objetoPesquisa.setAtualizacusto(chAtualizaCusto.isSelected());
				objetoPesquisa.setPlanoContas(txPanoDeContas.getObjetoPesquisado());
				return Boolean.TRUE;
			}

		};

		panelContent.add(panelActions, "cell 0 8 4 1,grow");

	}

}
