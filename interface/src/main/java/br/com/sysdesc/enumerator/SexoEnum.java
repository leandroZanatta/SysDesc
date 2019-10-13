package br.com.sysdesc.enumerator;

import java.util.HashMap;
import java.util.Map;

public enum SexoEnum {

	MASCULINO("M", "Masculino"),

	FEMININO("F", "Feminino"),

	OUTROS("O", "Outros");

	private static Map<String, SexoEnum> mapa = new HashMap<>();

	static {

		for (SexoEnum programa : SexoEnum.values()) {
			mapa.put(programa.getCodigo(), programa);
		}
	}

	private final String codigo;

	private final String descricao;

	SexoEnum(String codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}

	public String getCodigo() {
		return codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public static SexoEnum findByCodigo(String codigoPrograma) {
		return mapa.get(codigoPrograma);
	}

	@Override
	public String toString() {

		return descricao;
	}
}
