package br.com.sysdesc.components;

import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.event.InternalFrameEvent;

import br.com.sysdesc.repository.model.PermissaoPrograma;

public class AbstractInternalFrame extends JInternalFrame {

	private static final long serialVersionUID = 1L;

	private final PermissaoPrograma permissaoPrograma;
	private final JFrame parent;

	public AbstractInternalFrame(JFrame parent, PermissaoPrograma permissaoPrograma) {

		this.permissaoPrograma = permissaoPrograma;
		this.parent = parent;

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

	public JFrame getParent() {
		return parent;
	}

}
