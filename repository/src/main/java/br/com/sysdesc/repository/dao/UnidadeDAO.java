package br.com.sysdesc.repository.dao;

import static br.com.sysdesc.repository.model.QUnidade.unidade;

import br.com.sysdesc.repository.model.Unidade;

public class UnidadeDAO extends AbstractGenericDAO<Unidade> {

	public UnidadeDAO() {
		super(unidade, unidade.idUnidade);
	}

}
