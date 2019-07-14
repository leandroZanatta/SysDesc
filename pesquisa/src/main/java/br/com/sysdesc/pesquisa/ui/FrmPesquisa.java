package br.com.sysdesc.pesquisa.ui;

import static br.com.sysdesc.util.constants.MensagemConstants.MENSAGEM_SELECIONE_APENAS_UM_REGISTRO;
import static br.com.sysdesc.util.constants.MensagemConstants.MENSAGEM_SELECIONE_UM_REGISTRO;
import static br.com.sysdesc.util.resources.Resources.FRMLOGIN_MSG_VERIFICACAO;
import static br.com.sysdesc.util.resources.Resources.translate;

import java.awt.BorderLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.mysema.query.BooleanBuilder;

import br.com.sysdesc.components.JTextFieldMaiusculo;
import br.com.sysdesc.pesquisa.enumeradores.PesquisaEnum;
import br.com.sysdesc.pesquisa.enumeradores.TipoTamanhoEnum;
import br.com.sysdesc.pesquisa.models.GenericTableModel;
import br.com.sysdesc.repository.dao.PesquisaDAO;
import br.com.sysdesc.repository.model.Pesquisa;
import br.com.sysdesc.repository.model.PesquisaCampo;
import br.com.sysdesc.service.interfaces.impl.AbstractGenericService;
import br.com.sysdesc.util.classes.ContadorUtil;
import br.com.sysdesc.util.classes.ImageUtil;
import net.miginfocom.swing.MigLayout;

public class FrmPesquisa<T> extends JDialog {

	private static final long serialVersionUID = 1L;

	private static final String STR_REGISTROS = "Exibindo %d de %d registros";
	private JTable table;
	private JButton btConfigurar;
	private JPanel panel;
	private JPanel panel_1;
	private JScrollPane scrollPane;
	private JTextFieldMaiusculo textField;
	private JButton btImprimir;
	private JButton btnPesquisar;
	private JButton btnSelecionar;
	private JCheckBox chckbxContm;

	private Boolean ok = Boolean.FALSE;
	private List<T> objeto = new ArrayList<>();
	private final PesquisaEnum pesquisaEnum;
	private final Long codigoUsuario;
	private PesquisaDAO pesquisaDAO = new PesquisaDAO();
	private Pesquisa pesquisaExibir;
	private GenericTableModel<T> genericTableModel;
	private final AbstractGenericService<T> genericService;
	private Integer rows = 0;
	private Long numeroregistros = 0L;
	private JLabel lbRegistros;
	private final Boolean multiselect;
	private final BooleanBuilder preFilter;

	public FrmPesquisa(JFrame parent, PesquisaEnum pesquisaEnum, BooleanBuilder preFilter,
			AbstractGenericService<T> genericService, Long codigoUsuario) {
		this(parent, pesquisaEnum, preFilter, genericService, codigoUsuario, Boolean.FALSE);
	}

	public FrmPesquisa(JFrame parent, PesquisaEnum pesquisaEnum, BooleanBuilder preFilter,
			AbstractGenericService<T> genericService, Long codigoUsuario, Boolean multiselect) {
		super(parent, Boolean.TRUE);

		this.pesquisaEnum = pesquisaEnum;
		this.codigoUsuario = codigoUsuario;
		this.preFilter = preFilter;
		this.genericService = genericService;
		this.multiselect = multiselect;

		this.initComponents();
		this.buscarPesquisa();
		this.carregarCampos();
		this.iniciarPesquisa();
	}

	private void initComponents() {
		setSize(800, 450);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout(0, 0));

		panel = new JPanel();
		getContentPane().add(panel, BorderLayout.SOUTH);
		panel.setLayout(new MigLayout("", "[grow][][]", "[14px]"));

		lbRegistros = new JLabel("");
		panel.add(lbRegistros, "flowx,cell 0 0,alignx left,aligny center");

		btConfigurar = new JButton("");
		btConfigurar.setIcon(ImageUtil.resize("config.png", 20, 20));

		panel.add(btConfigurar, "cell 1 0");

		btImprimir = new JButton("");
		panel.add(btImprimir, "cell 2 0,grow");
		btImprimir.setIcon(ImageUtil.resize("print.png", 20, 20));
		panel_1 = new JPanel();
		getContentPane().add(panel_1, BorderLayout.NORTH);
		panel_1.setLayout(new MigLayout("", "[63px,left][86px,grow][79px][81px]", "[23px]"));

		chckbxContm = new JCheckBox("Contém");
		panel_1.add(chckbxContm, "cell 0 0,alignx left,aligny top");

		textField = new JTextFieldMaiusculo();
		panel_1.add(textField, "cell 1 0,growx,aligny center");
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {

				if (e.getKeyCode() == KeyEvent.VK_ENTER) {

					iniciarPesquisa();
				}
			}
		});

		btnPesquisar = new JButton("Pesquisar");
		panel_1.add(btnPesquisar, "cell 2 0,alignx left,aligny top");

		btnSelecionar = new JButton("Selecionar");
		btnSelecionar.addActionListener((e) -> selecionarRegistro());
		panel_1.add(btnSelecionar, "cell 3 0,alignx left,aligny top");

		scrollPane = new JScrollPane();
		getContentPane().add(scrollPane, BorderLayout.CENTER);

		table = new JTable();
		scrollPane.setViewportView(table);

	}

	private void selecionarRegistro() {

		if (table.getSelectedRowCount() == 0) {
			JOptionPane.showMessageDialog(getParent(), translate(MENSAGEM_SELECIONE_UM_REGISTRO),
					translate(FRMLOGIN_MSG_VERIFICACAO), JOptionPane.WARNING_MESSAGE);
			return;
		}

		if (!this.multiselect && table.getSelectedRowCount() != 1) {
			JOptionPane.showMessageDialog(getParent(), MENSAGEM_SELECIONE_APENAS_UM_REGISTRO,
					translate(FRMLOGIN_MSG_VERIFICACAO), JOptionPane.WARNING_MESSAGE);
			return;
		}

		for (int row : table.getSelectedRows()) {
			this.objeto.add(genericTableModel.getRow(row));
		}

		this.ok = Boolean.TRUE;

		dispose();

	}

	private void iniciarPesquisa() {

		this.rows = 0;

		genericTableModel.removeAll();

		numeroregistros = genericService.count(chckbxContm.isSelected(), textField.getText(), this.preFilter,
				pesquisaExibir);

		this.pesquisar();

	}

	private void pesquisar() {

		List<T> valores = genericService.pesquisar(chckbxContm.isSelected(), textField.getText(), this.preFilter,
				pesquisaExibir, rows);

		genericTableModel.addRows(valores);

		rows += valores.size();

		lbRegistros.setText(String.format(STR_REGISTROS, rows, numeroregistros));

	}

	private void buscarPesquisa() {

		List<Pesquisa> pesquisa = pesquisaDAO.buscarPesquisaPorUsuario(this.codigoUsuario,
				this.pesquisaEnum.getCodigoPesquisa());

		btConfigurar.setVisible(pesquisa.size() != 1);

		selecionarPesquisa(pesquisa);

	}

	private void selecionarPesquisa(List<Pesquisa> pesquisa) {

		if (pesquisa.size() == 0) {
			throw new RuntimeException("PESQUISA NÃO CONFIGURADA");
		}

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

	public Boolean getOk() {
		return ok;
	}

	public T getObjeto() {

		return objeto.get(0);
	}

	public List<T> getObjetos() {

		return objeto;
	}

}
