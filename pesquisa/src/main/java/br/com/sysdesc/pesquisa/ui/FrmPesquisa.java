package br.com.sysdesc.pesquisa.ui;

import java.awt.BorderLayout;
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

import br.com.sysdesc.pesquisa.enumeradores.PesquisaEnum;
import br.com.sysdesc.pesquisa.enumeradores.TipoTamanhoEnum;
import br.com.sysdesc.pesquisa.models.GenericTableModel;
import br.com.sysdesc.repository.dao.PesquisaDAO;
import br.com.sysdesc.repository.interfaces.GenericDAO;
import br.com.sysdesc.repository.model.Pesquisa;
import br.com.sysdesc.repository.model.PesquisaCampo;
import br.com.sysdesc.util.classes.ContadorUtil;
import br.com.sysdesc.util.classes.ImageUtil;
import net.miginfocom.swing.MigLayout;

public class FrmPesquisa<T> extends JDialog {

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
	private JCheckBox chckbxContm;

	private Boolean ok = Boolean.FALSE;
	private T objeto;
	private final PesquisaEnum pesquisaEnum;
	private final Long codigoUsuario;
	private PesquisaDAO pesquisaDAO = new PesquisaDAO();
	private Pesquisa pesquisaExibir;
	private GenericTableModel<T> genericTableModel;
	private final GenericDAO<T> genericDAO;
	private Integer rows = 0;

	public FrmPesquisa(JFrame parent, PesquisaEnum pesquisaEnum, GenericDAO<T> genericDAO, Long codigoUsuario) {
		super(parent, Boolean.TRUE);

		this.pesquisaEnum = pesquisaEnum;
		this.codigoUsuario = codigoUsuario;
		this.genericDAO = genericDAO;

		this.initComponents();
		this.buscarPesquisa();
		this.carregarCampos();
		this.iniciarPesquisa();
	}

	private void iniciarPesquisa() {

		this.rows = 0;

		genericTableModel.removeAll();

		this.pesquisar();

	}

	private void pesquisar() {

		List<T> valores = genericDAO.pesquisar(chckbxContm.isSelected(), textField.getText(), pesquisaExibir, rows);

		genericTableModel.addRows(valores);

		rows += valores.size();

	}

	private void buscarPesquisa() {

		List<Pesquisa> pesquisa = pesquisaDAO.buscarPesquisaPorUsuario(this.codigoUsuario,
				this.pesquisaEnum.getCodigoPesquisa());

		btConfigurar.setVisible(pesquisa.size() != 1);

		selecionarPesquisa(pesquisa);

	}

	private void selecionarPesquisa(List<Pesquisa> pesquisa) {

		if (pesquisa.size() == 1) {
			this.pesquisaExibir = pesquisa.get(0);

			return;
		}

		this.pesquisaExibir = pesquisa.get(0);

	}

	private void carregarCampos() {

		chckbxContm.setSelected(Boolean.TRUE);

		List<PesquisaCampo> campos = pesquisaExibir.getPesquisaCampos();

		this.genericTableModel = new GenericTableModel<T>(campos);

		this.table.setModel(genericTableModel);

		Long tamanho = 800L;

		Long tamanhoFixo = campos.stream().filter(x -> x.getFlagTipoTamanho().equals(1L))
				.mapToLong(PesquisaCampo::getFlagTipoTamanho).sum();

		Long tamanhoflex = campos.stream().filter(x -> x.getFlagTipoTamanho().equals(2L))
				.mapToLong(PesquisaCampo::getFlagTipoTamanho).sum();

		Long conversaoflex = 0L;

		if (tamanhoflex > 0) {

			conversaoflex = (tamanho - tamanhoFixo) / tamanhoflex;
		}

		ContadorUtil coluna = new ContadorUtil();

		for (PesquisaCampo campo : pesquisaExibir.getPesquisaCampos()) {

			Long tamanhoColuna = 0L;

			if (TipoTamanhoEnum.FLEX.getCodigo().equals(campo.getFlagTipoTamanho())) {

				tamanhoColuna = campo.getNumeroTamanho() * conversaoflex;
			} else {
				tamanhoColuna = campo.getNumeroTamanho();
			}

			table.getColumnModel().getColumn(coluna.getValue().intValue()).setPreferredWidth(tamanhoColuna.intValue());

			coluna.next();
		}
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

		panel.add(btConfigurar, "cell 1 0");

		panel_1 = new JPanel();
		getContentPane().add(panel_1, BorderLayout.NORTH);
		panel_1.setLayout(new MigLayout("", "[63px,left][86px,grow][79px][81px][71px][75px]", "[23px]"));

		chckbxContm = new JCheckBox("Contém");
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

		scrollPane = new JScrollPane();
		getContentPane().add(scrollPane, BorderLayout.CENTER);

		table = new JTable();
		scrollPane.setViewportView(table);

	}

	public Boolean getOk() {
		return ok;
	}

	public T getObjeto() {
		return objeto;
	}

}
