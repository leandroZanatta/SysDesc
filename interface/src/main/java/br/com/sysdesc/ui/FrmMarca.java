package br.com.sysdesc.ui;

import static br.com.sysdesc.util.resources.Resources.FRMMARCA_LB_CODIGO;
import static br.com.sysdesc.util.resources.Resources.FRMMARCA_LB_DESCRICAO;
import static br.com.sysdesc.util.resources.Resources.FRMMARCA_MSG_DESCRICAO_INVALIDA;
import static br.com.sysdesc.util.resources.Resources.FRMMARCA_TITLE;
import static br.com.sysdesc.util.resources.Resources.translate;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import br.com.sysdesc.components.AbstractInternalFrame;
import br.com.sysdesc.components.JNumericField;
import br.com.sysdesc.components.JTextFieldMaiusculo;
import br.com.sysdesc.components.PanelActions;
import br.com.sysdesc.components.adapters.PanelEventAdapter;
import br.com.sysdesc.repository.dao.MarcaDAO;
import br.com.sysdesc.repository.model.Marca;
import br.com.sysdesc.repository.model.PermissaoPrograma;
import br.com.sysdesc.util.classes.StringUtil;
import net.miginfocom.swing.MigLayout;

public class FrmMarca extends AbstractInternalFrame {

	private static final long serialVersionUID = 1L;

	private JTextFieldMaiusculo txDescricao;
	private JNumericField txCodigo;
	private JLabel lblCodigo;
	private JLabel lblDescricao;
	private JPanel painelContent;
	private PanelActions<Marca> panelActions;
	private MarcaDAO marcaDAO = new MarcaDAO();

	public FrmMarca(JFrame jFrame, PermissaoPrograma permissaoPrograma) {
		super(jFrame, permissaoPrograma);

		setSize(450, 160);
		setClosable(Boolean.TRUE);
		setTitle(translate(FRMMARCA_TITLE));

		painelContent = new JPanel();
		lblCodigo = new JLabel(translate(FRMMARCA_LB_CODIGO));
		txCodigo = new JNumericField();
		lblDescricao = new JLabel(translate(FRMMARCA_LB_DESCRICAO));
		txDescricao = new JTextFieldMaiusculo();

		painelContent.setLayout(new MigLayout("", "[grow]", "[][][][][][][grow]"));
		getContentPane().add(painelContent);

		painelContent.add(lblCodigo, "cell 0 0");
		painelContent.add(txCodigo, "cell 0 1,width 50:100:100");
		painelContent.add(lblDescricao, "cell 0 2");
		painelContent.add(txDescricao, "cell 0 3,growx");

		panelActions = new PanelActions<Marca>(this, marcaDAO) {

			private static final long serialVersionUID = 1L;

			@Override
			public void carregarObjeto(Marca objeto) {

				txCodigo.setValue(objeto.getIdMarca());
				txDescricao.setText(objeto.getDescricao());
			}

			@Override
			public void preencherObjeto(Marca objetoPesquisa) {

				objetoPesquisa.setIdMarca(txCodigo.getValue());
				objetoPesquisa.setDescricao(txDescricao.getText());
			}

			@Override
			public Boolean objetoValido() {

				if (StringUtil.isNullOrEmpty(txDescricao.getText())) {

					JOptionPane.showMessageDialog(this, translate(FRMMARCA_MSG_DESCRICAO_INVALIDA));

					return Boolean.FALSE;
				}

				return Boolean.TRUE;
			}
		};

		panelActions.addEventListener(new PanelEventAdapter<Marca>() {

			@Override
			public void saveEvent(Marca objeto) {
				txCodigo.setValue(objeto.getIdMarca());
			}
		});

		painelContent.add(panelActions, "cell 0 4,grow");
	}

}
