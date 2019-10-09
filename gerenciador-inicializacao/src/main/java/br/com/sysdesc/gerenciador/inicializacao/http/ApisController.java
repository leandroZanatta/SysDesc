package br.com.sysdesc.gerenciador.inicializacao.http;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.reflections.Reflections;

import br.com.sysdesc.gerenciador.inicializacao.http.anotations.RequestMethod;
import br.com.sysdesc.gerenciador.inicializacao.http.anotations.RestController;
import br.com.sysdesc.gerenciador.inicializacao.http.enumerators.HttpMethod;
import br.com.sysdesc.gerenciador.inicializacao.vo.ApiMethodVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ApisController {

	private static ApisController instance;
	private String pacote = "br.com.sysdesc.gerenciador.inicializacao";

	private Map<HttpMethod, Map<String, ApiMethodVO>> apis = new HashMap<>();

	private ApisController() {

		Reflections reflections = new Reflections(pacote);

		Set<Class<?>> controllers = reflections.getTypesAnnotatedWith(RestController.class);

		for (Class<?> controller : controllers) {

			try {

				RestController restController = controller.getAnnotation(RestController.class);

				Object objeto = controller.newInstance();

				Method[] methods = controller.getDeclaredMethods();

				for (Method method : methods) {

					getMethod(method, objeto, restController);

				}

			} catch (InstantiationException | IllegalAccessException e) {
				log.error("erro ao instanciar classe Controller", e);
			}
		}

	}

	private void getMethod(Method method, Object objeto, RestController restController) {

		if (method.isAnnotationPresent(RequestMethod.class)) {

			RequestMethod requestMethod = method.getAnnotation(RequestMethod.class);

			putMethod(new ApiMethodVO(method, objeto), requestMethod, restController);
		}

	}

	private void putMethod(ApiMethodVO apiMethodVO, RequestMethod requestMethod, RestController restController) {

		if (!apis.containsKey(requestMethod.method())) {
			apis.put(requestMethod.method(), new HashMap<>());
		}

		Map<String, ApiMethodVO> endpoint = apis.get(requestMethod.method());

		String url = restController.path() + requestMethod.path();

		log.info("encontrada url: " + url);

		endpoint.put(url, apiMethodVO);
	}

	public Map<HttpMethod, Map<String, ApiMethodVO>> getApis() {
		return apis;
	}

	public void setApis(Map<HttpMethod, Map<String, ApiMethodVO>> apis) {
		this.apis = apis;
	}

	public static ApisController getInstance() {

		if (instance == null) {
			instance = new ApisController();
		}

		return instance;
	}
}
