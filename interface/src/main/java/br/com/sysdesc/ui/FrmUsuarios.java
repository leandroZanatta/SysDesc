package br.com.sysdesc.ui;

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
import br.com.sysdesc.components.ApplicationJframe;
import br.com.sysdesc.components.actionbuttons.PanelActions;
import br.com.sysdesc.repository.dao.UsuarioDAO;
import br.com.sysdesc.repository.model.PermissaoPrograma;
import br.com.sysdesc.repository.model.Usuario;
import br.com.sysdesc.util.classes.CryptoUtil;
import br.com.sysdesc.util.classes.StringUtil;
import net.miginfocom.swing.MigLayout;

public class FrmUsuarios extends AbstractInternalFrame {

	private static final long serialVersionUID = 1L;
	private JPanel painelContent;
	// private TextFieldPesquisa<Usuario> txCodigo;
	private JTextField txUsuario;
	private JPasswordField passwordField;
	private JLabel lblId;
	private JLabel lblUsurio;
	private JLabel lblSenha;
	private PanelActions<Usuario> panelActions;
	private UsuarioDAO loginDAO = new UsuarioDAO();

	public FrmUsuarios(ApplicationJframe frmApplication, PermissaoPrograma permissaoPrograma) {
		super(frmApplication, permissaoPrograma);
		setSize(450, 210);
		setClosable(Boolean.TRUE);
		setTitle(translate(FRMUSUARIO_TITLE));

		painelContent = new JPanel();
		lblId = new JLabel(translate(FRMUSUARIO_LB_CODIGO));
		lblUsurio = new JLabel(translate(FRMUSUARIO_LB_USUARIO));
		lblSenha = new JLabel(translate(FRMUSUARIO_LB_SENHA));

		passwordField = new JPasswordField();
		txUsuario = new JTextField();

		painelContent.setLayout(new MigLayout("", "[grow]", "[][][][][][][grow]"));
		getContentPane().add(painelContent);

		painelContent.add(lblId, "cell 0 0");
		painelContent.add(lblUsurio, "cell 0 2");
		painelContent.add(txUsuario, "cell 0 3,growx");
		painelContent.add(lblSenha, "cell 0 4");
		painelContent.add(passwordField, "cell 0 5,growx");

		panelActions = new PanelActions<Usuario>(this.frmApplication, this, this.permissaoPrograma, loginDAO) {

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
