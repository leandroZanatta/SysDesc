package br.com.sysdesc.repository.dao;

import static br.com.sysdesc.repository.model.QFormasPagamento.formasPagamento;

import br.com.sysdesc.repository.model.FormasPagamento;

public class FormasPagamentoDAO extends AbstractGenericDAO<FormasPagamento> {

	public FormasPagamentoDAO() {
		super(formasPagamento, formasPagamento.idFormaPagamento);
	}

}
