package br.com.sysdesc.service.perfil;

import br.com.sysdesc.repository.dao.PerfilDAO;
import br.com.sysdesc.repository.model.Perfil;
import br.com.sysdesc.service.interfaces.impl.AbstractGenericService;
import br.com.sysdesc.util.classes.StringUtil;
import br.com.sysdesc.util.constants.MensagemConstants;
import br.com.sysdesc.util.exception.SysDescException;

public class PerfilService extends AbstractGenericService<Perfil> {

	public PerfilService() {
		super(new PerfilDAO(), Perfil::getIdPerfil);
	}

	@Override
	public void validar(Perfil objetoPersistir) {

		if (StringUtil.isNullOrEmpty(objetoPersistir.getDescricao())) {

			throw new SysDescException(MensagemConstants.INSIRA_DESCRICAO_VALIDA);
		}
	}
}
