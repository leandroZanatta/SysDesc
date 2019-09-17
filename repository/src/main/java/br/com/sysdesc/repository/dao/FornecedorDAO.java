package br.com.sysdesc.repository.dao;

import static br.com.sysdesc.repository.model.QFornecedor.fornecedor;

import br.com.sysdesc.repository.model.Fornecedor;

public class FornecedorDAO extends AbstractGenericDAO<Fornecedor> {

	public FornecedorDAO() {
		super(fornecedor, fornecedor.idfornecedor);
	}

}
