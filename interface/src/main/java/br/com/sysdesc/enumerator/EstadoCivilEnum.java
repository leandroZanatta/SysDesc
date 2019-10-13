package br.com.sysdesc.enumerator;

import java.util.HashMap;
import java.util.Map;

public enum EstadoCivilEnum {

	SOLTEIRO(1L, "Solteiro"),

	CASADO(2L, "Casado"),

	VIUVO(3L, "Viúvo"),

	DIVORCIADO(4L, "Divorciado");

	private static Map<Long, EstadoCivilEnum> mapa = new HashMap<>();

	static {

		for (EstadoCivilEnum programa : EstadoCivilEnum.values()) {
			mapa.put(programa.getCodigo(), programa);
		}
	}

	private final Long codigo;

	private final String descricao;

	EstadoCivilEnum(Long codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}

	public Long getCodigo() {
		return codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public static EstadoCivilEnum findByCodigo(Long codigoPrograma) {
		return mapa.get(codigoPrograma);
	}

	@Override
	public String toString() {

		return descricao;
	}
}
