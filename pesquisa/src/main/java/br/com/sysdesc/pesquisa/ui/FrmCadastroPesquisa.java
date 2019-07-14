package br.com.sysdesc.pesquisa.ui;

import static br.com.sysdesc.pesquisa.enumeradores.PesquisaEnum.PES_CATEGORIAS;
import static br.com.sysdesc.pesquisa.enumeradores.PesquisaEnum.forValue;
import static br.com.sysdesc.util.constants.MensagemConstants.MENSAGEM_SELECIONE_PESQUISA;
import static br.com.sysdesc.util.enumeradores.TipoPesquisaEnum.NORMAL;
import static br.com.sysdesc.util.resources.Resources.FRMLOGIN_MSG_VERIFICACAO;
import static br.com.sysdesc.util.resources.Resources.FRMPESQUISA_TITLE;
import static br.com.sysdesc.util.resources.Resources.translate;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import br.com.sysdesc.components.AbstractInternalFrame;
import br.com.sysdesc.components.JNumericField;
import br.com.sysdesc.components.JTextFieldMaiusculo;
import br.com.sysdesc.components.adapters.PanelEventAdapter;
import br.com.sysdesc.pesquisa.components.CampoPesquisaMultiSelect;
import br.com.sysdesc.pesquisa.components.PanelActions;
import br.com.sysdesc.pesquisa.enumeradores.PesquisaEnum;
import br.com.sysdesc.repository.model.Perfil;
import br.com.sysdesc.repository.model.PermissaoPrograma;
import br.com.sysdesc.repository.model.Pesquisa;
import br.com.sysdesc.repository.model.PesquisaCampo;
import br.com.sysdesc.repository.model.Usuario;
import br.com.sysdesc.service.login.LoginService;
import br.com.sysdesc.service.perfil.PerfilService;
import br.com.sysdesc.service.pesquisa.PesquisaBasicaService;
import br.com.sysdesc.util.classes.ImageUtil;
import net.miginfocom.swing.MigLayout;

public class FrmCadastroPesquisa extends AbstractInternalFrame {

	private static final long serialVersionUID = 1L;

	private JLabel lblCodigo;
	private JLabel lblPaginacao;
	private JLabel lblPesquisa;
	private JLabel lblDescricao;
	private JNumericField txCodigo;
	private JNumericField txPaginacao;
	private JComboBox<PesquisaEnum> cbPesquisa;
	private JPanel painelContent;
	private JTextFieldMaiusculo txDescricao;

	private PanelActions<Pesquisa> panelActions;
	private JLabel lblNewLabel;
	private CampoPesquisaMultiSelect<Usuario> pesquisaUsuario;
	private JLabel lblNewLabel_1;
	private CampoPesquisaMultiSelect<Perfil> pesquisaPerfis;
	private JPanel panel;
	private JPanel panel_1;
	private JScrollPane scrollPane;
	private JButton btAdd;
	private JButton btRemove;

	private PesquisaBasicaService pesquisaBasicaService = new PesquisaBasicaService();
	private LoginService loginService = new LoginService();
	private PerfilService perfilService = new PerfilService();

	public FrmCadastroPesquisa() {
		this(null, 1L);
	}

