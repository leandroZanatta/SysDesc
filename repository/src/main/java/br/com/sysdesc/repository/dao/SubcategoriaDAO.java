package br.com.sysdesc.repository.dao;

import static br.com.sysdesc.repository.model.QSubcategoria.subcategoria;

import br.com.sysdesc.repository.model.Subcategoria;

public class SubcategoriaDAO extends AbstractGenericDAO<Subcategoria> {

	public SubcategoriaDAO() {
		super(subcategoria, subcategoria.idSubcategoria);
	}

}
