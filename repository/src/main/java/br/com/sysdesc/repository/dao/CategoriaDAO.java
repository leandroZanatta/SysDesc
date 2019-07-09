package br.com.sysdesc.repository.dao;

import static br.com.sysdesc.repository.model.QCategoria.categoria;

import java.util.List;

import com.mysema.query.BooleanBuilder;

import br.com.sysdesc.repository.model.Categoria;

public class CategoriaDAO extends AbstractGenericDAO<Categoria> {

	public CategoriaDAO() {
		super(categoria, categoria.idCategoria);
	}

	public List<Categoria> buscarPorDepartamento(Long codigoDepartamento) {

		return from().where(getFilterDepartamento(codigoDepartamento)).list(categoria);
	}

	public BooleanBuilder getFilterDepartamento(Long codigoDepartamento) {

		return new BooleanBuilder().and(categoria.codigoDepartamento.eq(codigoDepartamento));
	}

}
