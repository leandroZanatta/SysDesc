package br.com.sysdesc.pesquisa.components.buttonactions;

import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.KeyStroke;

import br.com.sysdesc.components.listeners.ButtonActionListener;
import br.com.sysdesc.util.classes.ImageUtil;

public abstract class ButtonAction extends JButton implements ButtonActionListener {

	private final String mapName;
	private final KeyStroke keyStroke;
	private int ordem = 0;

	private static final long serialVersionUID = 1L;

	public ButtonAction(String mapName, int keyCode, int modifiers, String image, String tooltip) {
		super("");
		this.mapName = mapName;
		this.keyStroke = KeyStroke.getKeyStroke(keyCode, modifiers);

		setMargin(new Insets(10, 10, 10, 10));
		setIcon(ImageUtil.resize(image, 24, 24));
		setToolTipText(tooltip);
	}

	public String getMapName() {
		return mapName;
	}

	public KeyStroke getKeyStroke() {
		return keyStroke;
	}

	public int getOrdem() {
		return ordem;
	}

	public void setOrdem(int ordem) {
		this.ordem = ordem;
	}

}
