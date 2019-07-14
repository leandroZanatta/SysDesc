package br.com.sysdesc.pesquisa.formatters;

import java.awt.Component;

public interface Formatter {

	public String format(Object value, String format);

	public String getFormatterKey();

	public Component getComponent();

}
