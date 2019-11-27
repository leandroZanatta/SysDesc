package br.com.sysdesc.ui;

import static br.com.sysdesc.pesquisa.enumeradores.PesquisaEnum.PES_PERFIL;
import static br.com.sysdesc.util.resources.Resources.FRMPERFIL_LB_CODIGO;
import static br.com.sysdesc.util.resources.Resources.FRMPERFIL_LB_DESCRICAO;
import static br.com.sysdesc.util.resources.Resources.FRMPERFIL_TITLE;
import static br.com.sysdesc.util.resources.Resources.translate;

import javax.swing.JLabel;
import javax.swing.JPanel;

import br.com.sysdesc.components.AbstractInternalFrame;
import br.com.sysdesc.components.JTextFieldMaiusculo;
import br.com.sysdesc.pesquisa.components.JTextFieldId;
import br.com.sysdesc.pesquisa.components.PanelActions;
import br.com.sysdesc.repository.model.Perfil;
import br.com.sysdesc.repository.model.PermissaoPrograma;
import br.com.sysdesc.service.perfil.PerfilService;
import net.miginfocom.swing.MigLayout;

public class FrmPerfil extends AbstractInternalFrame {

	private static final long serialVersionUID = 1L;

	private JPanel painelContent;

	private JLabel lblCodigo;
	private JLabel lblDescricao;

	private JTextFieldId txCodigo;
	private JTextFieldMaiusculo txDescricao;

	private PanelActions<Perfil> panelActions;
	private PerfilService perfilService = new PerfilService();

	public FrmPerfil(PermissaoPrograma permissaoPrograma, Long codigoUsuario, Long codigoEmpresa) {
		super(permissaoPrograma, codigoUsuario, codigoEmpresa);

		initComponents();
	}

	private void initComponents() {

		setSize(450, 180);
		setClosable(Boolean.TRUE);
		setTitle(translate(FRMPERFIL_TITLE));

		painelContent = new JPanel();

		lblCodigo = new JLabel(translate(FRMPERFIL_LB_CODIGO));
		lblDescricao = new JLabel(translate(FRMPERFIL_LB_DESCRICAO));

		txCodigo = new JTextFieldId();
		txDescricao = new JTextFieldMaiusculo();

		painelContent.setLayout(new MigLayout("", "[grow]", "[][][][][][][grow]"));
		getContentPane().add(painelContent);

		painelContent.add(lblCodigo, "cell 0 0");
		painelContent.add(txCodigo, "cell 0 1,width 50:100:100");
		painelContent.add(lblDescricao, "cell 0 2");
		painelContent.add(txDescricao, "cell 0 3,growx");

		panelActions = new PanelActions<Perfil>(this, perfilService, PES_PERFIL) {

			private static final long serialVersionUID = 1L;

			@Override
			public void carregarObjeto(Perfil objeto) {
				txCodigo.setValue(objeto.getIdPerfil());
				txDescricao.setText(objeto.getDescricao());
			}

			@Override
			public Boolean preencherObjeto(Perfil objetoPesquisa) {

				objetoPesquisa.setIdPerfil(txCodigo.getValue());
				objetoPesquisa.setDescricao(txDescricao.getText());

				return Boolean.TRUE;
			}
		};

		panelActions.addSaveListener((objeto) -> txCodigo.setValue(objeto.getIdPerfil()));

		painelContent.add(panelActions, "cell 0 4,grow");
	}

}
