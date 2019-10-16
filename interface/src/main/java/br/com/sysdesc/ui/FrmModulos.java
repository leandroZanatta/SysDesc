package br.com.sysdesc.ui;

import static br.com.sysdesc.util.resources.Resources.FRMESTADO_TITLE;
import static br.com.sysdesc.util.resources.Resources.translate;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.InternalFrameEvent;

import br.com.sysdesc.components.ButtonColumn;
import br.com.sysdesc.repository.model.Pdv;
import br.com.sysdesc.tablemodels.ModulosTableModel;

public class FrmModulos extends JInternalFrame {

	private static final long serialVersionUID = 1L;

	private JPanel painelContent;
	private JTable tbModulo;
	private Pdv pdv;
	private ModulosTableModel model;

	public FrmModulos(Pdv pdv) {
		this.pdv = pdv;

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

		JScrollPane scrollPane = new JScrollPane();
		painelContent.add(scrollPane, BorderLayout.CENTER);

		model = new ModulosTableModel(pdv.getModuloPDVs());
		tbModulo = new JTable(model);

		ButtonColumn buttonColumn = new ButtonColumn(tbModulo, 4);
		ButtonColumn buttonConfiguracoes = new ButtonColumn(tbModulo, 5);

		buttonColumn.addButtonListener(e -> configurarGerenciador(e));
		buttonConfiguracoes.addButtonListener(e -> alterarConfiguracoesModulo(e));
		scrollPane.setViewportView(tbModulo);
	}

	private void alterarConfiguracoesModulo(int collumn) {

	}

	private void configurarGerenciador(int collumn) {

	}

}
