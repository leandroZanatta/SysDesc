package br.com.sysdesc.service.estado;

import java.util.List;

import br.com.sysdesc.repository.dao.EstadoDAO;
import br.com.sysdesc.repository.model.Estado;
import br.com.sysdesc.service.interfaces.impl.AbstractGenericService;
import br.com.sysdesc.util.classes.StringUtil;
import br.com.sysdesc.util.constants.MensagemConstants;
import br.com.sysdesc.util.exception.SysDescException;

public class EstadoService extends AbstractGenericService<Estado> {

	private final EstadoDAO estadoDAO;

	public EstadoService() {
		this(new EstadoDAO());
	}

	public EstadoService(EstadoDAO estadoDAO) {
		super(estadoDAO, Estado::getIdEstado);

		this.estadoDAO = estadoDAO;
	}

	@Override
	public void validar(Estado objetoPersistir) {

		if (StringUtil.isNullOrEmpty(objetoPersistir.getDescricao())) {

			throw new SysDescException(MensagemConstants.MENSAGEM_INSIRA_DESCRICAO_VALIDA);
		}

	}

	public List<Estado> listarEstados() {

		return estadoDAO.listar();
	}

}
