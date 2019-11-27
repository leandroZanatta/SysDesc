package br.com.sysdesc.components;

import javax.swing.JInternalFrame;
import javax.swing.event.InternalFrameEvent;

import br.com.sysdesc.repository.model.PermissaoPrograma;

public class AbstractInternalFrame extends JInternalFrame {

	private static final long serialVersionUID = 1L;

	private final PermissaoPrograma permissaoPrograma;

	private final Long codigoUsuario;

	private final Long codigoEmpresa;

	public AbstractInternalFrame(PermissaoPrograma permissaoPrograma, Long codigoUsuario, Long codigoEmpresa) {

		this.permissaoPrograma = permissaoPrograma;
		this.codigoUsuario = codigoUsuario;
		this.codigoEmpresa = codigoEmpresa;

		this.initComponents();
	}

	private void initComponents() {

		this.addInternalFrameListener(new javax.swing.event.InternalFrameAdapter() {

			@Override
			public void internalFrameClosed(InternalFrameEvent e) {

				super.internalFrameClosed(e);
			}
		});

	}

	public PermissaoPrograma getPermissaoPrograma() {
		return permissaoPrograma;
	}

	public Long getCodigoUsuario() {
		return codigoUsuario;
	}

	public Long getCodigoEmpresa() {
		return codigoEmpresa;
	}

}
