package br.com.sysdesc.enumerator;

import java.util.HashMap;
import java.util.Map;

public enum TipoStatusEnum {

	ATIVO(1L, "Ativo"),

	INATIVO(2L, "Inativo");

	private static Map<Long, TipoStatusEnum> mapa = new HashMap<>();

	static {

		for (TipoStatusEnum programa : TipoStatusEnum.values()) {
			mapa.put(programa.getCodigo(), programa);
		}
	}

	private final Long codigo;

	private final String descricao;

	TipoStatusEnum(Long codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}

	public Long getCodigo() {
		return codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public static TipoStatusEnum findByCodigo(Long codigoPrograma) {
		return mapa.get(codigoPrograma);
	}

	@Override
	public String toString() {

		return descricao;
	}
}
