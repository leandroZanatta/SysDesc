package br.com.sysdesc.pesquisa.components.buttonactions;

import java.awt.event.KeyEvent;

public class ButtonActionPrimeiro extends ButtonAction {

	private static final long serialVersionUID = 1L;

	public ButtonActionPrimeiro() {

		super("mapPrimeiro", KeyEvent.VK_PAGE_UP, KeyEvent.CTRL_MASK, "first.png", "Primeiro");
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
