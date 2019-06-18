package br.com.sysdesc.repository.dao;

import static br.com.sysdesc.repository.model.QPerfil.perfil;

import br.com.sysdesc.repository.model.Perfil;

public class PerfilDAO extends AbstractGenericDAO<Perfil> {

	public PerfilDAO() {
		super(perfil, perfil.idPerfil);
	}

}
