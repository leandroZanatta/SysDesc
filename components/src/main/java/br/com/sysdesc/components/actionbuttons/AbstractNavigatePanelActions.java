package br.com.sysdesc.components.actionbuttons;

import java.awt.Component;
import java.awt.Container;
import java.awt.Insets;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.EventListenerList;

import br.com.sysdesc.components.actionbuttons.buttons.SaveButtonAction;
import br.com.sysdesc.components.actionbuttons.listeners.PanelActionListener;
import br.com.sysdesc.repository.model.PermissaoPrograma;
import br.com.sysdesc.util.classes.ClassTypeUtil;
import br.com.sysdesc.util.classes.ImageUtil;
import br.com.sysdesc.util.classes.interfaces.GenericDAO;
import net.miginfocom.swing.MigLayout;

public abstract class AbstractNavigatePanelActions<T> extends JPanel {

	private static final long serialVersionUID = 1L;
	private static final String NENHUM_REGISTRO_ENCONTRADO = "NENHUM REGISTRO ENCONTRADO.";
	protected EventListenerList listenerList = new EventListenerList();
	private JButton btnFirst;
	private JButton btnFowad;
	private SaveButtonAction btnSave;
	private JButton btnEdit;
	private JButton btnNew;
	private JButton btnSearch;
	private JButton btnCancel;
	private JButton btnNext;
	private JButton btnLast;

	private T objetoPesquisa;
	private Map<Class<? extends Component>, List<Component>> camposTela = new HashMap<>();
	protected Boolean isEdit = Boolean.FALSE;
	private final JFrame jframe;
	private final JInternalFrame internalFrame;
	private final GenericDAO<T> genericDAO;

	public AbstractNavigatePanelActions(JFrame jframe, JInternalFrame internalFrame, PermissaoPrograma permissaoPrograma,
			GenericDAO<T> genericDAO) {
		this.jframe = jframe;
		this.internalFrame = internalFrame;
		this.genericDAO = genericDAO;
		initComponents();
	}

