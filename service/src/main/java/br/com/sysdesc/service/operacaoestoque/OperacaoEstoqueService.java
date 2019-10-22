package br.com.sysdesc.service.operacaoestoque;

import java.util.Date;
import java.util.List;

import org.w3c.dom.CDATASection;

import br.com.sysdesc.repository.dao.OperacaoEstoqueDAO;
import br.com.sysdesc.repository.model.Departamento;
import br.com.sysdesc.repository.model.OperacaoEstoque;
import br.com.sysdesc.service.interfaces.impl.AbstractGenericService;
import br.com.sysdesc.util.classes.LongUtil;
import br.com.sysdesc.util.classes.StringUtil;
import br.com.sysdesc.util.exception.SysDescException;

public class OperacaoEstoqueService extends AbstractGenericService<OperacaoEstoque> {

	private final OperacaoEstoqueDAO operacaoEstoqueDAO;

	public OperacaoEstoqueService() {
		this(new OperacaoEstoqueDAO());
	}

	public OperacaoEstoqueService(OperacaoEstoqueDAO operacaoEstoqueDAO) {
		super(new OperacaoEstoqueDAO(), OperacaoEstoque::getIdOperacoesEstoque);
		this.operacaoEstoqueDAO = operacaoEstoqueDAO;
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

	public List<OperacaoEstoque> listarOperacaoEstoque() {

		return operacaoEstoqueDAO.listar();
	}

}