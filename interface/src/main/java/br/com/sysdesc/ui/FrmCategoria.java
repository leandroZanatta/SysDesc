package br.com.sysdesc.ui;

import static br.com.sysdesc.pesquisa.enumeradores.PesquisaEnum.PES_CATEGORIAS;
import static br.com.sysdesc.util.resources.Resources.FRMCATEGORIA_LB_CODIGO;
import static br.com.sysdesc.util.resources.Resources.FRMCATEGORIA_LB_DEPARTAMENTO;
import static br.com.sysdesc.util.resources.Resources.FRMCATEGORIA_LB_DESCRICAO;
import static br.com.sysdesc.util.resources.Resources.FRMCATEGORIA_TITLE;
import static br.com.sysdesc.util.resources.Resources.translate;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

import br.com.sysdesc.components.AbstractInternalFrame;
import br.com.sysdesc.components.JNumericField;
import br.com.sysdesc.components.JTextFieldMaiusculo;
import br.com.sysdesc.components.adapters.PanelEventAdapter;
import br.com.sysdesc.pesquisa.components.PanelActions;
import br.com.sysdesc.repository.dao.CategoriaDAO;
import br.com.sysdesc.repository.dao.DepartamentoDAO;
import br.com.sysdesc.repository.model.Categoria;
import br.com.sysdesc.repository.model.Departamento;
import br.com.sysdesc.repository.model.PermissaoPrograma;
import br.com.sysdesc.util.classes.StringUtil;
import net.miginfocom.swing.MigLayout;

public class FrmCategoria extends AbstractInternalFrame {

	private static final long serialVersionUID = 1L;

	private JPanel painelContent;

	private JLabel lbDepartamento;
	private JLabel lblCodigo;
	private JLabel lblDescricao;

	private JNumericField txCodigo;
	private JComboBox<Departamento> cbDepartamento;
	private JTextFieldMaiusculo txDescricao;
	private PanelActions<Categoria> panelActions;

	private DepartamentoDAO departamentoDAO = new DepartamentoDAO();

	private CategoriaDAO categoriaDAO = new CategoriaDAO();

	public FrmCategoria(PermissaoPrograma permissaoPrograma, Long codigoUsuario) {
		super(permissaoPrograma, codigoUsuario);

		setSize(450, 200);
		setClosable(Boolean.TRUE);
		setTitle(translate(FRMCATEGORIA_TITLE));

		painelContent = new JPanel();

		lblCodigo = new JLabel(translate(FRMCATEGORIA_LB_CODIGO));
		lbDepartamento = new JLabel(translate(FRMCATEGORIA_LB_DEPARTAMENTO));
		lblDescricao = new JLabel(translate(FRMCATEGORIA_LB_DESCRICAO));

		txCodigo = new JNumericField();
		cbDepartamento = new JComboBox<>();
		txDescricao = new JTextFieldMaiusculo();

		departamentoDAO.listar().forEach(cbDepartamento::addItem);
		cbDepartamento.setEditable(Boolean.TRUE);
		AutoCompleteDecorator.decorate(cbDepartamento);

		painelContent.setLayout(new MigLayout("", "[grow]", "[][][][][][][grow]"));
		getContentPane().add(painelContent);

		painelContent.add(lblCodigo, "cell 0 0");
		painelContent.add(txCodigo, "cell 0 1,width 50:100:100");
		painelContent.add(lbDepartamento, "cell 0 2");
		painelContent.add(cbDepartamento, "cell 0 3,growx");
		painelContent.add(lblDescricao, "cell 0 4");
		painelContent.add(txDescricao, "cell 0 5,growx");

		panelActions = new PanelActions<Categoria>(this, Categoria::getIdCategoria, categoriaDAO, PES_CATEGORIAS) {

			private static final long serialVersionUID = 1L;

			@Override
			public void carregarObjeto(Categoria objeto) {
				txCodigo.setValue(objeto.getIdCategoria());
				cbDepartamento.setSelectedItem(objeto.getDepartamento());
				txDescricao.setText(objeto.getDescricao());
			}

			@Override
			public void preencherObjeto(Categoria objetoPesquisa) {
				objetoPesquisa.setIdCategoria(txCodigo.getValue());
				objetoPesquisa.setDepartamento((Departamento) cbDepartamento.getSelectedItem());
				objetoPesquisa.setDescricao(txDescricao.getText());
			}

			@Override
			public Boolean objetoValido() {

				if (cbDepartamento.getSelectedIndex() < 0) {
					JOptionPane.showMessageDialog(null, "SELECIONE UM DEPARTAMENTO");

					return Boolean.FALSE;
				}

				if (StringUtil.isNullOrEmpty(txDescricao.getText())) {
					JOptionPane.showMessageDialog(null, "INSIRA UMA DESCRIÇÃO PARA A CATEGORIA");

					return Boolean.FALSE;
				}

				return Boolean.TRUE;
			}
		};

		panelActions.addEventListener(new PanelEventAdapter<Categoria>() {

			@Override
			public void saveEvent(Categoria categoria) {
				txCodigo.setValue(categoria.getIdCategoria());
			}
		});
		painelContent.add(panelActions, "cell 0 6,grow");

	}

}
