package br.com.sysdesc.ui;

import static br.com.sysdesc.util.resources.Resources.FRMESTADO_TITLE;
import static br.com.sysdesc.util.resources.Resources.translate;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.event.InternalFrameEvent;

import br.com.sysdesc.components.AbstractInternalFrame;
import br.com.sysdesc.repository.model.PermissaoPrograma;

public class FrmModulos extends AbstractInternalFrame {

	private static final long serialVersionUID = 1L;

	private JPanel painelContent;

	public FrmModulos(PermissaoPrograma permissaoPrograma, Long codigoUsuario) {
		super(permissaoPrograma, codigoUsuario);

		initComponents();

		this.addInternalFrameListener(new javax.swing.event.InternalFrameAdapter() {

			@Override
			public void internalFrameClosed(InternalFrameEvent e) {

				super.internalFrameClosed(e);
			}
		});
	}

	private void initComponents() {

		setSize(450, 230);
		setClosable(Boolean.TRUE);
		setTitle(translate(FRMESTADO_TITLE));

		painelContent = new JPanel();
		getContentPane().add(painelContent);
		painelContent.setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		painelContent.add(panel, BorderLayout.SOUTH);

		JButton btSalvar = new JButton("Salvar");
		panel.add(btSalvar);

		JButton btCancelar = new JButton("Cancelar");
		btCancelar.addActionListener((e) -> dispose());
		panel.add(btCancelar);

	}

}
