package br.com.sysdesc.util;

import static org.junit.Assert.assertTrue;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Optional;

import javax.swing.JButton;
import javax.swing.JInternalFrame;

import br.com.sysdesc.enumerator.ProgramasEnum;
import br.com.sysdesc.pesquisa.components.PanelActions;
import br.com.sysdesc.repository.model.PermissaoPrograma;
import br.com.sysdesc.repository.model.Usuario;
import br.com.sysdesc.ui.FrmApplication;
import br.com.sysdesc.ui.FrmDepartamento;

public class FrmUtil {

	public static JInternalFrame openInstance(FrmApplication frmApplication, List<PermissaoPrograma> permissaoProgramas,
			ProgramasEnum programa) throws Exception {

		Optional<PermissaoPrograma> optional = permissaoProgramas.stream()
				.filter(x -> x.getCodigoPrograma().equals(programa.getCodigo())).findFirst();

		assertTrue(optional.isPresent());

		Class<?>[] parameters = { Class.class, PermissaoPrograma.class };

		Method metodo = frmApplication.getClass().getDeclaredMethod("getSingleInstance", parameters);

		metodo.setAccessible(true);

		Object[] objeto = { programa.getInternalFrame(), optional.get() };

		metodo.invoke(frmApplication, objeto);

		return frmApplication.getDesktopPane().getAllFrames()[0];
	}

	public static void setarUsuario(FrmApplication frmApplication, Usuario usuario) throws Exception {

		Field field = frmApplication.getClass().getDeclaredField("usuario");

		field.setAccessible(true);

		field.set(frmApplication, usuario);

	}

	public static void executeVoidMethod(Object object, String method, Class<?>[] parameters, Object[] valores)
			throws Exception {

		Method metodo = object.getClass().getDeclaredMethod(method, parameters);

		metodo.setAccessible(true);

		metodo.invoke(object, valores);
	}

	@SuppressWarnings("unchecked")
	public static <T> T getFied(Object frm, Class<?> clazz, String fieldStr) throws Exception {

		Field field = clazz.getDeclaredField(fieldStr);

		field.setAccessible(true);

		return (T) field.get(frm);
	}

	public static void novoRegistro(FrmDepartamento frmDepartamento, Class<?> clazz) throws Exception {

		PanelActions<?> panelActions = FrmUtil.getFied(frmDepartamento, clazz, "panelActions");

		((JButton) FrmUtil.getFied(panelActions, PanelActions.class, "btNovo")).doClick();
	}

	public static void salvarRegistro(FrmDepartamento frmDepartamento, Class<FrmDepartamento> clazz) throws Exception {
		PanelActions<?> panelActions = FrmUtil.getFied(frmDepartamento, clazz, "panelActions");

		((JButton) FrmUtil.getFied(panelActions, PanelActions.class, "btSalvar")).doClick();
	}

}
