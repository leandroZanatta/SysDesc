package br.com.sysdesc.pesquisa.components;

import java.awt.Component;
import java.awt.Container;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.EventListenerList;

import br.com.sysdesc.components.AbstractInternalFrame;
import br.com.sysdesc.components.listeners.PanelActionListener;
import br.com.sysdesc.pesquisa.enumeradores.PesquisaEnum;
import br.com.sysdesc.pesquisa.ui.FrmPesquisa;
import br.com.sysdesc.repository.interfaces.GenericDAO;
import br.com.sysdesc.util.classes.ClassTypeUtil;
import br.com.sysdesc.util.classes.ImageUtil;
import net.miginfocom.swing.MigLayout;

public abstract class PanelActions<T> extends JPanel {

	private static final String NENHUM_REGISTRO_ENCONTRADO = "NENHUM REGISTRO ENCONTRADO.";
	private static final long serialVersionUID = 1L;
	protected EventListenerList listenerList = new EventListenerList();
	private JButton btnFirst;
	private JButton btnFowad;
	private JButton btnSave;
	private JButton btnEdit;
	private JButton btnNew;
	private JButton btnSearch;
	private JButton btnCancel;
	private JButton btnNext;
	private JButton btnLast;
	private final AbstractInternalFrame internalFrame;
	private final GenericDAO<T> genericDAO;
	private final PesquisaEnum pesquisa;
	private T objetoPesquisa;
	private Map<Class<? extends Component>, List<Component>> camposTela = new HashMap<>();
	protected Boolean isEdit = Boolean.FALSE;
	private final Function<T, Long> id;

	public PanelActions(AbstractInternalFrame internalFrame, Function<T, Long> id, GenericDAO<T> genericDAO,
			PesquisaEnum pesquisa) {
		this.internalFrame = internalFrame;
		this.genericDAO = genericDAO;
		this.pesquisa = pesquisa;
		this.id = id;
		initComponents();
	}

	private void initComponents() {

		setLayout(new MigLayout("", "[grow][][][][][][][][][][grow]", "[23px,grow,center]"));

		btnFirst = new JButton("");
		btnFowad = new JButton("");
		btnSave = new JButton("");
		btnEdit = new JButton("");
		btnNew = new JButton("");
		btnSearch = new JButton("");
		btnCancel = new JButton("");
		btnNext = new JButton("");
		btnLast = new JButton("");

		btnFirst.setIcon(ImageUtil.resize("first.png", 24, 24));
		btnFowad.setIcon(ImageUtil.resize("fowad.png", 24, 24));
		btnSave.setIcon(ImageUtil.resize("save.png", 24, 24));
		btnEdit.setIcon(ImageUtil.resize("edit.png", 24, 24));
		btnNew.setIcon(ImageUtil.resize("new.png", 24, 24));
		btnSearch.setIcon(ImageUtil.resize("search.png", 24, 24));
		btnCancel.setIcon(ImageUtil.resize("cancel.png", 24, 24));
		btnNext.setIcon(ImageUtil.resize("next.png", 24, 24));
		btnLast.setIcon(ImageUtil.resize("last.png", 24, 24));

		btnFirst.setToolTipText("Primeiro");
		btnFowad.setToolTipText("Anterior");
		btnSave.setToolTipText("Salvar");
		btnEdit.setToolTipText("Editar");
		btnNew.setToolTipText("Novo");
		btnSearch.setToolTipText("Pesquisar");
		btnCancel.setToolTipText("Cancelar");
		btnNext.setToolTipText("Proximo");
		btnLast.setToolTipText("Último");

		final PanelActions<T> painel = this;

		btnCancel.addActionListener(e -> internalFrame.dispose());

		btnNext.addActionListener(e -> nextEvent(painel));

		btnFirst.addActionListener(e -> firstEvent(painel));

		btnLast.addActionListener(e -> lastEvent(painel));

		btnFowad.addActionListener(e -> fowadEvent(painel));

		btnNew.addActionListener(e -> newEvent(painel));

		btnSave.addActionListener(e -> saveEvent());

		btnEdit.addActionListener(e -> editEvent());

		btnSearch.addActionListener(e -> searchEvent());

		Insets margens = new Insets(10, 10, 10, 10);
		btnFirst.setMargin(margens);
		btnFowad.setMargin(margens);
		btnSave.setMargin(margens);
		btnEdit.setMargin(margens);
		btnNew.setMargin(margens);
		btnSearch.setMargin(margens);
		btnCancel.setMargin(margens);
		btnNext.setMargin(margens);
		btnLast.setMargin(margens);

		add(btnFirst, "cell 1 0,alignx left,aligny center");
		add(btnFowad, "cell 2 0,alignx left,aligny center");
		add(btnSave, "cell 3 0,alignx left,aligny center");
		add(btnEdit, "flowy,cell 4 0,alignx left,aligny center");
		add(btnNew, "cell 5 0,alignx left,aligny center");
		add(btnSearch, "cell 6 0,alignx left,aligny center");
		add(btnCancel, "cell 7 0");
		add(btnNext, "cell 8 0");
		add(btnLast, "cell 9 0");

		registrarCampos();

		limpar();

		bloquear(Boolean.TRUE);

		bloquearBotoes(Boolean.FALSE, Boolean.FALSE, Boolean.TRUE);

	}

