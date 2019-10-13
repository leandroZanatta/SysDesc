package br.com.sysdesc.repository.dao;

import static br.com.sysdesc.repository.model.QPdv.pdv;

import com.mysema.query.BooleanBuilder;

import br.com.sysdesc.repository.model.Pdv;
import br.com.sysdesc.util.classes.LongUtil;

public class PdvDAO extends AbstractGenericDAO<Pdv> {

	public PdvDAO() {
		super(pdv, pdv.idPdv);
	}

	public Pdv buscarPorNumeroPDV(Long idPdv, Long numeroPDV) {

		BooleanBuilder booleanBuilder = new BooleanBuilder(pdv.numeroPDV.eq(numeroPDV));

		if (!LongUtil.isNullOrZero(idPdv)) {
			booleanBuilder.and(pdv.idPdv.ne(idPdv));
		}

		return from().where(booleanBuilder).singleResult(pdv);
	}

	public Pdv buscarPorIPPDV(Long idPdv, String ipPDV) {

		BooleanBuilder booleanBuilder = new BooleanBuilder(pdv.ipPDV.eq(ipPDV));

		if (!LongUtil.isNullOrZero(idPdv)) {
			booleanBuilder.and(pdv.idPdv.ne(idPdv));
		}

		return from().where(booleanBuilder).singleResult(pdv);
	}

}
