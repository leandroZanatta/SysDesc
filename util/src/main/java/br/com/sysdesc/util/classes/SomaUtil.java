package br.com.sysdesc.util.classes;

import java.math.BigDecimal;

public class SomaUtil {

	private BigDecimal value;

	public SomaUtil() {
		this(BigDecimal.ZERO);
	}

	public SomaUtil(BigDecimal value) {
		this.value = value;
	}

	public BigDecimal somar(BigDecimal valor) {
		this.value = this.value.add(valor);

		return this.value;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

}
