package br.com.sysdesc.gerenciador.inicializacao.controller;

import java.util.List;

import br.com.sysdesc.gerenciador.inicializacao.service.InicializacaoModulosService;
import br.com.sysdesc.gerenciador.inicializacao.service.impl.InicializacaoModulosServiceIpml;
import br.com.sysdesc.http.server.anotation.RequestBody;
import br.com.sysdesc.http.server.anotation.RequestMethod;
import br.com.sysdesc.http.server.anotation.RestController;
import br.com.sysdesc.http.server.enumeradores.HttpMethod;
import br.com.sysdesc.util.vo.IPVO;

@RestController(path = "/configuracaoModulo")
public class ConfiguracaoModulosController {

	private InicializacaoModulosService inicializacaoModulosService = InicializacaoModulosServiceIpml.getInstance();

	@RequestMethod(method = HttpMethod.POST, path = "/atualizarConfiguracoes")
	public Boolean atualizarConfiguracoes(@RequestBody List<IPVO> ipvos) {

		inicializacaoModulosService.atualizarConfiguracao(ipvos);

		return true;
	}
}
