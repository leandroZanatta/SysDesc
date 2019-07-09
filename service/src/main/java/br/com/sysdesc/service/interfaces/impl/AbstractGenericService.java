package br.com.sysdesc.service.interfaces.impl;

import java.util.List;
import java.util.function.Function;

import com.mysema.query.BooleanBuilder;

import br.com.sysdesc.repository.dao.AbstractGenericDAO;
import br.com.sysdesc.repository.model.Pesquisa;
import br.com.sysdesc.service.interfaces.GenericService;
import br.com.sysdesc.util.constants.MensagemConstants;
import br.com.sysdesc.util.exception.SysDescException;

public abstract class AbstractGenericService<T> implements GenericService<T> {

	private final AbstractGenericDAO<T> abstractGenericDAO;

	private final Function<T, Long> id;

	public AbstractGenericService(AbstractGenericDAO<T> abstractGenericDAO, Function<T, Long> id) {
		this.abstractGenericDAO = abstractGenericDAO;
		this.id = id;
	}

	@Override
	public void salvar(T objetoPersistir) {

		abstractGenericDAO.salvar(objetoPersistir);
	}

	@Override
	public void validar(T objetoPersistir) {
	}

	public Function<T, Long> getId() {
		return id;
	}

	public T previows(Long valueId) {

		return validarObjeto(abstractGenericDAO.previows(valueId));
	};

	public T last() {

		return validarObjeto(abstractGenericDAO.last());
	};

	public T first() {

		return validarObjeto(abstractGenericDAO.first());
	};

	public T next(Long valueId) {

		return validarObjeto(abstractGenericDAO.next(valueId));
	};

	public List<T> pesquisar(boolean selected, String pesquisa, BooleanBuilder preFilter, Pesquisa pesquisaExibir,
			Integer rows) {

		return abstractGenericDAO.pesquisar(selected, pesquisa, preFilter, pesquisaExibir, rows);
	}

	public Long count(boolean selected, String pesquisa, BooleanBuilder preFilter, Pesquisa pesquisaExibir) {

		return abstractGenericDAO.count(selected, pesquisa, preFilter, pesquisaExibir);
	};

	private T validarObjeto(T objetoPesquisa) {

		if (objetoPesquisa == null) {
			throw new SysDescException(MensagemConstants.NENHUM_REGISTRO_ENCONTRADO);
		}

		return objetoPesquisa;
	}
}
