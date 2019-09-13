package br.com.sysdesc.renderes;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.plaf.ColorUIResource;
import javax.swing.tree.TreeCellRenderer;

public class CheckBoxPanel extends JPanel implements TreeCellRenderer {

	private static final long serialVersionUID = 1L;

	private TreeLabel label;
	private JCheckBox chVisualizar;
	private JCheckBox chCadastrar;
	private JCheckBox chExcluir;
	private Long posicao = 0L;

	public CheckBoxPanel() {
		setLayout(null);

		label = new TreeLabel();
		chVisualizar = new JCheckBox("");
		chCadastrar = new JCheckBox("");
		chExcluir = new JCheckBox("");

		label.setForeground(UIManager.getColor("Tree.textForeground"));
		chVisualizar.setBackground(UIManager.getColor("Tree.textBackground"));
		chCadastrar.setBackground(UIManager.getColor("Tree.textBackground"));
		chExcluir.setBackground(UIManager.getColor("Tree.textBackground"));

		add(label);
		add(chVisualizar);
		add(chCadastrar);
		add(chExcluir);
	}

	@Override
	public void setBackground(Color color) {

		if (color instanceof ColorUIResource) {
			color = null;
		}

		super.setBackground(color);
	}

	@Override
	public Component getTreeCellRendererComponent(JTree tree, Object value, boolean isSelected, boolean expanded,
			boolean leaf, int row, boolean hasFocus) {

		this.posicao = ((CheckNodePrograma) value).getNivel();

		String stringValue = tree.convertValueToText(value, isSelected, expanded, leaf, row, hasFocus);

		setEnabled(tree.isEnabled());

		chVisualizar.setSelected(((CheckNodePrograma) value).isSelected());

		label.setFont(tree.getFont());
		label.setText(stringValue);
		label.setSelected(isSelected);
		label.setFocus(hasFocus);

		if (leaf) {
			label.setIcon(UIManager.getIcon("Tree.leafIcon"));
		} else if (expanded) {
			label.setIcon(UIManager.getIcon("Tree.openIcon"));
		} else {
			label.setIcon(UIManager.getIcon("Tree.closedIcon"));
		}

		return this;
	}

	public Dimension getPreferredSize() {

		Long totalNivel = ((TreePanelPrograma) this.getParent().getParent()).getNiveis();

		Long tamanho = 425 - ((totalNivel - posicao) * 20L);

		return new Dimension(tamanho.intValue(), 20);
	}

	public void doLayout() {
		Dimension d_label = label.getPreferredSize();
		Dimension d_check = chVisualizar.getPreferredSize();

		int y_check = 0;
		int y_label = 0;

		if (d_check.height < d_label.height) {
			y_check = (d_label.height - d_check.height) / 2;
		} else {
			y_label = (d_check.height - d_label.height) / 2;
		}

		label.setLocation(0, y_label);
		label.setBounds(0, y_label, 200, d_label.height);

		Long totalNivel = ((TreePanelPrograma) this.getParent().getParent()).getNiveis();

		Long tamanho = 420 - ((totalNivel - 1) * 27L);

		Long posicaoCheckbox = tamanho - 69 + (posicao * 20L);

		chVisualizar.setLocation(posicaoCheckbox.intValue(), y_check);
		chVisualizar.setBounds(posicaoCheckbox.intValue(), y_check, d_check.width, d_check.height);

		chCadastrar.setLocation(posicaoCheckbox.intValue() + 23, y_check);
		chCadastrar.setBounds(posicaoCheckbox.intValue() + 23, y_check, d_check.width, d_check.height);

		chExcluir.setLocation(posicaoCheckbox.intValue() + 46, y_check);
		chExcluir.setBounds(posicaoCheckbox.intValue() + 46, y_check, d_check.width, d_check.height);
	}

}
