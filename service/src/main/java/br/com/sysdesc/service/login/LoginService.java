package br.com.sysdesc.service.login;

import static br.com.sysdesc.util.resources.Resources.FRMLOGIN_MSG_LOGIN;
import static br.com.sysdesc.util.resources.Resources.FRMLOGIN_MSG_SENHA;
import static br.com.sysdesc.util.resources.Resources.FRMLOGIN_MSG_USUARIO;

import br.com.sysdesc.repository.dao.PerfilUsuarioDAO;
import br.com.sysdesc.repository.dao.UsuarioDAO;
import br.com.sysdesc.repository.model.Usuario;
import br.com.sysdesc.service.interfaces.impl.AbstractGenericService;
import br.com.sysdesc.util.classes.CryptoUtil;
import br.com.sysdesc.util.classes.StringUtil;
import br.com.sysdesc.util.constants.MensagemConstants;
import br.com.sysdesc.util.exception.SysDescException;

public class LoginService extends AbstractGenericService<Usuario> {

	private final UsuarioDAO usuarioDAO;

	private PerfilUsuarioDAO perfilUsuarioDAO = new PerfilUsuarioDAO();

	public LoginService() {
		this(new UsuarioDAO());
	}

	public LoginService(UsuarioDAO usuarioDAO) {
		super(usuarioDAO, Usuario::getIdUsuario);

		this.usuarioDAO = usuarioDAO;
	}

	public Usuario efetuarLogin(String usuario, String senha) throws SysDescException {

		if (StringUtil.isNullOrEmpty(usuario)) {

			throw new SysDescException(FRMLOGIN_MSG_USUARIO);

		}
		if (StringUtil.isNullOrEmpty(senha)) {

			throw new SysDescException(FRMLOGIN_MSG_SENHA);

		}

		Usuario mdlUsuario = usuarioDAO.obterLogin(usuario, CryptoUtil.toMD5(senha));

		if (mdlUsuario == null) {

			throw new SysDescException(FRMLOGIN_MSG_LOGIN);
		}

		return mdlUsuario;
	}

	@Override
	public void validar(Usuario objetoPersistir) {

		if (StringUtil.isNullOrEmpty(objetoPersistir.getUsuario())) {

			throw new SysDescException(MensagemConstants.MENSAGEM_INSIRA_USUARIO);

		}

		if (objetoPersistir.getCliente() == null) {

			throw new SysDescException(MensagemConstants.MENSAGEM_SELECIONE_CLIENTE);

		}

		if (usuarioDAO.verificarUsuarioJaCadastrado(objetoPersistir.getCliente().getIdCliente(),
				objetoPersistir.getIdUsuario())) {

			throw new SysDescException(MensagemConstants.MENSAGEM_CLIENTE_COM_LOGIN);

		}

	}

}
