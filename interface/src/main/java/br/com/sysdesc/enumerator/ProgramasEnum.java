package br.com.sysdesc.enumerator;

import java.util.HashMap;
import java.util.Map;

import br.com.sysdesc.components.AbstractInternalFrame;
import br.com.sysdesc.pesquisa.ui.FrmCadastroPesquisa;
import br.com.sysdesc.ui.FrmCategoria;
import br.com.sysdesc.ui.FrmCidade;
import br.com.sysdesc.ui.FrmCliente;
import br.com.sysdesc.ui.FrmDepartamento;
import br.com.sysdesc.ui.FrmEstado;
import br.com.sysdesc.ui.FrmMarca;
import br.com.sysdesc.ui.FrmPerfil;
import br.com.sysdesc.ui.FrmProduto;
import br.com.sysdesc.ui.FrmSubCategoria;
import br.com.sysdesc.ui.FrmUnidade;
import br.com.sysdesc.ui.FrmUsuario;

public enum ProgramasEnum {

	CADASTRO_CLIENTES(2L, FrmCliente.class),

	CADASTRO_PERFIS(3L, FrmPerfil.class),

	CADASTRO_USUARIOS(4L, FrmUsuario.class),

	CADASTRO_MARCAS(5L, FrmMarca.class),

	CADASTRO_DEPARTAMENTOS(6L, FrmDepartamento.class),

	CADASTRO_ESTADOS(7L, FrmEstado.class),

	CADASTRO_CATEGORIA(8L, FrmCategoria.class),

	CADASTRO_CIDADE(9L, FrmCidade.class),

	CADASTRO_UNIDADE(10L, FrmUnidade.class),

	CADASTRO_PESQUISA(12L, FrmCadastroPesquisa.class),

	CADASTRO_SUBCATEGORIA(13L, FrmSubCategoria.class),

	CADASTRO_PRODUTO(14L, FrmProduto.class);

	private static Map<Long, ProgramasEnum> mapa = new HashMap<>();

	static {

		for (ProgramasEnum programa : ProgramasEnum.values()) {
			mapa.put(programa.getCodigo(), programa);
		}
	}

	private final Long codigo;

	private final Class<? extends AbstractInternalFrame> internalFrame;

	ProgramasEnum(Long codigo, Class<? extends AbstractInternalFrame> internalFrame) {
		this.codigo = codigo;
		this.internalFrame = internalFrame;
	}

	public Long getCodigo() {
		return codigo;
	}

	public Class<? extends AbstractInternalFrame> getInternalFrame() {
		return internalFrame;
	}

	public static ProgramasEnum findByCodigo(Long codigoPrograma) {
		return mapa.get(codigoPrograma);
	}
}
