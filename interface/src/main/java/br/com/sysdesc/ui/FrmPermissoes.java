package br.com.sysdesc.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.jdesktop.swingx.JXTreeTable;

import br.com.sysdesc.components.AbstractInternalFrame;
import br.com.sysdesc.components.listeners.ChangeListener;
import br.com.sysdesc.pesquisa.components.CampoPesquisa;
import br.com.sysdesc.pesquisa.enumeradores.PesquisaEnum;
import br.com.sysdesc.renderes.CheckBoxRenderer;
import br.com.sysdesc.repository.model.Perfil;
import br.com.sysdesc.repository.model.PermissaoPrograma;
import br.com.sysdesc.repository.model.Programa;
import br.com.sysdesc.repository.model.Usuario;
import br.com.sysdesc.service.login.LoginService;
import br.com.sysdesc.service.perfil.PerfilService;
import br.com.sysdesc.service.permissoes.PermissoesProgramaService;
import br.com.sysdesc.service.programa.ProgramaService;
import br.com.sysdesc.tablemodels.PermissaoTreeTableModel;
import br.com.sysdesc.util.constants.MensagemConstants;
import br.com.sysdesc.util.resources.Resources;
import br.com.sysdesc.util.vo.PermissaoProgramaVO;
import net.miginfocom.swing.MigLayout;

public class FrmPermissoes extends AbstractInternalFrame {

	private CampoPesquisa<Usuario> campoPesquisaUsuario;
	private CampoPesquisa<Perfil> campoPesquisaPerfil;

	private JXTreeTable treeTable;
	PermissaoTreeTableModel myTreeTableModel;
	private LoginService loginService = new LoginService();
	private PerfilService perfilService = new PerfilService();
	private ProgramaService programaService = new ProgramaService();
	PermissoesProgramaService permissoesProgramaService = new PermissoesProgramaService();

	private Map<Long, PermissaoProgramaVO> map = new HashMap<>();

	private static final long serialVersionUID = 1L;

	public FrmPermissoes(PermissaoPrograma permissaoPrograma, Long codigoUsuario) {
		super(permissaoPrograma, codigoUsuario);

		initComponents();
	}

	private void initComponents() {

		setSize(650, 600);
		setClosable(Boolean.TRUE);
		setTitle("");

		getContentPane().setLayout(new MigLayout("", "[grow]", "[][][][][grow][]"));

		JLabel lblUsurio = new JLabel("Usuário:");
		JLabel lblPerfil = new JLabel("Perfil:");
		JScrollPane scrollPane = new JScrollPane();

		JPanel panel_1 = new JPanel();
		JButton btnSalvar = new JButton("Salvar");
		JButton btnCancelar = new JButton("Cancelar");

		campoPesquisaUsuario = new CampoPesquisa<Usuario>(loginService, PesquisaEnum.PES_USUARIOS, getCodigoUsuario()) {

			private static final long serialVersionUID = 1L;

			@Override
			public String formatarValorCampo(Usuario objeto) {
				return String.format("%s - %s", objeto.getIdUsuario(), objeto.getCliente().getNome());
			}
		};

		campoPesquisaUsuario.addChangeListener(new ChangeListener<Usuario>() {

			@Override
			public void valueChanged(Usuario newValue, Usuario oldValue) {

				campoPesquisaPerfil.limpar();

				zerarPermissoes();

				atualizarPermissoesPrograma(newValue.getPermissaoProgramas());
			}

		});
		campoPesquisaPerfil = new CampoPesquisa<Perfil>(perfilService, PesquisaEnum.PES_PERFIL, getCodigoUsuario()) {

			private static final long serialVersionUID = 1L;

			@Override
			public String formatarValorCampo(Perfil objeto) {
				return String.format("%s - %s", objeto.getIdPerfil(), objeto.getDescricao());
			}
		};

		campoPesquisaPerfil.addChangeListener(new ChangeListener<Perfil>() {

			@Override
			public void valueChanged(Perfil newValue, Perfil oldValue) {

				campoPesquisaUsuario.limpar();

				zerarPermissoes();

				atualizarPermissoesPrograma(newValue.getPermissaoProgramas());
			}

		});

		PermissaoProgramaVO permissaoProgramaVO = new PermissaoProgramaVO();
		permissaoProgramaVO.setDescricao("TODOS OS PROGRAMAS");
		permissaoProgramaVO.setIdPrograma(0L);
		map.put(0L, permissaoProgramaVO);

		this.montarPermissoes(permissaoProgramaVO, programaService.buscarRootProgramas());

		myTreeTableModel = new PermissaoTreeTableModel(permissaoProgramaVO);

		treeTable = new JXTreeTable(myTreeTableModel);
		treeTable.setRootVisible(true);

		treeTable.getColumnModel().getColumn(0).setPreferredWidth(550);
		treeTable.getColumnModel().getColumn(1).setPreferredWidth(80);
		treeTable.getColumnModel().getColumn(2).setPreferredWidth(80);
		treeTable.getColumnModel().getColumn(3).setPreferredWidth(80);

		new CheckBoxRenderer(treeTable, 1);
		new CheckBoxRenderer(treeTable, 2);
		new CheckBoxRenderer(treeTable, 3);

		scrollPane.setViewportView(treeTable);

		btnCancelar.addActionListener((e) -> dispose());
		btnSalvar.addActionListener((e) -> salvarPermissoesPrograma());

		panel_1.add(btnSalvar);
		panel_1.add(btnCancelar);

		getContentPane().add(lblUsurio, "cell 0 0");
		getContentPane().add(campoPesquisaUsuario, "cell 0 1,growx");
		getContentPane().add(lblPerfil, "cell 0 2");
		getContentPane().add(campoPesquisaPerfil, "cell 0 3,growx");
		getContentPane().add(scrollPane, "cell 0 4,grow");
		getContentPane().add(panel_1, "cell 0 5,grow");

	}

