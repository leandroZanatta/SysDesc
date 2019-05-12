package br.com.sysdesc.components.adapters;

import br.com.sysdesc.components.listeners.PanelActionListener;

public abstract class PanelEventAdapter<T> implements PanelActionListener<T> {

	@Override
	public void saveEvent(T panelEvent) {
		
	}

	@Override
	public void clearEvent() {
		
	}

	@Override
	public void newEvent() {
		
	}

	@Override
	public void changeStateEvent(Boolean state) {
		
	}

	

}
