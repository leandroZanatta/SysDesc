package br.com.sysdesc.pesquisa.components.buttonactions;

import java.awt.Component;
import java.awt.Container;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.EventListenerList;

import br.com.sysdesc.components.AbstractInternalFrame;

public abstract class AbstractButtonAction extends JPanel {

	private static final long serialVersionUID = 1L;
	protected EventListenerList listenerList = new EventListenerList();
	protected final AbstractInternalFrame internalFrame;
	private Map<Class<? extends Component>, List<Component>> camposTela = new HashMap<>();

	public AbstractButtonAction(AbstractInternalFrame internalFrame) {
		this.internalFrame = internalFrame;

	}

	protected void registrarCampos() {

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

		this.fireClearEvent();
	}

	protected abstract void fireClearEvent();

	protected abstract void fireChangeStateEvent(Boolean bloquear);

}
