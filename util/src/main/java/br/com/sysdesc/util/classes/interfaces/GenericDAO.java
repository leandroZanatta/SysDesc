package br.com.sysdesc.util.classes.interfaces;

public interface GenericDAO<T> {

	public abstract T previows(T objetoPesquisa);

	public abstract T next(T objetoPesquisa);

	public abstract T last();

	public abstract T first();

	public abstract void salvar(T objetoPesquisa);

}
