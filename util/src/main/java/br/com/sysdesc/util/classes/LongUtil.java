package br.com.sysdesc.util.classes;

public class LongUtil {

	public static Boolean isNullOrZero(Long value) {

		return value == null || value.equals(0L);
	}

}
