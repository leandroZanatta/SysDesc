package br.com.sysdesc.ui;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.toedter.calendar.JDateChooser;

import br.com.sysdesc.components.AbstractInternalFrame;
import br.com.sysdesc.components.JTextFieldMaiusculo;
import br.com.sysdesc.components.listeners.ChangeListener;
import br.com.sysdesc.enumerator.TipoSaldoEnum;
import br.com.sysdesc.enumerator.TipoStatusEnum;
import br.com.sysdesc.pesquisa.components.CampoPesquisa;
import br.com.sysdesc.pesquisa.components.JTextFieldId;
import br.com.sysdesc.pesquisa.components.PanelActions;
import br.com.sysdesc.pesquisa.enumeradores.PesquisaEnum;
import br.com.sysdesc.repository.model.PermissaoPrograma;
import br.com.sysdesc.repository.model.PlanoContas;
import br.com.sysdesc.service.planocontas.PlanoContasService;
import net.miginfocom.swing.MigLayout;

public class FrmPlanoContas extends AbstractInternalFrame {

	private static final long serialVersionUID = 1L;

	private JLabel lbDescricao;
	private JLabel lbIdentificador;
	private JLabel lbContaPrincipal;
	private JLabel lbCodigo;
	private JLabel lbCadastro;
	private JLabel lbSituacao;
	private JLabel lbTipoSaldo;
	private JLabel lbManutencao;

	private JPanel painelContent;
	private PanelActions<PlanoContas> panelActions;

	private CampoPesquisa<PlanoContas> txContaPrincipal;
	private JTextField txIdentificador;
	private JTextFieldMaiusculo txDescricao;
	private JDateChooser txCadastro;
	private JDateChooser txManutencao;
	private JTextFieldId txCodigo;

	private JComboBox<TipoSaldoEnum> cbTipoSaldo;
	private JComboBox<TipoStatusEnum> cbSituacao;

	private JCheckBox chContaAnalitica;

	private PlanoContasService planoContasService = new PlanoContasService();

	public FrmPlanoContas(PermissaoPrograma permissaoPrograma, Long codigoUsuario) {
		super(permissaoPrograma, codigoUsuario);

		initComponents();
	}

