package br.com.sysdesc.ui;

import static br.com.sysdesc.pesquisa.enumeradores.PesquisaEnum.PES_UNIDADES;
import static br.com.sysdesc.util.resources.Resources.FRMUNIDADE_LB_CODIGO;
import static br.com.sysdesc.util.resources.Resources.FRMUNIDADE_LB_DESCRICAO;
import static br.com.sysdesc.util.resources.Resources.FRMUNIDADE_LB_DESCRICAO_REDUZIDA;
import static br.com.sysdesc.util.resources.Resources.FRMUNIDADE_TITLE;
import static br.com.sysdesc.util.resources.Resources.translate;

import javax.swing.JLabel;
import javax.swing.JPanel;

import br.com.sysdesc.components.AbstractInternalFrame;
import br.com.sysdesc.components.JNumericField;
import br.com.sysdesc.components.JTextFieldMaiusculo;
import br.com.sysdesc.pesquisa.components.PanelActions;
import br.com.sysdesc.repository.model.PermissaoPrograma;
import br.com.sysdesc.repository.model.Unidade;
import br.com.sysdesc.service.unidade.UnidadeService;
import net.miginfocom.swing.MigLayout;

public class FrmUnidade extends AbstractInternalFrame {

	private static final long serialVersionUID = 1L;

	private JPanel painelContent;
	private JNumericField txCodigo;
	private JLabel lblCodigo;
	private JLabel lblDescricao;
	private JLabel lblDescricaoReduzida;
	private JTextFieldMaiusculo txDescricao;
	private PanelActions<Unidade> panelActions;
	private UnidadeService unidadeService = new UnidadeService();
	private JTextFieldMaiusculo textField;

	public FrmUnidade(PermissaoPrograma permissaoPrograma, Long codigoUsuario) {
		super(permissaoPrograma, codigoUsuario);

		initComponents();
	}

	private void initComponents() {

		setSize(450, 190);
		setClosable(Boolean.TRUE);
		setTitle(translate(FRMUNIDADE_TITLE));

		painelContent = new JPanel();
		txCodigo = new JNumericField();
		lblCodigo = new JLabel(translate(FRMUNIDADE_LB_CODIGO));
		lblDescricao = new JLabel(translate(FRMUNIDADE_LB_DESCRICAO));
		lblDescricaoReduzida = new JLabel(translate(FRMUNIDADE_LB_DESCRICAO_REDUZIDA));
		txDescricao = new JTextFieldMaiusculo();
		textField = new JTextFieldMaiusculo();

		painelContent.setLayout(new MigLayout("", "[grow][]", "[][][][][grow]"));
		getContentPane().add(painelContent);
		painelContent.add(lblCodigo, "cell 0 0");
		painelContent.add(txCodigo, "cell 0 1,width 50:100:100");

		painelContent.add(lblDescricao, "cell 0 2");
		painelContent.add(txDescricao, "cell 0 3,growx");

		painelContent.add(lblDescricaoReduzida, "cell 1 2");
		painelContent.add(textField, "cell 1 3,width 50:100:100");

		panelActions = new PanelActions<Unidade>(this, unidadeService, PES_UNIDADES) {

			private static final long serialVersionUID = 1L;

			@Override
			public void carregarObjeto(Unidade objeto) {
				txCodigo.setValue(objeto.getIdUnidade());
				txDescricao.setText(objeto.getDescricao());
				textField.setText(objeto.getDescricaoReduzida());
			}

			@Override
			public Boolean preencherObjeto(Unidade objetoPesquisa) {

				objetoPesquisa.setIdUnidade(txCodigo.getValue());
				objetoPesquisa.setDescricao(txDescricao.getText());
				objetoPesquisa.setDescricaoReduzida(textField.getText());

				return Boolean.TRUE;
			}
		};

		panelActions.addSaveListener((unidade) -> txCodigo.setValue(unidade.getIdUnidade()));

		painelContent.add(panelActions, "cell 0 4 2 1,grow");
	}

}
