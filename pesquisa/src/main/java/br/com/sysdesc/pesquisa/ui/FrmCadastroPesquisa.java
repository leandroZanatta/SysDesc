package br.com.sysdesc.pesquisa.ui;

import static br.com.sysdesc.pesquisa.enumeradores.PesquisaEnum.PES_PESQUISA;
import static br.com.sysdesc.pesquisa.enumeradores.PesquisaEnum.forValue;
import static br.com.sysdesc.util.constants.MensagemConstants.MENSAGEM_SELECIONE_APENAS_UM_REGISTRO;
import static br.com.sysdesc.util.constants.MensagemConstants.MENSAGEM_SELECIONE_PESQUISA;
import static br.com.sysdesc.util.enumeradores.TipoPesquisaEnum.NORMAL;
import static br.com.sysdesc.util.resources.Resources.FRMLOGIN_MSG_VERIFICACAO;
import static br.com.sysdesc.util.resources.Resources.FRMPESQUISA_LB_CODIGO;
import static br.com.sysdesc.util.resources.Resources.FRMPESQUISA_LB_DESCRICAO;
import static br.com.sysdesc.util.resources.Resources.FRMPESQUISA_LB_PAGINACAO;
import static br.com.sysdesc.util.resources.Resources.FRMPESQUISA_LB_PERFIL;
import static br.com.sysdesc.util.resources.Resources.FRMPESQUISA_LB_PESQUISA;
import static br.com.sysdesc.util.resources.Resources.FRMPESQUISA_LB_USUARIO;
import static br.com.sysdesc.util.resources.Resources.FRMPESQUISA_TITLE;
import static br.com.sysdesc.util.resources.Resources.translate;

import java.awt.BorderLayout;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
import javax.swing.JTable;

import br.com.sysdesc.components.AbstractInternalFrame;
import br.com.sysdesc.components.JNumericField;
import br.com.sysdesc.components.JTextFieldMaiusculo;
import br.com.sysdesc.pesquisa.components.CampoPesquisaMultiSelect;
import br.com.sysdesc.pesquisa.components.PanelActions;
import br.com.sysdesc.pesquisa.enumeradores.PesquisaEnum;
import br.com.sysdesc.pesquisa.models.ProjectionsTableModel;
import br.com.sysdesc.repository.model.Perfil;
import br.com.sysdesc.repository.model.PermissaoPesquisa;
import br.com.sysdesc.repository.model.PermissaoPrograma;
import br.com.sysdesc.repository.model.Pesquisa;
import br.com.sysdesc.repository.model.PesquisaCampo;
import br.com.sysdesc.repository.model.Usuario;
import br.com.sysdesc.service.login.LoginService;
import br.com.sysdesc.service.perfil.PerfilService;
import br.com.sysdesc.service.pesquisa.PesquisaBasicaService;
import br.com.sysdesc.util.classes.ContadorUtil;
import br.com.sysdesc.util.classes.ImageUtil;
import br.com.sysdesc.util.classes.ListUtil;
import net.miginfocom.swing.MigLayout;

public class FrmCadastroPesquisa extends AbstractInternalFrame {

	private static final long serialVersionUID = 1L;

	private JLabel lblCodigo;
	private JLabel lblPaginacao;
	private JLabel lblPesquisa;
	private JLabel lblDescricao;
	private JLabel lbUsuario;
	private JLabel lbPerfil;

	private JPanel painelContent;
	private JPanel painelCampos;
	private JPanel painelActionCampos;

	private JTextFieldMaiusculo txDescricao;
	private JNumericField txCodigo;
	private JNumericField txPaginacao;
	private JComboBox<PesquisaEnum> cbPesquisa;
	private CampoPesquisaMultiSelect<Usuario> pesquisaUsuario;
	private CampoPesquisaMultiSelect<Perfil> pesquisaPerfis;
	private JScrollPane scrollPane;
	private JTable tabela;
	private JButton btAdd;
	private JButton btRemove;
	private ProjectionsTableModel projectionsTableModel = new ProjectionsTableModel();

	private PanelActions<Pesquisa> panelActions;

	private PesquisaBasicaService pesquisaBasicaService = new PesquisaBasicaService();
	private LoginService loginService = new LoginService();
	private PerfilService perfilService = new PerfilService();

