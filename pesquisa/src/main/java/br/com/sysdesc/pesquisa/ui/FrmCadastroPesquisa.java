package br.com.sysdesc.pesquisa.ui;

import static br.com.sysdesc.util.resources.Resources.FRMPESQUISA_LB_CODIGO;
import static br.com.sysdesc.util.resources.Resources.FRMPESQUISA_LB_DESCRICAO;
import static br.com.sysdesc.util.resources.Resources.FRMPESQUISA_LB_PAGINACAO;
import static br.com.sysdesc.util.resources.Resources.FRMPESQUISA_LB_PESQUISA;
import static br.com.sysdesc.util.resources.Resources.FRMPESQUISA_MSG_DESCRICAO;
import static br.com.sysdesc.util.resources.Resources.FRMPESQUISA_MSG_PAGINACAO;
import static br.com.sysdesc.util.resources.Resources.FRMPESQUISA_MSG_PESQUISA;
import static br.com.sysdesc.util.resources.Resources.FRMPESQUISA_TITLE;
import static br.com.sysdesc.util.resources.Resources.translate;

import java.awt.BorderLayout;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;

import br.com.sysdesc.components.AbstractInternalFrame;
import br.com.sysdesc.components.CampoPesquisa;
import br.com.sysdesc.components.JNumericField;
import br.com.sysdesc.components.JTextFieldMaiusculo;
import br.com.sysdesc.components.PanelActions;
import br.com.sysdesc.components.adapters.PanelEventAdapter;
import br.com.sysdesc.pesquisa.enumeradores.PesquisaEnum;
import br.com.sysdesc.repository.dao.PesquisaNormalDAO;
import br.com.sysdesc.repository.model.PermissaoPrograma;
import br.com.sysdesc.repository.model.Pesquisa;
import br.com.sysdesc.util.classes.LongUtil;
import br.com.sysdesc.util.classes.StringUtil;
import br.com.sysdesc.util.enumeradores.TipoPesquisaEnum;
import net.miginfocom.swing.MigLayout;

public class FrmCadastroPesquisa extends AbstractInternalFrame {

	private static final long serialVersionUID = 1L;

	private JLabel lblCodigo;
	private JLabel lblPaginacao;
	private JLabel lblPesquisa;
	private JLabel lblDescricao;
	private JNumericField txCodigo;
	private JNumericField txPaginacao;
	private JComboBox<PesquisaEnum> cbPesquisa;
	private JPanel painelContent;
	private JTextFieldMaiusculo txDescricao;
	private JTabbedPane tabbedPane;
	private JPanel contentGeral;

	private PanelActions<Pesquisa> panelActions;
	private PesquisaNormalDAO pesquisaDAO = new PesquisaNormalDAO();
	private JPanel panel;
	private JScrollPane scrollPane;
	private JTable table;
	private JLabel lblNewLabel;
	private CampoPesquisa pesquisaUsuario;
	private JLabel lblNewLabel_1;
	private CampoPesquisa pesquisaPerfis;

	public FrmCadastroPesquisa() {
		this(null);
	}

