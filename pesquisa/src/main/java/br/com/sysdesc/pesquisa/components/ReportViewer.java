package br.com.sysdesc.pesquisa.components;

import java.awt.BorderLayout;
import java.awt.Toolkit;

import javax.swing.JDialog;
import javax.swing.JPanel;

import net.sf.jasperreports.engine.DefaultJasperReportsContext;
import net.sf.jasperreports.engine.JasperPrint;

public class ReportViewer extends JDialog {

	private static final long serialVersionUID = 1L;

	protected net.sf.jasperreports.swing.JRViewer viewer;
	private JPanel pnlMain;

	public ReportViewer(JDialog parent, JasperPrint jasperPrint) {
		super(parent, Boolean.TRUE);

		initComponents();

		this.viewer = new net.sf.jasperreports.swing.JRViewer(DefaultJasperReportsContext.getInstance(), jasperPrint,
				null, null);

		this.pnlMain.add(this.viewer, BorderLayout.CENTER);

	}

	private void initComponents() {
		pnlMain = new javax.swing.JPanel();

		setTitle("JasperViewer");
		setIconImage(new javax.swing.ImageIcon(getClass().getResource("/net/sf/jasperreports/view/images/jricon.GIF"))
				.getImage());
		addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent evt) {
				exitForm();
			}
		});

		pnlMain.setLayout(new java.awt.BorderLayout());

		getContentPane().add(pnlMain, java.awt.BorderLayout.CENTER);

		pack();

		Toolkit toolkit = java.awt.Toolkit.getDefaultToolkit();
		java.awt.Dimension screenSize = toolkit.getScreenSize();
		int screenResolution = toolkit.getScreenResolution();
		float zoom = ((float) screenResolution) / net.sf.jasperreports.swing.JRViewerPanel.REPORT_RESOLUTION;

		int height = (int) (550 * zoom);

		if (height > screenSize.getHeight()) {
			height = (int) screenSize.getHeight();
		}

		int width = (int) (750 * zoom);
		if (width > screenSize.getWidth()) {
			width = (int) screenSize.getWidth();
		}

		java.awt.Dimension dimension = new java.awt.Dimension(width, height);
		setSize(dimension);
		setLocation((screenSize.width - width) / 2, (screenSize.height - height) / 2);

	}

	private void exitForm() {

		this.setVisible(false);
		this.viewer.clear();
		this.viewer = null;
		this.getContentPane().removeAll();
		this.dispose();

	}

}
