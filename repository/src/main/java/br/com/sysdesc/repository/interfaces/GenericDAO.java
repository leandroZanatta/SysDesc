package br.com.sysdesc.repository.interfaces;

import java.util.List;

import br.com.sysdesc.repository.model.Pesquisa;

public interface GenericDAO<T> {

	public abstract T previows(Long id);

	public abstract T next(Long id);

	public abstract T last();

	public abstract T first();

	public abstract void salvar(T objetoPesquisa);

	public abstract List<T> pesquisar(boolean selected, String text, Pesquisa pesquisaExibir, Integer rows);

	public abstract Long count(boolean selected, String pesquisa, Pesquisa pesquisaExibir);

}