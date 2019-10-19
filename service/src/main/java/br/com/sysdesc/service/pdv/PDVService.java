package br.com.sysdesc.service.pdv;

import java.util.List;
import java.util.Optional;

import br.com.sysdesc.repository.dao.GerenciadorPDVDAO;
import br.com.sysdesc.repository.dao.ModuloPDVDAO;
import br.com.sysdesc.repository.dao.PdvDAO;
import br.com.sysdesc.repository.model.GerenciadorPDV;
import br.com.sysdesc.repository.model.ModuloGerenciadorPDV;
import br.com.sysdesc.repository.model.ModuloPDV;
import br.com.sysdesc.repository.model.PDVModuloGerenciadorPDV;
import br.com.sysdesc.repository.model.Pdv;
import br.com.sysdesc.service.interfaces.impl.AbstractGenericService;
import br.com.sysdesc.util.classes.IPUtil;
import br.com.sysdesc.util.classes.LongUtil;
import br.com.sysdesc.util.classes.StringUtil;
import br.com.sysdesc.util.constants.MensagemConstants;
import br.com.sysdesc.util.exception.SysDescException;

public class PDVService extends AbstractGenericService<Pdv> {

	private PdvDAO pdvDAO;

	private GerenciadorPDVDAO gerenciadorPDVDAO = new GerenciadorPDVDAO();

	private ModuloPDVDAO moduloDAO = new ModuloPDVDAO();

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

		if (!IPUtil.isOnlyNetworkMatch(objetoPersistir.getIpPDV())) {

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

		List<ModuloPDV> modulos = moduloDAO.listar();

		GerenciadorPDV gerenciadorPDV = buscarGerenciador(objetoPersistir);

		modulos.forEach(modulo -> gerarModuloPDV(objetoPersistir, gerenciadorPDV, modulo));

		pdvDAO.salvar(objetoPersistir);
	}

	private void gerarModuloPDV(Pdv objetoPersistir, GerenciadorPDV gerenciadorPDV, ModuloPDV modulo) {

		Optional<PDVModuloGerenciadorPDV> optional = objetoPersistir.getModuloGerenciadorPDVs().stream()
				.filter(x -> x.getModuloGerenciadorPDV() != null)
				.filter(x -> x.getModuloGerenciadorPDV().getModuloPDV() != null).filter(x -> x.getModuloGerenciadorPDV()
						.getModuloPDV().getIdModuloPDV().equals(modulo.getIdModuloPDV()))
				.findFirst();

		if (!optional.isPresent()) {

			PDVModuloGerenciadorPDV pdvModuloGerenciadorPDV = new PDVModuloGerenciadorPDV();

			ModuloGerenciadorPDV moduloGerenciadorPDV = new ModuloGerenciadorPDV();
			moduloGerenciadorPDV.setCodigoGerenciadorPDV(gerenciadorPDV.getIdGerenciadorPDV());
			moduloGerenciadorPDV.setCodigoModuloPDV(modulo.getIdModuloPDV());
			moduloGerenciadorPDV.setCodigoPorta(modulo.getPortaPadrao());

			pdvModuloGerenciadorPDV.setModuloGerenciadorPDV(moduloGerenciadorPDV);

			pdvModuloGerenciadorPDV.setPdv(objetoPersistir);

			objetoPersistir.getModuloGerenciadorPDVs().add(pdvModuloGerenciadorPDV);
		}
	}

	private GerenciadorPDV buscarGerenciador(Pdv objetoPersistir) {

		GerenciadorPDV gerenciadorPDV = gerenciadorPDVDAO.buscarPorIp(objetoPersistir.getIpPDV());

		if (gerenciadorPDV == null) {

			gerenciadorPDV = new GerenciadorPDV();
			gerenciadorPDV.setIpGerenciador(objetoPersistir.getIpPDV());
			gerenciadorPDV.setCodigoPorta(3100L);

			gerenciadorPDVDAO.salvar(gerenciadorPDV);
		}

		return gerenciadorPDV;
	}

}
