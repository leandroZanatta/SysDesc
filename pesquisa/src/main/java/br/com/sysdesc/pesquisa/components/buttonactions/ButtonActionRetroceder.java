package br.com.sysdesc.pesquisa.components.buttonactions;

import java.awt.event.KeyEvent;

public class ButtonActionRetroceder extends ButtonAction {

	private static final long serialVersionUID = 1L;

	public ButtonActionRetroceder() {

		super("mapRetroceder", KeyEvent.VK_PAGE_DOWN, 0, "fowad.png", "Anterior");
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
