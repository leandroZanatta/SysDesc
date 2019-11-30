package br.com.sysdesc.service.kardex;

import java.util.Date;
import java.util.List;

import br.com.sysdesc.repository.dao.KardexDAO;
import br.com.sysdesc.repository.model.Kardex;
import br.com.sysdesc.service.interfaces.impl.AbstractGenericService;

public class KardexService extends AbstractGenericService<Kardex> {

	private KardexDAO kardexDAO;

	public KardexService() {
		this(new KardexDAO());
	}

	public KardexService(KardexDAO kardexDAO) {
		super(new KardexDAO(), Kardex::getIdKardex);
		this.kardexDAO = kardexDAO;
	}

	public List<Kardex> buscarRegistroskardex(Long idEmpresa, Long idProduto, String operacao, Date dataInicial,
			Date datafinal) {

		return kardexDAO.buscarRegistroskardex(idEmpresa, idProduto, operacao, dataInicial, datafinal);
	}

}
