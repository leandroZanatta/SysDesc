package br.com.sysdesc.ui;

import static br.com.sysdesc.util.resources.Resources.FRMUSUARIO_LB_CODIGO;
import static br.com.sysdesc.util.resources.Resources.FRMUSUARIO_TITLE;
import static br.com.sysdesc.util.resources.Resources.translate;

import java.awt.event.ActionEvent;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import br.com.sysdesc.components.AbstractInternalFrame;
import br.com.sysdesc.components.ValidarSenha;
import br.com.sysdesc.pesquisa.components.CampoPesquisa;
import br.com.sysdesc.pesquisa.components.CampoPesquisaMultiSelect;
import br.com.sysdesc.pesquisa.components.JTextFieldId;
import br.com.sysdesc.pesquisa.components.PanelActions;
import br.com.sysdesc.pesquisa.enumeradores.PesquisaEnum;
import br.com.sysdesc.repository.model.Cliente;
import br.com.sysdesc.repository.model.Perfil;
import br.com.sysdesc.repository.model.PerfilUsuario;
import br.com.sysdesc.repository.model.PermissaoPrograma;
import br.com.sysdesc.repository.model.Usuario;
import br.com.sysdesc.service.cliente.ClienteService;
import br.com.sysdesc.service.login.LoginService;
import br.com.sysdesc.service.perfil.PerfilService;
import br.com.sysdesc.ui.buttonactions.ButtonActionAlterarPermissoes;
import br.com.sysdesc.ui.buttonactions.ButtonActionAlterarSenha;
import br.com.sysdesc.util.classes.ContadorUtil;
import br.com.sysdesc.util.classes.StringUtil;
import net.miginfocom.swing.MigLayout;

public class FrmUsuario extends AbstractInternalFrame {

	private static final long serialVersionUID = 1L;
	private JPanel painelContent;
	private JTextField txUsuario;
	private JLabel lblCliente;
	private JLabel lbPerfil;
	private JLabel lblCodigo;
	private JTextFieldId txCodigo;
	private PerfilService perfilService = new PerfilService();
	private PanelActions<Usuario> panelActions;
	private CampoPesquisaMultiSelect<Perfil> pesquisaPerfis;
	private LoginService loginService = new LoginService();
	private CampoPesquisa<Cliente> pesquisaCliente;
	private ClienteService clienteService = new ClienteService();
	private ButtonActionAlterarSenha alterarSenha;
	private ButtonActionAlterarPermissoes alterarPermissoes;
	private JLabel lblUsuario;

	public FrmUsuario(PermissaoPrograma permissaoPrograma, Long codigoUsuario) {
		super(permissaoPrograma, codigoUsuario);

		initComponents();
	}

