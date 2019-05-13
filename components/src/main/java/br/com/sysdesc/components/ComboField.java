package br.com.sysdesc.components;

import java.lang.reflect.Field;

public class ComboField {

    private String descricao;

    private Field field;

    public ComboField(String descricao, Field field) {
        this.descricao = descricao;
        this.field = field;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    @Override
    public String toString() {
        return getDescricao();
    }

}
