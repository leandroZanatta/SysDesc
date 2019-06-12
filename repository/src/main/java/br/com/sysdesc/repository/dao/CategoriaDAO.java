package br.com.sysdesc.repository.dao;

import static br.com.sysdesc.repository.model.QCategoria.categoria;

import br.com.sysdesc.repository.model.Categoria;

public class CategoriaDAO extends AbstractGenericDAO<Categoria> {

	public CategoriaDAO() {
		super(categoria, categoria.idCategoria);
	}

}
