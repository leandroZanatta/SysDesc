package br.com.sysdesc.pesquisa.components.buttonactions;

import java.awt.event.KeyEvent;

public class ButtonActionAvancar extends ButtonAction {

	private static final long serialVersionUID = 1L;

	public ButtonActionAvancar() {

		super("mapAvancar", KeyEvent.VK_PAGE_UP, 0, "next.png", "Avançar");
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
