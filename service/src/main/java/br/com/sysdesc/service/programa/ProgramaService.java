package br.com.sysdesc.service.programa;

import java.util.List;

import br.com.sysdesc.repository.dao.ProgramaDAO;
import br.com.sysdesc.repository.model.Programa;
import br.com.sysdesc.service.interfaces.impl.AbstractGenericService;

public class ProgramaService extends AbstractGenericService<Programa> {

	private ProgramaDAO programaDAO;

	public ProgramaService() {
		this(new ProgramaDAO());
	}

	public ProgramaService(ProgramaDAO programaDAO) {
		super(programaDAO, Programa::getIdPrograma);

		this.programaDAO = programaDAO;
	}

	public List<Programa> buscarRootProgramas() {

		return programaDAO.buscarRootProgramas();
	}
}
