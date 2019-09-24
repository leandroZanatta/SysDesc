package br.com.sysdesc.pesquisa.enumeradores;

import java.util.HashMap;
import java.util.Map;

import com.mysema.query.types.path.EntityPathBase;

import br.com.sysdesc.repository.model.QCategoria;
import br.com.sysdesc.repository.model.QCidade;
import br.com.sysdesc.repository.model.QCliente;
import br.com.sysdesc.repository.model.QDepartamento;
import br.com.sysdesc.repository.model.QEntradaCabecalho;
import br.com.sysdesc.repository.model.QEstado;
import br.com.sysdesc.repository.model.QFormasPagamento;
import br.com.sysdesc.repository.model.QMarca;
import br.com.sysdesc.repository.model.QPerfil;
import br.com.sysdesc.repository.model.QPesquisa;
import br.com.sysdesc.repository.model.QPlanoContas;
import br.com.sysdesc.repository.model.QProduto;
import br.com.sysdesc.repository.model.QSubcategoria;
import br.com.sysdesc.repository.model.QUnidade;
import br.com.sysdesc.repository.model.QUsuario;

public enum PesquisaEnum {

	PES_USUARIOS(1L, "Usuários", QUsuario.class),

	PES_PERFIL(2L, "Perfis", QPerfil.class),

	PES_ESTADOS(3L, "Estados", QEstado.class),

	PES_MARCAS(4L, "Marcas", QMarca.class),

	PES_DEPARTAMENTOS(5L, "Departamentos", QDepartamento.class),

	PES_UNIDADES(6L, "Unidades", QUnidade.class),

	PES_CATEGORIAS(7L, "Categorias", QCategoria.class),

	PES_CIDADES(8L, "Cidades", QCidade.class),

	PES_SUBCATEGORIAS(9L, "Subcategorias", QSubcategoria.class),

	PES_CLIENTES(10L, "Clientes", QCliente.class),

	PES_PRODUTOS(11L, "Produtos", QProduto.class),

	PES_PESQUISA(12L, "Pesquisa", QPesquisa.class),

	PES_FORMAS_PAGAMENTO(13L, "Formas de Pagamento", QFormasPagamento.class),

	PES_ENTRADAS(14L, "Notas de Entrada", QEntradaCabecalho.class),

	PES_PLANOCONTAS(15L, "Plano de Contas", QPlanoContas.class);

	private static Map<Long, PesquisaEnum> map = new HashMap<>();

	private final Long codigoPesquisa;

	private final String descricaoPesquisa;

	private final Class<? extends EntityPathBase<?>> entityPath;

	static {
		for (PesquisaEnum pesquisaEnum : PesquisaEnum.values()) {
			map.put(pesquisaEnum.codigoPesquisa, pesquisaEnum);
		}
	}

	private PesquisaEnum(Long codigoPesquisa, String descricaoPesquisa, Class<? extends EntityPathBase<?>> entityPath) {
		this.codigoPesquisa = codigoPesquisa;
		this.descricaoPesquisa = descricaoPesquisa;
		this.entityPath = entityPath;
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

	public Class<? extends EntityPathBase<?>> getEntityPath() {
		return entityPath;
	}

	@Override
	public String toString() {
		return descricaoPesquisa;
	}
}
