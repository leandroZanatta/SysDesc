package br.com.sysdesc.thread;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class PDVSchedule {

	private static PDVSchedule instance;

	private ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

	private PDVSchedule() {

	}

	public void startScheduleAtualizacaoGerenciadores() {

		executorService.scheduleAtFixedRate(new AtualizacaoGerenciadorPDVThread(), 0, 20, TimeUnit.SECONDS);
	}

	public static PDVSchedule getInstance() {

		if (instance == null) {
			instance = new PDVSchedule();
		}

		return instance;
	}

}
