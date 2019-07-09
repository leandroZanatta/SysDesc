package br.com.sysdesc.service.produto;

import br.com.sysdesc.repository.dao.ProdutoDAO;
import br.com.sysdesc.repository.model.Produto;
import br.com.sysdesc.service.interfaces.impl.AbstractGenericService;
import br.com.sysdesc.util.classes.StringUtil;
import br.com.sysdesc.util.constants.MensagemConstants;
import br.com.sysdesc.util.exception.SysDescException;

public class ProdutoService extends AbstractGenericService<Produto> {

	public ProdutoService() {
		super(new ProdutoDAO(), Produto::getIdProduto);
	}

	@Override
	public void validar(Produto objetoPersistir) {

		if (objetoPersistir.getSubcategoria() == null) {

			throw new SysDescException(MensagemConstants.SELECIONE_SUBCATEGORIA);
		}

		if (StringUtil.isNullOrEmpty(objetoPersistir.getDescricao())) {

			throw new SysDescException(MensagemConstants.INSIRA_DESCRICAO_VALIDA);
		}
	}
}
