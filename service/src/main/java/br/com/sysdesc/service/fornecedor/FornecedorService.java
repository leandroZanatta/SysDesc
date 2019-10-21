package br.com.sysdesc.service.fornecedor;

import br.com.sysdesc.repository.dao.FornecedorDAO;
import br.com.sysdesc.repository.model.Fornecedor;
import br.com.sysdesc.service.interfaces.impl.AbstractGenericService;
import br.com.sysdesc.util.exception.SysDescException;

public class FornecedorService extends AbstractGenericService<Fornecedor> {

	public FornecedorService() {
		super(new FornecedorDAO(), Fornecedor::getIdfornecedor);
	}

	@Override
	public void validar(Fornecedor objetoPersistir) {

		if (objetoPersistir.getCliente() == null) {
			throw new SysDescException("Selecione um cliente");
		}
	}

}
