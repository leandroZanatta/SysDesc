package br.com.sysdesc.util.classes;

import java.lang.reflect.ParameterizedType;

public class ClassTypeUtil {

	@SuppressWarnings("unchecked")
	public static <T> Class<T> getGenericType(Class<?> classePai) {

		final ParameterizedType type = (ParameterizedType) classePai.getGenericSuperclass();

		Class<T> clazz = (Class<T>) type.getActualTypeArguments()[0];

		return clazz;
	}

}
