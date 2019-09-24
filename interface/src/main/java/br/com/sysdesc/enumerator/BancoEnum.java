package br.com.sysdesc.enumerator;

import java.util.HashMap;
import java.util.Map;

public enum BancoEnum {

	BANCO_BRASIL(1L, "Banco do brasil"),

	BANCO_SICREDI(2L, "Banco Sicredi"),

	BANCO_SANTANDER(3L, "Banco Santander"),

	BANCO_SICOOB(4L, "Banco Sicoob"),

	CAIXA(5L, "Caixa");

	private static Map<Long, BancoEnum> mapa = new HashMap<>();

	static {

		for (BancoEnum programa : BancoEnum.values()) {
			mapa.put(programa.getCodigo(), programa);
		}
	}

	private final Long codigo;

	private final String descricao;

	BancoEnum(Long codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}

	public Long getCodigo() {
		return codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public static BancoEnum findByCodigo(Long codigoPrograma) {
		return mapa.get(codigoPrograma);
	}

	@Override
	public String toString() {

		return descricao;
	}
}
