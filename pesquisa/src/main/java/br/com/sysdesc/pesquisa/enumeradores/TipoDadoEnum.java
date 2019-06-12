package br.com.sysdesc.pesquisa.enumeradores;

import java.util.HashMap;
import java.util.Map;

public enum TipoDadoEnum {

	LONG(1L, Long.class), STRING(2L, String.class);

	private Long codigo;

	private Class<?> classe;

	private static Map<Long, TipoDadoEnum> map = new HashMap<>();

	private static Map<Class<?>, TipoDadoEnum> dadoClasse = new HashMap<>();

	static {
		for (TipoDadoEnum tipoDadoEnum : TipoDadoEnum.values()) {
			map.put(tipoDadoEnum.getCodigo(), tipoDadoEnum);
			dadoClasse.put(tipoDadoEnum.getClasse(), tipoDadoEnum);
		}

	}

	private TipoDadoEnum(Long codigo, Class<?> classe) {
		this.codigo = codigo;
		this.classe = classe;
	}

	public Long getCodigo() {
		return codigo;
	}

	public Class<?> getClasse() {
		return classe;
	}

	public static TipoDadoEnum forClass(Class<?> classe) {
		return dadoClasse.get(classe);
	}

	public static TipoDadoEnum tipoDadoForCodigo(Long codigo) {
		return map.get(codigo);
	}

}