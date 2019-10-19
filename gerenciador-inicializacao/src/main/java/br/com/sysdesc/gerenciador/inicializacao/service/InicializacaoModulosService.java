package br.com.sysdesc.gerenciador.inicializacao.service;

import java.util.List;
import java.util.concurrent.ExecutionException;

import br.com.sysdesc.gerenciador.inicializacao.listener.InicializacaoModulosListener;
import br.com.sysdesc.util.vo.IPVO;

public interface InicializacaoModulosService {

	public abstract void addInicializacaoModulosListener(InicializacaoModulosListener listener);

	public abstract void removeInicializacaoModulosListener(InicializacaoModulosListener listener);

	public abstract void iniciarModulos(Boolean abrirFrontends) throws InterruptedException, ExecutionException;

	public abstract void pararModulos() throws InterruptedException, ExecutionException;

	public abstract void abrirFrontEnds() throws InterruptedException, ExecutionException;

	public abstract void atualizarConfiguracao(List<IPVO> ipvos);

}
