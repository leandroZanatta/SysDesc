package br.com.sysdesc.ui;

import br.com.sysdesc.components.AbstractInternalFrame;
import br.com.sysdesc.repository.model.PermissaoPrograma;

public class FrmCliente extends AbstractInternalFrame {

	public FrmCliente(PermissaoPrograma permissaoPrograma, Long codigoUsuario) {
		super(permissaoPrograma, codigoUsuario);

		setSize(450, 210);
		setClosable(Boolean.TRUE);

	}

}
