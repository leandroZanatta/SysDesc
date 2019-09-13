package br.com.sysdesc.renderes;

import java.util.Enumeration;

import javax.swing.tree.DefaultMutableTreeNode;

import br.com.sysdesc.repository.model.Programa;
import br.com.sysdesc.util.resources.Resources;

public class CheckNodePrograma extends DefaultMutableTreeNode {

	private static final long serialVersionUID = 1L;

	public final static int SINGLE_SELECTION = 0;

	public final static int DIG_IN_SELECTION = 4;

	protected int selectionMode;

	protected boolean isSelected;

	private Long nivel = 0L;

	public CheckNodePrograma() {
		this(null);
	}

	public CheckNodePrograma(Object userObject) {
		this(userObject, true, false);
	}

	public CheckNodePrograma(Object userObject, boolean allowsChildren, boolean isSelected) {
		super(userObject, allowsChildren);
		this.isSelected = isSelected;
		setSelectionMode(DIG_IN_SELECTION);
	}

	public void setSelectionMode(int mode) {
		selectionMode = mode;
	}

	public int getSelectionMode() {
		return selectionMode;
	}

	public void setSelected(boolean isSelected) {

		this.isSelected = isSelected;

		if ((selectionMode == DIG_IN_SELECTION) && (children != null)) {

			Enumeration<?> e = children.elements();

			while (e.hasMoreElements()) {
				((CheckNodePrograma) e.nextElement()).setSelected(isSelected);
			}
		}
	}

	public boolean isSelected() {
		return isSelected;
	}

	public void addNode(CheckNodePrograma newChild) {

		if (getNivel() <= newChild.getNivel()) {

			this.nivel = newChild.getNivel() + 1L;
		}

		if (newChild != null && newChild.getParent() == this)
			insert(newChild, getChildCount() - 1);
		else
			insert(newChild, getChildCount());
	}

	@Override
	public String toString() {

		if (userObject == null) {
			return "TODOS OS PROGRAMAS";
		}

		return Resources.translate(((Programa) userObject).getDescricao());
	}

	public Long getNivel() {
		return nivel;
	}

}