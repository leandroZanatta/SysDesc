package br.com.sysdesc.repository.dao;

import static br.com.sysdesc.repository.model.QUsuario.usuario1;

import com.mysema.query.BooleanBuilder;

import br.com.sysdesc.repository.model.Usuario;
import br.com.sysdesc.util.classes.LongUtil;

public class UsuarioDAO extends AbstractGenericDAO<Usuario> {

	public UsuarioDAO() {
		super(usuario1, usuario1.idUsuario);
	}

	public Usuario obterLogin(String usuario, String md5) {

		return from().where(usuario1.usuario.eq(usuario).and(usuario1.senha.eq(md5))).singleResult(usuario1);
	}

	public boolean verificarUsuarioJaCadastrado(Long idCliente, Long idUsuario) {

		BooleanBuilder booleanBuilder = new BooleanBuilder(usuario1.cliente.idCliente.eq(idCliente));

		if (!LongUtil.isNullOrZero(idUsuario)) {
			booleanBuilder.and(usuario1.idUsuario.ne(idUsuario));
		}

		return from().where(booleanBuilder).exists();
	}

}
