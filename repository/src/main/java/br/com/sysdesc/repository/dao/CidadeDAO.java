package br.com.sysdesc.repository.dao;

import static br.com.sysdesc.repository.model.QCidade.cidade;

import br.com.sysdesc.repository.model.Cidade;

public class CidadeDAO extends AbstractGenericDAO<Cidade> {

	public CidadeDAO() {
		super(cidade, cidade.idCidade);
	}

}
