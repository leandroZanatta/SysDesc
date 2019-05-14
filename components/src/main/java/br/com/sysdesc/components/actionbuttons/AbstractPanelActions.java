package br.com.sysdesc.components.actionbuttons;

import javax.swing.event.EventListenerList;

import br.com.sysdesc.components.AbstractInternalFrame;
import br.com.sysdesc.components.actionbuttons.buttons.JButtonAction;

public abstract class AbstractPanelActions<T> extends AbstractPanel<T> {

	private static final long serialVersionUID = 1L;
	private static final String NENHUM_REGISTRO_ENCONTRADO = "NENHUM REGISTRO ENCONTRADO.";
	protected EventListenerList listenerList = new EventListenerList();

	/*
	 * private SaveButtonAction btnSave = new SaveButtonAction(); private
	 * EditButtonAction btnEdit = new EditButtonAction(); private NewButtonAction
	 * btnNew = new NewButtonAction(); private SearchButtonAction btnSearch = new
	 * SearchButtonAction(); private CancelButtonAction btnCancel = new
	 * CancelButtonAction();
	 * 
	 * private T objetoPesquisa; protected Boolean isEdit = Boolean.FALSE; private
	 * final JFrame jframe; private final GenericDAO<T> genericDAO;
	 */

	public AbstractPanelActions(AbstractInternalFrame<T> internalFrame, JButtonAction[] actions) {
		super(internalFrame, actions);

	}

}
