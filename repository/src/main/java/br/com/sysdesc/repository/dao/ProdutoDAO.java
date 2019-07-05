package br.com.sysdesc.repository.dao;

import static br.com.sysdesc.repository.model.QProduto.produto;

import br.com.sysdesc.repository.model.Produto;

public class ProdutoDAO extends AbstractGenericDAO<Produto> {

	public ProdutoDAO() {
		super(produto, produto.idProduto);
	}

}
