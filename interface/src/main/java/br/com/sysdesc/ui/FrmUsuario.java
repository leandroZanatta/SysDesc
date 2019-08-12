package br.com.sysdesc.ui;

import static br.com.sysdesc.pesquisa.enumeradores.PesquisaEnum.PES_USUARIOS;
import static br.com.sysdesc.util.resources.Resources.FRMUSUARIO_LB_CODIGO;
import static br.com.sysdesc.util.resources.Resources.FRMUSUARIO_LB_USUARIO;
import static br.com.sysdesc.util.resources.Resources.FRMUSUARIO_TITLE;
import static br.com.sysdesc.util.resources.Resources.translate;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
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
	private JLabel lblCliente;
	private JLabel lblUsuario;
	private JLabel lblCodigo;
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
		lblCodigo = new JLabel(translate(FRMUSUARIO_LB_CODIGO));
		lblCliente = new JLabel("Cliente:");
		lblUsuario = new JLabel(translate(FRMUSUARIO_LB_USUARIO));
		txCodigo = new JNumericField();

		lblCodigo = new JLabel("Código:");
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

		painelContent.add(lblCodigo, "cell 0 0");
		painelContent.add(txCodigo, "cell 0 1,width 50:100:100");
		painelContent.add(lblCliente, "cell 0 2");
		painelContent.add(pesquisaCliente, "cell 0 3,growx");
		painelContent.add(lblUsuario, "cell 0 4");
		painelContent.add(txUsuario, "cell 0 5,growx");

		panelActions = new PanelActions<Usuario>(this, loginService, PES_USUARIOS) {

			private static final long serialVersionUID = 1L;

			@Override
			public void carregarObjeto(Usuario objeto) {

				txCodigo.setValue(objeto.getIdUsuario());
				txUsuario.setText(objeto.getUsuario());
				pesquisaCliente.setValue(objeto.getCliente());
			}

			@Override
			public void preencherObjeto(Usuario objetoPesquisa) {

				objetoPesquisa.setIdUsuario(txCodigo.getValue());
				objetoPesquisa.setUsuario(txUsuario.getText());
				objetoPesquisa.setCliente(pesquisaCliente.getObjetoPesquisado());

				if (StringUtil.isNullOrEmpty(objetoPesquisa.getSenha())) {
					String senha = JOptionPane.showInputDialog(null, "Insira uma Senha", "Verificaçao");

					objetoPesquisa.setSenha(CryptoUtil.toMD5(senha));
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
