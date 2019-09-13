package br.com.sysdesc.ui;

import static br.com.sysdesc.util.resources.Resources.FRMDEPARTAMENTO_TITLE;
import static br.com.sysdesc.util.resources.Resources.translate;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.TreeSelectionModel;

import br.com.sysdesc.components.AbstractInternalFrame;
import br.com.sysdesc.pesquisa.components.CampoPesquisa;
import br.com.sysdesc.pesquisa.enumeradores.PesquisaEnum;
import br.com.sysdesc.renderes.CheckBoxPanel;
import br.com.sysdesc.renderes.TreePanelPrograma;
import br.com.sysdesc.repository.model.Perfil;
import br.com.sysdesc.repository.model.PermissaoPrograma;
import br.com.sysdesc.repository.model.Usuario;
import br.com.sysdesc.service.login.LoginService;
import br.com.sysdesc.service.perfil.PerfilService;
import br.com.sysdesc.service.permissoes.PermissoesProgramaService;
import net.miginfocom.swing.MigLayout;

public class FrmPermissoes extends AbstractInternalFrame {

	private CampoPesquisa<Usuario> campoPesquisaUsuario;
	private CampoPesquisa<Perfil> campoPesquisaPerfil;

	private LoginService loginService = new LoginService();
	private PerfilService perfilService = new PerfilService();
	private PermissoesProgramaService permissoesProgramaService = new PermissoesProgramaService();

	private static final long serialVersionUID = 1L;

	public FrmPermissoes(PermissaoPrograma permissaoPrograma, Long codigoUsuario) {
		super(permissaoPrograma, codigoUsuario);

		initComponents();
	}

	private void initComponents() {

		setSize(450, 400);
		setClosable(Boolean.TRUE);
		setTitle(translate(FRMDEPARTAMENTO_TITLE));

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

		campoPesquisaPerfil = new CampoPesquisa<Perfil>(perfilService, PesquisaEnum.PES_PERFIL, getCodigoUsuario()) {

			private static final long serialVersionUID = 1L;

			@Override
			public String formatarValorCampo(Perfil objeto) {
				return String.format("%s - %s", objeto.getIdPerfil(), objeto.getDescricao());
			}
		};

		JTree tree = new TreePanelPrograma();

		tree.setCellRenderer(new CheckBoxPanel());

		tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		tree.putClientProperty("JTree.lineStyle", "Angled");

		scrollPane.setViewportView(tree);

		btnCancelar.addActionListener((e) -> dispose());
		// tree.addMouseListener(new NodeSelectionListener(tree));

		panel_1.add(btnSalvar);
		panel_1.add(btnCancelar);

		getContentPane().add(lblUsurio, "cell 0 0");
		getContentPane().add(campoPesquisaUsuario, "cell 0 1,growx");
		getContentPane().add(lblPerfil, "cell 0 2");
		getContentPane().add(campoPesquisaPerfil, "cell 0 3,growx");
		getContentPane().add(scrollPane, "cell 0 4,grow");
		getContentPane().add(panel_1, "cell 0 5,grow");

	}

}
