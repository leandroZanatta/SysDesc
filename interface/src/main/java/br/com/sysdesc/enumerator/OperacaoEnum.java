package br.com.sysdesc.enumerator;

import java.util.HashMap;
import java.util.Map;

public enum OperacaoEnum {

	TODOS("T", "Todos"),

	ENTRADA("E", "Entrada"),

	SAÍDA("S", "Saída");

	private static Map<String, OperacaoEnum> mapa = new HashMap<>();

	static {

		for (OperacaoEnum programa : OperacaoEnum.values()) {
			mapa.put(programa.getCodigo(), programa);
		}
	}

	private final String codigo;

	private final String descricao;

	OperacaoEnum(String codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}

	public String getCodigo() {
		return codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public static OperacaoEnum findByCodigo(String codigoPrograma) {
		return mapa.get(codigoPrograma);
	}

	@Override
	public String toString() {

		return descricao;
	}
}
