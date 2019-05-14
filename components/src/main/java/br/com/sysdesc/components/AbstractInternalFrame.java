package br.com.sysdesc.components;

import javax.swing.JInternalFrame;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

import br.com.sysdesc.repository.model.PermissaoPrograma;

public class AbstractInternalFrame extends JInternalFrame {

	private static final long serialVersionUID = 1L;

	protected final ApplicationJframe frmApplication;
	protected final PermissaoPrograma permissaoPrograma;

	public AbstractInternalFrame(ApplicationJframe frmApplication, PermissaoPrograma permissaoPrograma) {

		this.frmApplication = frmApplication;
		this.permissaoPrograma = permissaoPrograma;

		AbstractInternalFrame abstractInternalFrame = this;

		this.addInternalFrameListener(new InternalFrameAdapter() {

			@Override
			public void internalFrameClosed(InternalFrameEvent e) {

				frmApplication.closeFrame(abstractInternalFrame);

				super.internalFrameClosed(e);
			}
		});

	}

}
