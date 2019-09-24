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
import br.com.sysdesc.pesquisa.components.JTextFieldId;
import br.com.sysdesc.repository.model.PermissaoPrograma;
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
	private JTextField txCliente;
	private JTextField txAgencia;
	private JTextField txConta;
	private JTextField txContaContabil;

	private JPanel pnlDadosBancarios;
	private JPanel painelContent;
	private JPanel panel_1;

	private JComboBox<BancoEnum> cbBanco;
	private JComboBox cbTipoConta;
	private JComboBox cbStatus;

	private JTextArea taObservacoes;

	public FrmFornecedor(PermissaoPrograma permissaoPrograma, Long codigoUsuario) {

		super(permissaoPrograma, codigoUsuario);

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
		txCliente = new JTextField();
		txAgencia = new JTextField();
		txConta = new JTextField();
		txContaContabil = new JTextField();

		painelContent = new JPanel();
		pnlDadosBancarios = new JPanel();

		cbBanco = new JComboBox<>(BancoEnum.values());
		cbTipoConta = new JComboBox();
		cbStatus = new JComboBox();

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

		panel_1 = new JPanel();
		painelContent.add(panel_1, "cell 0 9 2 1,grow");
	}

}