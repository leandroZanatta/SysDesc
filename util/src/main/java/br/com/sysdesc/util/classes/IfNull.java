package br.com.sysdesc.util.classes;

public class IfNull {

	public static <T> T get(T objeto, T caseNull) {

		if (objeto == null) {
			return caseNull;
		}

		return objeto;
	}

	public static String getStringEmpty(String value) {

		if (!StringUtil.isNullOrEmptyTrim(value)) {
			return value.trim();
		}

		return null;
	}

	public static String getStringChar(String value, String string) {

		if (StringUtil.isNullOrEmptyTrim(value)) {
			return null;
		}

		if (!value.contains(string)) {
			return value;
		}

		return null;
	}

}
