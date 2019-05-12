package br.com.sysdesc.pesquisa.ui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import br.com.sysdesc.util.classes.ImageUtil;
import net.miginfocom.swing.MigLayout;

public class Pesquisa<T> extends JDialog {

	private static final long serialVersionUID = 1L;

	private JTable table;
	private JButton btConfigurar;
	private JPanel panel;
	private JPanel panel_1;
	private JScrollPane scrollPane;
	private JTextField textField;
	private JButton btnNewButton;
	private JButton btnPesquisar;
	private JButton btnSelecionar;
	private JButton btnCancelar;
	private JCheckBox chckbxContm;

	private Boolean ok = Boolean.FALSE;
	private T objeto;

	public Pesquisa(JFrame frame, Long codigoUsuario, Long codigoPesquisa) {
		super(frame);

		initComponents();

		carregarPesquisa();
	}

	private void carregarPesquisa() {

	}

	private void validarCampos(List<Pesquisa> configuracaoPesquisas) {

		if (configuracaoPesquisas.isEmpty()) {
			this.abrirConfiguracao();
			this.carregarPesquisa();
		}

		this.carregarCampos(configuracaoPesquisas.get(0));
	}

	private void carregarCampos(Pesquisa pesquisa) {

		chckbxContm.setSelected(Boolean.TRUE);

		// this.table.setModel(new
		// GenericTableModel<Usuario>(this.tipoPesquisaEnum.getClassePesquisa(),
		// pesquisa., loginDAO.listar()));
	}

	private void initComponents() {
		setSize(800, 500);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout(0, 0));

		panel = new JPanel();
		getContentPane().add(panel, BorderLayout.SOUTH);
		panel.setLayout(new MigLayout("", "[grow][46px]", "[14px]"));

		JLabel lbRegistros = new JLabel("");
		panel.add(lbRegistros, "cell 0 0,alignx left,aligny top");

		btConfigurar = new JButton("");
		btConfigurar.setIcon(ImageUtil.resize("image//config.png", 24, 24));
		btConfigurar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				abrirConfiguracao();
			}
		});
		panel.add(btConfigurar, "cell 1 0");

		panel_1 = new JPanel();
		getContentPane().add(panel_1, BorderLayout.NORTH);
		panel_1.setLayout(new MigLayout("", "[63px,left][86px,grow][79px][81px][71px][75px]", "[23px]"));

		chckbxContm = new JCheckBox("Cont\u00E9m");
		panel_1.add(chckbxContm, "cell 0 0,alignx left,aligny top");

		textField = new JTextField();
		panel_1.add(textField, "cell 1 0,growx,aligny center");
		textField.setColumns(10);

		btnPesquisar = new JButton("Pesquisar");
		panel_1.add(btnPesquisar, "cell 2 0,alignx left,aligny top");

		btnSelecionar = new JButton("Selecionar");
		panel_1.add(btnSelecionar, "cell 3 0,alignx left,aligny top");

		btnNewButton = new JButton("Imprimir");
		panel_1.add(btnNewButton, "cell 4 0,alignx left,aligny top");

		btnCancelar = new JButton("Cancelar");
		panel_1.add(btnCancelar, "cell 5 0,alignx left,aligny top");

		scrollPane = new JScrollPane();
		getContentPane().add(scrollPane, BorderLayout.CENTER);

		table = new JTable();
		scrollPane.setViewportView(table);

	}

	public void abrirConfiguracao() {
		// new FrmAdicionarCampo(null).setVisible(true);
	}

	public Boolean getOk() {
		return ok;
	}

	public T getObjeto() {
		return objeto;
	}

}
