package br.com.sysdesc.ui;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import br.com.sysdesc.components.AbstractInternalFrame;
import br.com.sysdesc.enumerator.BancoEnum;
import br.com.sysdesc.enumerator.TipoContaEnum;
import br.com.sysdesc.enumerator.TipoStatusEnum;
import br.com.sysdesc.pesquisa.components.CampoPesquisa;
import br.com.sysdesc.pesquisa.components.JTextFieldId;
import br.com.sysdesc.pesquisa.components.PanelActions;
import br.com.sysdesc.pesquisa.enumeradores.PesquisaEnum;
import br.com.sysdesc.repository.model.Cliente;
import br.com.sysdesc.repository.model.Fornecedor;
import br.com.sysdesc.repository.model.PermissaoPrograma;
import br.com.sysdesc.repository.model.PlanoContas;
import br.com.sysdesc.service.cliente.ClienteService;
import br.com.sysdesc.service.fornecedor.FornecedorService;
import br.com.sysdesc.service.planocontas.PlanoContasService;
import net.miginfocom.swing.MigLayout;

public class FrmFornecedor extends AbstractInternalFrame {

	private static final long serialVersionUID = 1L;

	private JLabel lblCodigo;
	private JLabel lblCliente;
	private JLabel lblObservacoes;
	private JLabel lblBanco;
	private JLabel lblContaContbil;
	private JLabel lblStatus;
	private JLabel lblAgencia;
	private JLabel lblConta;
	private JLabel lblTipoDeConta;

	private JTextFieldId txCodigo;
	private CampoPesquisa<Cliente> txCliente;
	private JTextField txAgencia;
	private JTextField txConta;
	private CampoPesquisa<PlanoContas> txContaContabil;

	private JPanel pnlDadosBancarios;
	private JPanel painelContent;
	private PanelActions<Fornecedor> panelActions;

	private JComboBox<BancoEnum> cbBanco;
	private JComboBox<TipoContaEnum> cbTipoConta;
	private JComboBox<TipoStatusEnum> cbStatus;

	private JTextArea taObservacoes;

	private ClienteService cliente = new ClienteService();

	private FornecedorService fornecedorService = new FornecedorService();

	private PlanoContasService planoContasService = new PlanoContasService();

	public FrmFornecedor(PermissaoPrograma permissaoPrograma, Long codigoUsuario, Long codigoEmpresa) {

		super(permissaoPrograma, codigoUsuario, codigoEmpresa);

		initComponents();
	}

