package br.com.sysdesc.service.produto;

import br.com.sysdesc.repository.dao.ProdutoDAO;
import br.com.sysdesc.repository.model.Produto;
import br.com.sysdesc.service.interfaces.impl.AbstractGenericService;
import br.com.sysdesc.util.classes.BigDecimalUtil;
import br.com.sysdesc.util.classes.StringUtil;
import br.com.sysdesc.util.constants.MensagemConstants;
import br.com.sysdesc.util.exception.SysDescException;

public class ProdutoService extends AbstractGenericService<Produto> {

	public ProdutoService() {
		super(new ProdutoDAO(), Produto::getIdProduto);
	}

	@Override
	public void validar(Produto objetoPersistir) {

		if (StringUtil.isNullOrEmpty(objetoPersistir.getDescricao())) {

			throw new SysDescException(MensagemConstants.MENSAGEM_INSIRA_DESCRICAO_VALIDA);
		}

		if (objetoPersistir.getSubcategoria() == null) {

			throw new SysDescException(MensagemConstants.MENSAGEM_SELECIONE_SUBCATEGORIA);
		}

		if (objetoPersistir.getMarca() == null) {

			throw new SysDescException(MensagemConstants.MENSAGEM_SELECIONE_MARCA);
		}

		if (objetoPersistir.getUnidade() == null) {

			throw new SysDescException(MensagemConstants.MENSAGEM_SELECIONE_UNIDADE);
		}

		if (BigDecimalUtil.maior(objetoPersistir.getValorEstoqueMinimo(), objetoPersistir.getValorEstoqueMaximo())) {

			throw new SysDescException(MensagemConstants.MENSAGEM_VALOR_ESTOQUE_MINIMO_MAIOR_MAXIMO);
		}

		if (objetoPersistir.getCodigoTipo() == null) {

			throw new SysDescException(MensagemConstants.MENSAGEM_SELECIONE_TIPO);
		}
		if (objetoPersistir.getCodigoStatus() == null) {

			throw new SysDescException(MensagemConstants.MENSAGEM_SELECIONE_STATUS);
		}
	}
}
