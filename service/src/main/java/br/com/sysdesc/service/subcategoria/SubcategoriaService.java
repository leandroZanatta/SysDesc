package br.com.sysdesc.service.subcategoria;

import com.mysema.query.BooleanBuilder;

import br.com.sysdesc.repository.dao.SubcategoriaDAO;
import br.com.sysdesc.repository.model.Subcategoria;
import br.com.sysdesc.service.interfaces.impl.AbstractGenericService;
import br.com.sysdesc.util.classes.StringUtil;
import br.com.sysdesc.util.constants.MensagemConstants;
import br.com.sysdesc.util.exception.SysDescException;

public class SubcategoriaService extends AbstractGenericService<Subcategoria> {

	private final SubcategoriaDAO subcategoriaDAO;

	public SubcategoriaService() {
		this(new SubcategoriaDAO());
	}

	public SubcategoriaService(SubcategoriaDAO subcategoriaDAO) {
		super(subcategoriaDAO, Subcategoria::getIdSubcategoria);

		this.subcategoriaDAO = subcategoriaDAO;
	}

	@Override
	public void validar(Subcategoria objetoPersistir) {

		if (objetoPersistir.getCategoria() == null) {

			throw new SysDescException(MensagemConstants.MENSAGEM_SELECIONE_CATEGORIA);
		}

		if (StringUtil.isNullOrEmpty(objetoPersistir.getDescricao())) {

			throw new SysDescException(MensagemConstants.MENSAGEM_INSIRA_DESCRICAO_VALIDA);
		}
	}

	public BooleanBuilder getFilterCategoria(Long codigoCategoria) {

		return subcategoriaDAO.getFilterCategoria(codigoCategoria);
	}
}
