package br.com.sysdesc.enumerator;

import java.util.HashMap;
import java.util.Map;

public enum TipoProdutoEnum {

	PRODUTO(1L, "Produto"),

	SERVICO(2L, "Serviço"),

	USO_CONSUMO(3L, "Uso Consumo");

	private static Map<Long, TipoProdutoEnum> mapa = new HashMap<>();

	static {

		for (TipoProdutoEnum programa : TipoProdutoEnum.values()) {
			mapa.put(programa.getCodigo(), programa);
		}
	}

	private final Long codigo;

	private final String descricao;

	TipoProdutoEnum(Long codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}

	public Long getCodigo() {
		return codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public static TipoProdutoEnum findByCodigo(Long codigoPrograma) {
		return mapa.get(codigoPrograma);
	}

	@Override
	public String toString() {

		return descricao;
	}
}
