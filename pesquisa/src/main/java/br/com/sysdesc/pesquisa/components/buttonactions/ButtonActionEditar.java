package br.com.sysdesc.pesquisa.components.buttonactions;

import java.awt.event.KeyEvent;

public class ButtonActionEditar extends ButtonAction {

	private static final long serialVersionUID = 1L;

	public ButtonActionEditar() {

		super("mapEditar", KeyEvent.VK_E, KeyEvent.CTRL_MASK, "edit.png", "Editar");
	}

	@Override
	public void saveEvent() {
		setEnabled(Boolean.FALSE);
	}

	@Override
	public void editEvent() {
		setEnabled(Boolean.FALSE);
	}

	@Override
	public void newEvent() {
		setEnabled(Boolean.FALSE);
	}

	@Override
	public void searchEvent() {
		setEnabled(Boolean.TRUE);
	}

	@Override
	public void startEvent() {
		setEnabled(Boolean.FALSE);
	}

}
