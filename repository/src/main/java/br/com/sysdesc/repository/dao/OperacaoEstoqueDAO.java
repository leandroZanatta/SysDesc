package br.com.sysdesc.repository.dao;

import static br.com.sysdesc.repository.model.QOperacaoEstoque.operacaoEstoque;

import br.com.sysdesc.repository.model.OperacaoEstoque;

public class OperacaoEstoqueDAO extends AbstractGenericDAO<OperacaoEstoque> {

	public OperacaoEstoqueDAO() {
		super(operacaoEstoque,operacaoEstoque.idOperacoesEstoque);
	}

}
