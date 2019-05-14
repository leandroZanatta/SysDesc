package br.com.sysdesc.components.actionbuttons;

import java.awt.Component;
import java.awt.Container;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.JTextField;

import br.com.sysdesc.components.AbstractInternalFrame;
import br.com.sysdesc.components.actionbuttons.buttons.JButtonAction;
import br.com.sysdesc.components.actionbuttons.listeners.PanelActionListener;
import net.miginfocom.swing.MigLayout;

public class AbstractPanel<T> extends JPanel {

	private static final long serialVersionUID = 1L;

	private Map<Class<? extends Component>, List<Component>> camposTela = new HashMap<>();
	private final AbstractInternalFrame<T> internalFrame;
	private final JButtonAction[] actions;

	public AbstractPanel(AbstractInternalFrame<T> internalFrame, JButtonAction... actions) {
		this.internalFrame = internalFrame;
		this.actions = actions;

		initComponents();
	}

	private void initComponents() {

		setLayout(new MigLayout("", "[grow][][][][][][][][][][grow]", "[23px,grow,center]"));

		registrarCampos();

		limpar();

		bloquear(Boolean.TRUE);

	}

	private void registrarCampos() {

		this.findComponents(internalFrame.getContentPane());
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

	private void bloquear(Boolean bloquear) {

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

	}

	private void limpar() {

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

	}

	public void addEventListener(PanelActionListener<T> panelEvent) {

		listenerList.add(PanelActionListener.class, panelEvent);
	}

	public void removeEventListener(PanelActionListener<T> panelEvent) {

		listenerList.remove(PanelActionListener.class, panelEvent);
	}

	public void fireChangeStateEvent(Boolean evt) {

		Object[] listeners = listenerList.getListenerList();

		for (int i = 0; i < listeners.length; i = i + 2) {

			if (listeners[i] == PanelActionListener.class) {

				((PanelActionListener<?>) listeners[i + 1]).changeStateEvent(evt);
			}
		}
	}

}
