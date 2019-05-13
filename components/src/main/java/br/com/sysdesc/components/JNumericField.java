package br.com.sysdesc.components;

import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class JNumericField extends JTextField {

	private static final long serialVersionUID = 1L;

	public JNumericField() {
		super();
		setDocument(new LongDocumento());
	}

	public Long getValue() {
		try {
			return Long.valueOf(getText());
		} catch (Exception e) {
			return null;
		}
	}

	public void setValue(Long value) {
		if (value != null) {
			setText(value.toString());
		}
	}

}

class LongDocumento extends PlainDocument {

	private static final long serialVersionUID = 1L;

	@Override
	public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
		if (str == null)
			return;

		super.insertString(offset, str.replaceAll("[^0-9]", ""), attr);

		return;
	}
}