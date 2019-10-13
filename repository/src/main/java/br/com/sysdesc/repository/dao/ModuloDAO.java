package br.com.sysdesc.repository.dao;

import static br.com.sysdesc.repository.model.QModulo.modulo;

import br.com.sysdesc.repository.model.Modulo;

public class ModuloDAO extends AbstractGenericDAO<Modulo> {

	public ModuloDAO() {
		super(modulo, modulo.idModulo);
	}

}
