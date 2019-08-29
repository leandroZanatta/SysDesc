package br.com.sysdesc.service.entradas;

import br.com.sysdesc.repository.dao.EntradaCabecalhoDAO;
import br.com.sysdesc.repository.model.EntradaCabecalho;
import br.com.sysdesc.service.interfaces.impl.AbstractGenericService;

public class EntradasService extends AbstractGenericService<EntradaCabecalho> {

	private final EntradaCabecalhoDAO entradaCabecalhoDAO;

	public EntradasService() {

		this(new EntradaCabecalhoDAO());
	}

	public EntradasService(EntradaCabecalhoDAO entradaCabecalhoDAO) {
		super(entradaCabecalhoDAO, EntradaCabecalho::getIdEntradaCabecalho);

		this.entradaCabecalhoDAO = entradaCabecalhoDAO;
	}

	@Override
	public void validar(EntradaCabecalho objetoPersistir) {

	}

}
