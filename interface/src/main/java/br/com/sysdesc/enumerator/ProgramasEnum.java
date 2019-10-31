package br.com.sysdesc.enumerator;

import java.util.HashMap;
import java.util.Map;

import br.com.sysdesc.components.AbstractInternalFrame;
import br.com.sysdesc.pesquisa.ui.FrmCadastroPesquisa;
import br.com.sysdesc.ui.FrmCategoria;
import br.com.sysdesc.ui.FrmCidade;
import br.com.sysdesc.ui.FrmCliente;
import br.com.sysdesc.ui.FrmDepartamento;
import br.com.sysdesc.ui.FrmEntradaNota;
import br.com.sysdesc.ui.FrmEstado;
import br.com.sysdesc.ui.FrmFormasPagamento;
import br.com.sysdesc.ui.FrmFornecedor;
import br.com.sysdesc.ui.FrmLimite;
import br.com.sysdesc.ui.FrmMarca;
import br.com.sysdesc.ui.FrmModulos;
import br.com.sysdesc.ui.FrmOperacaoEstoque;
import br.com.sysdesc.ui.FrmPDVS;
import br.com.sysdesc.ui.FrmPerfil;
import br.com.sysdesc.ui.FrmPermissoes;
import br.com.sysdesc.ui.FrmPlanoContas;
import br.com.sysdesc.ui.FrmProduto;
import br.com.sysdesc.ui.FrmSubCategoria;
import br.com.sysdesc.ui.FrmUnidade;
import br.com.sysdesc.ui.FrmUsuario;

public enum ProgramasEnum {

	CADASTRO_CLIENTES(5L, FrmCliente.class),

	CADASTRO_PERFIS(2L, FrmPerfil.class),

	CADASTRO_MARCAS(7L, FrmMarca.class),

	CADASTRO_DEPARTAMENTOS(9L, FrmDepartamento.class),

	CADASTRO_ESTADOS(3L, FrmEstado.class),

	CADASTRO_CATEGORIA(10L, FrmCategoria.class),

	CADASTRO_CIDADE(4L, FrmCidade.class),

	CADASTRO_UNIDADE(8L, FrmUnidade.class),

	CADASTRO_PESQUISA(39L, FrmCadastroPesquisa.class),

	CADASTRO_SUBCATEGORIA(11L, FrmSubCategoria.class),

	CADASTRO_PRODUTO(12L, FrmProduto.class),

	CADASTRO_FORMAS_PAGAMENTO(13L, FrmFormasPagamento.class),

	CADASTRO_ENTRADA_NOTA(16L, FrmEntradaNota.class),

	CADASTRO_USUARIOS(40L, FrmUsuario.class),

	CADASTRO_PERMISSOES(41L, FrmPermissoes.class),

	CADASTRO_OPERACAO_ESTOQUE(42L, FrmOperacaoEstoque.class),

	CADASTRO_PLANOCONTAS(32L, FrmPlanoContas.class),

	CADASTRO_FORNECEDOR(15L, FrmFornecedor.class),

	CADASTRO_LIMITES(24L, FrmLimite.class),

	CADASTRO_PDVS(43L, FrmPDVS.class),

	CADASTRO_MODULOS(44L, FrmModulos.class),

	CADASTRO_EMPRESAS(45L, FrmModulos.class);

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
