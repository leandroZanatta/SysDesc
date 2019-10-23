package br.com.sysdesc.components;

import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class JTextFieldMaiusculo extends JTextField {

	private static final long serialVersionUID = 1L;

	public JTextFieldMaiusculo() {
		this(0);
	}

	public JTextFieldMaiusculo(int limiteCampo) {
		super();
		setDocument(new CustomDocument(limiteCampo));
	}

}

class CustomDocument extends PlainDocument {

	private static final long serialVersionUID = 1L;
	private final int limiteCampo;

	public CustomDocument(int limiteCampo) {
		this.limiteCampo = limiteCampo;
	}

	public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {

		if (str == null) {
			return;
		}

		if (limiteCampo == 0 || getLength() < this.limiteCampo) {

			super.insertString(offset, str.toUpperCase(), attr);

		}

		return;
	}
}