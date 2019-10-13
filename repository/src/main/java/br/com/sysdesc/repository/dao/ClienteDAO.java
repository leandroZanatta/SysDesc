package br.com.sysdesc.repository.dao;

import static br.com.sysdesc.repository.model.QCliente.cliente;

import com.mysema.query.BooleanBuilder;

import br.com.sysdesc.repository.model.Cliente;
import br.com.sysdesc.util.classes.LongUtil;

public class ClienteDAO extends AbstractGenericDAO<Cliente> {

	public ClienteDAO() {
		super(cliente, cliente.idCliente);
	}

	public Cliente buscarClientePorCpf(String cgc, Long idCliente) {

		BooleanBuilder booleanBuilder = new BooleanBuilder(cliente.cgc.eq(cgc));

		if (!LongUtil.isNullOrZero(idCliente)) {
			booleanBuilder.and(cliente.idCliente.ne(idCliente));
		}

		return from().where(booleanBuilder).singleResult(cliente);
	}

}
