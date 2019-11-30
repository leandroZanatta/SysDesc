package br.com.sysdesc.repository.dao;

import static br.com.sysdesc.repository.model.QKardex.kardex;

import java.util.Date;
import java.util.List;

import com.mysema.query.BooleanBuilder;
import com.mysema.query.jpa.impl.JPAQuery;

import br.com.sysdesc.repository.model.Kardex;

public class KardexDAO extends AbstractGenericDAO<Kardex> {

	public KardexDAO() {
		super(kardex, kardex.idKardex);
	}

	public List<Kardex> buscarRegistroskardex(Long idEmpresa, Long idProduto, String operacao, Date dataInicial,
			Date datafinal) {

		JPAQuery query = from();

		BooleanBuilder booleanBuilder = new BooleanBuilder();

		booleanBuilder.and(kardex.codigoEmpresa.eq(idEmpresa)).and(kardex.codigoProduto.eq(idProduto))
				.and(kardex.dataMovimento.between(dataInicial, datafinal));

		if (!operacao.equals("T")) {

			booleanBuilder.and(kardex.flagOperacao.eq(operacao));
		}

		return query.where(booleanBuilder).list(kardex);
	}

}
