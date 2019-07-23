package br.com.sysdesc.util.constants;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ResourcesTest {

	@Test
	public void testOrdenarPtBr() throws Exception {

		List<String> resources = FileUtils
				.readLines(new File("src/main/resources/pt_br.properties"), Charset.forName("UTF-8")).stream()
				.filter(x -> x != null && !"".equals(x)).sorted(Comparator.comparing(String::toString))
				.collect(Collectors.toList());

		List<String> newList = new ArrayList<String>();

		LinkedHashMap<String, List<String>> result = resources.stream()
				.collect(Collectors.groupingBy(a -> a.toString().split("_")[0])).entrySet().stream()
				.sorted(Map.Entry.comparingByKey()).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
						(oldValue, newValue) -> oldValue, LinkedHashMap::new));

		for (Entry<String, List<String>> keyset : result.entrySet()) {
			newList.addAll(keyset.getValue().stream().sorted(Comparator.comparing(String::toString))
					.collect(Collectors.toList()));
			newList.add("");
		}

		FileUtils.writeLines(new File("src/main/resources/pt_br.properties"), "UTF-8", newList);
	}

	@Test
	public void testPtBrsemResource() throws Exception {

		Properties properties = getPropertiesPtBr();

		Set<String> resources = new HashSet<>();

		resources.addAll(getPropertiesFromResourcesAtualizacao());

		Boolean testeSucesso = Boolean.TRUE;

		List<String> modificacoes = new ArrayList<String>();

		for (Entry<Object, Object> entry : properties.entrySet()) {

			if (!resources.contains(entry.getKey().toString())) {

				System.out.println("Excluir Chave do arquivo pt_br.properties: " + entry.getKey().toString());

				modificacoes.add(entry.getKey().toString());

				testeSucesso = Boolean.FALSE;
			}
		}

		if (!testeSucesso) {

			modificacoes.forEach(m -> properties.remove(m));

			List<String> newList = new ArrayList<String>();

			properties.entrySet().forEach(entry -> {
				newList.add(entry.getKey() + "=" + entry.getValue());
			});

			FileUtils.writeLines(new File("src/main/resources/pt_br.properties"), "UTF-8", newList);

		}

		assertTrue(testeSucesso);
	}

	@Test
	public void testResourcesMensagensNaoCadastradasInstalador() throws Exception {

		Properties properties = getPropertiesPtBr();

		List<String> resourcesFromMensagens = getPropertiesFromResourcesAtualizacao();

		Boolean testeSucesso = Boolean.TRUE;

		for (String field : resourcesFromMensagens) {

			if (properties.getProperty(field, "").equals("")) {

				System.out.println("Mensagem não encontrada no arquivo pt_br.properties: " + field);

				testeSucesso = Boolean.FALSE;
			}
		}

		assertTrue(testeSucesso);
	}

	@Test
	public void testResourcesDuplicadas() throws Exception {

		List<String> valores = FileUtils.readLines(
				new File(getClass().getClassLoader().getResource("pt_br.properties").toURI()),
				Charset.forName("UTF-8"));

		Map<String, List<String>> mapa = valores.stream().map(x -> x.split("=")[0]).filter(x -> !"".equals(x))
				.collect(Collectors.groupingBy(String::toString));

		Boolean testeSucesso = Boolean.TRUE;

		for (Entry<String, List<String>> x : mapa.entrySet()) {

			if (x.getValue().size() > 1) {

				System.out.println("Chaves duplicadas no arquivo pt_br.properties :" + x.getKey());

				testeSucesso = Boolean.FALSE;
			}
		}

		assertTrue(testeSucesso);
	}

	private List<String> getPropertiesFromResourcesAtualizacao() {
		Class<?> clazz = br.com.sysdesc.atualizacao.util.resources.Resources.class;

		return Arrays.asList(clazz.getDeclaredFields()).stream().filter(x -> x.getModifiers() != 10).map(Field::getName)
				.collect(Collectors.toList());
	}

	private Properties getPropertiesPtBr() throws FileNotFoundException, IOException {

		Properties properties = new Properties();

		File file = new File("src/main/resources/pt_br.properties");

		file.getAbsolutePath();

		InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(file),
				Charset.forName("UTF-8"));

		properties.load(inputStreamReader);

		return properties;
	}

}