	private void initComponents() {
		setSize(597, 371);
		setClosable(Boolean.TRUE);
		setTitle("Cadastro de Fornecedores");

		lblCodigo = new JLabel("Código:");
		lblCliente = new JLabel("Cliente:");
		lblBanco = new JLabel("Banco:");
		lblAgencia = new JLabel("Agência:");
		lblConta = new JLabel("Conta:");
		lblTipoDeConta = new JLabel("Tipo de Conta:");
		lblContaContbil = new JLabel("Conta Contábil:");
		lblStatus = new JLabel("Státus:");
		lblObservacoes = new JLabel("Observações:");

		txCodigo = new JTextFieldId();
		txCliente = new CampoPesquisa<Cliente>(cliente, PesquisaEnum.PES_CLIENTES, getCodigoUsuario()) {

			private static final long serialVersionUID = 1L;

			@Override
			public String formatarValorCampo(Cliente objeto) {

				return String.format("%d - %s", objeto.getIdCliente(), objeto.getNome());
			}
		};
		txAgencia = new JTextField();
		txConta = new JTextField();
		txContaContabil = new CampoPesquisa<PlanoContas>(planoContasService, PesquisaEnum.PES_PLANOCONTAS,
				getCodigoUsuario()) {

			private static final long serialVersionUID = 1L;

			@Override
			public String formatarValorCampo(PlanoContas objeto) {

				return String.format("%s - %s", objeto.getIdentificador(), objeto.getDescricao());
			}
		};

		painelContent = new JPanel();
		pnlDadosBancarios = new JPanel();

		cbBanco = new JComboBox<>(BancoEnum.values());
		cbTipoConta = new JComboBox<>(TipoContaEnum.values());
		cbStatus = new JComboBox<>(TipoStatusEnum.values());

		taObservacoes = new JTextArea();

		pnlDadosBancarios.setLayout(new MigLayout("", "[grow][grow][grow][grow]", "[][]"));
		painelContent.setLayout(new MigLayout("", "[436.00,grow][150px:n:150px]", "[][][][][][][][][grow][grow]"));

		pnlDadosBancarios.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null),
				"Dados Banc\u00E1rios", TitledBorder.CENTER, TitledBorder.TOP, null, null));

		painelContent.add(lblCodigo, "cell 0 0");
		painelContent.add(lblCliente, "cell 0 2");

		pnlDadosBancarios.add(lblBanco, "cell 0 0");
		pnlDadosBancarios.add(lblAgencia, "cell 1 0");
		pnlDadosBancarios.add(lblConta, "cell 2 0");
		pnlDadosBancarios.add(lblTipoDeConta, "cell 3 0");
		painelContent.add(lblObservacoes, "cell 0 7");
		painelContent.add(lblContaContbil, "cell 0 5");
		painelContent.add(lblStatus, "cell 1 5");

		painelContent.add(txCodigo, "cell 0 1,width 50:100:100");
		painelContent.add(txCliente, "cell 0 3 2 1,growx");
		pnlDadosBancarios.add(txAgencia, "cell 1 1,growx");
		pnlDadosBancarios.add(txConta, "cell 2 1,growx");
		painelContent.add(txContaContabil, "cell 0 6,growx");
		painelContent.add(pnlDadosBancarios, "cell 0 4 2 1,grow");

		pnlDadosBancarios.add(cbBanco, "cell 0 1,growx");
		pnlDadosBancarios.add(cbTipoConta, "cell 3 1,growx");
		painelContent.add(cbStatus, "cell 1 6,growx");

		painelContent.add(taObservacoes, "cell 0 8 2 1,grow");

		getContentPane().add(painelContent);

		panelActions = new PanelActions<Fornecedor>(this, fornecedorService, PesquisaEnum.PES_FORNECEDORES) {

			private static final long serialVersionUID = 1L;

			@Override
			public void carregarObjeto(Fornecedor objeto) {

				txCodigo.setValue(objeto.getIdfornecedor());
				txCliente.setValue(objeto.getCliente());
				cbBanco.setSelectedItem(BancoEnum.findByCodigo(objeto.getNumeroBanco()));
				txAgencia.setText(objeto.getAgencia());
				txConta.setText(objeto.getConta());
				cbTipoConta.setSelectedItem(TipoContaEnum.findByCodigo(objeto.getTipoConta()));
				cbStatus.setSelectedItem(TipoStatusEnum.findByCodigo(objeto.getCodigoStatus()));

			}

			@Override
			public Boolean preencherObjeto(Fornecedor objetoPesquisa) {

				objetoPesquisa.setIdfornecedor(txCodigo.getValue());
				objetoPesquisa.setCliente(txCliente.getObjetoPesquisado());

				Long codigoStatus = null;
				Long numeroBanco = null;
				Long tipoConta = null;
				BancoEnum bancoEnum = (BancoEnum) cbBanco.getSelectedItem();
				TipoStatusEnum tipoStatusEnum = (TipoStatusEnum) cbStatus.getSelectedItem();
				TipoContaEnum tipoContaEnum = (TipoContaEnum) cbTipoConta.getSelectedItem();

				if (bancoEnum != null) {
					numeroBanco = bancoEnum.getCodigo();
				}

				if (tipoStatusEnum != null) {
					codigoStatus = tipoStatusEnum.getCodigo();
				}

				if (tipoContaEnum != null) {
					tipoConta = tipoContaEnum.getCodigo();
				}

				objetoPesquisa.setNumeroBanco(numeroBanco);
				objetoPesquisa.setCodigoStatus(codigoStatus);
				objetoPesquisa.setTipoConta(tipoConta);
				objetoPesquisa.setAgencia(txAgencia.getText());
				objetoPesquisa.setConta(txConta.getText());
				objetoPesquisa.setPlanoContas(txContaContabil.getObjetoPesquisado());
				return Boolean.TRUE;
			}

		};

		painelContent.add(panelActions, "cell 0 9 2 1,grow");
	}

}