package br.com.sysdesc.service.login;

import static br.com.sysdesc.util.resources.Resources.FRMLOGIN_MSG_LOGIN;
import static br.com.sysdesc.util.resources.Resources.FRMLOGIN_MSG_SENHA;
import static br.com.sysdesc.util.resources.Resources.FRMLOGIN_MSG_USUARIO;
import static br.com.sysdesc.util.resources.Resources.translate;

import br.com.sysdesc.repository.dao.UsuarioDAO;
import br.com.sysdesc.repository.model.Usuario;
import br.com.sysdesc.service.interfaces.impl.AbstractGenericService;
import br.com.sysdesc.util.classes.CryptoUtil;
import br.com.sysdesc.util.classes.StringUtil;
import br.com.sysdesc.util.exception.SysDescException;

public class LoginService extends AbstractGenericService<Usuario> {

	private final UsuarioDAO usuarioDAO;

	public LoginService() {
		this(new UsuarioDAO());
	}

	public LoginService(UsuarioDAO usuarioDAO) {
		super(usuarioDAO, Usuario::getIdUsuario);

		this.usuarioDAO = usuarioDAO;
	}

	public Usuario efetuarLogin(String usuario, String senha) throws SysDescException {

		if (StringUtil.isNullOrEmpty(usuario)) {

			throw new SysDescException(translate(FRMLOGIN_MSG_USUARIO));

		}
		if (StringUtil.isNullOrEmpty(senha)) {

			throw new SysDescException(translate(FRMLOGIN_MSG_SENHA));

		}

		Usuario mdlUsuario = usuarioDAO.obterLogin(usuario, CryptoUtil.toMD5(senha));

		if (mdlUsuario == null) {

			throw new SysDescException(translate(FRMLOGIN_MSG_LOGIN));
		}

		return mdlUsuario;
	}

}
