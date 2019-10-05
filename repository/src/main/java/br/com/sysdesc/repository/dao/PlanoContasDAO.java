package br.com.sysdesc.repository.dao;

import static br.com.sysdesc.repository.model.QPlanoContas.planoContas1;

import com.mysema.query.BooleanBuilder;

import br.com.sysdesc.repository.model.PlanoContas;

public class PlanoContasDAO extends AbstractGenericDAO<PlanoContas> {

	public PlanoContasDAO() {
		super(planoContas1, planoContas1.idPlanoContas);
	}

	public Long getNextIdentifier(Long idPlanoContas) {

		return from().where(planoContas1.contaPrincipal.eq(idPlanoContas)).count();
	}

	public BooleanBuilder getContasAnaliticas() {

		return new BooleanBuilder(planoContas1.contaAnalitica.eq(Boolean.FALSE));
	}

}
