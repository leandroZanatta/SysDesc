package br.com.sysdesc.service.operacaoestoque;

import br.com.sysdesc.repository.dao.OperacaoEstoqueDAO;
import br.com.sysdesc.repository.model.OperacaoEstoque;
import br.com.sysdesc.service.interfaces.impl.AbstractGenericService;

public class OperacaoEstoqueService extends AbstractGenericService<OperacaoEstoque> {

	public OperacaoEstoqueService() {
		super(new OperacaoEstoqueDAO(), OperacaoEstoque::getIdOperacoesEstoque);

	}

	@Override
	public void validar(OperacaoEstoque objetoPersistir) {

	}
}