package br.com.sysdesc.util.vo;

import java.lang.reflect.Field;

public class FieldPesquisaVO {

	private final String name;

	private final Field field;

	public FieldPesquisaVO(String name, Field field) {
		this.name = name;
		this.field = field;
	}

	public String getName() {
		return name;
	}

	public Field getField() {
		return field;
	}

	@Override
	public String toString() {

		return name;
	}

	@Override
	public int hashCode() {
		return name.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return obj != null && this.hashCode() == obj.hashCode();
	}

}
