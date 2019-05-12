package br.com.sysdesc.util.classes;

public class IfNull {

	public static <T> T get(T objeto, T caseNull) {

		if (objeto == null) {
			return caseNull;
		}

		return objeto;
	}

}