	private void initComponents() {

		setLayout(new MigLayout("", "[grow][][][][][][][][][][grow]", "[23px,grow,center]"));

		btnFirst = new JButton("");
		btnFowad = new JButton("");
		btnSave = new SaveButtonAction();
		btnEdit = new JButton("");
		btnNew = new JButton("");
		btnSearch = new JButton("");
		btnCancel = new JButton("");
		btnNext = new JButton("");
		btnLast = new JButton("");

		btnFirst.setIcon(ImageUtil.resize("first.png", 24, 24));
		btnFowad.setIcon(ImageUtil.resize("fowad.png", 24, 24));
		btnEdit.setIcon(ImageUtil.resize("edit.png", 24, 24));
		btnNew.setIcon(ImageUtil.resize("new.png", 24, 24));
		btnSearch.setIcon(ImageUtil.resize("search.png", 24, 24));
		btnCancel.setIcon(ImageUtil.resize("cancel.png", 24, 24));
		btnNext.setIcon(ImageUtil.resize("next.png", 24, 24));
		btnLast.setIcon(ImageUtil.resize("last.png", 24, 24));

		btnFirst.setToolTipText("Primeiro");
		btnFowad.setToolTipText("Anterior");
		btnEdit.setToolTipText("Editar");
		btnNew.setToolTipText("Novo");
		btnSearch.setToolTipText("Pesquisar");
		btnCancel.setToolTipText("Cancelar");
		btnNext.setToolTipText("Proximo");
		btnLast.setToolTipText("Último");

		final AbstractNavigatePanelActions<T> painel = this;

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
		add(btnEdit, "cell 4 0,alignx left,aligny center");
		add(btnNew, "cell 5 0,alignx left,aligny center");
		add(btnSearch, "cell 6 0,alignx left,aligny center");
		add(btnCancel, "cell 7 0,alignx left,aligny center");
		add(btnNext, "cell 8 0,alignx left,aligny center");
		add(btnLast, "cell 9 0,alignx left,aligny center");

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
	private void newEvent(AbstractNavigatePanelActions<T> painel) {

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

		preencherObjeto(objetoPesquisa);

		genericDAO.salvar(objetoPesquisa);

		bloquear(Boolean.TRUE);

		bloquearBotoes(Boolean.TRUE, Boolean.FALSE, Boolean.TRUE);

		fireSaveEvent(objetoPesquisa);
	}

	private void fowadEvent(AbstractNavigatePanelActions<T> painel) {

		objetoPesquisa = genericDAO.previows(objetoPesquisa);

		if (objetoPesquisa == null) {
			JOptionPane.showMessageDialog(painel, NENHUM_REGISTRO_ENCONTRADO);
		}

		limpar();

		bloquear(Boolean.TRUE);

		carregarObjeto(objetoPesquisa);

		bloquearBotoes(Boolean.TRUE, Boolean.FALSE, Boolean.TRUE);

	}

	private void lastEvent(AbstractNavigatePanelActions<T> painel) {

		objetoPesquisa = genericDAO.last();

		if (objetoPesquisa == null) {
			JOptionPane.showMessageDialog(painel, NENHUM_REGISTRO_ENCONTRADO);
		}

		limpar();

		bloquear(Boolean.TRUE);

		carregarObjeto(objetoPesquisa);

		bloquearBotoes(Boolean.TRUE, Boolean.FALSE, Boolean.TRUE);
	}

	private void firstEvent(AbstractNavigatePanelActions<T> painel) {

		objetoPesquisa = genericDAO.first();

		if (objetoPesquisa == null) {
			JOptionPane.showMessageDialog(painel, NENHUM_REGISTRO_ENCONTRADO);
		}

		limpar();

		bloquear(Boolean.TRUE);

		carregarObjeto(objetoPesquisa);

		bloquearBotoes(Boolean.TRUE, Boolean.FALSE, Boolean.TRUE);

	}

	private void nextEvent(AbstractNavigatePanelActions<T> painel) {

		objetoPesquisa = genericDAO.next(objetoPesquisa);

		if (objetoPesquisa == null) {
			JOptionPane.showMessageDialog(painel, NENHUM_REGISTRO_ENCONTRADO);
		}

		limpar();

		bloquear(Boolean.TRUE);

		carregarObjeto(objetoPesquisa);

		bloquearBotoes(Boolean.TRUE, Boolean.FALSE, Boolean.TRUE);

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
			camposTela.get(JComboBox.class).forEach(x -> ((JComboBox<?>) x).setEditable(!bloquear));
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

	@SuppressWarnings("unchecked")
	public void pesquisar() {

		try {

			Class<?> classePesquisa = Class.forName("br.com.sysdesc.pesquisa.ui.Pesquisa");

			Constructor<?> constructor = classePesquisa.getConstructor(JFrame.class, Long.class, Long.class);

			Method metodoVisibilidade = classePesquisa.getMethod("setVisible", Boolean.class);

			Object instancia = constructor.newInstance(this.jframe);

			metodoVisibilidade.invoke(instancia, Boolean.TRUE);

			Method metodoIsOK = classePesquisa.getMethod("isOk");

			Boolean isOk = (Boolean) metodoIsOK.invoke(instancia);

			if (isOk) {

				Method metodoGetObjeto = classePesquisa.getMethod("getObjeto");

				this.objetoPesquisa = (T) metodoGetObjeto.invoke(instancia);

				limpar();

				bloquear(Boolean.TRUE);

				carregarObjeto(objetoPesquisa);

				bloquearBotoes(Boolean.TRUE, Boolean.FALSE, Boolean.TRUE);

			}

		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "ERRO AO INICIAR PESQUISA");
		}

	}

	public abstract void carregarObjeto(T objeto);

	public abstract void preencherObjeto(T objetoPesquisa);

}
