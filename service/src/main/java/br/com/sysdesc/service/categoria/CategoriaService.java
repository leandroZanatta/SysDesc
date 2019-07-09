package br.com.sysdesc.service.categoria;

import java.util.List;

import com.mysema.query.BooleanBuilder;

import br.com.sysdesc.repository.dao.CategoriaDAO;
import br.com.sysdesc.repository.model.Categoria;
import br.com.sysdesc.service.interfaces.impl.AbstractGenericService;
import br.com.sysdesc.util.classes.StringUtil;
import br.com.sysdesc.util.constants.MensagemConstants;
import br.com.sysdesc.util.exception.SysDescException;

public class CategoriaService extends AbstractGenericService<Categoria> {

	private final CategoriaDAO categoriaDAO;

	public CategoriaService() {
		this(new CategoriaDAO());
	}

	public CategoriaService(CategoriaDAO categoriaDAO) {
		super(categoriaDAO, Categoria::getIdCategoria);

		this.categoriaDAO = categoriaDAO;
	}

	@Override
	public void validar(Categoria objetoPersistir) {

		if (objetoPersistir.getDepartamento() == null) {

			throw new SysDescException(MensagemConstants.SELECIONE_DEPARTAMENTO);
		}

		if (StringUtil.isNullOrEmpty(objetoPersistir.getDescricao())) {

			throw new SysDescException(MensagemConstants.INSIRA_DESCRICAO_VALIDA);
		}
	}

	public List<Categoria> buscarPorDepartamento(Long codigoDepartamento) {

		return categoriaDAO.buscarPorDepartamento(codigoDepartamento);
	}

	public BooleanBuilder getFilterDepartamento(Long codigoDepartamento) {

		return categoriaDAO.getFilterDepartamento(codigoDepartamento);
	}
}
