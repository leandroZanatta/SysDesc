package br.com.sysdesc.ui;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.text.ParseException;
import java.util.Arrays;

import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;

import com.toedter.calendar.JDateChooser;

import br.com.sysdesc.components.AbstractInternalFrame;
import br.com.sysdesc.components.JNumericField;
import br.com.sysdesc.components.JTextFieldMaiusculo;
import br.com.sysdesc.enumerator.EstadoCivilEnum;
import br.com.sysdesc.enumerator.SexoEnum;
import br.com.sysdesc.enumerator.TipoStatusEnum;
import br.com.sysdesc.pesquisa.components.JTextFieldId;
import br.com.sysdesc.pesquisa.components.PanelActions;
import br.com.sysdesc.pesquisa.enumeradores.PesquisaEnum;
import br.com.sysdesc.repository.enumeradores.TipoClienteEnum;
import br.com.sysdesc.repository.model.Cidade;
import br.com.sysdesc.repository.model.Cliente;
import br.com.sysdesc.repository.model.Estado;
import br.com.sysdesc.repository.model.PermissaoPrograma;
import br.com.sysdesc.service.cidade.CidadeService;
import br.com.sysdesc.service.cliente.ClienteService;
import br.com.sysdesc.service.estado.EstadoService;
import br.com.sysdesc.util.classes.CNPJUtil;
import br.com.sysdesc.util.classes.CPFUtil;
import br.com.sysdesc.util.classes.IfNull;
import br.com.sysdesc.util.resources.Resources;
import net.miginfocom.swing.MigLayout;

public class FrmCliente extends AbstractInternalFrame {

	private static final long serialVersionUID = 1L;

	private JTextFieldId txCodigo;
	private JTextField txEmail;
	private JTextFieldMaiusculo txNome;
	private JTextFieldMaiusculo txIncricaoEstadual;
	private JTextFieldMaiusculo txEndereco;
	private JTextFieldMaiusculo txBairro;
	private JNumericField txNumero;
	private JFormattedTextField txCelular;
	private JFormattedTextField txCep;
	private JFormattedTextField txCgc;
	private JDateChooser txDataDeNascimento;
	private ClienteService clienteService = new ClienteService();
	private EstadoService estadoService = new EstadoService();
	private CidadeService cidadeService = new CidadeService();
	private PanelActions<Cliente> painelDeBotoes;
	private JComboBox<Estado> cbEstado;
	private JComboBox<Cidade> cbCidade;
	private JComboBox<EstadoCivilEnum> cbEstadoCivil;
	private JComboBox<TipoStatusEnum> cbSituacao;
	private JComboBox<SexoEnum> cbSexo;
	private JLabel lblCpfcnpj;
	private JLabel lblRazoSocial;
	private JLabel lblDataNascimento;
	private JLabel lblInscrioEstadual;
	private JLabel lblEstadoCivil;
	private JLabel lblSexo;
	private JLabel lblCdigo;
	private JLabel lblCidade;
	private JLabel lblEstado;
	private JLabel lblEndereo;
	private JLabel lblNmero;
	private JLabel lblNewLabel;
	private JLabel lblCep;
	private JLabel lbCelular;
	private JLabel lblEmail;
	private JLabel lblSituacao;
	private MaskFormatter mascaraCPF;
	private MaskFormatter mascaraCNPJ;
	private MaskFormatter mascaraCelular;
	private MaskFormatter mascaraCep;
	private ButtonGroup buttonGroup;
	private JRadioButton rdbtnFisca;
	private JRadioButton rdbtnJurdica;

	public FrmCliente(PermissaoPrograma permissaoPrograma, Long codigoUsuario) throws ParseException {

		super(permissaoPrograma, codigoUsuario);

		initComponents();
	}

