package br.com.sysdesc.repository.dao;

import static br.com.sysdesc.repository.model.QCidade.cidade;

import java.util.List;

import br.com.sysdesc.repository.model.Cidade;

public class CidadeDAO extends AbstractGenericDAO<Cidade> {

	public CidadeDAO() {
		super(cidade, cidade.idCidade);
	}

	public List<Cidade> buscarCidadePorEstado(Long idEstado) {

		return from().where(cidade.codigoEstado.eq(idEstado)).list(cidade);
	}

}
