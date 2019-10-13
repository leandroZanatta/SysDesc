package br.com.sysdesc.components.listeners;

import java.util.EventListener;

@FunctionalInterface
public interface SaveListener<T> extends EventListener {

	public void saveEvent(T panelEvent);

}
