package br.com.sysdesc.util.enumeradores;

import java.util.HashMap;
import java.util.Map;

public enum PesquisaEnum {

	PES_USUARIOS(1L, "Usuários"),

	PES_PERFIL(2L, "Perfil"),

	PES_ESTADOS(3L, "Estados"),

	PES_MARCAS(4L, "Marcas"),

	PES_DEPARTAMENTOS(5L, "Departamentos"),

	PES_UNIDADES(6L, "Unidades"),

	PES_CATEGORIAS(7L, "Categorias");

	private static Map<Long, PesquisaEnum> map = new HashMap<>();

	private final Long codigoPesquisa;

	private final String descricaoPesquisa;

	static {
		for (PesquisaEnum pesquisaEnum : PesquisaEnum.values()) {
			map.put(pesquisaEnum.codigoPesquisa, pesquisaEnum);
		}
	}

	private PesquisaEnum(Long codigoPesquisa, String descricaoPesquisa) {
		this.codigoPesquisa = codigoPesquisa;
		this.descricaoPesquisa = descricaoPesquisa;
	}

	public static PesquisaEnum forValue(Long value) {
		return map.get(value);
	}

	public Long getCodigoPesquisa() {
		return codigoPesquisa;
	}

	public String getDescricaoPesquisa() {
		return descricaoPesquisa;
	}

	@Override
	public String toString() {
		return descricaoPesquisa;
	}
}
