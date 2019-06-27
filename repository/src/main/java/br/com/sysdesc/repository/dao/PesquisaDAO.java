package br.com.sysdesc.repository.dao;

import static br.com.sysdesc.repository.model.QPerfilUsuario.perfilUsuario;
import static br.com.sysdesc.repository.model.QPermissaoPesquisa.permissaoPesquisa;
import static br.com.sysdesc.repository.model.QPesquisa.pesquisa;

import java.util.List;

import br.com.sysdesc.repository.model.Pesquisa;

public class PesquisaDAO extends AbstractGenericDAO<Pesquisa> {

	public PesquisaDAO() {
		super(pesquisa, pesquisa.idPesquisa);
	}

	public List<Pesquisa> buscarConfiguracoesPorPrograma(Long codigoPesquisa) {

		return from().where(pesquisa.codigoPesquisa.eq(codigoPesquisa)).list(pesquisa);
	}

	public List<Pesquisa> buscarPesquisaPorUsuario(Long codigoUsuario, Long codigoPesquisa) {

		return query().from(permissaoPesquisa).innerJoin(pesquisa)
				.on(permissaoPesquisa.codigoPesquisa.eq(pesquisa.idPesquisa))
				.where(pesquisa.codigoPesquisa.eq(codigoPesquisa)
						.and(permissaoPesquisa.codigoUsuario.eq(codigoUsuario)
								.or(permissaoPesquisa.codigoPerfil.in(subQuery().from(perfilUsuario)
										.where(perfilUsuario.codigoUsuario.eq(codigoUsuario))
										.list(perfilUsuario.codigoPerfil)))))
				.list(pesquisa);
	}

}
