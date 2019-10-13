package br.com.sysdesc.repository.dao;

import static br.com.sysdesc.repository.model.QPesquisa.pesquisa;
import static br.com.sysdesc.repository.model.QPesquisaPadrao.pesquisaPadrao;

import com.mysema.query.jpa.impl.JPADeleteClause;

import br.com.sysdesc.repository.model.PesquisaPadrao;

public class PesquisaPadraoDAO extends AbstractGenericDAO<PesquisaPadrao> {

	public PesquisaPadraoDAO() {
		super(pesquisaPadrao, pesquisaPadrao.codigoPesquisa);
	}

	public void excluirPesquisaUsuario(Long codigoPesquisa, Long codigoUsuario) {

		getEntityManager().getTransaction().begin();

		new JPADeleteClause(getEntityManager(), pesquisaPadrao)
				.where(pesquisaPadrao.codigoUsuario.eq(codigoUsuario)
						.and(pesquisaPadrao.codigoPesquisa.in(subQuery().from(pesquisa)
								.where(pesquisa.codigoPesquisa.eq(codigoPesquisa)).list(pesquisa.idPesquisa))))
				.execute();

		getEntityManager().getTransaction().commit();
	}

}
