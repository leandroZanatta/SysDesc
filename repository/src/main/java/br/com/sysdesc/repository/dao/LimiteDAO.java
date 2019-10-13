package br.com.sysdesc.repository.dao;

import static br.com.sysdesc.repository.model.QLimite.limite;

import br.com.sysdesc.repository.model.Limite;
public class LimiteDAO extends AbstractGenericDAO<Limite> {

	public LimiteDAO() {
		super(limite, limite.codigoCliente);
	}

}
