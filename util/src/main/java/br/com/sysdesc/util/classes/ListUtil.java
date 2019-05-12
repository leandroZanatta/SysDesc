package br.com.sysdesc.util.classes;

import java.util.ArrayList;
import java.util.List;

public class ListUtil {

	@SafeVarargs
	public static <T> List<T> toList(T... array) {

		List<T> lista = new ArrayList<>();
		for (T item : array) {
			lista.add(item);
		}

		return lista;
	}

	public static <T> boolean isNullOrEmpty(List<T> lista) {

		return lista == null || lista.isEmpty();
	}

}
