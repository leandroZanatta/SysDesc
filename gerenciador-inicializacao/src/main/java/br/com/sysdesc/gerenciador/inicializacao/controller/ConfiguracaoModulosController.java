package br.com.sysdesc.gerenciador.inicializacao.controller;

import br.com.sysdesc.gerenciador.inicializacao.http.anotations.RequestMethod;
import br.com.sysdesc.gerenciador.inicializacao.http.anotations.RestController;
import br.com.sysdesc.gerenciador.inicializacao.http.enumerators.HttpMethod;

@RestController(path = "/configuracaoModulo")
public class ConfiguracaoModulosController {

	@RequestMethod(method = HttpMethod.POST, path = "/atualizarConfiguracoes")
	public Boolean atualizarConfiguracoes() {

		return true;
	}
}
