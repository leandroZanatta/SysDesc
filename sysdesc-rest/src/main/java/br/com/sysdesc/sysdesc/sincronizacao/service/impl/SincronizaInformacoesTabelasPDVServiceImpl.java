package br.com.sysdesc.sysdesc.sincronizacao.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import br.com.sysdesc.sysdesc.sincronizacao.service.SincronizacaoInformacoesService;
import br.com.sysdesc.sysdesc.sincronizacao.service.SincronizacaoItemService;

@Service("sincronizacaoTabelas")
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class SincronizaInformacoesTabelasPDVServiceImpl implements SincronizacaoInformacoesService {

	@Autowired
	@Lazy
	private ApplicationContext applicationContext;

	@Override
	public Boolean sincronizar() throws Exception {

		Collection<SincronizacaoItemService> mapaSincronizacoes = applicationContext
				.getBeansOfType(SincronizacaoItemService.class).values();

		AsyncResult<List<Future<Boolean>>> threads = this.sincronizarTabelas(mapaSincronizacoes);

		return Boolean.TRUE;
	}

	private AsyncResult<List<Future<Boolean>>> sincronizarTabelas(
			Collection<SincronizacaoItemService> sincronizacaoItems) throws InterruptedException {

		List<Future<Boolean>> listaFutures = new ArrayList<>();

		sincronizacaoItems.forEach(sincronizacao -> {
			listaFutures.add(executarSincronizacaoTabela(sincronizacao));
		});

		return new AsyncResult<List<Future<Boolean>>>(listaFutures);
	}

	@Async("executorSincronizacaoGrupoTabelas")
	private Future<Boolean> executarSincronizacaoTabela(SincronizacaoItemService sincronizacao) {
		return null;
	}

}