	private void initComponents() {

		setSize(550, 270);
		setClosable(Boolean.TRUE);
		setTitle("Cadastro de plano de contas");

		lbCodigo = new JLabel("Código:");
		lbContaPrincipal = new JLabel("Conta Principal:");
		lbIdentificador = new JLabel("Identificador:");
		lbDescricao = new JLabel("Descrição:");
		lbTipoSaldo = new JLabel("Tipo de Saldo:");
		lbSituacao = new JLabel("Situação:");
		lbCadastro = new JLabel("Cadastro:");
		lbManutencao = new JLabel("Manutenção:");

		txCodigo = new JTextFieldId();

		txContaPrincipal = new CampoPesquisa<PlanoContas>(planoContasService, PesquisaEnum.PES_PLANOCONTAS,
				getCodigoUsuario(), planoContasService.getContasAnaliticas()) {

			private static final long serialVersionUID = 1L;

			@Override
			public String formatarValorCampo(PlanoContas objeto) {
				return String.format("%d - %s", objeto.getIdPlanoContas(), objeto.getDescricao());
			}

		};

		txContaPrincipal.addChangeListener(new ChangeListener<PlanoContas>() {

			@Override
			public void valueChanged(PlanoContas newValue, PlanoContas oldValue) {

				criarIdentificador(newValue);

				cbTipoSaldo.setSelectedItem(TipoSaldoEnum.findByCodigo(newValue.getSaldo()));
				cbTipoSaldo.setEnabled(false);
			}

		});

		txIdentificador = new JTextField();
		txDescricao = new JTextFieldMaiusculo();
		txCadastro = new JDateChooser("dd/MM/yyyy", "##/##/####", '_');
		txManutencao = new JDateChooser("dd/MM/yyyy", "##/##/####", '_');

		cbTipoSaldo = new JComboBox<>(TipoSaldoEnum.values());
		cbSituacao = new JComboBox<>(TipoStatusEnum.values());

		chContaAnalitica = new JCheckBox("Conta Analítica");

		painelContent = new JPanel();

		chContaAnalitica.addActionListener((e) -> changeTipoContaAnalitica());

		painelContent.setLayout(new MigLayout("", "[grow][grow][grow][]", "[][][][][][][][][grow]"));

		painelContent.add(lbCodigo, "cell 0 0");
		painelContent.add(txCodigo, "cell 0 1,width 50:100:100");
		painelContent.add(lbContaPrincipal, "cell 0 2");
		painelContent.add(txContaPrincipal, "cell 0 3 3 1,growx");
		painelContent.add(chContaAnalitica, "cell 3 3");
		painelContent.add(lbIdentificador, "cell 0 4");
		painelContent.add(lbDescricao, "cell 1 4");
		painelContent.add(txIdentificador, "cell 0 5,growx");
		painelContent.add(txDescricao, "cell 1 5 3 1,growx");
		painelContent.add(lbTipoSaldo, "cell 0 6");
		painelContent.add(lbSituacao, "cell 1 6");
		painelContent.add(lbCadastro, "cell 2 6");
		painelContent.add(lbManutencao, "cell 3 6");
		painelContent.add(cbTipoSaldo, "cell 0 7,growx");
		painelContent.add(cbSituacao, "cell 1 7,growx");
		painelContent.add(txCadastro, "cell 2 7,growx");
		painelContent.add(txManutencao, "cell 3 7,growx");

		getContentPane().add(painelContent);

		panelActions = new PanelActions<PlanoContas>(this, planoContasService, PesquisaEnum.PES_PLANOCONTAS) {

			private static final long serialVersionUID = 1L;

			@Override
			public void carregarObjeto(PlanoContas objeto) {

				txCodigo.setValue(objeto.getIdPlanoContas());
				txIdentificador.setText(objeto.getIdentificador());
				txDescricao.setText(objeto.getDescricao());
				cbTipoSaldo.setSelectedItem(TipoSaldoEnum.findByCodigo(objeto.getSaldo()));
				txContaPrincipal.setValue(objeto.getPlanoContas());
				cbSituacao.setSelectedItem(TipoStatusEnum.findByCodigo(objeto.getSituacao()));
				txCadastro.setDate(objeto.getCadastro());
				txManutencao.setDate(objeto.getManutencao());
				chContaAnalitica.setSelected(objeto.getContaAnalitica());
			}

			@Override
			public Boolean preencherObjeto(PlanoContas objetoPesquisa) {

				objetoPesquisa.setIdPlanoContas(txCodigo.getValue());
				objetoPesquisa.setIdentificador(txIdentificador.getText());
				objetoPesquisa.setDescricao(txDescricao.getText());
				objetoPesquisa.setPlanoContas(txContaPrincipal.getObjetoPesquisado());

				String codigoSaldo = null;
				Long codigoStatus = null;

				TipoSaldoEnum tipoSaldo = (TipoSaldoEnum) cbTipoSaldo.getSelectedItem();
				TipoStatusEnum tipoStatus = (TipoStatusEnum) cbSituacao.getSelectedItem();

				if (tipoSaldo != null) {
					codigoSaldo = tipoSaldo.getCodigo();
				}

				if (tipoStatus != null) {
					codigoStatus = tipoStatus.getCodigo();
				}

				objetoPesquisa.setSaldo(codigoSaldo);
				objetoPesquisa.setSituacao(codigoStatus);

				objetoPesquisa.setCadastro(txCadastro.getDate());
				objetoPesquisa.setManutencao(txManutencao.getDate());
				objetoPesquisa.setContaAnalitica(chContaAnalitica.isSelected());

				return Boolean.TRUE;
			}

		};
		panelActions.addSaveListener(planoContas -> {

			txCodigo.setValue(planoContas.getIdPlanoContas());
			txCadastro.setDate(planoContas.getCadastro());
			txManutencao.setDate(planoContas.getManutencao());

		});
		panelActions.addNewListener(() -> this.criarIdentificador(null));
		painelContent.add(panelActions, "cell 0 8 4 1,grow");

	}

	private void changeTipoContaAnalitica() {

		if (txContaPrincipal.getObjetoPesquisado() != null) {

			criarIdentificador(txContaPrincipal.getObjetoPesquisado());
		}
	}

	private void criarIdentificador(PlanoContas newValue) {

		Long codigoContaPrincipal = null;
		String codigoIdentificador = "";
		String separador = "";

		if (newValue != null) {
			codigoContaPrincipal = newValue.getIdPlanoContas();
			codigoIdentificador = newValue.getIdentificador();
			separador = ".";
		}

		StringBuilder stringBuilder = new StringBuilder(codigoIdentificador);
		stringBuilder.append(separador);

		String identificador = String.format(chContaAnalitica.isSelected() ? "%03d" : "%d",
				planoContasService.getNextIdentifier(codigoContaPrincipal));

		stringBuilder.append(identificador);

		txIdentificador.setText(stringBuilder.toString());
	}
}