	private void searchEvent() {

		pesquisar();

		limpar();

		carregarObjeto(objetoPesquisa);

		bloquearBotoes(Boolean.TRUE, Boolean.FALSE, Boolean.TRUE);
	}

	private void editEvent() {

		bloquear(Boolean.FALSE);

		isEdit = Boolean.TRUE;

		bloquearBotoes(Boolean.FALSE, Boolean.TRUE, Boolean.TRUE);
	}

	@SuppressWarnings("unchecked")
	private void newEvent(PanelActions<T> painel) {

		try {

			objetoPesquisa = (T) ClassTypeUtil.getGenericType(painel.getClass()).newInstance();

			limpar();

			bloquear(Boolean.FALSE);

			bloquearBotoes(Boolean.FALSE, Boolean.TRUE, Boolean.FALSE);

			fireNewEvent();

		} catch (IllegalAccessException | InstantiationException e1) {
			e1.printStackTrace();
		}

	}

	private void saveEvent() {

		if (this.objetoValido()) {

			preencherObjeto(objetoPesquisa);

			genericDAO.salvar(objetoPesquisa);

			bloquear(Boolean.TRUE);

			bloquearBotoes(Boolean.TRUE, Boolean.FALSE, Boolean.TRUE);

			fireSaveEvent(objetoPesquisa);

		}
	}

	private void fowadEvent(PanelActions<T> painel) {

		objetoPesquisa = genericDAO.previows(getValueId());

		if (objetoPesquisa == null) {
			JOptionPane.showMessageDialog(painel, NENHUM_REGISTRO_ENCONTRADO);
		}

		limpar();

		bloquear(Boolean.TRUE);

		carregarObjeto(objetoPesquisa);

		bloquearBotoes(Boolean.TRUE, Boolean.FALSE, Boolean.TRUE);

	}

	private void lastEvent(PanelActions<T> painel) {

		objetoPesquisa = genericDAO.last();

		if (objetoPesquisa == null) {
			JOptionPane.showMessageDialog(painel, NENHUM_REGISTRO_ENCONTRADO);
		}

		limpar();

		bloquear(Boolean.TRUE);

		carregarObjeto(objetoPesquisa);

		bloquearBotoes(Boolean.TRUE, Boolean.FALSE, Boolean.TRUE);
	}

	private void firstEvent(PanelActions<T> painel) {

		objetoPesquisa = genericDAO.first();

		if (objetoPesquisa == null) {
			JOptionPane.showMessageDialog(painel, NENHUM_REGISTRO_ENCONTRADO);
		}

		limpar();

		bloquear(Boolean.TRUE);

		carregarObjeto(objetoPesquisa);

		bloquearBotoes(Boolean.TRUE, Boolean.FALSE, Boolean.TRUE);

	}

	private void nextEvent(PanelActions<T> painel) {

		objetoPesquisa = genericDAO.next(getValueId());

		if (objetoPesquisa == null) {
			JOptionPane.showMessageDialog(painel, NENHUM_REGISTRO_ENCONTRADO);
		}

		limpar();

		bloquear(Boolean.TRUE);

		carregarObjeto(objetoPesquisa);

		bloquearBotoes(Boolean.TRUE, Boolean.FALSE, Boolean.TRUE);

	}

	private Long getValueId() {
		Long valor = null;

		if (objetoPesquisa != null) {
			valor = id.apply(objetoPesquisa);
		}
		return valor;
	}

	protected void bloquearBotoes(Boolean edit, Boolean save, Boolean novo) {

		btnEdit.setEnabled(edit);

		btnSave.setEnabled(save);

		btnNew.setEnabled(novo);
	}

	private void registrarCampos() {

		findComponents(internalFrame.getContentPane());
	}

	private void findComponents(Container container) {

		for (Component component : container.getComponents()) {

			if (component instanceof Container) {
				findComponents((Container) component);
			}

			if (component instanceof JTextField) {

				addCampo(component, JTextField.class);
			} else if (component instanceof JComboBox) {

				addCampo(component, JComboBox.class);
			} else if (component instanceof JCheckBox) {

				addCampo(component, JCheckBox.class);
			} else if (component instanceof JRadioButton) {

				addCampo(component, JRadioButton.class);
			} else if (component instanceof JTable) {

				addCampo(component, JTable.class);
			} else if (component instanceof JButton) {

				addCampo(component, JButton.class);
			}

		}

	}

	private void addCampo(Component component, Class<? extends Component> classe) {

		if (!camposTela.containsKey(classe)) {
			camposTela.put(classe, new ArrayList<>());
		}

		camposTela.get(classe).add(component);
	}

