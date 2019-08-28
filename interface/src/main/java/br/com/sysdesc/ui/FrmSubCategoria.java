
package br.com.sysdesc.ui;

import static br.com.sysdesc.pesquisa.enumeradores.PesquisaEnum.PES_SUBCATEGORIAS;
import static br.com.sysdesc.util.resources.Resources.FRMSUBCATEGORIA_LB_CATEGORIA;
import static br.com.sysdesc.util.resources.Resources.FRMSUBCATEGORIA_LB_CODIGO;
import static br.com.sysdesc.util.resources.Resources.FRMSUBCATEGORIA_LB_DEPARTAMENTO;
import static br.com.sysdesc.util.resources.Resources.FRMSUBCATEGORIA_LB_DESCRICAO;
import static br.com.sysdesc.util.resources.Resources.FRMSUBCATEGORIA_TITLE;
import static br.com.sysdesc.util.resources.Resources.translate;

import javax.swing.JComboBox;
import javax.swing.JLabel;

import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

import br.com.sysdesc.components.AbstractInternalFrame;
import br.com.sysdesc.components.JTextFieldMaiusculo;
import br.com.sysdesc.pesquisa.components.JTextFieldId;
import br.com.sysdesc.pesquisa.components.PanelActions;
import br.com.sysdesc.repository.model.Categoria;
import br.com.sysdesc.repository.model.Departamento;
import br.com.sysdesc.repository.model.PermissaoPrograma;
import br.com.sysdesc.repository.model.Subcategoria;
import br.com.sysdesc.service.categoria.CategoriaService;
import br.com.sysdesc.service.departamento.DepartamentoService;
import br.com.sysdesc.service.subcategoria.SubcategoriaService;
import net.miginfocom.swing.MigLayout;

public class FrmSubCategoria extends AbstractInternalFrame {
	private static final long serialVersionUID = 1L;

	private JTextFieldId txCodigo;
	private JTextFieldMaiusculo txDescricao;
	private JLabel lbCodigo;
	private JLabel lbCategoria;
	private JComboBox<Categoria> cbCategoria;
	private JLabel lbDescricao;
	private JLabel lbDepartamento;
	private JComboBox<Departamento> cbDepartamento;

	private PanelActions<Subcategoria> panelActions;

	private DepartamentoService departamentoService = new DepartamentoService();
	private CategoriaService categoriaService = new CategoriaService();
	private SubcategoriaService subcategoriaService = new SubcategoriaService();

	public FrmSubCategoria(PermissaoPrograma permissaoPrograma, Long codigoUsuario) {
		super(permissaoPrograma, codigoUsuario);

		initComponents();
	}

	private void initComponents() {

		setSize(450, 270);
		setClosable(Boolean.TRUE);
		setTitle(translate(FRMSUBCATEGORIA_TITLE));

		getContentPane().setLayout(new MigLayout("", "[grow]", "[][][][][][][][][]"));

		lbCodigo = new JLabel(translate(FRMSUBCATEGORIA_LB_CODIGO));
		txCodigo = new JTextFieldId();
		cbCategoria = new JComboBox<>();
		lbDescricao = new JLabel(translate(FRMSUBCATEGORIA_LB_DESCRICAO));
		txDescricao = new JTextFieldMaiusculo();
		cbDepartamento = new JComboBox<>();
		lbCategoria = new JLabel(translate(FRMSUBCATEGORIA_LB_CATEGORIA));
		lbDepartamento = new JLabel(translate(FRMSUBCATEGORIA_LB_DEPARTAMENTO));

		AutoCompleteDecorator.decorate(cbDepartamento);
		AutoCompleteDecorator.decorate(cbCategoria);
		departamentoService.listarDepartamentos().forEach(cbDepartamento::addItem);

		cbDepartamento.addActionListener((e) -> selecionarCategorias());

		getContentPane().add(lbCodigo, "cell 0 0");
		getContentPane().add(txCodigo, "cell 0 1,width 50:100:100");
		getContentPane().add(lbDepartamento, "cell 0 2");
		getContentPane().add(cbDepartamento, "cell 0 3,growx");
		getContentPane().add(lbCategoria, "cell 0 4");
		getContentPane().add(cbCategoria, "cell 0 5,growx");
		getContentPane().add(lbDescricao, "cell 0 6");
		getContentPane().add(txDescricao, "cell 0 7,growx");

		panelActions = new PanelActions<Subcategoria>(this, subcategoriaService, PES_SUBCATEGORIAS) {

			private static final long serialVersionUID = 1L;

			@Override
			public void carregarObjeto(Subcategoria objeto) {
				txCodigo.setValue(objeto.getIdSubcategoria());
				cbDepartamento.setSelectedItem(objeto.getCategoria().getDepartamento());
				cbCategoria.setSelectedItem(objeto.getCategoria());
				txDescricao.setText(objeto.getDescricao());
			}

			@Override
			public Boolean preencherObjeto(Subcategoria objetoPesquisa) {
				objetoPesquisa.setIdSubcategoria(txCodigo.getValue());

				if (cbCategoria.getSelectedIndex() >= 0) {
					objetoPesquisa.setCategoria((Categoria) cbCategoria.getSelectedItem());
				}
				objetoPesquisa.setDescricao(txDescricao.getText());

				return Boolean.TRUE;
			}
		};

		panelActions.addSaveListener((subcategoria) -> txCodigo.setValue(subcategoria.getIdSubcategoria()));

		getContentPane().add(panelActions, "cell 0 8,grow");
	}

	private void selecionarCategorias() {

		cbCategoria.removeAllItems();

		if (cbDepartamento.getSelectedIndex() >= 0) {

			Long codigoDepartamento = ((Departamento) cbDepartamento.getSelectedItem()).getIdDepartamento();

			categoriaService.buscarPorDepartamento(codigoDepartamento).forEach(cbCategoria::addItem);

			cbCategoria.setSelectedIndex(-1);
		}
	}
}
