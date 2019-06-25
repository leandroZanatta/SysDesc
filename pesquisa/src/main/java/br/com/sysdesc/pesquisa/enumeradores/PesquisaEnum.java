package br.com.sysdesc.pesquisa.enumeradores;

import java.util.HashMap;
import java.util.Map;

import br.com.sysdesc.repository.model.Departamento;
import br.com.sysdesc.repository.model.Estado;
import br.com.sysdesc.repository.model.Marca;

public enum PesquisaEnum {

	PES_ESTADOS(1L, "Estados", Estado.class),

	PES_MARCAS(2L, "Marcas", Marca.class),

	PES_DEPARTAMENTOS(3L, "Departamentos", Departamento.class),

	PES_UNIDADES(4L, "Unidades", Departamento.class),

	PES_CATEGORIAS(5L, "Categorias", Departamento.class);

	private static Map<Long, PesquisaEnum> map = new HashMap<>();

	private final Long codigoPesquisa;

	private final String descricaoPesquisa;

	private final Class<?> clazz;

	static {
		for (PesquisaEnum pesquisaEnum : PesquisaEnum.values()) {
			map.put(pesquisaEnum.codigoPesquisa, pesquisaEnum);
		}
	}

	private PesquisaEnum(Long codigoPesquisa, String descricaoPesquisa, Class<?> clazz) {
		this.codigoPesquisa = codigoPesquisa;
		this.descricaoPesquisa = descricaoPesquisa;
		this.clazz = clazz;
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

	public Class<?> getClazz() {
		return clazz;
	}

	@Override
	public String toString() {
		return descricaoPesquisa;
	}
}