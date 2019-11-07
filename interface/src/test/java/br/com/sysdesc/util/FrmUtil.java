package br.com.sysdesc.util;

import static org.junit.Assert.assertTrue;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Optional;

import javax.swing.JInternalFrame;

import br.com.sysdesc.components.AbstractInternalFrame;
import br.com.sysdesc.enumerator.ProgramasEnum;
import br.com.sysdesc.repository.model.PermissaoPrograma;
import br.com.sysdesc.repository.model.Usuario;
import br.com.sysdesc.ui.FrmApplication;

public class FrmUtil {

    public static JInternalFrame openInstance(FrmApplication frmApplication, List<PermissaoPrograma> permissaoProgramas, ProgramasEnum programa)
            throws Exception {

        Optional<PermissaoPrograma> optional = permissaoProgramas.stream().filter(x -> x.getCodigoPrograma().equals(programa.getCodigo()))
                .findFirst();

        assertTrue(optional.isPresent());

        Class<?>[] parameters = { AbstractInternalFrame.class, PermissaoPrograma.class };

        Method metodo = frmApplication.getClass().getDeclaredMethod("getSingleInstance", parameters);

        Object[] objeto = { programa.getInternalFrame(), optional.get() };

        metodo.invoke(frmApplication, objeto);

        return frmApplication.getDesktopPane().getAllFrames()[0];
    }

    public static void setarUsuario(FrmApplication frmApplication, Usuario usuario) throws Exception {

        Field field = frmApplication.getClass().getDeclaredField("usuario");

        field.set(frmApplication, usuario);

    }

    public static void executeVoidMethod(FrmApplication frmApplication, String method, Class<?>[] parameters, Object[] valores) throws Exception {

        Method metodo = frmApplication.getClass().getDeclaredMethod(method, parameters);

        metodo.invoke(frmApplication, valores);
    }

}
