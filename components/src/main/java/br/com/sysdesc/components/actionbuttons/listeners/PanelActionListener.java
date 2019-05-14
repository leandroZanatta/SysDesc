package br.com.sysdesc.components.actionbuttons.listeners;

import java.util.EventListener;

public interface PanelActionListener<T> extends EventListener {

	public void saveEvent(T panelEvent);

	public void clearEvent();

	public void newEvent();

	public void changeStateEvent(Boolean state);

}
