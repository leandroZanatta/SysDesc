package br.com.sysdesc.sysdesc.sincronizacao.factory.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import br.com.sysdesc.sysdesc.sincronizacao.factory.SincronizaDadosPDVFactory;
import br.com.sysdesc.sysdesc.sincronizacao.service.SincronizacaoInformacoesService;
import br.com.sysdesc.sysdesc.sincronizacao.util.TipoSincronizacaoEnum;
import br.com.sysdesc.sysdesc.sincronizacao.util.constants.SysdescRestConstants;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class SincronizaDadosPDVFactoryImpl implements SincronizaDadosPDVFactory {

	@Autowired
	@Lazy
	@Qualifier(value = "sincronizacaoTabelas")
	private SincronizacaoInformacoesService sincronizaInformacoesTabelasPDVService;

	private Map<TipoSincronizacaoEnum, Future<Boolean>> processamentos = new HashMap<>();

	@Override
	public void sincronizarDados() {

		runThread(TipoSincronizacaoEnum.TABELAS, sincronizaInformacoesTabelasPDVService);
	}

	@Async("singleTaskExecutor")
	public void runThread(TipoSincronizacaoEnum tipoSincronizacao,
			SincronizacaoInformacoesService sincronizacaoInformacoes) {

		Future<Boolean> future = processamentos.get(tipoSincronizacao);

		if (future == null || future.isDone()) {

			try {

				processamentos.put(tipoSincronizacao, new AsyncResult<Boolean>(sincronizacaoInformacoes.sincronizar()));

			} catch (Exception e) {

				log.error(SysdescRestConstants.ERRO_INICIANDO_SINCRONIZACAO, e);
			}
		}
	}

}
