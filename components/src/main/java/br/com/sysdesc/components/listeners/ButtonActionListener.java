package br.com.sysdesc.components.listeners;

import java.util.EventListener;

public interface ButtonActionListener extends EventListener {

	public void startEvent();

	public void saveEvent();

	public void editEvent();

	public void newEvent();

	public void searchEvent();

}
