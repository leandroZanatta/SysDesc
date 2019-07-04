package br.com.sysdesc.repository.dao;

import static br.com.sysdesc.repository.model.QCategoria.categoria;

import java.util.List;

import br.com.sysdesc.repository.model.Categoria;

public class CategoriaDAO extends AbstractGenericDAO<Categoria> {

	public CategoriaDAO() {
		super(categoria, categoria.idCategoria);
	}

	public List<Categoria> buscarPorDepartamento(Long codigoDepartamento) {

		return from().where(categoria.codigoDepartamento.eq(codigoDepartamento)).list(categoria);
	}

}
