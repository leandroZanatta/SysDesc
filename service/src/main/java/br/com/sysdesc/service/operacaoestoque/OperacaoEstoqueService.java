package br.com.sysdesc.service.operacaoestoque;

import java.util.Date;

import org.w3c.dom.CDATASection;

import br.com.sysdesc.repository.dao.OperacaoEstoqueDAO;
import br.com.sysdesc.repository.model.OperacaoEstoque;
import br.com.sysdesc.service.interfaces.impl.AbstractGenericService;
import br.com.sysdesc.util.classes.LongUtil;
import br.com.sysdesc.util.classes.StringUtil;
import br.com.sysdesc.util.exception.SysDescException;

public class OperacaoEstoqueService extends AbstractGenericService<OperacaoEstoque> {

	public OperacaoEstoqueService() {
		super(new OperacaoEstoqueDAO(), OperacaoEstoque::getIdOperacoesEstoque);

	}

	@Override
	public void validar(OperacaoEstoque objetoPersistir) {
		Date date = new Date();
		if (StringUtil.isNullOrEmpty(objetoPersistir.getDescricao())) {
			throw new SysDescException("Insira uma descrição");
		}

		if (LongUtil.isNullOrZero(objetoPersistir.getIdOperacoesEstoque())) {
			objetoPersistir.setCadastro(date);
		}
		objetoPersistir.setManutencao(date);
	}
}