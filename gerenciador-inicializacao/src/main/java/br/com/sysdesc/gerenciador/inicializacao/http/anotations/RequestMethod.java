package br.com.sysdesc.gerenciador.inicializacao.http.anotations;

import static java.lang.annotation.ElementType.METHOD;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import br.com.sysdesc.gerenciador.inicializacao.http.enumerators.HttpMethod;

@Target({ METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestMethod {

	public HttpMethod method();

	public String path() default "";
}
