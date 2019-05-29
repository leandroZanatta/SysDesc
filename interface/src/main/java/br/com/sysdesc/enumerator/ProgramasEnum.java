package br.com.sysdesc.enumerator;

import java.util.HashMap;
import java.util.Map;

import br.com.sysdesc.ui.AbstractInternalFrame;
import br.com.sysdesc.ui.FrmDepartamento;
import br.com.sysdesc.ui.FrmUsuarios;

public enum ProgramasEnum {

	CADASTRO_CLIENTES(2L, FrmUsuarios.class),

	CADASTRO_USUARIOS(3L, FrmUsuarios.class),

	CADASTRO_DEPARTAMENTOS(5L, FrmDepartamento.class);

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
