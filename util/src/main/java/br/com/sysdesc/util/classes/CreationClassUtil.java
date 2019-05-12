package br.com.sysdesc.util.classes;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.charset.Charset;
import java.util.List;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;

import org.apache.commons.io.FileUtils;

import com.google.googlejavaformat.java.Formatter;

import br.com.sysdesc.util.enumeradores.ModificadoresEnum;
import br.com.sysdesc.util.resources.Configuracoes;
import br.com.sysdesc.util.vo.CriacaoClasseVO;

public class CreationClassUtil {

	private static final String FECHA_CHAVE = "}";
	private static final String PONTO_VIRGULA = ";";

	public static Class<?> createClas(List<CriacaoClasseVO> criacaoClasseVOs, String name) {

		File root = new File(Configuracoes.FOLDER_PESQUISAS);

		if (!root.exists()) {
			root.mkdirs();
		}

		File arquivoClasse = new File(Configuracoes.FOLDER_PESQUISAS + "\\" + name + ".java");

		create(arquivoClasse, criacaoClasseVOs, name);

		compile(arquivoClasse);

		return instance(root, name);
	}

	private static void create(File arquivo, List<CriacaoClasseVO> criacaoClasseVOs, String name) {
		try {

			StringBuilder stringBuilder = new StringBuilder();

			organizeImports(stringBuilder, criacaoClasseVOs);

			stringBuilder.append("public class " + name + " implements Serializable{");

			StringBuilder getterSetter = new StringBuilder();

			criacaoClasseVOs.forEach(x -> {

				stringBuilder.append(ModificadoresEnum.getModifiers(x.getModificadores()));

				stringBuilder.append(" ").append(x.getTipoMetodo().getSimpleName()).append(" ")
						.append(x.getNomeMetodo()).append(PONTO_VIRGULA);

				if (x.getGerarGetterSetter()) {

					getterSetter.append("public String get").append(x.getNomeMetodo().substring(0, 1).toUpperCase())
							.append(x.getNomeMetodo().substring(1, x.getNomeMetodo().length())).append("(){")
							.append("return this.").append(x.getNomeMetodo())
							.append(x.getTipoMetodo().equals(String.class) ? "" : ".toString()").append(PONTO_VIRGULA)
							.append(FECHA_CHAVE);

					getterSetter.append("public void set").append(x.getNomeMetodo().substring(0, 1).toUpperCase())
							.append(x.getNomeMetodo().substring(1, x.getNomeMetodo().length())).append("(")
							.append(x.getTipoMetodo().getSimpleName()).append(" ").append(x.getNomeMetodo())
							.append("){").append("this.").append(x.getNomeMetodo()).append("=")
							.append(x.getNomeMetodo()).append(PONTO_VIRGULA).append(FECHA_CHAVE);

				}

			});

			stringBuilder.append(" public " + name + "() {").append(FECHA_CHAVE);

			stringBuilder.append(getterSetter.toString());

			stringBuilder.append(FECHA_CHAVE);

			FileUtils.writeStringToFile(arquivo, new Formatter().formatSourceAndFixImports(stringBuilder.toString()),
					Charset.defaultCharset());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void organizeImports(StringBuilder stringBuilder, List<CriacaoClasseVO> criacaoClasseVOs) {

		stringBuilder.append("import java.io.Serializable;\n");

		criacaoClasseVOs.stream()
				.forEach(x -> stringBuilder.append("import ").append(x.getTipoMetodo().getName()).append(";\n"));

	}

	private static void compile(File arquivo) {
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();

		compiler.run(null, null, null, arquivo.getPath());

	}

	private static Class<?> instance(File root, String name) {
		URLClassLoader classLoader;

		try {
			classLoader = URLClassLoader.newInstance(new URL[] { root.toURI().toURL() });
			return Class.forName(name, true, classLoader);

		} catch (Exception e) {
			return null;
		}

	}

}
