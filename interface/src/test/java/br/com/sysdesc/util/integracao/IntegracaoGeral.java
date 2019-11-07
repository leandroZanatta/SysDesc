package br.com.sysdesc.util.integracao;

import java.lang.reflect.Field;

import javax.swing.JButton;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import br.com.sysdesc.repository.model.Usuario;
import br.com.sysdesc.ui.FrmApplication;
import br.com.sysdesc.ui.FrmLogin;
import br.com.sysdesc.ui.util.LookAndFeelUtil;
import br.com.sysdesc.util.FrmUtil;

@RunWith(MockitoJUnitRunner.class)
public class IntegracaoGeral {

    @Test
    public void testarIntegracao() throws Exception {

        Class<?>[] parameters = {};

        Object[] valores = {};

        LookAndFeelUtil.configureLayout();

        new GeracaoBaseVazia().gerarBaseVazia();

        FrmApplication frmApplication = new FrmApplication();
        frmApplication.setVisible(Boolean.TRUE);

        FrmUtil.setarUsuario(frmApplication, autenticarUsuario(frmApplication, "admin", "123456"));

        FrmUtil.executeVoidMethod(frmApplication, "createMenus", parameters, valores);
        FrmUtil.executeVoidMethod(frmApplication, "setarLabels", parameters, valores);
    }

    private Usuario autenticarUsuario(FrmApplication frmApplication, String usuario, String senha) {

        FrmLogin frmLogin = new FrmLogin(frmApplication);

        new Thread(new Runnable() {

            @Override
            public void run() {

                try {

                    Thread.sleep(1000);

                    Field field = frmLogin.getClass().getDeclaredField("txLogin");

                    field.setAccessible(true);

                    JTextField txLogin = (JTextField) field.get(frmLogin);

                    txLogin.setText(usuario);

                    Field fieldSenha = frmLogin.getClass().getDeclaredField("txSenha");

                    fieldSenha.setAccessible(true);

                    JPasswordField txSenha = (JPasswordField) fieldSenha.get(frmLogin);

                    txSenha.setText(senha);

                    Field fieldOk = frmLogin.getClass().getDeclaredField("btLogin");

                    fieldOk.setAccessible(true);

                    JButton btOk = (JButton) fieldOk.get(frmLogin);

                    btOk.doClick();

                } catch (Exception e) {

                    System.exit(0);
                }

            }

        }).start();

        frmLogin.setVisible(Boolean.TRUE);

        return frmLogin.getUsuario();
    }
}
