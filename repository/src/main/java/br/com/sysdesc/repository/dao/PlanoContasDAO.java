package br.com.sysdesc.repository.dao;

import static br.com.sysdesc.repository.model.QPlanoContas.planoContas1;

import com.mysema.query.BooleanBuilder;

import br.com.sysdesc.repository.model.PlanoContas;
import br.com.sysdesc.util.classes.LongUtil;

public class PlanoContasDAO extends AbstractGenericDAO<PlanoContas> {

	public PlanoContasDAO() {
		super(planoContas1, planoContas1.idPlanoContas);
	}

	public Long getNextIdentifier(Long idPlanoContas) {

		BooleanBuilder booleanBuilder = new BooleanBuilder();

		if (LongUtil.isNullOrZero(idPlanoContas)) {
			booleanBuilder.and(planoContas1.contaPrincipal.isNull());
		} else {
			booleanBuilder.and(planoContas1.contaPrincipal.eq(idPlanoContas));
		}

		return from().where(booleanBuilder).count();
	}

	public BooleanBuilder getContasAnaliticas() {

		return new BooleanBuilder(planoContas1.contaAnalitica.eq(Boolean.FALSE));
	}

}
