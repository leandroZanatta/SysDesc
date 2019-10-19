package br.com.sysdesc.controller;

import br.com.sysdesc.http.server.anotation.RequestMethod;
import br.com.sysdesc.http.server.anotation.RestController;
import br.com.sysdesc.http.server.enumeradores.HttpMethod;
import br.com.sysdesc.service.gerenciador.GerenciadorInicializacaoService;
import br.com.sysdesc.util.vo.ConfigurationVO;

@RestController(path = "/configuracaoModulos")
public class ConfiguracaoModulosController {

	private GerenciadorInicializacaoService gerenciadorInicializacaoService = new GerenciadorInicializacaoService();

	@RequestMethod(method = HttpMethod.GET, path = "/buscarconfiguracaomodulos")
	public ConfigurationVO atualizarConfiguracoes(String ipGerenciador) {

		return gerenciadorInicializacaoService.buscarConfiguracoesInicializacaoAtualizadas(ipGerenciador);
	}
}
