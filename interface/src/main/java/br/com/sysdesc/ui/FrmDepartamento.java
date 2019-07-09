package br.com.sysdesc.ui;

import static br.com.sysdesc.pesquisa.enumeradores.PesquisaEnum.PES_DEPARTAMENTOS;
import static br.com.sysdesc.util.resources.Resources.FRMDEPARTAMENTO_LB_CODIGO;
import static br.com.sysdesc.util.resources.Resources.FRMDEPARTAMENTO_LB_DESCRICAO;
import static br.com.sysdesc.util.resources.Resources.FRMDEPARTAMENTO_TITLE;
import static br.com.sysdesc.util.resources.Resources.translate;

import javax.swing.JLabel;
import javax.swing.JPanel;

import br.com.sysdesc.components.AbstractInternalFrame;
import br.com.sysdesc.components.JNumericField;
import br.com.sysdesc.components.JTextFieldMaiusculo;
import br.com.sysdesc.components.adapters.PanelEventAdapter;
import br.com.sysdesc.pesquisa.components.PanelActions;
import br.com.sysdesc.repository.model.Departamento;
import br.com.sysdesc.repository.model.PermissaoPrograma;
import br.com.sysdesc.service.departamento.DepartamentoService;
import net.miginfocom.swing.MigLayout;

public class FrmDepartamento extends AbstractInternalFrame {

	private static final long serialVersionUID = 1L;

	private JPanel painelContent;

	private JLabel lblCodigo;
	private JLabel lblDescricao;

	private JNumericField txCodigo;
	private JTextFieldMaiusculo txDescricao;
	private PanelActions<Departamento> panelActions;
	private DepartamentoService departamentoService = new DepartamentoService();

	public FrmDepartamento(PermissaoPrograma permissaoPrograma, Long codigoUsuario) {
		super(permissaoPrograma, codigoUsuario);

		initComponents();
	}

	private void initComponents() {

		setSize(450, 170);
		setClosable(Boolean.TRUE);
		setTitle(translate(FRMDEPARTAMENTO_TITLE));

		painelContent = new JPanel();

		txCodigo = new JNumericField();

		lblCodigo = new JLabel(translate(FRMDEPARTAMENTO_LB_CODIGO));

		lblDescricao = new JLabel(translate(FRMDEPARTAMENTO_LB_DESCRICAO));

		txDescricao = new JTextFieldMaiusculo();

		painelContent.setLayout(new MigLayout("", "[grow]", "[][][][][][][grow]"));
		getContentPane().add(painelContent);

		painelContent.add(lblCodigo, "cell 0 0");
		painelContent.add(txCodigo, "cell 0 1,width 50:100:100");
		painelContent.add(lblDescricao, "cell 0 2");
		painelContent.add(txDescricao, "cell 0 3,growx");

		panelActions = new PanelActions<Departamento>(this, departamentoService, PES_DEPARTAMENTOS) {

			private static final long serialVersionUID = 1L;

			@Override
			public void carregarObjeto(Departamento objeto) {
				txCodigo.setValue(objeto.getIdDepartamento());
				txDescricao.setText(objeto.getDescricao());
			}

			@Override
			public void preencherObjeto(Departamento objetoPesquisa) {
				objetoPesquisa.setIdDepartamento(txCodigo.getValue());
				objetoPesquisa.setDescricao(txDescricao.getText());
			}

		};

		panelActions.addEventListener(new PanelEventAdapter<Departamento>() {

			@Override
			public void saveEvent(Departamento departamento) {
				txCodigo.setValue(departamento.getIdDepartamento());
			}
		});

		painelContent.add(panelActions, "cell 0 4,grow");
	}

}
