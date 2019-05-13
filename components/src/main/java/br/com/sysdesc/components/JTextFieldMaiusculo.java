package br.com.sysdesc.components;

import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class JTextFieldMaiusculo extends JTextField {

    private static final long serialVersionUID = 1L;

    public JTextFieldMaiusculo() {
        super();
        setDocument(new CustomDocument());
    }

}

class CustomDocument extends PlainDocument {

    private static final long serialVersionUID = 1L;

    public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {

        if (str == null) {
            return;
        }

        super.insertString(offset, str.toUpperCase(), attr);

        return;
    }
}