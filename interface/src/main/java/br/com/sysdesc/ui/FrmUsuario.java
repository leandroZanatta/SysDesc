package br.com.sysdesc.ui;

import static br.com.sysdesc.pesquisa.enumeradores.PesquisaEnum.PES_USUARIOS;
import static br.com.sysdesc.util.resources.Resources.FRMUSUARIO_LB_CODIGO;
import static br.com.sysdesc.util.resources.Resources.FRMUSUARIO_LB_SENHA;
import static br.com.sysdesc.util.resources.Resources.FRMUSUARIO_LB_USUARIO;
import static br.com.sysdesc.util.resources.Resources.FRMUSUARIO_TITLE;
import static br.com.sysdesc.util.resources.Resources.translate;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import br.com.sysdesc.components.AbstractInternalFrame;
import br.com.sysdesc.components.JNumericField;
import br.com.sysdesc.pesquisa.components.CampoPesquisa;
import br.com.sysdesc.pesquisa.components.PanelActions;
import br.com.sysdesc.pesquisa.enumeradores.PesquisaEnum;
import br.com.sysdesc.repository.model.Cliente;
import br.com.sysdesc.repository.model.PermissaoPrograma;
import br.com.sysdesc.repository.model.Usuario;
import br.com.sysdesc.service.cliente.ClienteService;
import br.com.sysdesc.service.login.LoginService;
import br.com.sysdesc.util.classes.CryptoUtil;
import br.com.sysdesc.util.classes.StringUtil;
import net.miginfocom.swing.MigLayout;

public class FrmUsuario extends AbstractInternalFrame {

	private static final long serialVersionUID = 1L;
	private JPanel painelContent;
	// private TextFieldPesquisa<Usuario> txCodigo;
	private JTextField txUsuario;
	private JPasswordField passwordField;
	private JLabel lblId;
	private JLabel lblUsurio;
	private JLabel lblSenha;
	private JNumericField txCodigo;
	private PanelActions<Usuario> panelActions;
	private LoginService loginService = new LoginService();
	private CampoPesquisa<Cliente> pesquisaCliente;
	private ClienteService clienteService = new ClienteService();

	public FrmUsuario(PermissaoPrograma permissaoPrograma, Long codigoUsuario) {
		super(permissaoPrograma, codigoUsuario);

		initComponents();
	}

	private void initComponents() {

		setSize(450, 210);
		setClosable(Boolean.TRUE);
		setTitle(translate(FRMUSUARIO_TITLE));

		painelContent = new JPanel();
		lblId = new JLabel(translate(FRMUSUARIO_LB_CODIGO));
		lblUsurio = new JLabel(translate(FRMUSUARIO_LB_USUARIO));
		lblSenha = new JLabel(translate(FRMUSUARIO_LB_SENHA));

		passwordField = new JPasswordField();
		txUsuario = new JTextField();
		pesquisaCliente = new CampoPesquisa<Cliente>(clienteService, PesquisaEnum.PES_CLIENTES, getCodigoUsuario()) {

			private static final long serialVersionUID = 1L;

			@Override
			public String formatarValorCampo(Cliente objeto) {
				return String.format("%d - %s", objeto.getIdCliente(), objeto.getNome());
			}
		};
		painelContent.setLayout(new MigLayout("", "[grow]", "[][][][][][][grow]"));
		getContentPane().add(painelContent);

		painelContent.add(lblId, "cell 0 0");
		painelContent.add(lblUsurio, "cell 0 2");
		painelContent.add(pesquisaCliente, "cell 0 3,width 50:100:100");
		painelContent.add(lblSenha, "cell 0 4");
		painelContent.add(passwordField, "cell 0 5,growx");

		panelActions = new PanelActions<Usuario>(this, loginService, PES_USUARIOS) {

			private static final long serialVersionUID = 1L;

			@Override
			public void carregarObjeto(Usuario objeto) {
				// txCodigo.setText(objeto == null ? "" : objeto.getIdUsuario().toString());
				txUsuario.setText(objeto == null ? "" : objeto.getUsuario());
				passwordField.setText(objeto == null ? "" : objeto.getSenha());
			}

			@Override
			public void preencherObjeto(Usuario objetoPesquisa) {

				objetoPesquisa.setUsuario(txUsuario.getText());

				if (!isEdit) {
					objetoPesquisa.setSenha(CryptoUtil.toMD5(StringUtil.arrayToString(passwordField.getPassword())));
				}

			}
		};

		/*
		 * txCodigo = new TextFieldPesquisa<Usuario>(panelActions) {
		 * 
		 * private static final long serialVersionUID = 1L;
		 * 
		 * @Override public String preencherCampoId(Usuario login) {
		 * 
		 * return login.getIdUsuario().toString(); } }; painelContent.add(txCodigo,
		 * "cell 0 1,alignx left");
		 */
		painelContent.add(panelActions, "cell 0 6,grow");
	}

}
