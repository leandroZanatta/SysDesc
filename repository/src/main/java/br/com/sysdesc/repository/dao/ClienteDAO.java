package br.com.sysdesc.repository.dao;

import static br.com.sysdesc.repository.model.QCliente.cliente;

import br.com.sysdesc.repository.model.Cliente;

public class ClienteDAO extends AbstractGenericDAO<Cliente> {

	public ClienteDAO() {
		super(cliente, cliente.idCliente);
	}

}