	private void initComponents() throws ParseException {

		setTitle(Resources.translate(Resources.FRMCLIENTE_TITLE));
		setSize(600, 460);
		setClosable(Boolean.TRUE);
		getContentPane().setLayout(new MigLayout("", "[grow][grow][grow][grow][grow][grow][grow]",
				"[][][][][][][][][][][][][][][][][grow]"));

		mascaraCep = new MaskFormatter("#####-###");
		mascaraCNPJ = new MaskFormatter("##.###.###/####-##");
		mascaraCPF = new MaskFormatter("###.###.###-##");
		mascaraCelular = new MaskFormatter("(##) #####-####");

		lblCdigo = new JLabel(Resources.translate(Resources.FRMCLIENTE_LBL_CODIGO));
		lblCpfcnpj = new JLabel(Resources.translate(Resources.FRMCLIENTE_LBL_CPF_CNPJ));
		lblRazoSocial = new JLabel(Resources.translate(Resources.FRMCLIENTE_LBL_RAZAO_SOCIAL));
		lblInscrioEstadual = new JLabel(Resources.translate(Resources.FRMCLIENTE_LBL_INSCRICAO_ESTADUAL));
		lblDataNascimento = new JLabel(Resources.translate(Resources.FRMCLIENTE_LBL_DATA_DE_NASCIMENTO));
		lblEstado = new JLabel(Resources.translate(Resources.FRMCLIENTE_LBL_ESTADO));
		lblCidade = new JLabel(Resources.translate(Resources.FRMCLIENTE_LBL_CIDADE));
		lblEndereo = new JLabel(Resources.translate(Resources.FRMCLIENTE_LBL_ENDERECO));
		lblNmero = new JLabel(Resources.translate(Resources.FRMCLIENTE_LBL_NUMERO));
		lblNewLabel = new JLabel(Resources.translate(Resources.FRMCLIENTE_LBL_BAIRRO));
		lblCep = new JLabel(Resources.translate(Resources.FRMCLIENTE_LBL_CEP));
		lbCelular = new JLabel(Resources.translate(Resources.FRMCLIENTE_LBL_CELULAR));
		lblEmail = new JLabel(Resources.translate(Resources.FRMCLIENTE_LBL_EMAIL));
		lblSituacao = new JLabel(Resources.translate(Resources.FRMCLIENTE_LBL_SITUACAO));
		lblEstadoCivil = new JLabel(Resources.translate(Resources.FRMCLIENTE_LBL_ESTADO_CIVIL));
		lblSexo = new JLabel(Resources.translate(Resources.FRMCLIENTE_LBL_SEXO));
		rdbtnFisca = new JRadioButton(Resources.translate(Resources.FRMCLIENTE_RB_PESSOA_FISICA));
		rdbtnJurdica = new JRadioButton(Resources.translate(Resources.FRMCLIENTE_RB_PESSOA_JURIDICA));
		buttonGroup = new ButtonGroup();
		txDataDeNascimento = new JDateChooser("dd/MM/yyyy", "##/##/#####", '_');
		txCodigo = new JTextFieldId();
		txIncricaoEstadual = new JTextFieldMaiusculo();
		txCgc = new JFormattedTextField();
		txCep = new JFormattedTextField();
		txCelular = new JFormattedTextField();
		txNome = new JTextFieldMaiusculo();
		txEndereco = new JTextFieldMaiusculo();
		txNumero = new JNumericField();
		txBairro = new JTextFieldMaiusculo();
		cbEstado = new JComboBox<Estado>();
		cbEstadoCivil = new JComboBox<>();
		cbSexo = new JComboBox<>();
		cbSituacao = new JComboBox<>();
		cbCidade = new JComboBox<>();
		txEmail = new JTextField();
		estadoService.listarEstados().forEach(cbEstado::addItem);
		cbEstado.addActionListener((e) -> carregarCidades());

		getContentPane().add(lblCpfcnpj, "cell 5 0 2 1");
		getContentPane().add(lblCdigo, "cell 0 0");
		getContentPane().add(txCodigo, "cell 0 1,growx");
		getContentPane().add(rdbtnFisca, "flowx,cell 1 1 2 1,alignx right");
		getContentPane().add(rdbtnJurdica, "cell 3 1 2 1");
		getContentPane().add(txCgc, "cell 5 1 2 1,growx");
		getContentPane().add(lblRazoSocial, "cell 0 2");
		getContentPane().add(txNome, "cell 0 3 7 1,growx");
		getContentPane().add(lblInscrioEstadual, "cell 0 4");
		getContentPane().add(lblDataNascimento, "cell 5 4");
		getContentPane().add(txIncricaoEstadual, "flowy,cell 0 5 5 1,growx");
		getContentPane().add(txDataDeNascimento, "cell 5 5 2 1,growx");
		getContentPane().add(lblEstado, "cell 0 6");
		getContentPane().add(cbCidade, "cell 4 7 3 1,growx");
		getContentPane().add(lblCidade, "cell 4 6");
		getContentPane().add(cbEstado, "cell 0 7 4 1,growx");
		getContentPane().add(lblEndereo, "cell 0 8");
		getContentPane().add(lblNmero, "cell 6 8");
		getContentPane().add(txEndereco, "cell 0 9 6 1,growx");
		getContentPane().add(txNumero, "cell 6 9,growx");
		getContentPane().add(lblNewLabel, "cell 0 10");
		getContentPane().add(lblCep, "cell 5 10");
		getContentPane().add(txBairro, "cell 0 11 5 1,growx");
		getContentPane().add(txCep, "cell 5 11 2 1,growx");
		getContentPane().add(lbCelular, "cell 0 12");
		getContentPane().add(lblEmail, "cell 3 12");
		getContentPane().add(txCelular, "cell 0 13 3 1,growx");
		getContentPane().add(txEmail, "cell 3 13 4 1,growx");
		getContentPane().add(lblEstadoCivil, "cell 0 14");
		getContentPane().add(lblSexo, "cell 2 14");
		getContentPane().add(lblSituacao, "cell 5 14");
		getContentPane().add(cbEstadoCivil, "cell 0 15 2 1,growx");
		getContentPane().add(cbSexo, "cell 2 15 3 1,growx");

		buttonGroup.add(rdbtnFisca);
		buttonGroup.add(rdbtnJurdica);
		rdbtnFisca.addActionListener((e) -> selecionouPessoaFisica());
		rdbtnJurdica.addActionListener((e) -> selecionouPessoaJuridica());
		txCgc.addFocusListener(new FocusAdapter() {

			@Override
			public void focusLost(FocusEvent e) {

				Boolean documentoValido = Boolean.FALSE;

				String documento;

				if (rdbtnFisca.isSelected()) {
					documento = "CPF";
					documentoValido = CPFUtil.isCPFValido(txCgc.getText());
				} else {
					documento = "CNPJ";
					documentoValido = CNPJUtil.isCNPJValido(txCgc.getText());
				}

				if (!documentoValido) {
					JOptionPane.showMessageDialog(null, "O " + documento + " informado é inválido.");

					txCgc.setText("");

					return;
				}

				Cliente cliente = clienteService.buscarClientePorCpf(txCgc.getText(), txCodigo.getValue());

				if (cliente != null) {

					Integer confirmacao = JOptionPane.showConfirmDialog(null,
							"O " + documento + " informado já está cadastrado.\n Deseja Carrega-lo?", "Verificação",
							JOptionPane.YES_NO_OPTION);

					if (confirmacao == JOptionPane.YES_OPTION) {

						painelDeBotoes.carregarObjetoPesquisado(cliente);
					}
				}

			}
		});
		mascaraCelular.setPlaceholderCharacter('_');
		mascaraCNPJ.setPlaceholderCharacter('_');
		mascaraCep.setPlaceholderCharacter('_');
		mascaraCPF.setPlaceholderCharacter('_');

		Arrays.asList(TipoStatusEnum.values()).forEach(cbSituacao::addItem);
		Arrays.asList(SexoEnum.values()).forEach(cbSexo::addItem);
		Arrays.asList(EstadoCivilEnum.values()).forEach(cbEstadoCivil::addItem);

		getContentPane().add(cbSituacao, "cell 5 15 2 1,growx");

		painelDeBotoes = new PanelActions<Cliente>(this, clienteService, PesquisaEnum.PES_CLIENTES) {

			private static final long serialVersionUID = 1L;

			@Override
			public void carregarObjeto(Cliente objeto) {
				txCodigo.setValue(objeto.getIdCliente());

				if (objeto.getFlagTipoCliente().equals(TipoClienteEnum.PESSOA_FISICA.getCodigo())) {
					rdbtnFisca.setSelected(Boolean.TRUE);
					selecionouPessoaFisica();
				} else {
					rdbtnJurdica.setSelected(Boolean.TRUE);
					selecionouPessoaJuridica();
				}

				txCgc.setText(objeto.getCgc());
				txNome.setText(objeto.getNome());
				txIncricaoEstadual.setText(objeto.getRgie());
				txDataDeNascimento.setDate(objeto.getDatadenascimento());
				cbEstado.setSelectedItem(objeto.getCidade().getEstado());
				cbCidade.setSelectedItem(objeto.getCidade());
				txEndereco.setText(objeto.getEndereco());
				txNumero.setText(objeto.getNumero());
				txBairro.setText(objeto.getBairro());
				txCep.setText(objeto.getCep());
				txCelular.setText(objeto.getTelefone());
				txEmail.setText(objeto.getEmail());
				cbEstadoCivil.setSelectedItem(EstadoCivilEnum.findByCodigo(objeto.getEstadocivil()));
				cbSexo.setSelectedItem(SexoEnum.findByCodigo(objeto.getSexo()));
				cbSituacao.setSelectedItem(TipoStatusEnum.findByCodigo(objeto.getSituacao()));

			}

			@Override
			public Boolean preencherObjeto(Cliente objetoPesquisa) {

				objetoPesquisa.setCgc(txCgc.getText());
				objetoPesquisa.setNome(txNome.getText());
				objetoPesquisa.setDatadenascimento(txDataDeNascimento.getDate());
				objetoPesquisa.setRgie(IfNull.getStringEmpty(txIncricaoEstadual.getText()));
				objetoPesquisa.setEndereco(IfNull.getStringEmpty(txEndereco.getText()));
				objetoPesquisa.setNumero(IfNull.getStringEmpty(txNumero.getText()));
				objetoPesquisa.setBairro(IfNull.getStringEmpty(txBairro.getText()));
				objetoPesquisa.setEmail(IfNull.getStringEmpty(txEmail.getText()));
				objetoPesquisa.setCep(IfNull.getStringChar(txCep.getText(), "_"));
				objetoPesquisa.setTelefone(IfNull.getStringChar(txCelular.getText(), "_"));

				if (rdbtnFisca.isSelected()) {
					objetoPesquisa.setFlagTipoCliente(TipoClienteEnum.PESSOA_FISICA.getCodigo());
				} else if (rdbtnJurdica.isSelected()) {
					objetoPesquisa.setFlagTipoCliente(TipoClienteEnum.PESSOA_JURIDICA.getCodigo());
				}

				if (cbCidade.getSelectedIndex() >= 0) {
					objetoPesquisa.setCidade((Cidade) cbCidade.getSelectedItem());
				}

				if (cbEstadoCivil.getSelectedIndex() >= 0) {
					objetoPesquisa.setEstadocivil(((EstadoCivilEnum) cbEstadoCivil.getSelectedItem()).getCodigo());
				}

				if (cbSexo.getSelectedIndex() >= 0) {
					objetoPesquisa.setSexo(((SexoEnum) cbSexo.getSelectedItem()).getCodigo());
				}

				if (cbSituacao.getSelectedIndex() >= 0) {
					objetoPesquisa.setSituacao(((TipoStatusEnum) cbSituacao.getSelectedItem()).getCodigo());
				}

				return Boolean.TRUE;
			}
		};

		painelDeBotoes.addSaveListener((cliente) -> txCodigo.setValue(cliente.getIdCliente()));

		getContentPane().add(painelDeBotoes, "cell 0 16 7 1,grow");

		rdbtnFisca.setSelected(Boolean.TRUE);

		mascaraCep.install(txCep);
		mascaraCelular.install(txCelular);
		selecionouPessoaFisica();
	}

	private void selecionouPessoaJuridica() {

		lblCpfcnpj.setText("CNPJ:");
		lblRazoSocial.setText("Razão Social:");
		lblDataNascimento.setText("Data de Fundação:");
		lblInscrioEstadual.setText("Inscrição Estadual:");
		cbEstadoCivil.setEnabled(false);
		cbSexo.setEnabled(false);
		mascaraCNPJ.install(txCgc);

	}

	private void selecionouPessoaFisica() {
		lblCpfcnpj.setText("CPF:");
		lblRazoSocial.setText("Nome:");
		lblDataNascimento.setText("Data de Nascimento:");
		lblInscrioEstadual.setText("RG:");
		cbEstadoCivil.setEnabled(painelDeBotoes.isEditable() && true);
		cbSexo.setEnabled(painelDeBotoes.isEditable() && true);
		mascaraCPF.install(txCgc);

	}

	private void carregarCidades() {
		cbCidade.removeAllItems();

		if (cbEstado.getSelectedIndex() >= 0) {
			cidadeService.buscarCidadesPorEstado(((Estado) cbEstado.getSelectedItem()).getIdEstado())
					.forEach(cbCidade::addItem);
		}

	}

}
