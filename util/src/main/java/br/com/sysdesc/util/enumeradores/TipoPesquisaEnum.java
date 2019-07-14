package br.com.sysdesc.util.enumeradores;

import java.util.HashMap;
import java.util.Map;

public enum TipoPesquisaEnum {

	NORMAL(0L), AVANCADA(1L);

	private Long codigo;

	private static Map<Long, TipoPesquisaEnum> mapa = new HashMap<>();

	static {
		for (TipoPesquisaEnum tipoTamanho : TipoPesquisaEnum.values()) {
			mapa.put(tipoTamanho.getCodigo(), tipoTamanho);
		}

	}

	private TipoPesquisaEnum(Long codigo) {
		this.codigo = codigo;
	}

	public Long getCodigo() {
		return codigo;
	}

	public static TipoPesquisaEnum tipoTamanhoForCodigo(Long codigo) {
		return mapa.get(codigo);
	}

}