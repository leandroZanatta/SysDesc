package br.com.sysdesc.components;

import javax.swing.JInternalFrame;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

import br.com.sysdesc.repository.dao.AbstractGenericDAO;
import br.com.sysdesc.repository.model.PermissaoPrograma;

public class AbstractInternalFrame<T> extends JInternalFrame {

	private static final long serialVersionUID = 1L;

	private final ApplicationJframe frmApplication;
	private final PermissaoPrograma permissaoPrograma;
	private final AbstractGenericDAO<T> abstractGenericDAO;

	public AbstractInternalFrame(ApplicationJframe frmApplication, PermissaoPrograma permissaoPrograma,
			AbstractGenericDAO<T> abstractGenericDAO) {

		this.frmApplication = frmApplication;
		this.permissaoPrograma = permissaoPrograma;
		this.abstractGenericDAO = abstractGenericDAO;

		AbstractInternalFrame<T> abstractInternalFrame = this;

		this.addInternalFrameListener(new InternalFrameAdapter() {

			@Override
			public void internalFrameClosed(InternalFrameEvent e) {

				frmApplication.closeFrame(abstractInternalFrame);

				super.internalFrameClosed(e);
			}
		});

	}

	public ApplicationJframe getFrmApplication() {
		return frmApplication;
	}

	public PermissaoPrograma getPermissaoPrograma() {
		return permissaoPrograma;
	}

	public AbstractGenericDAO<T> getAbstractGenericDAO() {
		return abstractGenericDAO;
	}

}
