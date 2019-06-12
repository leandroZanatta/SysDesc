package br.com.sysdesc.enumerator;

import java.util.HashMap;
import java.util.Map;

import br.com.sysdesc.components.AbstractInternalFrame;
import br.com.sysdesc.pesquisa.ui.FrmCadastroPesquisa;
import br.com.sysdesc.ui.FrmCategoria;
import br.com.sysdesc.ui.FrmDepartamento;
import br.com.sysdesc.ui.FrmEstado;
import br.com.sysdesc.ui.FrmMarca;
import br.com.sysdesc.ui.FrmUnidade;
import br.com.sysdesc.ui.FrmUsuarios;

public enum ProgramasEnum {

    CADASTRO_CLIENTES(2L, FrmUsuarios.class),

    CADASTRO_USUARIOS(3L, FrmUsuarios.class),

    CADASTRO_MARCAS(4L, FrmMarca.class),

    CADASTRO_DEPARTAMENTOS(5L, FrmDepartamento.class),

    CADASTRO_ESTADOS(6L, FrmEstado.class),

    CADASTRO_CATEGORIA(7L, FrmCategoria.class),

    CADASTRO_UNIDADE(9L, FrmUnidade.class),

    CADASTRO_PESQUISA(11L, FrmCadastroPesquisa.class);

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