	public FrmCadastroPesquisa(PermissaoPrograma permissaoPrograma, Long codigoUsuario) {
		super(permissaoPrograma, codigoUsuario);

		setSize(800, 400);
		setClosable(Boolean.TRUE);
		setTitle(translate(FRMPESQUISA_TITLE));

		painelContent = new JPanel();

		lblCodigo = new JLabel("Código:");
		lblPaginacao = new JLabel("Paginacao:");
		lblDescricao = new JLabel("Descrição:");

		txCodigo = new JNumericField();
		txDescricao = new JTextFieldMaiusculo();
		txPaginacao = new JNumericField();

		panel_1 = new JPanel();
		btAdd = new JButton("");
		btRemove = new JButton("");
		panel = new JPanel();

		painelContent.setLayout(new MigLayout("", "[grow][grow][80.00]", "[][][][][][][grow][]"));

		painelContent.add(lblCodigo, "cell 0 0");
		painelContent.add(txCodigo, "cell 0 1, width 50:100:100");

		painelContent.add(lblDescricao, "cell 0 2");
		lblPesquisa = new JLabel("Pesquisa:");

		painelContent.add(lblPesquisa, "cell 1 2");
		painelContent.add(txDescricao, "cell 0 3,growx");

		painelContent.add(lblPaginacao, "cell 2 2");
		cbPesquisa = new JComboBox<PesquisaEnum>();
		painelContent.add(cbPesquisa, "cell 1 3,growx");
		painelContent.add(txPaginacao, "cell 2 3,growx");

		lblNewLabel_1 = new JLabel("Perfis:");
		painelContent.add(lblNewLabel_1, "cell 0 4");

		lblNewLabel = new JLabel("Usuário:");
		painelContent.add(lblNewLabel, "cell 1 4");

		Arrays.asList(PesquisaEnum.values()).forEach(cbPesquisa::addItem);

		pesquisaPerfis = new CampoPesquisaMultiSelect<Perfil>(perfilService, PesquisaEnum.PES_PERFIL, codigoUsuario) {

			private static final long serialVersionUID = 1L;

			@Override
			protected String formatarValorCampoMultiple(List<Perfil> objetosPesquisados) {

				return objetosPesquisados.stream().map(x -> x.getIdPerfil().toString())
						.collect(Collectors.joining(",", "<", ">"));
			}

			@Override
			protected String formatarValorCampoSingle(Perfil objeto) {

				return String.format("%s - %s", objeto.getIdPerfil(), objeto.getDescricao());
			}

		};

		painelContent.add(pesquisaPerfis, "cell 0 5,growx");

		pesquisaUsuario = new CampoPesquisaMultiSelect<Usuario>(loginService, PesquisaEnum.PES_USUARIOS,
				codigoUsuario) {

			private static final long serialVersionUID = 1L;

			@Override
			protected String formatarValorCampoMultiple(List<Usuario> objetosPesquisados) {

				return objetosPesquisados.stream().map(x -> x.getIdUsuario().toString())
						.collect(Collectors.joining(",", "<", ">"));
			}

			@Override
			protected String formatarValorCampoSingle(Usuario objeto) {
				return String.format("%s - %s", objeto.getIdUsuario(), objeto.getCliente().getNome());
			}

		};

		painelContent.add(pesquisaUsuario, "cell 1 5 2 1,growx");

		getContentPane().add(painelContent);

		painelContent.add(panel, "cell 0 6 3 1,grow");
		panel.setLayout(new BorderLayout(0, 0));

		btAdd.addActionListener((l) -> abrirPesquisaCampo());
		panel.add(panel_1, BorderLayout.EAST);
		panel_1.setLayout(new MigLayout("", "[]", "[23px,grow][][][grow]"));
		btAdd.setIcon(ImageUtil.resize("add.png", 15, 15));
		panel_1.add(btAdd, "cell 0 1,alignx left,aligny top");
		btRemove.setIcon(ImageUtil.resize("minus.png", 15, 15));
		panel_1.add(btRemove, "cell 0 2,alignx left,aligny top");

		scrollPane = new JScrollPane();
		panel.add(scrollPane, BorderLayout.CENTER);

		panelActions = new PanelActions<Pesquisa>(this, pesquisaBasicaService, PES_CATEGORIAS) {

			private static final long serialVersionUID = 1L;

			@Override
			public void carregarObjeto(Pesquisa objeto) {
				txCodigo.setValue(objeto.getIdPesquisa());
				txDescricao.setText(objeto.getDescricao());
				txPaginacao.setValue(objeto.getPaginacao());
				cbPesquisa.setSelectedItem(forValue(objeto.getCodigoPesquisa()));

				List<Perfil> perfis = new ArrayList<>();
				List<Usuario> usuarios = new ArrayList<>();

				objeto.getPermissaoPesquisas().forEach(x -> {

					if (x.getPerfil() != null) {
						perfis.add(x.getPerfil());
					}

					if (x.getUsuario() != null) {
						usuarios.add(x.getUsuario());
					}
				});

				pesquisaPerfis.setValue(perfis);
				pesquisaUsuario.setValue(usuarios);
			}

			@Override
			public void preencherObjeto(Pesquisa objetoPesquisa) {
				objetoPesquisa.setIdPesquisa(txCodigo.getValue());
				objetoPesquisa.setDescricao(txDescricao.getText());
				objetoPesquisa.setPaginacao(txPaginacao.getValue());
				objetoPesquisa.setCodigoPesquisa(((PesquisaEnum) cbPesquisa.getSelectedItem()).getCodigoPesquisa());
				objetoPesquisa.setTipo(NORMAL.getCodigo());
			}
		};

		panelActions.addEventListener(new PanelEventAdapter<Pesquisa>() {

			@Override
			public void saveEvent(Pesquisa objeto) {
				txCodigo.setValue(objeto.getIdPesquisa());
			}

			@Override
			public void newEvent() {
				txPaginacao.setValue(20L);
			}
		});

		painelContent.add(panelActions, "cell 0 7 3 1,alignx center");

	}

	private void abrirPesquisaCampo() {

		if (cbPesquisa.getSelectedIndex() < 0) {

			JOptionPane.showMessageDialog(null, translate(MENSAGEM_SELECIONE_PESQUISA),
					translate(FRMLOGIN_MSG_VERIFICACAO), JOptionPane.WARNING_MESSAGE);

			return;
		}

		FrmPesquisaBasicaCampo frmPesquisaBasicaCampo = new FrmPesquisaBasicaCampo(new PesquisaCampo(),
				(PesquisaEnum) cbPesquisa.getSelectedItem());

		frmPesquisaBasicaCampo.setVisible(Boolean.TRUE);

	}

}
