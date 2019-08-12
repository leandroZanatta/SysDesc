package br.com.sysdesc.ui;

import java.text.ParseException;

import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;

import com.toedter.calendar.JDateChooser;

import br.com.sysdesc.components.AbstractInternalFrame;
import br.com.sysdesc.pesquisa.components.PanelActions;
import br.com.sysdesc.pesquisa.enumeradores.PesquisaEnum;
import br.com.sysdesc.repository.model.Cidade;
import br.com.sysdesc.repository.model.Cliente;
import br.com.sysdesc.repository.model.Estado;
import br.com.sysdesc.repository.model.PermissaoPrograma;
import br.com.sysdesc.service.cidade.CidadeService;
import br.com.sysdesc.service.cliente.ClienteService;
import br.com.sysdesc.service.estado.EstadoService;
import net.miginfocom.swing.MigLayout;

public class FrmCliente extends AbstractInternalFrame {
	private JTextField txCodigo;
	private JTextField textField_2;
	private JTextField txIncricaoEstadual;
	private JTextField txEndereco;
	private JTextField txBairro;
	private JFormattedTextField txCelular;
	private JTextField textField_1;
	private JTextField txNumero;
	private JDateChooser txDataDeNascimento;
	private JFormattedTextField txCep;
	private JFormattedTextField txCgc;
	private ClienteService clienteService = new ClienteService();
	private EstadoService estadoService = new EstadoService();
	private CidadeService cidadeService = new CidadeService();
	private PanelActions<Cliente> painelDeBotoes;
	private JComboBox<Estado> cbEstado;
	private JComboBox<Cidade> cbCidade;
	private ButtonGroup buttonGroup;
	private JLabel lblCpfcnpj;
	private JLabel lblRazoSocial;
	private JLabel lblDataNascimento;
	private JLabel lblInscrioEstadual;
	private JLabel lblEstadoCivil;
	private JLabel lblSexo;
	private JLabel lblCdigo;
	private JComboBox cbEstadoCivil;
	private JComboBox cbSexo;
	private MaskFormatter mascaraCgc;

