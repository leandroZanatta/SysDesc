package br.com.sysdesc.repository.enumeradores;

import java.util.HashMap;
import java.util.Map;

public enum FormaPagamentoEnum {

	DINHEIRO(1L, false, "Dinheiro"),

	CHEQUE(1L, true, "Cheque"),

	CONVENIO(1L, true, "Convênio"),

	CARTAO(1L, true, "Cartão");

	private static Map<Long, FormaPagamentoEnum> mapa = new HashMap<>();

	private final Long codigo;

	private final Boolean pagamentoAPrazo;

	private final String descricao;

	static {
		for (FormaPagamentoEnum tipoFieldEnum : FormaPagamentoEnum.values()) {
			mapa.put(tipoFieldEnum.getCodigo(), tipoFieldEnum);
		}
	}

	private FormaPagamentoEnum(Long codigo, Boolean pagamentoAPrazo, String descricao) {
		this.codigo = codigo;
		this.pagamentoAPrazo = pagamentoAPrazo;
		this.descricao = descricao;
	}

	public Long getCodigo() {
		return codigo;
	}

	public Boolean getPagamentoAPrazo() {
		return pagamentoAPrazo;
	}

	public String getDescricao() {
		return descricao;
	}

	@Override
	public String toString() {

		return this.descricao;
	}

	public static FormaPagamentoEnum forCodigo(Long codigo) {

		return mapa.get(codigo);
	}

}