	public FrmCadastroPesquisa(PermissaoPrograma permissaoPrograma, Long codigoUsuario, Long codigoEmpresa) {
		super(permissaoPrograma, codigoUsuario, codigoEmpresa);

		setSize(800, 400);
		setClosable(Boolean.TRUE);
		setTitle(translate(FRMPESQUISA_TITLE));

		painelContent = new JPanel();

		lblCodigo = new JLabel(translate(FRMPESQUISA_LB_CODIGO));
		lblPaginacao = new JLabel(translate(FRMPESQUISA_LB_PAGINACAO));
		lblDescricao = new JLabel(translate(FRMPESQUISA_LB_DESCRICAO));
		lblPesquisa = new JLabel(translate(FRMPESQUISA_LB_PESQUISA));
		lbPerfil = new JLabel(translate(FRMPESQUISA_LB_PERFIL));
		lbUsuario = new JLabel(translate(FRMPESQUISA_LB_USUARIO));

		txCodigo = new JNumericField();
		txDescricao = new JTextFieldMaiusculo();
		txPaginacao = new JNumericField();
		cbPesquisa = new JComboBox<PesquisaEnum>();

		painelActionCampos = new JPanel();
		painelCampos = new JPanel();

		btAdd = new JButton("");
		btRemove = new JButton("");

		projectionsTableModel = new ProjectionsTableModel();

		tabela = new JTable(projectionsTableModel);
		scrollPane = new JScrollPane(tabela);

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

		tabela.addMouseListener(new MouseAdapter() {

			public void mousePressed(MouseEvent mouseEvent) {

				JTable table = (JTable) mouseEvent.getSource();

				Point point = mouseEvent.getPoint();

				int row = table.rowAtPoint(point);

				if (mouseEvent.getClickCount() == 2 && table.getSelectedRow() != -1) {

					abrirPesquisaCampo(projectionsTableModel.getRow(row));
				}
			}
		});

		btAdd.setIcon(ImageUtil.resize("add.png", 15, 15));
		btRemove.setIcon(ImageUtil.resize("minus.png", 15, 15));

		btAdd.addActionListener((l) -> abrirPesquisaCampo());
		btRemove.addActionListener((l) -> removerPesquisaCampo());

		painelContent.setLayout(new MigLayout("", "[grow][grow][80.00]", "[][][][][][][grow][]"));
		painelCampos.setLayout(new BorderLayout(0, 0));
		painelActionCampos.setLayout(new MigLayout("", "[]", "[23px,grow][][][grow]"));

		painelContent.add(lblCodigo, "cell 0 0");
		painelContent.add(txCodigo, "cell 0 1, width 50:100:100");
		painelContent.add(lblDescricao, "cell 0 2");
		painelContent.add(lblPesquisa, "cell 1 2");
		painelContent.add(txDescricao, "cell 0 3,growx");
		painelContent.add(lblPaginacao, "cell 2 2");
		painelContent.add(cbPesquisa, "cell 1 3,growx");
		painelContent.add(txPaginacao, "cell 2 3,growx");
		painelContent.add(lbPerfil, "cell 0 4");
		painelContent.add(lbUsuario, "cell 1 4");
		painelContent.add(pesquisaPerfis, "cell 0 5,growx");
		painelContent.add(pesquisaUsuario, "cell 1 5 2 1,growx");
		painelContent.add(painelCampos, "cell 0 6 3 1,grow");

		painelActionCampos.add(btAdd, "cell 0 1,alignx left,aligny top");
		painelActionCampos.add(btRemove, "cell 0 2,alignx left,aligny top");

		painelCampos.add(painelActionCampos, BorderLayout.EAST);
		painelCampos.add(scrollPane, BorderLayout.CENTER);

		getContentPane().add(painelContent);

		panelActions = new PanelActions<Pesquisa>(this, pesquisaBasicaService, PES_PESQUISA) {

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

				projectionsTableModel.setRows(new ArrayList<>(objeto.getPesquisaCampos()));
			}

			@Override
			public Boolean preencherObjeto(Pesquisa objetoPesquisa) {

				objetoPesquisa.setIdPesquisa(txCodigo.getValue());
				objetoPesquisa.setDescricao(txDescricao.getText());
				objetoPesquisa.setPaginacao(txPaginacao.getValue());

				if (cbPesquisa.getSelectedIndex() >= 0) {

					objetoPesquisa.setCodigoPesquisa(((PesquisaEnum) cbPesquisa.getSelectedItem()).getCodigoPesquisa());

				}

				objetoPesquisa.setTipo(NORMAL.getCodigo());

				List<PesquisaCampo> pesquisaCampos = projectionsTableModel.getRows();

				ContadorUtil contadorUtil = new ContadorUtil();

				pesquisaCampos.forEach(pesquisaCampo -> {

					pesquisaCampo.setPesquisa(objetoPesquisa);

					pesquisaCampo.setFlagOrdem(contadorUtil.next());
				});

				objetoPesquisa.setPesquisaCampos(pesquisaCampos);

				List<PermissaoPesquisa> permissaoPesquisas = new ArrayList<>();

				permissaoPesquisas
						.addAll(criarPermissaoUsuarios(pesquisaUsuario.getObjetosPesquisado(), objetoPesquisa));
				permissaoPesquisas.addAll(criarPermissaoPerfis(pesquisaPerfis.getObjetosPesquisado(), objetoPesquisa));
				objetoPesquisa.setPermissaoPesquisas(permissaoPesquisas);

				return Boolean.TRUE;
			}

			private List<PermissaoPesquisa> criarPermissaoPerfis(List<Perfil> objetosPesquisado,
					Pesquisa objetoPesquisa) {

				List<PermissaoPesquisa> permissaoPesquisas = new ArrayList<>();

				if (!ListUtil.isNullOrEmpty(objetosPesquisado)) {

					objetosPesquisado.forEach(perfil -> {

						PermissaoPesquisa permissaoPesquisa = new PermissaoPesquisa();
						permissaoPesquisa.setPerfil(perfil);
						permissaoPesquisa.setPesquisa(objetoPesquisa);

						permissaoPesquisas.add(permissaoPesquisa);
					});
				}

				return permissaoPesquisas;
			}

			private List<PermissaoPesquisa> criarPermissaoUsuarios(List<Usuario> objetosPesquisado,
					Pesquisa objetoPesquisa) {

				List<PermissaoPesquisa> permissaoPesquisas = new ArrayList<>();

				if (!ListUtil.isNullOrEmpty(objetosPesquisado)) {

					objetosPesquisado.forEach(usuario -> {

						PermissaoPesquisa permissaoPesquisa = new PermissaoPesquisa();
						permissaoPesquisa.setUsuario(usuario);
						permissaoPesquisa.setPesquisa(objetoPesquisa);

						permissaoPesquisas.add(permissaoPesquisa);
					});
				}

				return permissaoPesquisas;
			}
		};

