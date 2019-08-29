package br.com.sysdesc.ui;

import static br.com.sysdesc.pesquisa.enumeradores.PesquisaEnum.PES_ENTRADAS;
import static br.com.sysdesc.util.resources.Resources.FRMDEPARTAMENTO_TITLE;
import static br.com.sysdesc.util.resources.Resources.translate;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import br.com.sysdesc.components.AbstractInternalFrame;
import br.com.sysdesc.pesquisa.components.PanelActions;
import br.com.sysdesc.repository.model.EntradaCabecalho;
import br.com.sysdesc.repository.model.PermissaoPrograma;
import br.com.sysdesc.service.entradas.EntradasService;
import net.miginfocom.swing.MigLayout;

public class FrmEntradaNota extends AbstractInternalFrame {

	private static final long serialVersionUID = 1L;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;
	private JTextField textField_6;
	private JTextField textField_7;
	private JTable table;
	private PanelActions<EntradaCabecalho> panelActions;

	private EntradasService entradasService = new EntradasService();

	public FrmEntradaNota(PermissaoPrograma permissaoPrograma, Long codigoUsuario) {
		super(permissaoPrograma, codigoUsuario);

		initComponents();
	}

	private void initComponents() {

		setSize(600, 400);
		setClosable(Boolean.TRUE);
		setTitle(translate(FRMDEPARTAMENTO_TITLE));

		getContentPane().setLayout(new MigLayout("", "[grow][grow][grow][grow][grow]", "[][][][][][][][][grow][]"));

		JLabel lblCdigo = new JLabel("Código:");
		getContentPane().add(lblCdigo, "cell 0 0");

		textField = new JTextField();
		getContentPane().add(textField, "cell 0 1,growx");

		JLabel lblNewLabel = new JLabel("Natureza da Operação:");
		getContentPane().add(lblNewLabel, "cell 0 2");

		JLabel lblNewLabel_1 = new JLabel("Tipo de Operação:");
		getContentPane().add(lblNewLabel_1, "cell 3 2");

		JLabel lblNmeroNota = new JLabel("Número Nota:");
		getContentPane().add(lblNmeroNota, "cell 4 2");

		JComboBox comboBox = new JComboBox();
		getContentPane().add(comboBox, "cell 0 3 3 1,growx");

		JComboBox comboBox_1 = new JComboBox();
		getContentPane().add(comboBox_1, "cell 3 3,growx");

		textField_1 = new JTextField();
		getContentPane().add(textField_1, "cell 4 3,growx");

		JLabel lblEmitente = new JLabel("Emitente:");
		getContentPane().add(lblEmitente, "cell 0 4");

		textField_4 = new JTextField();
		getContentPane().add(textField_4, "cell 0 5 5 1,growx");

		JLabel lblDataEmisso = new JLabel("Data Emissão:");
		getContentPane().add(lblDataEmisso, "cell 0 6");

		JLabel lblData = new JLabel("Data Saída:");
		getContentPane().add(lblData, "cell 1 6");

		JLabel lblFrete = new JLabel("Frete:");
		getContentPane().add(lblFrete, "cell 2 6");

		JLabel lblValorProdutos = new JLabel("Valor Produtos:");
		getContentPane().add(lblValorProdutos, "cell 3 6");

		JLabel lblValorNota = new JLabel("Valor Nota:");
		getContentPane().add(lblValorNota, "cell 4 6");

		textField_2 = new JTextField();
		getContentPane().add(textField_2, "cell 0 7,growx");

		textField_3 = new JTextField();
		getContentPane().add(textField_3, "cell 1 7,growx");

		textField_5 = new JTextField();
		getContentPane().add(textField_5, "cell 2 7,growx");

		textField_6 = new JTextField();
		getContentPane().add(textField_6, "cell 3 7,growx");

		textField_7 = new JTextField();
		getContentPane().add(textField_7, "cell 4 7,growx");

		JScrollPane scrollPane = new JScrollPane();
		getContentPane().add(scrollPane, "cell 0 8 5 1,grow");

		table = new JTable();
		scrollPane.setViewportView(table);

		panelActions = new PanelActions<EntradaCabecalho>(this, entradasService, PES_ENTRADAS) {

			private static final long serialVersionUID = 1L;

			@Override
			public void carregarObjeto(EntradaCabecalho objeto) {

			}

			@Override
			public Boolean preencherObjeto(EntradaCabecalho objetoPesquisa) {

				return Boolean.TRUE;
			}
		};

//		panelActions.addSaveListener((categoria) -> txCodigo.setValue(categoria.getIdCategoria()));
		getContentPane().add(panelActions, "cell 0 9 5 1,grow");
	}

}
