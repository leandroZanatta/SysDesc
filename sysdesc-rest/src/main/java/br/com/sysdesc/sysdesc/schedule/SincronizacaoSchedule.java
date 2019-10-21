package br.com.sysdesc.sysdesc.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import br.com.sysdesc.sysdesc.sincronizacao.factory.SincronizaDadosPDVFactory;

@Component
public class SincronizacaoSchedule {

	@Autowired
	@Lazy
	private SincronizaDadosPDVFactory sincronizaDadosPDVFactory;

	@Scheduled(cron = "0 * * * * *")
	public void executar() {

		sincronizaDadosPDVFactory.sincronizarDados();
	}
}
