package br.com.sysdesc.repository.dao;

import static br.com.sysdesc.repository.model.QPerfilUsuario.perfilUsuario;

import br.com.sysdesc.repository.model.PerfilUsuario;

public class PerfilUsuarioDAO extends AbstractGenericDAO<PerfilUsuario> {

	public PerfilUsuarioDAO() {
		super(perfilUsuario, perfilUsuario.codigoPerfil);
	}

}
