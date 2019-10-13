package br.com.sysdesc.components.listeners;

import java.util.EventListener;

public interface ChangeListener<T> extends EventListener {

	public void valueChanged(T newValue, T oldValue);

}
