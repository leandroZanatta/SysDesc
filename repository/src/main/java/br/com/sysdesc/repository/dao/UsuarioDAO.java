package br.com.sysdesc.repository.dao;

import static br.com.sysdesc.repository.model.QUsuario.usuario1;

import br.com.sysdesc.repository.model.Usuario;

public class UsuarioDAO extends AbstractGenericDAO<Usuario> {

	public UsuarioDAO() {
		super(usuario1, usuario1.idUsuario);
	}

	public Usuario obterLogin(String usuario, String md5) {

		return from().where(usuario1.usuario.eq(usuario).and(usuario1.senha.eq(md5))).singleResult(usuario1);
	}

}
