package br.com.sysdesc.ui;

import static br.com.sysdesc.pesquisa.enumeradores.PesquisaEnum.PES_CIDADES;
import static br.com.sysdesc.util.resources.Resources.FRMCIDADE_LB_CODIGO;
import static br.com.sysdesc.util.resources.Resources.FRMCIDADE_LB_DESCRICAO;
import static br.com.sysdesc.util.resources.Resources.FRMCIDADE_LB_ESTADO;
import static br.com.sysdesc.util.resources.Resources.FRMCIDADE_TITLE;
import static br.com.sysdesc.util.resources.Resources.translate;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import br.com.sysdesc.components.AbstractInternalFrame;
import br.com.sysdesc.components.JNumericField;
import br.com.sysdesc.components.JTextFieldMaiusculo;
import br.com.sysdesc.pesquisa.components.PanelActions;
import br.com.sysdesc.repository.model.Cidade;
import br.com.sysdesc.repository.model.Estado;
import br.com.sysdesc.repository.model.PermissaoPrograma;
import br.com.sysdesc.service.cidade.CidadeService;
import br.com.sysdesc.service.estado.EstadoService;
import net.miginfocom.swing.MigLayout;

public class FrmCidade extends AbstractInternalFrame {

	private static final long serialVersionUID = 1L;

	private JPanel painelContent;

	private JLabel lbCodigo;
	private JNumericField txCodigo;
	private JTextFieldMaiusculo txDescricao;
	private JLabel lbEstado;
	private JComboBox<Estado> cbEstado;
	private JLabel lbDescricao;
	private PanelActions<Cidade> painelBotoes;
	private CidadeService cidadeService = new CidadeService();
	private EstadoService estadoService = new EstadoService();

	public FrmCidade(PermissaoPrograma permissaoPrograma, Long codigoUsuario) {
		super(permissaoPrograma, codigoUsuario);

		initComponents();
	}

	private void initComponents() {

		setSize(450, 230);
		setClosable(Boolean.TRUE);
		setTitle(translate(FRMCIDADE_TITLE));

		painelContent = new JPanel();
		lbCodigo = new JLabel(translate(FRMCIDADE_LB_CODIGO));
		txCodigo = new JNumericField();
		lbEstado = new JLabel(translate(FRMCIDADE_LB_ESTADO));
		cbEstado = new JComboBox<>();
		lbDescricao = new JLabel(translate(FRMCIDADE_LB_DESCRICAO));
		txDescricao = new JTextFieldMaiusculo();

		estadoService.listarEstados().stream().forEach(cbEstado::addItem);

		painelContent.setLayout(new MigLayout("", "[grow]", "[][][][][][][grow]"));
		getContentPane().add(painelContent);

		painelContent.add(lbCodigo, "cell 0 0");
		painelContent.add(txCodigo, "cell 0 1,growx");
		painelContent.add(lbEstado, "cell 0 2");
		painelContent.add(cbEstado, "cell 0 3,growx");
		painelContent.add(lbDescricao, "cell 0 4");
		painelContent.add(txDescricao, "cell 0 5,growx");

		painelBotoes = new PanelActions<Cidade>(this, cidadeService, PES_CIDADES) {

			private static final long serialVersionUID = 1L;

			@Override
			public void carregarObjeto(Cidade objeto) {
				txCodigo.setValue(objeto.getIdCidade());
				txDescricao.setText(objeto.getDescricao());
				cbEstado.setSelectedItem(objeto.getEstado());
			}

			@Override
			public Boolean preencherObjeto(Cidade objetoPesquisa) {
				objetoPesquisa.setIdCidade(txCodigo.getValue());

				if (cbEstado.getSelectedIndex() >= 0) {
					objetoPesquisa.setEstado((Estado) cbEstado.getSelectedItem());
				}

				objetoPesquisa.setDescricao(txDescricao.getText());

				return Boolean.TRUE;
			}
		};

		painelBotoes.addSaveListener((cidade) -> txCodigo.setValue(cidade.getIdCidade()));

		painelContent.add(painelBotoes, "cell 0 6,grow");
	}

}