	protected void bloquear(Boolean bloquear) {

		if (camposTela.containsKey(JTextField.class)) {
			camposTela.get(JTextField.class).forEach(x -> ((JTextField) x).setEditable(!bloquear));
		}

		if (camposTela.containsKey(JComboBox.class)) {
			camposTela.get(JComboBox.class).forEach(x -> ((JComboBox<?>) x).setEnabled(!bloquear));
		}

		if (camposTela.containsKey(JCheckBox.class)) {
			camposTela.get(JCheckBox.class).forEach(x -> ((JCheckBox) x).setEnabled(!bloquear));
		}

		if (camposTela.containsKey(JRadioButton.class)) {
			camposTela.get(JRadioButton.class).forEach(x -> ((JRadioButton) x).setEnabled(!bloquear));
		}

		if (camposTela.containsKey(JTable.class)) {
			camposTela.get(JTable.class).forEach(x -> ((JTable) x).setEnabled(!bloquear));
		}

		if (camposTela.containsKey(JButton.class)) {
			camposTela.get(JButton.class).forEach(x -> ((JButton) x).setEnabled(!bloquear));
		}

		fireChangeStateEvent(bloquear);

		isEdit = Boolean.FALSE;
	}

	protected void limpar() {

		if (camposTela.containsKey(JTextField.class)) {
			camposTela.get(JTextField.class).forEach(x -> ((JTextField) x).setText(""));
		}

		if (camposTela.containsKey(JComboBox.class)) {
			camposTela.get(JComboBox.class).forEach(x -> ((JComboBox<?>) x).setSelectedIndex(-1));
		}

		if (camposTela.containsKey(JCheckBox.class)) {
			camposTela.get(JCheckBox.class).forEach(x -> ((JCheckBox) x).setSelected(Boolean.FALSE));
		}

		if (camposTela.containsKey(JRadioButton.class)) {
			camposTela.get(JRadioButton.class).forEach(x -> ((JRadioButton) x).setSelected(Boolean.FALSE));
		}

		if (camposTela.containsKey(JTable.class)) {
			camposTela.get(JTable.class).forEach(x -> ((JTable) x).getModel().getRowCount());
		}

		fireClearEvent();
	}

	public Boolean objetoValido() {

		return Boolean.TRUE;
	}

	public void addEventListener(PanelActionListener<T> panelEvent) {

		listenerList.add(PanelActionListener.class, panelEvent);
	}

	public void removeMyEventListener(PanelActionListener<T> panelEvent) {

		listenerList.remove(PanelActionListener.class, panelEvent);
	}

	@SuppressWarnings("unchecked")
	public void fireSaveEvent(T evt) {
		Object[] listeners = listenerList.getListenerList();

		for (int i = 0; i < listeners.length; i = i + 2) {

			if (listeners[i] == PanelActionListener.class) {

				((PanelActionListener<T>) listeners[i + 1]).saveEvent(evt);
			}
		}
	}

	public void fireClearEvent() {

		Object[] listeners = listenerList.getListenerList();

		for (int i = 0; i < listeners.length; i = i + 2) {

			if (listeners[i] == PanelActionListener.class) {

				((PanelActionListener<?>) listeners[i + 1]).clearEvent();
			}
		}
	}

	public void fireNewEvent() {

		Object[] listeners = listenerList.getListenerList();

		for (int i = 0; i < listeners.length; i = i + 2) {

			if (listeners[i] == PanelActionListener.class) {

				((PanelActionListener<?>) listeners[i + 1]).newEvent();
			}
		}
	}

	public void fireChangeStateEvent(Boolean evt) {

		Object[] listeners = listenerList.getListenerList();

		for (int i = 0; i < listeners.length; i = i + 2) {

			if (listeners[i] == PanelActionListener.class) {

				((PanelActionListener<?>) listeners[i + 1]).changeStateEvent(evt);
			}
		}
	}

	public GenericDAO<T> getGenericDAO() {
		return genericDAO;
	}

	public T getObjetoPesquisa() {
		return objetoPesquisa;
	}

	public void setObjetoPesquisa(T objetoPesquisa) {
		this.objetoPesquisa = objetoPesquisa;
	}

	public void pesquisar() {
		try {
			JFrame parent = (JFrame) SwingUtilities.getAncestorOfClass(JFrame.class, this);

			FrmPesquisa<T> pesquisa = new FrmPesquisa<>(parent, this.pesquisa, this.genericDAO,
					this.internalFrame.getCodigoUsuario());

			pesquisa.setVisible(Boolean.TRUE);

			if (pesquisa.getOk()) {

				this.objetoPesquisa = pesquisa.getObjeto();

				limpar();

				bloquear(Boolean.TRUE);

				carregarObjeto(objetoPesquisa);

				bloquearBotoes(Boolean.TRUE, Boolean.FALSE, Boolean.TRUE);
			}
		} catch (RuntimeException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}

	public abstract void carregarObjeto(T objeto);

	public abstract void preencherObjeto(T objetoPesquisa);

}