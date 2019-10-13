package br.com.sysdesc.service.pdv;

import java.util.List;
import java.util.Optional;

import br.com.sysdesc.repository.dao.ModuloDAO;
import br.com.sysdesc.repository.dao.PdvDAO;
import br.com.sysdesc.repository.model.Modulo;
import br.com.sysdesc.repository.model.ModuloPDV;
import br.com.sysdesc.repository.model.Pdv;
import br.com.sysdesc.service.interfaces.impl.AbstractGenericService;
import br.com.sysdesc.util.classes.IPUtil;
import br.com.sysdesc.util.classes.LongUtil;
import br.com.sysdesc.util.classes.StringUtil;
import br.com.sysdesc.util.constants.MensagemConstants;
import br.com.sysdesc.util.exception.SysDescException;

public class PDVService extends AbstractGenericService<Pdv> {

	private PdvDAO pdvDAO;

	private ModuloDAO moduloDAO = new ModuloDAO();

	public PDVService() {
		this(new PdvDAO());
	}

	public PDVService(PdvDAO pdvDAO) {
		super(pdvDAO, Pdv::getIdPdv);

		this.pdvDAO = pdvDAO;
	}

	@Override
	public void validar(Pdv objetoPersistir) {

		if (LongUtil.isNullOrZero(objetoPersistir.getNumeroPDV())) {

			throw new SysDescException(MensagemConstants.MENSAGEM_INFORME_NUMERO_PDV);
		}

		if (StringUtil.isNullOrEmpty(objetoPersistir.getIpPDV())) {

			throw new SysDescException(MensagemConstants.MENSAGEM_INFORME_IP_PDV);
		}

		if (!IPUtil.isIpValid(objetoPersistir.getIpPDV())) {

			throw new SysDescException(MensagemConstants.MENSAGEM_IP_PDV_INVALIDO);
		}

		if (!IPUtil.isNetworkMatch(objetoPersistir.getIpPDV())) {

			throw new SysDescException(MensagemConstants.MENSAGEM_IP_PDV_FORA_REDE);
		}

		Pdv pdv = pdvDAO.buscarPorNumeroPDV(objetoPersistir.getIdPdv(), objetoPersistir.getNumeroPDV());

		if (pdv != null) {

			throw new SysDescException(MensagemConstants.MENSAGEM_PDV_JA_CADASTRADO);
		}

		Pdv pdvIp = pdvDAO.buscarPorIPPDV(objetoPersistir.getIdPdv(), objetoPersistir.getIpPDV());

		if (pdvIp != null) {

			throw new SysDescException(MensagemConstants.MENSAGEM_IP_JA_CADASTRADO);
		}
	}

	@Override
	public void salvar(Pdv objetoPersistir) {

		List<Modulo> modulos = moduloDAO.listar();

		modulos.forEach(modulo -> {

			ModuloPDV moduloPDV = getModulo(modulo, objetoPersistir.getModuloPDVs());

			moduloPDV.setIpModulo(objetoPersistir.getIpPDV());
			moduloPDV.setPdv(objetoPersistir);

			objetoPersistir.getModuloPDVs().add(moduloPDV);

		});

		pdvDAO.salvar(objetoPersistir);
	}

	private ModuloPDV getModulo(Modulo modulo, List<ModuloPDV> moduloPDVs) {

		Optional<ModuloPDV> optional = moduloPDVs.stream()
				.filter(x -> x.getModulo().getIdModulo().equals(modulo.getIdModulo())).findFirst();

		if (optional.isPresent()) {

			return optional.get();
		}

		ModuloPDV moduloPDV = new ModuloPDV();
		moduloPDV.setModulo(modulo);

		return moduloPDV;
	}
}
