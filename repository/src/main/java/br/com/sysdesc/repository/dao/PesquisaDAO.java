package br.com.sysdesc.repository.dao;

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

}
