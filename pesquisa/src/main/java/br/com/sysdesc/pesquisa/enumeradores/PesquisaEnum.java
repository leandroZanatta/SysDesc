package br.com.sysdesc.pesquisa.enumeradores;

import java.util.HashMap;
import java.util.Map;

import br.com.sysdesc.repository.model.Cidade;
import br.com.sysdesc.repository.model.Cliente;
import br.com.sysdesc.repository.model.Departamento;
import br.com.sysdesc.repository.model.Estado;
import br.com.sysdesc.repository.model.Marca;
import br.com.sysdesc.repository.model.Perfil;
import br.com.sysdesc.repository.model.Produto;
import br.com.sysdesc.repository.model.Subcategoria;
import br.com.sysdesc.repository.model.Usuario;

public enum PesquisaEnum {

	PES_USUARIOS(1L, "Usuários", Usuario.class),

	PES_PERFIL(2L, "Perfis", Perfil.class),

	PES_ESTADOS(3L, "Estados", Estado.class),

	PES_MARCAS(4L, "Marcas", Marca.class),

	PES_DEPARTAMENTOS(5L, "Departamentos", Departamento.class),

	PES_UNIDADES(6L, "Unidades", Departamento.class),

	PES_CATEGORIAS(7L, "Categorias", Departamento.class),

	PES_CIDADES(8L, "Cidades", Cidade.class),

	PES_SUBCATEGORIAS(9L, "Cidades", Subcategoria.class),

	PES_CLIENTES(10L, "Clientes", Cliente.class),

	PES_PRODUTOS(11L, "Produtos", Produto.class);

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
