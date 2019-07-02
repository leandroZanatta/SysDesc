package br.com.sysdesc.atualizacao.util.classes;

import java.util.Properties;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.jtattoo.plaf.mint.MintLookAndFeel;

public class LookAndFeelUtil {

	private final static String FONTE = "Times New Roman plain 12";

	public static void configureLayout() throws ClassNotFoundException, InstantiationException, IllegalAccessException,
			UnsupportedLookAndFeelException {

		Properties p = new Properties();
		p.put("windowTitleFont", FONTE);
		p.put("logoString", "");
		p.put("windowDecoration", "off");
		p.setProperty("controlTextFont", FONTE);
		p.setProperty("systemTextFont", FONTE);
		p.setProperty("userTextFont", FONTE);
		p.setProperty("menuTextFont", FONTE);
		p.setProperty("windowTitleFont", FONTE);
		p.setProperty("subTextFont", FONTE);

		p.setProperty("controlTextFont", FONTE);
		p.setProperty("systemTextFont", FONTE);
		p.setProperty("userTextFont", FONTE);
		p.setProperty("menuTextFont", FONTE);
		p.setProperty("windowTitleFont", FONTE);
		p.setProperty("subTextFont", FONTE);

		p.setProperty("controlTextFont", FONTE);
		p.setProperty("systemTextFont", FONTE);
		p.setProperty("userTextFont", FONTE);
		p.setProperty("menuTextFont", FONTE);
		p.setProperty("windowTitleFont", FONTE);
		p.setProperty("subTextFont", FONTE);
		MintLookAndFeel.setCurrentTheme(p);

		UIManager.setLookAndFeel("com.jtattoo.plaf.mint.MintLookAndFeel");
	}
}
