package br.com.sysdesc.enumerator;

import java.util.HashMap;
import java.util.Map;

public enum TipoContaEnum {

	CONTA_CORRENTE(1L, "Conta Corrente"),

	POUPANCA(2L, "Conta Poupança");

	private static Map<Long, TipoContaEnum> mapa = new HashMap<>();

	static {

		for (TipoContaEnum programa : TipoContaEnum.values()) {
			mapa.put(programa.getCodigo(), programa);
		}
	}

	private final Long codigo;

	private final String descricao;

	TipoContaEnum(Long codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}

	public Long getCodigo() {
		return codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public static TipoContaEnum findByCodigo(Long codigoPrograma) {
		return mapa.get(codigoPrograma);
	}

	@Override
	public String toString() {

		return descricao;
	}
}
