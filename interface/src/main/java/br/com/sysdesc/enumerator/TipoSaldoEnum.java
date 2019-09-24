package br.com.sysdesc.enumerator;

import java.util.HashMap;
import java.util.Map;

public enum TipoSaldoEnum {

	ATIVO("A", "Ativo"),

	PASSIVO("P", "Inativo"),

	CREDOR("C", "Credor"),

	DEVEDOR("D", "Devedor");

	private static Map<String, TipoSaldoEnum> mapa = new HashMap<>();

	static {

		for (TipoSaldoEnum programa : TipoSaldoEnum.values()) {
			mapa.put(programa.getCodigo(), programa);
		}
	}

	private final String codigo;

	private final String descricao;

	TipoSaldoEnum(String codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}

	public String getCodigo() {
		return codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public static TipoSaldoEnum findByCodigo(String codigoPrograma) {
		return mapa.get(codigoPrograma);
	}

	@Override
	public String toString() {

		return descricao;
	}
}
