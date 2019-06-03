package br.com.sysdesc.ui;

import javax.swing.JInternalFrame;
import javax.swing.event.InternalFrameEvent;

public class AbstractInternalFrame extends JInternalFrame {

	private static final long serialVersionUID = 1L;
	private FrmApplication frmApplication;

	public AbstractInternalFrame(FrmApplication frmApplication) {

		this.setFrmApplication(frmApplication);

		AbstractInternalFrame abstractInternalFrame = this;

		this.addInternalFrameListener(new javax.swing.event.InternalFrameAdapter() {

			@Override
			public void internalFrameClosed(InternalFrameEvent e) {

				frmApplication.closeFrame(abstractInternalFrame);

				super.internalFrameClosed(e);
			}
		});

	}

	public FrmApplication getFrmApplication() {
		return frmApplication;
	}

	public void setFrmApplication(FrmApplication frmApplication) {
		this.frmApplication = frmApplication;
	}

}
