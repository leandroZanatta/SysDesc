
package br.com.sysdesc.util.enumeradores;

import java.util.Comparator;
import java.util.List;

public enum ModificadoresEnum {

	PRIVATE(1L), PROTECTED(2L), PUBLIC(3L), FINAL(4L), ABSTRACT(5L), STATIC(6L);

	Long ordenacao;

	private ModificadoresEnum(Long ordenacao) {
		this.ordenacao = ordenacao;
	}

	public Long getOrdenacao() {
		return ordenacao;
	}

	public static String getModifiers(List<ModificadoresEnum> modificadoresEnum) {

		StringBuilder stringBuilder = new StringBuilder();

		modificadoresEnum.stream().sorted(Comparator.comparing(ModificadoresEnum::getOrdenacao))
				.forEach(x -> stringBuilder.append(x.toString().toLowerCase()).append(" "));

		return stringBuilder.toString().trim();
	}
}
