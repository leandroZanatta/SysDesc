package br.com.sysdesc.repository.dao;

import static br.com.sysdesc.repository.model.QEntradaCabecalho.entradaCabecalho;

import br.com.sysdesc.repository.model.EntradaCabecalho;

public class EntradaCabecalhoDAO extends AbstractGenericDAO<EntradaCabecalho> {

	public EntradaCabecalhoDAO() {
		super(entradaCabecalho, entradaCabecalho.idEntradaCabecalho);
	}

}
