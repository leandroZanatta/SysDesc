package br.com.sysdesc.pesquisa.ui;

import static br.com.sysdesc.util.resources.Resources.FRMPESQUISA_LB_CODIGO;
import static br.com.sysdesc.util.resources.Resources.FRMPESQUISA_LB_PAGINACAO;
import static br.com.sysdesc.util.resources.Resources.FRMPESQUISA_LB_PESQUISA;
import static br.com.sysdesc.util.resources.Resources.FRMPESQUISA_TITLE;
import static br.com.sysdesc.util.resources.Resources.translate;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import br.com.sysdesc.components.AbstractInternalFrame;
import br.com.sysdesc.components.JNumericField;
import br.com.sysdesc.pesquisa.enumeradores.PesquisaEnum;
import br.com.sysdesc.repository.model.PermissaoPrograma;
import net.miginfocom.swing.MigLayout;

public class FrmCadastroPesquisa extends AbstractInternalFrame {

	private JLabel lblCodigo;
	private JNumericField txCodigo;
	private JLabel lblPaginacao;
	private JNumericField txPaginacao;
	private JLabel lblDescricao;
	private JComboBox<PesquisaEnum> cbPesquisa;
	private JPanel painelContent;

	public FrmCadastroPesquisa(PermissaoPrograma permissaoPrograma) {
		super(permissaoPrograma);

		setSize(450, 160);
		setClosable(Boolean.TRUE);
		setTitle(translate(FRMPESQUISA_TITLE));

		painelContent = new JPanel();
		lblCodigo = new JLabel(translate(FRMPESQUISA_LB_CODIGO));
		lblDescricao = new JLabel(translate(FRMPESQUISA_LB_PESQUISA));
		lblPaginacao = new JLabel(translate(FRMPESQUISA_LB_PAGINACAO));
		txPaginacao = new JNumericField();
		txCodigo = new JNumericField();
		cbPesquisa = new JComboBox<PesquisaEnum>(PesquisaEnum.values());

		painelContent.setLayout(new MigLayout("", "[grow][grow]", "[][][][][][][grow]"));

		painelContent.add(lblCodigo, "cell 0 0");
		painelContent.add(txCodigo, "cell 0 1,width 50:100:100");
		painelContent.add(lblPaginacao, "cell 0 2");
		painelContent.add(txPaginacao, "cell 0 3,width 50:100:100");
		painelContent.add(lblDescricao, "cell 0 4");
		painelContent.add(cbPesquisa, "cell 0 5,growx");

		getContentPane().add(painelContent);

	}

	private static final long serialVersionUID = 1L;

}
