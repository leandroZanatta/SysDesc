package br.com.sysdesc.pesquisa.enumeradores;

import java.util.HashMap;
import java.util.Map;

public enum TipoTamanhoEnum {

	PIXELS(1L), FLEX(2L);

	private Long codigo;

	private static Map<Long, TipoTamanhoEnum> mapa = new HashMap<>();

	static {
		for (TipoTamanhoEnum tipoTamanho : TipoTamanhoEnum.values()) {
			mapa.put(tipoTamanho.getCodigo(), tipoTamanho);
		}

	}

	private TipoTamanhoEnum(Long codigo) {
		this.codigo = codigo;
	}

	public Long getCodigo() {
		return codigo;
	}

	public static TipoTamanhoEnum tipoTamanhoForCodigo(Long codigo) {
		return mapa.get(codigo);
	}

}