package br.com.sysdesc.pesquisa.components.buttonactions;

import java.awt.event.KeyEvent;

public class ButtonActionCancelar extends ButtonAction {

	private static final long serialVersionUID = 1L;

	public ButtonActionCancelar() {

		super("mapCancelar", KeyEvent.VK_ESCAPE, 0, "cancel.png", "Cancelar");
	}

	@Override
	public void saveEvent() {
		setEnabled(Boolean.TRUE);
	}

	@Override
	public void editEvent() {
		setEnabled(Boolean.TRUE);
	}

	@Override
	public void newEvent() {
		setEnabled(Boolean.TRUE);
	}

	@Override
	public void searchEvent() {
		setEnabled(Boolean.TRUE);
	}

	@Override
	public void startEvent() {
		setEnabled(Boolean.TRUE);
	}

}
