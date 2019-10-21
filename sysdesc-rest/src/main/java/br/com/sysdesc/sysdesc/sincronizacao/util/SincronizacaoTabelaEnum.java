package br.com.sysdesc.sysdesc.sincronizacao.util;

import java.util.HashMap;
import java.util.Map;

public enum SincronizacaoTabelaEnum {

	PRODUTO(1L, 1L);

	private static Map<Long, SincronizacaoTabelaEnum> map = new HashMap<>();
	private Long codigoTabela;
	private Long ordem;

	SincronizacaoTabelaEnum(Long codigoTabela, Long ordem) {
		this.codigoTabela = codigoTabela;
		this.ordem = ordem;
	}

	static {
		for (SincronizacaoTabelaEnum tipo : SincronizacaoTabelaEnum.values()) {
			map.put(tipo.codigoTabela, tipo);
		}
	}

	public static SincronizacaoTabelaEnum valueOf(Long tipo) {
		return map.get(tipo);
	}

	public Long getCodigoTabela() {
		return codigoTabela;
	}

	public Long getOrdem() {
		return ordem;
	}

}
