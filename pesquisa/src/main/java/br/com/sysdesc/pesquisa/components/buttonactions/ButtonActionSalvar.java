package br.com.sysdesc.pesquisa.components.buttonactions;

import java.awt.event.KeyEvent;

public class ButtonActionSalvar extends ButtonAction {

	private static final long serialVersionUID = 1L;

	public ButtonActionSalvar() {

		super("mapSalvar", KeyEvent.VK_S, KeyEvent.CTRL_MASK, "save.png", "Salvar");
	}

	@Override
	public void saveEvent() {
		setEnabled(Boolean.FALSE);
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
		setEnabled(Boolean.FALSE);
	}

	@Override
	public void startEvent() {
		setEnabled(Boolean.FALSE);
	}
}