		panelActions.addSaveListener((objeto) -> txCodigo.setValue(objeto.getIdPesquisa()));
		panelActions.addNewListener(() -> txPaginacao.setValue(20L));

		painelContent.add(panelActions, "cell 0 7 3 1,alignx center");

	}

	private void removerPesquisaCampo() {

		if (tabela.getSelectedRow() < 0) {
			JOptionPane.showMessageDialog(null, translate(MENSAGEM_SELECIONE_APENAS_UM_REGISTRO));

			return;
		}

		projectionsTableModel.remove(tabela.getSelectedRow());
	}

	private void abrirPesquisaCampo() {

		abrirPesquisaCampo(null);
	}

	private void abrirPesquisaCampo(PesquisaCampo pesquisaCampoEdicao) {

		if (cbPesquisa.getSelectedIndex() < 0) {

			JOptionPane.showMessageDialog(null, translate(MENSAGEM_SELECIONE_PESQUISA),
					translate(FRMLOGIN_MSG_VERIFICACAO), JOptionPane.WARNING_MESSAGE);

			return;
		}

		PesquisaCampo pesquisaCampo = new PesquisaCampo();

		if (pesquisaCampoEdicao != null) {
			pesquisaCampo = pesquisaCampoEdicao;
		}

		FrmPesquisaBasicaCampo frmPesquisaBasicaCampo = new FrmPesquisaBasicaCampo(pesquisaCampo,
				(PesquisaEnum) cbPesquisa.getSelectedItem());

		frmPesquisaBasicaCampo.setVisible(Boolean.TRUE);

		if (frmPesquisaBasicaCampo.getSucesso()) {
			projectionsTableModel.addProjection(frmPesquisaBasicaCampo.getPesquisaCampo());
		}
	}

}
