package br.com.sysdesc.ui;

import static br.com.sysdesc.util.resources.Resources.FRMCIDADE_TITLE;
import static br.com.sysdesc.util.resources.Resources.translate;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import br.com.sysdesc.components.AbstractInternalFrame;
import br.com.sysdesc.components.JNumericField;
import br.com.sysdesc.components.JTextFieldMaiusculo;
import br.com.sysdesc.components.PanelActions;
import br.com.sysdesc.repository.model.Cidade;
import br.com.sysdesc.repository.model.PermissaoPrograma;
import net.miginfocom.swing.MigLayout;

public class FrmCidade extends AbstractInternalFrame {

	private static final long serialVersionUID = 1L;

	private JPanel painelContent;

	private JLabel lbCodigo;
	private JNumericField txCodigo;
	private JTextFieldMaiusculo txDescricao;
	private JLabel lbEstado;
	private JComboBox cbEstado;
	private JLabel lbDescricao;
	private PanelActions<Cidade> painelBotoes;

	public FrmCidade(PermissaoPrograma permissaoPrograma) {
		super(permissaoPrograma);

		setSize(450, 210);
		setClosable(Boolean.TRUE);
		setTitle(translate(FRMCIDADE_TITLE));

		painelContent = new JPanel();
		lbCodigo = new JLabel("Código:");
		txCodigo = new JNumericField();
		lbEstado = new JLabel("Estado:");
		cbEstado = new JComboBox();
		lbDescricao = new JLabel("Descrição:");
		txDescricao = new JTextFieldMaiusculo();

		painelContent.setLayout(new MigLayout("", "[grow]", "[][][][][][][grow]"));
		getContentPane().add(painelContent);

		painelContent.add(lbCodigo, "cell 0 0");
		painelContent.add(txCodigo, "cell 0 1,growx");
		painelContent.add(lbEstado, "cell 0 2");
		painelContent.add(cbEstado, "cell 0 3,growx");
		painelContent.add(lbDescricao, "cell 0 4");
		painelContent.add(txDescricao, "cell 0 5,growx");

		painelBotoes = new PanelActions<Cidade>(this, null) {

			@Override
			public void carregarObjeto(Cidade objeto) {
				// TODO Auto-generated method stub

			}

			@Override
			public void preencherObjeto(Cidade objetoPesquisa) {
				// TODO Auto-generated method stub

			}
		};

	}

}