	private void salvarPermissoesPrograma() {

		if (salvarPermissoes()) {
			JOptionPane.showMessageDialog(null, Resources.translate(MensagemConstants.MENSAGENS_PERMISSOES_SALVAS));

			dispose();
		}
	}

	private Boolean salvarPermissoes() {

		if (campoPesquisaPerfil.getObjetoPesquisado() != null) {

			return salvarPermissoesProgramaPerfil();

		}
		return salvarPermissoesProgramaUsuario();
	}

	private Boolean salvarPermissoesProgramaUsuario() {

		List<PermissaoPrograma> permissoes = new ArrayList<>();

		Usuario usuario = campoPesquisaUsuario.getObjetoPesquisado();

		List<PermissaoPrograma> permissoesUsuario = usuario.getPermissaoProgramas();

		map.values().stream().filter(x -> !x.getIdPrograma().equals(0L)).forEach(programa -> {

			Optional<PermissaoPrograma> optional = permissoesUsuario.stream()
					.filter(x -> x.getCodigoPrograma().equals(programa.getIdPrograma())).findFirst();

			PermissaoPrograma permissao = getPermissaoPrograma(optional, programa.getIdPrograma());

			permissao.setCodigoUsuario(usuario.getIdUsuario());
			permissao.setFlagCadastro(programa.getFlagCadastro());
			permissao.setFlagExclusao(programa.getFlagExclusao());
			permissao.setFlagLeitura(programa.getFlagLeitura());

			permissoes.add(permissao);
		});

		permissoesProgramaService.salvarTodos(permissoes);

		return Boolean.TRUE;
	}

	private PermissaoPrograma getPermissaoPrograma(Optional<PermissaoPrograma> optional, Long programa) {

		if (optional.isPresent()) {
			return optional.get();
		}

		PermissaoPrograma permissaoPrograma = new PermissaoPrograma();
		permissaoPrograma.setCodigoPrograma(programa);

		return permissaoPrograma;
	}

	private Boolean salvarPermissoesProgramaPerfil() {
		List<PermissaoPrograma> permissoes = new ArrayList<>();

		Perfil perfil = campoPesquisaPerfil.getObjetoPesquisado();

		List<PermissaoPrograma> permissoesPerfil = perfil.getPermissaoProgramas();

		map.values().stream().filter(x -> !x.getIdPrograma().equals(0L)).forEach(programa -> {

			Optional<PermissaoPrograma> optional = permissoesPerfil.stream()
					.filter(x -> x.getCodigoPrograma().equals(programa.getIdPrograma())).findFirst();

			PermissaoPrograma permissao = getPermissaoPrograma(optional, programa.getIdPrograma());

			permissao.setCodigoPerfil(perfil.getIdPerfil());
			permissao.setFlagCadastro(programa.getFlagCadastro());
			permissao.setFlagExclusao(programa.getFlagExclusao());
			permissao.setFlagLeitura(programa.getFlagLeitura());

			permissoes.add(permissao);
		});

		permissoesProgramaService.salvarTodos(permissoes);

		return Boolean.TRUE;
	}

	protected void atualizarPermissoesPrograma(List<PermissaoPrograma> permissoes) {

		permissoes.forEach(permissao -> {

			if (map.containsKey(permissao.getCodigoPrograma())) {

				PermissaoProgramaVO permissaoProgramaVO = map.get(permissao.getCodigoPrograma());
				permissaoProgramaVO.setFlagCadastro(permissao.getFlagCadastro());
				permissaoProgramaVO.setFlagExclusao(permissao.getFlagExclusao());
				permissaoProgramaVO.setFlagLeitura(permissao.getFlagLeitura());
			}
		});

		PermissaoProgramaVO root = map.get(0L);

		root.setFlagCadastro(
				map.values().stream().filter(x -> !x.getIdPrograma().equals(0L)).allMatch(x -> x.getFlagCadastro()));
		root.setFlagExclusao(
				map.values().stream().filter(x -> !x.getIdPrograma().equals(0L)).allMatch(x -> x.getFlagExclusao()));
		root.setFlagLeitura(
				map.values().stream().filter(x -> !x.getIdPrograma().equals(0L)).allMatch(x -> x.getFlagLeitura()));

		treeTable.updateUI();
	}

	private void zerarPermissoes() {

		map.values().forEach(permissao -> {
			permissao.setFlagLeitura(Boolean.FALSE);
			permissao.setFlagCadastro(Boolean.FALSE);
			permissao.setFlagExclusao(Boolean.FALSE);
		});
	}

	private void montarPermissoes(PermissaoProgramaVO root, List<Programa> rootProgramas) {

		for (Programa programa : rootProgramas) {

			PermissaoProgramaVO permissaoProgramaVO = new PermissaoProgramaVO();
			permissaoProgramaVO.setIdPrograma(programa.getIdPrograma());
			permissaoProgramaVO.setDescricao(Resources.translate(programa.getDescricao()));

			if (!programa.getProgramas().isEmpty()) {
				montarPermissoes(permissaoProgramaVO, programa.getProgramas());
			}

			map.put(permissaoProgramaVO.getIdPrograma(), permissaoProgramaVO);

			root.addChild(permissaoProgramaVO);
		}

	}

}
