package br.com.sysdesc.pesquisa.formatters.impl;

import static br.com.sysdesc.util.constants.MensagemConstants.MENSAGEM_INSIRA_NUMERO_CASAS_DECIMAIS;

import java.awt.Component;
import java.math.BigDecimal;
import java.text.NumberFormat;

import javax.swing.JPanel;

import br.com.sysdesc.components.JNumericField;
import br.com.sysdesc.pesquisa.formatters.Formatter;
import br.com.sysdesc.util.classes.StringUtil;
import br.com.sysdesc.util.exception.SysDescException;

public class BigdecimalFormat extends JPanel implements Formatter {

	private static final long serialVersionUID = 1L;

	private JNumericField casasDecimais;

	public BigdecimalFormat() {

		casasDecimais = new JNumericField();

		casasDecimais.setValue(2L);

		add(casasDecimais);
	}

	@Override
	public String format(Object value, String format) {

		if (value == null) {
			return "";
		}

		if (StringUtil.isNullOrEmpty(format)) {
			return value.toString();
		}

		NumberFormat nf = NumberFormat.getNumberInstance();
		nf.setMaximumFractionDigits(Integer.valueOf(format));
		nf.setMinimumFractionDigits(Integer.valueOf(format));

		return nf.format(((BigDecimal) value).doubleValue());
	}

	@Override
	public String getFormatterKey() {

		if (casasDecimais.getValue() == null) {
			throw new SysDescException(MENSAGEM_INSIRA_NUMERO_CASAS_DECIMAIS);
		}

		return casasDecimais.getValue().toString();
	}

	@Override
	public Component getComponent() {
		return this;
	}

}
