package br.com.sysdesc.pesquisa.formatters.impl;

import static br.com.sysdesc.util.constants.MensagemConstants.MENSAGEM_SELECIONE_UMA_FORMATACAO;

import java.awt.Component;
import java.text.NumberFormat;

import javax.swing.JComboBox;

import br.com.sysdesc.pesquisa.formatters.Formatter;
import br.com.sysdesc.util.classes.StringUtil;
import br.com.sysdesc.util.exception.SysDescException;

public class LongFormatter extends JComboBox<LongComponent> implements Formatter {

	private static final long serialVersionUID = 1L;

	public LongFormatter() {

		addItem(new LongComponent("Casas de Milhar", "1"));
	}

	@Override
	public String format(Object value, String format) {

		if (value == null) {
			return "";
		}

		if (StringUtil.isNullOrEmpty(format)) {
			return value.toString();
		}

		if (format.equals("1")) {

			return NumberFormat.getNumberInstance().format(value);
		}

		return String.format(format, value);
	}

	@Override
	public String getFormatterKey() {

		if (getSelectedIndex() < 0) {
			throw new SysDescException(MENSAGEM_SELECIONE_UMA_FORMATACAO);
		}

		return ((LongComponent) getSelectedItem()).getValor();
	}

	@Override
	public Component getComponent() {
		return this;
	}

}

class LongComponent {

	private final String descricao;

	private final String valor;

	public LongComponent(String descricao, String valor) {
		this.descricao = descricao;
		this.valor = valor;
	}

	public String getDescricao() {
		return descricao;
	}

	public String getValor() {
		return valor;
	}

	@Override
	public String toString() {

		return descricao;
	}
}
