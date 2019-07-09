package br.com.sysdesc.service.cliente;

import br.com.sysdesc.repository.dao.ClienteDAO;
import br.com.sysdesc.repository.model.Cliente;
import br.com.sysdesc.service.interfaces.impl.AbstractGenericService;

public class ClienteService extends AbstractGenericService<Cliente> {

	public ClienteService() {
		super(new ClienteDAO(), Cliente::getIdCliente);
	}

}