	public FrmCadastroPesquisa(PermissaoPrograma permissaoPrograma) {
		super(permissaoPrograma);

		setSize(643, 400);
		setClosable(Boolean.TRUE);
		setTitle(translate(FRMPESQUISA_TITLE));

		painelContent = new JPanel();
		painelContent.setLayout(new BorderLayout(0, 0));
		lblCodigo = new JLabel(translate(FRMPESQUISA_LB_CODIGO));
		lblPesquisa = new JLabel(translate(FRMPESQUISA_LB_PESQUISA));
		lblPaginacao = new JLabel(translate(FRMPESQUISA_LB_PAGINACAO));
		lblDescricao = new JLabel(translate(FRMPESQUISA_LB_DESCRICAO));

		tabbedPane = new JTabbedPane();
		contentGeral = new JPanel();
		panel = new JPanel();

		txCodigo = new JNumericField();
		txDescricao = new JTextFieldMaiusculo();
		txPaginacao = new JNumericField();
		cbPesquisa = new JComboBox<PesquisaEnum>(PesquisaEnum.values());
		// cbPesquisa = new JComboBox<PesquisaEnum>();

		tabbedPane.addTab("Geral", null, contentGeral, null);
		tabbedPane.addTab("Fields", null, panel, null);
		panel.setLayout(new BorderLayout(0, 0));

		scrollPane = new JScrollPane();
		panel.add(scrollPane, BorderLayout.CENTER);

		table = new JTable();
		scrollPane.setViewportView(table);

		cbPesquisa.addActionListener((e) -> tabbedPane.setEnabledAt(1, cbPesquisa.getSelectedIndex() >= 0));

		tabbedPane.setEnabledAt(1, false);
		painelContent.add(tabbedPane);

		contentGeral.setLayout(new MigLayout("", "[grow][80.00]", "[][][][][][][][][][][grow]"));

		contentGeral.add(lblCodigo, "cell 0 0");
		contentGeral.add(txCodigo, "cell 0 1, width 50:100:100");

		contentGeral.add(lblDescricao, "cell 0 2");
		contentGeral.add(txDescricao, "cell 0 3,growx");

		contentGeral.add(lblPaginacao, "cell 1 2");
		contentGeral.add(txPaginacao, "cell 1 3,growx");

		contentGeral.add(lblPesquisa, "cell 0 4");
		contentGeral.add(cbPesquisa, "cell 0 5 2 1,growx");

		lblNewLabel = new JLabel("Usuário:");
		contentGeral.add(lblNewLabel, "cell 0 6");

		pesquisaUsuario = new CampoPesquisa();
		contentGeral.add(pesquisaUsuario, "cell 0 7 2 1,growx");

		lblNewLabel_1 = new JLabel("Perfis:");
		contentGeral.add(lblNewLabel_1, "cell 0 8");

		pesquisaPerfis = new CampoPesquisa();
		contentGeral.add(pesquisaPerfis, "cell 0 9 2 1,growx");

		getContentPane().add(painelContent);

		panelActions = new PanelActions<Pesquisa>(this, pesquisaDAO) {

			private static final long serialVersionUID = 1L;

			@Override
			public void carregarObjeto(Pesquisa objeto) {
				txCodigo.setValue(objeto.getIdPesquisa());
				txDescricao.setText(objeto.getDescricao());
				txPaginacao.setValue(objeto.getPaginacao());
				cbPesquisa.setSelectedItem(PesquisaEnum.forValue(objeto.getCodigoPesquisa()));
			}

			@Override
			public void preencherObjeto(Pesquisa objetoPesquisa) {
				objetoPesquisa.setIdPesquisa(txCodigo.getValue());
				objetoPesquisa.setDescricao(txDescricao.getText());
				objetoPesquisa.setPaginacao(txPaginacao.getValue());
				objetoPesquisa.setCodigoPesquisa(((PesquisaEnum) cbPesquisa.getSelectedItem()).getCodigoPesquisa());
				objetoPesquisa.setTipo(TipoPesquisaEnum.NORMAL.getCodigo());
			}

			@Override
			public Boolean objetoValido() {

				if (StringUtil.isNullOrEmpty(txDescricao.getText())) {

					JOptionPane.showMessageDialog(this, translate(FRMPESQUISA_MSG_DESCRICAO));

					return Boolean.FALSE;
				}

				if (LongUtil.isNullOrZero(txPaginacao.getValue())) {

					JOptionPane.showMessageDialog(this, translate(FRMPESQUISA_MSG_PAGINACAO));

					return Boolean.FALSE;
				}

				if (cbPesquisa.getSelectedIndex() < 0) {

					JOptionPane.showMessageDialog(this, translate(FRMPESQUISA_MSG_PESQUISA));

					return Boolean.FALSE;
				}

				return Boolean.TRUE;
			}
		};

		panelActions.addEventListener(new PanelEventAdapter<Pesquisa>() {

			@Override
			public void saveEvent(Pesquisa objeto) {
				txCodigo.setValue(objeto.getIdPesquisa());
			}

			@Override
			public void newEvent() {
				txPaginacao.setValue(20L);
			}
		});

		painelContent.add(panelActions, BorderLayout.SOUTH);

	}

}
