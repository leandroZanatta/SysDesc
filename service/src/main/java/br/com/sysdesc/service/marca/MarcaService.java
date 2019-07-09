package br.com.sysdesc.service.marca;

import br.com.sysdesc.repository.dao.MarcaDAO;
import br.com.sysdesc.repository.model.Marca;
import br.com.sysdesc.service.interfaces.impl.AbstractGenericService;
import br.com.sysdesc.util.classes.StringUtil;
import br.com.sysdesc.util.constants.MensagemConstants;
import br.com.sysdesc.util.exception.SysDescException;

public class MarcaService extends AbstractGenericService<Marca> {

	public MarcaService() {
		super(new MarcaDAO(), Marca::getIdMarca);
	}

	@Override
	public void validar(Marca objetoPersistir) {

		if (StringUtil.isNullOrEmpty(objetoPersistir.getDescricao())) {

			throw new SysDescException(MensagemConstants.INSIRA_DESCRICAO_VALIDA);
		}

	}
}
