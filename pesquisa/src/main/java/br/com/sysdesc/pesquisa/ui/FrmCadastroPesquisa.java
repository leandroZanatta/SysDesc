package br.com.sysdesc.pesquisa.ui;

import static br.com.sysdesc.pesquisa.enumeradores.PesquisaEnum.PES_CATEGORIAS;
import static br.com.sysdesc.pesquisa.enumeradores.PesquisaEnum.forValue;
import static br.com.sysdesc.util.enumeradores.TipoPesquisaEnum.NORMAL;
import static br.com.sysdesc.util.resources.Resources.FRMPESQUISA_MSG_DESCRICAO;
import static br.com.sysdesc.util.resources.Resources.FRMPESQUISA_MSG_PAGINACAO;
import static br.com.sysdesc.util.resources.Resources.FRMPESQUISA_MSG_PESQUISA;
import static br.com.sysdesc.util.resources.Resources.FRMPESQUISA_TITLE;
import static br.com.sysdesc.util.resources.Resources.translate;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import br.com.sysdesc.components.AbstractInternalFrame;
import br.com.sysdesc.components.JNumericField;
import br.com.sysdesc.components.JTextFieldMaiusculo;
import br.com.sysdesc.components.adapters.PanelEventAdapter;
import br.com.sysdesc.pesquisa.components.CampoPesquisa;
import br.com.sysdesc.pesquisa.components.PanelActions;
import br.com.sysdesc.pesquisa.enumeradores.PesquisaEnum;
import br.com.sysdesc.repository.dao.PesquisaNormalDAO;
import br.com.sysdesc.repository.model.PermissaoPrograma;
import br.com.sysdesc.repository.model.Pesquisa;
import br.com.sysdesc.util.classes.LongUtil;
import br.com.sysdesc.util.classes.StringUtil;
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

	private PanelActions<Pesquisa> panelActions;
	private PesquisaNormalDAO pesquisaDAO = new PesquisaNormalDAO();
	private JLabel lblNewLabel;
	private CampoPesquisa pesquisaUsuario;
	private JLabel lblNewLabel_1;
	private CampoPesquisa pesquisaPerfis;
	private JPanel panel;
	private JPanel panel_1;
	private JScrollPane scrollPane;
	private JButton btnNewButton;
	private JButton btnNewButton_1;

	public FrmCadastroPesquisa() {
		this(null, 1L);
	}

	public FrmCadastroPesquisa(PermissaoPrograma permissaoPrograma, Long codigoUsuario) {
		super(permissaoPrograma, codigoUsuario);

		setSize(800, 400);
		setClosable(Boolean.TRUE);
		setTitle(translate(FRMPESQUISA_TITLE));

		painelContent = new JPanel();

		lblCodigo = new JLabel("Código:");
		lblPaginacao = new JLabel("Paginacao:");
		lblDescricao = new JLabel("Descrição:");

		txCodigo = new JNumericField();
		txDescricao = new JTextFieldMaiusculo();
		txPaginacao = new JNumericField();

		painelContent.setLayout(new MigLayout("", "[grow][grow][80.00]", "[][][][][][][grow][]"));

		painelContent.add(lblCodigo, "cell 0 0");
		painelContent.add(txCodigo, "cell 0 1, width 50:100:100");

		painelContent.add(lblDescricao, "cell 0 2");
		lblPesquisa = new JLabel("Pesquisa:");

		painelContent.add(lblPesquisa, "cell 1 2");
		painelContent.add(txDescricao, "cell 0 3,growx");

		painelContent.add(lblPaginacao, "cell 2 2");
		// cbPesquisa = new JComboBox<PesquisaEnum>(PesquisaEnum.values());
		cbPesquisa = new JComboBox<PesquisaEnum>();
		painelContent.add(cbPesquisa, "cell 1 3,growx");
		painelContent.add(txPaginacao, "cell 2 3,growx");

		lblNewLabel_1 = new JLabel("Perfis:");
		painelContent.add(lblNewLabel_1, "cell 0 4");

		lblNewLabel = new JLabel("Usuário:");
		painelContent.add(lblNewLabel, "cell 1 4");

		pesquisaPerfis = new CampoPesquisa();
		painelContent.add(pesquisaPerfis, "cell 0 5,growx");

		pesquisaUsuario = new CampoPesquisa();
		painelContent.add(pesquisaUsuario, "cell 1 5 2 1,growx");

		getContentPane().add(painelContent);

		panel = new JPanel();
		painelContent.add(panel, "cell 0 6 3 1,grow");
		panel.setLayout(new BorderLayout(0, 0));

		panel_1 = new JPanel();
		panel.add(panel_1, BorderLayout.EAST);
		panel_1.setLayout(new MigLayout("", "[]", "[23px,grow][][][grow]"));

		btnNewButton = new JButton("");
		panel_1.add(btnNewButton, "cell 0 1,alignx left,aligny top");

		btnNewButton_1 = new JButton("");
		panel_1.add(btnNewButton_1, "cell 0 2,alignx left,aligny top");

		scrollPane = new JScrollPane();
		panel.add(scrollPane, BorderLayout.CENTER);

		panelActions = new PanelActions<Pesquisa>(this, Pesquisa::getIdPesquisa, pesquisaDAO, PES_CATEGORIAS) {

			private static final long serialVersionUID = 1L;

			@Override
			public void carregarObjeto(Pesquisa objeto) {
				txCodigo.setValue(objeto.getIdPesquisa());
				txDescricao.setText(objeto.getDescricao());
				txPaginacao.setValue(objeto.getPaginacao());
				cbPesquisa.setSelectedItem(forValue(objeto.getCodigoPesquisa()));
			}

			@Override
			public void preencherObjeto(Pesquisa objetoPesquisa) {
				objetoPesquisa.setIdPesquisa(txCodigo.getValue());
				objetoPesquisa.setDescricao(txDescricao.getText());
				objetoPesquisa.setPaginacao(txPaginacao.getValue());
				objetoPesquisa.setCodigoPesquisa(((PesquisaEnum) cbPesquisa.getSelectedItem()).getCodigoPesquisa());
				objetoPesquisa.setTipo(NORMAL.getCodigo());
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

		painelContent.add(panelActions, "cell 0 7 3 1,alignx center");

	}

}
