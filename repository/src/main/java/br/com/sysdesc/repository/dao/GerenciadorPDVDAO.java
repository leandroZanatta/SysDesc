package br.com.sysdesc.repository.dao;

import static br.com.sysdesc.repository.model.QGerenciadorPDV.gerenciadorPDV;

import br.com.sysdesc.repository.model.GerenciadorPDV;

public class GerenciadorPDVDAO extends AbstractGenericDAO<GerenciadorPDV> {

	public GerenciadorPDVDAO() {
		super(gerenciadorPDV, gerenciadorPDV.idGerenciadorPDV);
	}

	public GerenciadorPDV buscarPorIp(String idPdv) {

		return from().where(gerenciadorPDV.ipGerenciador.eq(idPdv)).singleResult(gerenciadorPDV);
	}

}
