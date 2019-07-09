package br.com.sysdesc.service.departamento;

import java.util.List;

import br.com.sysdesc.repository.dao.DepartamentoDAO;
import br.com.sysdesc.repository.model.Departamento;
import br.com.sysdesc.service.interfaces.impl.AbstractGenericService;
import br.com.sysdesc.util.classes.StringUtil;
import br.com.sysdesc.util.constants.MensagemConstants;
import br.com.sysdesc.util.exception.SysDescException;

public class DepartamentoService extends AbstractGenericService<Departamento> {

	private final DepartamentoDAO departamentoDAO;

	public DepartamentoService() {

		this(new DepartamentoDAO());
	}

	public DepartamentoService(DepartamentoDAO departamentoDAO) {
		super(departamentoDAO, Departamento::getIdDepartamento);

		this.departamentoDAO = departamentoDAO;
	}

	@Override
	public void validar(Departamento objetoPersistir) {

		if (StringUtil.isNullOrEmpty(objetoPersistir.getDescricao())) {

			throw new SysDescException(MensagemConstants.INSIRA_DESCRICAO_VALIDA);
		}

	}

	public List<Departamento> listarDepartamentos() {

		return departamentoDAO.listar();
	}

}
