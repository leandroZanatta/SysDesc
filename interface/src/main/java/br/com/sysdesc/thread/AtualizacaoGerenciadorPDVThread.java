package br.com.sysdesc.thread;

import java.util.List;

import br.com.sysdesc.intercomm.PDVClient;
import br.com.sysdesc.intercomm.builder.RequestBuilder;
import br.com.sysdesc.repository.model.GerenciadorPDV;
import br.com.sysdesc.service.gerenciador.GerenciadorInicializacaoService;
import br.com.sysdesc.util.classes.IPUtil;
import br.com.sysdesc.util.resources.ApplicationProperies;
import br.com.sysdesc.util.vo.IPVO;
import feign.Retryer;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AtualizacaoGerenciadorPDVThread implements Runnable {

	private GerenciadorInicializacaoService gerenciadorInicializacaoService = new GerenciadorInicializacaoService();

	@Override
	public void run() {

		List<IPVO> ipERP = IPUtil.getIps();

		ipERP.forEach(ip -> ip
				.setPorta(Integer.valueOf(ApplicationProperies.getInstance().getProperty("erp.server.port", "3200"))));

		List<GerenciadorPDV> gerenciadorPDVs = gerenciadorInicializacaoService.buscarGerenciadoresCadastrados();

		log.info("Numero de gerenciadores cadastrados: " + gerenciadorPDVs.size());

		atualizarConfiguracoes(ipERP, gerenciadorPDVs);

	}

	private Boolean atualizarConfiguracoes(List<IPVO> ipERP, List<GerenciadorPDV> gerenciadorPDVs) {

		for (GerenciadorPDV gerenciador : gerenciadorPDVs) {

			String url = String.format("http://%s:%d", gerenciador.getIpGerenciador(), gerenciador.getCodigoPorta());

			try {

				Boolean retornoConfiguracao = RequestBuilder.build().retryer(Retryer.NEVER_RETRY)
						.target(PDVClient.class, url).atualizarGerenciadorPDV(ipERP);

				if (retornoConfiguracao) {

					log.info("Atualizada configuração de ip do gerenciador:" + url);
				}

				return retornoConfiguracao;

			} catch (Exception e) {

				log.warn("Erro ao atualizar configuração do gerenciador" + e.getMessage());
			}

		}

		return false;
	}

}
