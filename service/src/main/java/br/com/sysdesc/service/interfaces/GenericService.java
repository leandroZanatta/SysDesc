package br.com.sysdesc.service.interfaces;

import java.util.List;

import com.mysema.query.BooleanBuilder;

import br.com.sysdesc.repository.model.Pesquisa;

public interface GenericService<T> {

	public void validar(T objetoPersistir);

	public void salvar(T objetoPersistir);

	public T previows(Long valueId);

	public T next(Long valueId);

	public T last();

	public T first();

	public List<T> pesquisar(boolean selected, String text, BooleanBuilder preFilter, Pesquisa pesquisaExibir,
			Integer rows);

	public Long count(boolean selected, String text, BooleanBuilder preFilter, Pesquisa pesquisaExibir);

}
