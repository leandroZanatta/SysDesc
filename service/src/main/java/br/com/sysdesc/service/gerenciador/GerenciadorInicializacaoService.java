package br.com.sysdesc.service.gerenciador;

import java.util.List;

import br.com.sysdesc.repository.dao.GerenciadorPDVDAO;
import br.com.sysdesc.repository.model.GerenciadorPDV;
import br.com.sysdesc.util.vo.ConfigurationVO;
import br.com.sysdesc.util.vo.ServerVO;

public class GerenciadorInicializacaoService {

	private GerenciadorPDVDAO gerenciadorPDVDAO = new GerenciadorPDVDAO();

	public List<GerenciadorPDV> buscarGerenciadoresCadastrados() {

		return gerenciadorPDVDAO.listar();
	}

	public ConfigurationVO buscarConfiguracoesInicializacaoAtualizadas(String ipGerenciador) {

		ConfigurationVO configurationVO = new ConfigurationVO();

		GerenciadorPDV gerenciadorPDV = gerenciadorPDVDAO.buscarPorIp(ipGerenciador);

		gerenciadorPDV.getModuloGerenciadorPDVs().forEach(modulo -> {

			ServerVO serverVO = new ServerVO();
			serverVO.setAsync(Boolean.TRUE);
			serverVO.setServerName(modulo.getModuloPDV().getDescricao());
			serverVO.setId(modulo.getModuloPDV().getIdModuloPDV());

			if (modulo.getModuloPDV().getDescricao().equals("FRONTEND")) {

				serverVO.setComand("sysdescpdv.exe");

				configurationVO.getFrontEnds().add(serverVO);
			} else {

				serverVO.setComand("java -jar sysdesc-rest.jar");
				serverVO.setMsgValidacaoStart("Started Application");

				configurationVO.getServers().add(serverVO);
			}

		});

		return configurationVO;
	}

}
