package br.com.sysdesc.util.integracao.departamentos;

import java.util.List;

import br.com.sysdesc.enumerator.ProgramasEnum;
import br.com.sysdesc.repository.model.PermissaoPrograma;
import br.com.sysdesc.ui.FrmApplication;
import br.com.sysdesc.util.FrmUtil;

public class TestarDepartamento {

    public void testCadastrarDepartamento(FrmApplication frmApplication, List<PermissaoPrograma> permissaoProgramas) throws Exception {

        FrmUtil.openInstance(frmApplication, permissaoProgramas, ProgramasEnum.CADASTRO_DEPARTAMENTOS);
    }

}
