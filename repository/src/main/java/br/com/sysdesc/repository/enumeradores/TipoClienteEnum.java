package br.com.sysdesc.repository.enumeradores;

import java.util.HashMap;
import java.util.Map;

public enum TipoClienteEnum {

	PESSOA_FISICA("F"),

	PESSOA_JURIDICA("J");

	private static Map<String, TipoClienteEnum> mapa = new HashMap<>();

	static {

		for (TipoClienteEnum programa : TipoClienteEnum.values()) {
			mapa.put(programa.getCodigo(), programa);
		}
	}

	private final String codigo;

	public String getCodigo() {
		return codigo;
	}

	TipoClienteEnum(String codigo) {
		this.codigo = codigo;
	}

	public static TipoClienteEnum findByCodigo(String tipoCliente) {
		return mapa.get(tipoCliente);
	}

}
