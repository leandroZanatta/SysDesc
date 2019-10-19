package br.com.sysdesc.gerenciador.inicializacao.service.impl;

import static br.com.sysdesc.gerenciador.inicializacao.util.GerenciadorInicializacaoConstants.FILE_NETWORK_JSON;
import static br.com.sysdesc.gerenciador.inicializacao.util.GerenciadorInicializacaoConstants.FILE_SERVER_JSON;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

import javax.swing.event.EventListenerList;

import org.apache.commons.io.FileUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import br.com.sysdesc.gerenciador.inicializacao.intercomm.ConfiguracaoModulosClient;
import br.com.sysdesc.gerenciador.inicializacao.intercomm.builder.RequestBuilder;
import br.com.sysdesc.gerenciador.inicializacao.listener.InicializacaoModulosListener;
import br.com.sysdesc.gerenciador.inicializacao.service.InicializacaoModulosService;
import br.com.sysdesc.gerenciador.inicializacao.thread.ModuleFactory;
import br.com.sysdesc.gerenciador.inicializacao.util.GerenciadorInicializacaoLogConstants;
import br.com.sysdesc.util.classes.IPUtil;
import br.com.sysdesc.util.classes.ListUtil;
import br.com.sysdesc.util.vo.ConfigurationVO;
import br.com.sysdesc.util.vo.IPVO;
import br.com.sysdesc.util.vo.ModuleVO;
import br.com.sysdesc.util.vo.ServerVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class InicializacaoModulosServiceIpml implements InicializacaoModulosService {

	private static final InicializacaoModulosService INSTANCE = new InicializacaoModulosServiceIpml();

	protected EventListenerList listenerList = new EventListenerList();

	private ExecutorService executor = Executors.newCachedThreadPool();

	private Map<Long, Future<ModuleVO>> modulos = new HashMap<>();

	private InicializacaoModulosServiceIpml() {
	}

	@Override
	public void addInicializacaoModulosListener(InicializacaoModulosListener listener) {

		listenerList.add(InicializacaoModulosListener.class, listener);
	}

	@Override
	public void removeInicializacaoModulosListener(InicializacaoModulosListener listener) {

		listenerList.remove(InicializacaoModulosListener.class, listener);
	}

	public static InicializacaoModulosService getInstance() {

		return INSTANCE;
	}

	@Override
	public void iniciarModulos(Boolean abrirFrontends) throws InterruptedException, ExecutionException {

		ConfigurationVO configuracoes = this.buscarConfiguracaoModulos();

		fireConfiguracaoModulosChanged(configuracoes);

		if (!ListUtil.isNullOrEmpty(configuracoes.getFrontEnds()) && abrirFrontends) {

			this.inicializarListaModulos(configuracoes.getFrontEnds());
		}

		this.inicializarListaModulos(configuracoes.getServers());
	}

	@Override
	public void pararModulos() throws InterruptedException, ExecutionException {

		for (Entry<Long, Future<ModuleVO>> modulo : modulos.entrySet()) {

			this.encerrarModulo(modulo.getKey(), modulo.getValue());
		}

		modulos.clear();
	}

	@Override
	public void abrirFrontEnds() throws InterruptedException, ExecutionException {

		List<ServerVO> frontends = this.buscarConfiguracaoModulos().getFrontEnds().stream()
				.collect(Collectors.toList());

		if (!ListUtil.isNullOrEmpty(frontends)) {

			this.inicializarListaModulos(frontends);
		}
	}

	private List<IPVO> buscarConfiguracaoIP() {

		List<IPVO> configuracao = null;

		try (InputStream inputStream = new FileInputStream(new File(FILE_NETWORK_JSON));

				InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.defaultCharset())) {

			configuracao = new Gson().fromJson(inputStreamReader,
					TypeToken.getParameterized(List.class, IPVO.class).getType());

		} catch (Exception e) {

			log.info(GerenciadorInicializacaoLogConstants.CONFIGURACAO_NETWORK_NAO_ENCONTRADA, e);

			configuracao = new ArrayList<>();
		}

		return configuracao;
	}

	private void inicializarListaModulos(List<ServerVO> serverModel) throws InterruptedException, ExecutionException {

		if (!ListUtil.isNullOrEmpty(serverModel)) {

			List<ServerVO> execucoesIndependentes = serverModel.stream()
					.filter(x -> ListUtil.isNullOrEmpty(x.getDependsOn())).collect(Collectors.toList());

			this.pararEIniciarProcessos(execucoesIndependentes);

			List<ServerVO> execucoesDependentes = serverModel.stream()
					.filter(x -> !ListUtil.isNullOrEmpty(x.getDependsOn())).collect(Collectors.toList());

			this.pararEIniciarProcessos(execucoesDependentes);

		}
	}

	private void pararEIniciarProcessos(List<ServerVO> execucoes) throws InterruptedException, ExecutionException {

		if (!ListUtil.isNullOrEmpty(execucoes)) {

			for (ServerVO execucao : execucoes) {
				this.validarDependsOnOutrosProjetos(execucao);
			}
		}
	}

	private void validarDependsOnOutrosProjetos(ServerVO modulo) throws InterruptedException, ExecutionException {

		if (modulos.containsKey(modulo.getId())) {

			this.encerrarModulo(modulo.getId(), modulos.get(modulo.getId()));
		}

		if (ListUtil.isNullOrEmpty(modulo.getDependsOn())) {

			modulos.put(modulo.getId(), executeTread(modulo));

		} else {

			this.iniciarProcessoDependsOn(modulo);
		}

	}

	private void iniciarProcessoDependsOn(ServerVO modulo) {

		new Thread(() -> this.iniciarExecucaoSincrona(modulo)).start();
	}

	private void iniciarExecucaoSincrona(ServerVO modulo) {

		try {

			for (Long moduloStartado : modulo.getDependsOn()) {

				if (modulos.containsKey(moduloStartado)) {

					tornarThreadsDoProcessoSincrono(moduloStartado);
				}
			}

			modulos.put(modulo.getId(), executeTread(modulo));

		} catch (Exception e) {

			log.error("", e);
		}
	}

	private void tornarThreadsDoProcessoSincrono(Long moduloStartado) throws InterruptedException, ExecutionException {

		Future<ModuleVO> moduleExecution = modulos.get(moduloStartado);

		if (!moduleExecution.isDone()) {

			moduleExecution.get();

		}
	}

	private Future<ModuleVO> executeTread(ServerVO modulo) {

		return executor.submit(new ModuleFactory(modulo, listenerList));
	}

	private void encerrarModulo(Long codigoModulo, Future<ModuleVO> modulo)
			throws InterruptedException, ExecutionException {

		if (!modulo.isDone() && !modulo.isCancelled()) {

			modulo.cancel(Boolean.TRUE);
		}

		if (!modulo.isCancelled() && modulo.get() != null) {

			ModuleVO moduloVo = modulo.get();

			if (moduloVo.getFechamentoExterno() != null) {

				moduloVo.getFechamentoExterno().interrupt();
			}

			moduloVo.getProcess().destroyForcibly();
		}

		fireModuloStopped(codigoModulo);

	}

	private void fireConfiguracaoModulosChanged(ConfigurationVO configuracao) {

		Object[] listeners = listenerList.getListenerList();

		for (int i = 0; i < listeners.length; i = i + 2) {

			if (listeners[i] == InicializacaoModulosListener.class) {

				((InicializacaoModulosListener) listeners[i + 1]).configuracaoModulosChanged(configuracao);
			}
		}
	}

	private void fireModuloStopped(Long codigoModulo) {

		Object[] listeners = listenerList.getListenerList();

		for (int i = 0; i < listeners.length; i = i + 2) {

			if (listeners[i] == InicializacaoModulosListener.class) {

				((InicializacaoModulosListener) listeners[i + 1]).moduloStopped(codigoModulo);
			}
		}
	}

	@Override
	public void atualizarConfiguracao(List<IPVO> ipvos) {
		log.info("Atualizando Configuração de ips de conexão do ERP");

		if (!ListUtil.isNullOrEmpty(ipvos)) {

			List<IPVO> ipsCadastrados = buscarConfiguracaoIP();

			if (ListUtil.isNullOrEmpty(ipsCadastrados)) {

				new Thread(() -> atualizarConfiguracaoGerenciador(ipvos)).start();
			}

			String strIp = new Gson().toJson(ipvos);

			if (!strIp.equals(new Gson().toJson(ipsCadastrados))) {

				salvarConfiguracaoIPs(strIp);
			}
		}
	}

	private void atualizarConfiguracaoGerenciador(List<IPVO> ipvos) {

		ConfigurationVO configurationVO = buscarConfiguracoesGerenciador(ipvos);

		if (configurationVO != null) {

			salvarConfiguracaoModulos(configurationVO);
		}

	}

	private ConfigurationVO buscarConfiguracoesGerenciador(List<IPVO> ipvos) {

		List<IPVO> ipsGerenciador = IPUtil.getIps();

		ConfigurationVO configurationVO = null;

		while (configurationVO == null) {

			for (IPVO ipRede : ipvos) {

				List<IPVO> ipsValidos = ipsGerenciador.stream()
						.filter(ipGerenciador -> IPUtil.isNetworkMatch(ipRede, ipGerenciador.getIp()))
						.collect(Collectors.toList());

				for (IPVO ipValido : ipsValidos) {

					String requisicao = String.format("http://%s:%d", ipRede.getIp(), ipRede.getPorta());

					log.info("Tentando conectar ao endpoint: " + requisicao);

					configurationVO = RequestBuilder.build().target(ConfiguracaoModulosClient.class, requisicao)
							.buscarConfiguracaoModulos(ipValido.getIp());

					if (configurationVO != null) {

						return configurationVO;
					}
				}

			}

			try {
				Thread.sleep(5000);

			} catch (InterruptedException e) {

				log.error(GerenciadorInicializacaoLogConstants.ERRO_BUSCAR_CONFIGURACAO_MODULOS, e);
			}
		}

		return null;
	}

	private void salvarConfiguracaoModulos(ConfigurationVO configurationVO) {

		try {

			String data = new Gson().toJson(configurationVO);

			FileUtils.writeStringToFile(new File(FILE_SERVER_JSON), data, Charset.defaultCharset());

		} catch (Exception e) {

			log.error(GerenciadorInicializacaoLogConstants.ERRO_SALVAR_CONFIGURACAO_MODULOS, e);
		}
	}

	private void salvarConfiguracaoIPs(String data) {
		log.info("Salvando Configuração de ips de conexão do ERP");

		try {

			FileUtils.writeStringToFile(new File(FILE_NETWORK_JSON), data, Charset.defaultCharset());

		} catch (Exception e) {

			log.error(GerenciadorInicializacaoLogConstants.ERRO_SALVAR_CONFIGURACAO_MODULOS, e);
		}
	}

	protected ConfigurationVO buscarConfiguracaoModulos() {

		ConfigurationVO configuracao = null;

		try (InputStream inputStream = new FileInputStream(new File(FILE_SERVER_JSON));

				InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.defaultCharset())) {

			configuracao = new Gson().fromJson(inputStreamReader, ConfigurationVO.class);

		} catch (Exception e) {

			log.info(GerenciadorInicializacaoLogConstants.CONFIGURACAO_MODULOS_NAO_ENCONTRADA, e);

			configuracao = new ConfigurationVO();
		}

		return configuracao;
	}

}
