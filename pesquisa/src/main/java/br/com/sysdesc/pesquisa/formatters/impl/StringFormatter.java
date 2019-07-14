package br.com.sysdesc.pesquisa.formatters.impl;

import java.awt.Component;

import javax.swing.JTextField;

import br.com.sysdesc.pesquisa.formatters.Formatter;
import br.com.sysdesc.util.classes.StringUtil;

public class StringFormatter extends JTextField implements Formatter {

	private static final long serialVersionUID = 1L;

	public StringFormatter() {

	}

	@Override
	public String format(Object value, String format) {

		if (value == null) {
			return "";
		}

		if (StringUtil.isNullOrEmpty(format)) {

			return value.toString();
		}

		return String.format(format, value);
	}

	@Override
	public Component getComponent() {
		return this;
	}

	@Override
	public String getFormatterKey() {

		return getText();
	}

}