	public FrmCliente(PermissaoPrograma permissaoPrograma, Long codigoUsuario) throws ParseException {
		super(permissaoPrograma, codigoUsuario);

		setSize(600, 440);
		setClosable(Boolean.TRUE);
		getContentPane().setLayout(new MigLayout("", "[grow][grow][grow][grow][grow][grow][grow]",
				"[][][][][][][][][][][][][][][][][grow]"));

		lblCdigo = new JLabel("Código:");
		getContentPane().add(lblCdigo, "cell 0 0");

		lblCpfcnpj = new JLabel("CPF/CNPJ:");
		getContentPane().add(lblCpfcnpj, "cell 5 0 2 1");

		txCodigo = new JTextField();
		getContentPane().add(txCodigo, "cell 0 1,growx");
		txCodigo.setColumns(10);

		JRadioButton rdbtnFisca = new JRadioButton("Pessoa Fisíca");
		getContentPane().add(rdbtnFisca, "flowx,cell 1 1 2 1,alignx right");

		JRadioButton rdbtnJurdica = new JRadioButton("Pessoa Jurídica");
		getContentPane().add(rdbtnJurdica, "cell 3 1 2 1");

		buttonGroup = new ButtonGroup();
		buttonGroup.add(rdbtnFisca);
		buttonGroup.add(rdbtnJurdica);
		rdbtnFisca.addActionListener((e) -> selecionouPessoaFisica());
		rdbtnJurdica.addActionListener((e) -> selecionouPessoaJuridica());

		MaskFormatter mascaraCgc = new MaskFormatter("###.###.###-##");
		mascaraCgc.setPlaceholderCharacter('_');
		txCgc = new JFormattedTextField(mascaraCgc);
		getContentPane().add(txCgc, "cell 5 1 2 1,growx");
		txCgc.setColumns(10);

		lblRazoSocial = new JLabel("Razão Social:");
		getContentPane().add(lblRazoSocial, "cell 0 2");

		textField_2 = new JTextField();
		getContentPane().add(textField_2, "cell 0 3 7 1,growx");
		textField_2.setColumns(10);

		lblInscrioEstadual = new JLabel("Inscrição Estadual:");
		getContentPane().add(lblInscrioEstadual, "cell 0 4");

		lblDataNascimento = new JLabel("Data Nascimento:");
		getContentPane().add(lblDataNascimento, "cell 5 4");

		txIncricaoEstadual = new JTextField();
		getContentPane().add(txIncricaoEstadual, "flowy,cell 0 5 5 1,growx");
		txIncricaoEstadual.setColumns(10);

		txDataDeNascimento = new JDateChooser("dd/MM/yyyy", "##/##/#####", '_');
		getContentPane().add(txDataDeNascimento, "cell 5 5 2 1,growx");

		JLabel lblEstado = new JLabel("Estado:");
		getContentPane().add(lblEstado, "cell 0 6");

		JLabel lblCidade = new JLabel("Cidade:");
		getContentPane().add(lblCidade, "cell 4 6");

		cbEstado = new JComboBox<Estado>();
		estadoService.listarEstados().forEach(cbEstado::addItem);
		getContentPane().add(cbEstado, "cell 0 7 4 1,growx");
		cbEstado.addActionListener((e) -> carregarCidades());
		cbCidade = new JComboBox<>();
		getContentPane().add(cbCidade, "cell 4 7 3 1,growx");

		JLabel lblEndereo = new JLabel("Endereço:");
		getContentPane().add(lblEndereo, "cell 0 8");

		JLabel lblNmero = new JLabel("Número:");
		getContentPane().add(lblNmero, "cell 6 8");

		txEndereco = new JTextField();
		getContentPane().add(txEndereco, "cell 0 9 6 1,growx");
		txEndereco.setColumns(10);

		txNumero = new JTextField();
		getContentPane().add(txNumero, "cell 6 9,growx");
		txNumero.setColumns(10);

		JLabel lblNewLabel = new JLabel("Bairro:");
		getContentPane().add(lblNewLabel, "cell 0 10");

		JLabel lblCep = new JLabel("Cep:");
		getContentPane().add(lblCep, "cell 5 10");

		txBairro = new JTextField();
		getContentPane().add(txBairro, "cell 0 11 5 1,growx");
		txBairro.setColumns(10);

		MaskFormatter mascaraCep = new MaskFormatter("#####-###");
		mascaraCep.setPlaceholderCharacter('_');
		txCep = new JFormattedTextField(mascaraCep);
		getContentPane().add(txCep, "cell 5 11 2 1,growx");
		txCep.setColumns(10);

		JLabel lbCelular = new JLabel("Celular:");
		getContentPane().add(lbCelular, "cell 0 12");

		JLabel lblEmail = new JLabel("Email:");
		getContentPane().add(lblEmail, "cell 3 12");

		MaskFormatter mascaraCelular = new MaskFormatter("(##) #####-####");
		mascaraCelular.setPlaceholderCharacter('_');
		txCelular = new JFormattedTextField(mascaraCelular);
		getContentPane().add(txCelular, "cell 0 13 3 1,growx");
		txCelular.setColumns(10);

		textField_1 = new JTextField();
		getContentPane().add(textField_1, "cell 3 13 4 1,growx");
		textField_1.setColumns(10);

		lblEstadoCivil = new JLabel("Estado Civil:");
		getContentPane().add(lblEstadoCivil, "cell 0 14");

		lblSexo = new JLabel("Sexo:");
		getContentPane().add(lblSexo, "cell 2 14");

		JLabel lblSituacao = new JLabel("Situação:");
		getContentPane().add(lblSituacao, "cell 5 14");

		cbEstadoCivil = new JComboBox();
		getContentPane().add(cbEstadoCivil, "cell 0 15 2 1,growx");

		cbSexo = new JComboBox();
		getContentPane().add(cbSexo, "cell 2 15 3 1,growx");

		JComboBox cbSituacao = new JComboBox();
		getContentPane().add(cbSituacao, "cell 5 15 2 1,growx");
		painelDeBotoes = new PanelActions<Cliente>(this, clienteService, PesquisaEnum.PES_CLIENTES) {

			@Override
			public void carregarObjeto(Cliente objeto) {
				// TODO Auto-generated method stub

			}

			@Override
			public void preencherObjeto(Cliente objetoPesquisa) {
				// TODO Auto-generated method stub

			}
		};
		getContentPane().add(painelDeBotoes, "cell 0 16 7 1,grow");
	}

	private void selecionouPessoaJuridica() {
		System.out.println("a");
		lblCpfcnpj.setText("CNPJ:");
		lblRazoSocial.setText("Razão Social:");
		lblDataNascimento.setText("Data de Fundação:");
		lblInscrioEstadual.setText("Inscrição Estadual:");
		lblEstadoCivil.setEnabled(false);
		lblSexo.setEnabled(false);
		cbEstadoCivil.setEnabled(false);
		cbSexo.setEnabled(false);
		try {
			mascaraCgc.setMask("##.###.###/####-##");

		} catch (ParseException e) {

		}
	}

	private void selecionouPessoaFisica() {
		System.out.println("b");
		lblCpfcnpj.setText("CPF:");
		lblRazoSocial.setText("Nome:");
		lblDataNascimento.setText("Data de Nascimento:");
		lblInscrioEstadual.setText("RG:");
		lblEstadoCivil.setEnabled(true);
		lblSexo.setEnabled(true);
		cbEstadoCivil.setEnabled(true);
		cbSexo.setEnabled(true);

		try {
			mascaraCgc.setMask("###.###.###-##");

		} catch (ParseException e) {

		}
	}

	private void carregarCidades() {
		cbCidade.removeAllItems();

		if (cbEstado.getSelectedIndex() >= 0) {
			cidadeService.buscarCidadesPorEstado(((Estado) cbEstado.getSelectedItem()).getIdEstado())
					.forEach(cbCidade::addItem);
		}

	}

}
