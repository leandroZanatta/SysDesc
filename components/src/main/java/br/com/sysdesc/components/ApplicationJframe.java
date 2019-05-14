package br.com.sysdesc.components;

import java.awt.Dimension;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import br.com.sysdesc.repository.model.PermissaoPrograma;

public class ApplicationJframe extends JFrame {

	private static final long serialVersionUID = 1L;

	protected List<AbstractInternalFrame> frames = new ArrayList<>();
	protected JDesktopPane desktopPane;;

	public void closeFrame(AbstractInternalFrame abstractInternalFrame) {

		this.frames.remove(abstractInternalFrame);
	}

	protected void getSingleInstance(Class<? extends AbstractInternalFrame> frame,
			PermissaoPrograma permissaoPrograma) {

		try {

			Optional<AbstractInternalFrame> optional = frames.stream().filter(x -> x.getClass().equals(frame))
					.findFirst();

			if (!optional.isPresent()) {

				Constructor<? extends AbstractInternalFrame> constructor = frame.getConstructor(ApplicationJframe.class,
						PermissaoPrograma.class);

				optional = Optional.of(constructor.newInstance(this, permissaoPrograma));

				this.desktopPane.add(optional.get());

				Dimension desktopSize = desktopPane.getSize();
				Dimension jInternalFrameSize = optional.get().getSize();

				optional.get().setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
						(desktopSize.height - jInternalFrameSize.height) / 2);

				this.frames.add(optional.get());
			}

			optional.get().setVisible(Boolean.TRUE);

		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "ERRO");
		}

	}

}
