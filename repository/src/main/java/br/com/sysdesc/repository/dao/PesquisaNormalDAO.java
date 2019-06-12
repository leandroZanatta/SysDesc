package br.com.sysdesc.repository.dao;

import static br.com.sysdesc.repository.model.QPesquisa.pesquisa;

import br.com.sysdesc.repository.model.Pesquisa;
import br.com.sysdesc.util.enumeradores.TipoPesquisaEnum;

public class PesquisaNormalDAO extends AbstractGenericDAO<Pesquisa> {

	public PesquisaNormalDAO() {
		super(pesquisa, pesquisa.idPesquisa);
	}

	@Override
	public Pesquisa next(Pesquisa classeConsulta) {

		if (classeConsulta == null || classeConsulta.getIdPesquisa() == null) {
			return last();
		}

		Pesquisa objeto = from()
				.where(pesquisa.tipo.eq(TipoPesquisaEnum.NORMAL.getCodigo())
						.and(pesquisa.idPesquisa.gt(Long.valueOf(classeConsulta.getIdPesquisa()))))
				.orderBy(pesquisa.idPesquisa.asc()).limit(1L).singleResult(pesquisa);

		if (objeto == null) {
			return first();
		}

		return objeto;
	}

	@Override
	public Pesquisa previows(Pesquisa classeConsulta) {

		if (classeConsulta == null || classeConsulta.getIdPesquisa() == null) {
			return first();
		}

		Pesquisa objeto = from()
				.where(pesquisa.tipo.eq(TipoPesquisaEnum.NORMAL.getCodigo())
						.and(pesquisa.idPesquisa.lt(Long.valueOf(classeConsulta.getIdPesquisa()))))
				.orderBy(pesquisa.idPesquisa.desc()).limit(1L).singleResult(pesquisa);

		if (objeto == null) {
			return last();
		}

		return objeto;
	}

	@Override
	public Pesquisa last() {
		return from().where(pesquisa.tipo.eq(TipoPesquisaEnum.NORMAL.getCodigo())).orderBy(pesquisa.idPesquisa.desc())
				.limit(1L).singleResult(pesquisa);
	}

	@Override
	public Pesquisa first() {
		return from().where(pesquisa.tipo.eq(TipoPesquisaEnum.NORMAL.getCodigo())).orderBy(pesquisa.idPesquisa.asc())
				.limit(1L).singleResult(pesquisa);
	}

}