	private void initComponents() {

		setSize(450, 275);
		setClosable(Boolean.TRUE);
		setTitle(translate(FRMUSUARIO_TITLE));

		painelContent = new JPanel();
		lblCodigo = new JLabel(translate(FRMUSUARIO_LB_CODIGO));
		lblCliente = new JLabel("Cliente:");
		lbPerfil = new JLabel("Perfil:");
		txCodigo = new JTextFieldId();

		txUsuario = new JTextField();
		alterarSenha = new ButtonActionAlterarSenha();
		alterarPermissoes = new ButtonActionAlterarPermissoes();

		pesquisaCliente = new CampoPesquisa<Cliente>(clienteService, PesquisaEnum.PES_CLIENTES, getCodigoUsuario()) {

			private static final long serialVersionUID = 1L;

			@Override
			public String formatarValorCampo(Cliente objeto) {
				return String.format("%d - %s", objeto.getIdCliente(), objeto.getNome());
			}
		};

		painelContent.setLayout(new MigLayout("", "[grow]", "[][][][][][][][][grow]"));
		getContentPane().add(painelContent);

		painelContent.add(lblCodigo, "cell 0 0");
		painelContent.add(txCodigo, "cell 0 1,width 50:100:100");
		painelContent.add(lblCliente, "cell 0 2");
		painelContent.add(pesquisaCliente, "cell 0 3,growx");
		painelContent.add(lbPerfil, "cell 0 4");

		pesquisaPerfis = new CampoPesquisaMultiSelect<Perfil>(perfilService, PesquisaEnum.PES_PERFIL,
				getCodigoUsuario()) {

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

		lblUsuario = new JLabel("Usuário:");
		painelContent.add(lblUsuario, "cell 0 6");
		painelContent.add(txUsuario, "cell 0 7,growx");

		Action actionAlterarSenha = new AbstractAction() {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {

				ValidarSenha validarSenha = new ValidarSenha();
				validarSenha.setVisible(Boolean.TRUE);

				if (!validarSenha.getOk()) {
					return;
				}

				Usuario usuario = panelActions.getObjetoPesquisa();

				usuario.setSenha(validarSenha.getSenha());

				loginService.salvar(usuario);

				JOptionPane.showMessageDialog(null, "SENHA ALTERADA COM SUCESSO.", "Verificação",
						JOptionPane.PLAIN_MESSAGE);
			}

		};

		Action actionConfigurarPermissao = new AbstractAction() {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {

			}

		};

		panelActions = new PanelActions<Usuario>(this, loginService, PesquisaEnum.PES_USUARIOS, Boolean.FALSE,
				alterarSenha, alterarPermissoes) {

			private static final long serialVersionUID = 1L;

			protected void posicionarBotoes() {

				ContadorUtil contadorUtil = new ContadorUtil();

				posicionarBotao(contadorUtil, alterarSenha, Boolean.TRUE);
				posicionarBotao(contadorUtil, alterarPermissoes, Boolean.TRUE);
				posicionarBotao(contadorUtil, btSalvar, Boolean.TRUE);
				posicionarBotao(contadorUtil, btEditar, Boolean.TRUE);
				posicionarBotao(contadorUtil, btNovo, Boolean.TRUE);
				posicionarBotao(contadorUtil, btBuscar, Boolean.TRUE);
				posicionarBotao(contadorUtil, btCancelar, Boolean.TRUE);

			}

			@Override
			protected void registrarEventosBotoesPagina() {
				registrarEvento(alterarSenha, actionAlterarSenha);
				registrarEvento(alterarPermissoes, actionConfigurarPermissao);
			}

			@Override
			public void carregarObjeto(Usuario objeto) {

				txCodigo.setValue(objeto.getIdUsuario());
				txUsuario.setText(objeto.getUsuario());
				pesquisaCliente.setValue(objeto.getCliente());

				pesquisaPerfis.setValue(
						objeto.getPerfilUsuarios().stream().map(PerfilUsuario::getPerfil).collect(Collectors.toList()));
			}

			@Override
			public Boolean preencherObjeto(Usuario objetoPesquisa) {
				objetoPesquisa.setIdUsuario(txCodigo.getValue());
				objetoPesquisa.setUsuario(txUsuario.getText());
				objetoPesquisa.setCliente(pesquisaCliente.getObjetoPesquisado());

				pesquisaPerfis.getObjetosPesquisado().forEach(objetoPesquisa::addPerfilUsuario);

				if (StringUtil.isNullOrEmpty(objetoPesquisa.getSenha())) {

					ValidarSenha validarSenha = new ValidarSenha();
					validarSenha.setVisible(Boolean.TRUE);

					if (!validarSenha.getOk()) {
						return Boolean.FALSE;
					}

					objetoPesquisa.setSenha(validarSenha.getSenha());
				}

				return Boolean.TRUE;
			}

		};
		panelActions.addSaveListener((usuario) -> txCodigo.setValue(usuario.getIdUsuario()));

		painelContent.add(panelActions, "cell 0 8,grow");
	}

}
