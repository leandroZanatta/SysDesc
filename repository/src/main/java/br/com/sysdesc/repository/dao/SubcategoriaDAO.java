package br.com.sysdesc.repository.dao;

import static br.com.sysdesc.repository.model.QSubcategoria.subcategoria;

import com.mysema.query.BooleanBuilder;

import br.com.sysdesc.repository.model.Subcategoria;

public class SubcategoriaDAO extends AbstractGenericDAO<Subcategoria> {

	public SubcategoriaDAO() {
		super(subcategoria, subcategoria.idSubcategoria);
	}

	public BooleanBuilder getFilterCategoria(Long codigoCategoria) {

		return new BooleanBuilder(subcategoria.codigoCategoria.eq(codigoCategoria));
	}

}
