package br.com.sysdesc.components.listeners;

import java.util.EventListener;

@FunctionalInterface
public interface NewListener extends EventListener {

	public void newEvent();

}
