package br.com.sysdesc.components.actionbuttons.buttons;

import java.awt.Insets;

import javax.swing.JButton;

import br.com.sysdesc.util.classes.ImageUtil;
import br.com.sysdesc.util.classes.LongUtil;

public class JButtonAction extends JButton {

	private static final long serialVersionUID = 1L;
	private Long order = LongUtil.UM;

	public JButtonAction(String iconName, String tooltip) {
		super(ImageUtil.resize(iconName, 24, 24));

		this.setToolTipText(tooltip);

		this.setMargin(new Insets(10, 10, 10, 10));

	}

	public Long getOrder() {
		return order;
	}

	public void setOrder(Long order) {
		this.order = order;
	}

}
