package br.com.sysdesc.sysdesc.config;

import springfox.documentation.service.Parameter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;

import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Bean
	public Docket api() {

		List<Parameter> operationParameters = new ArrayList<>();
		operationParameters.add(new ParameterBuilder().name("Content-Type").description("Content-Type")
				.defaultValue(MediaType.APPLICATION_JSON_VALUE).modelRef(new ModelRef("string")).parameterType("header")
				.required(true).build());

		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(new ApiInfo("REST API - Sysdesc", "Documentação online da API", "0.0.11", "Terms of service",
						new Contact("Leandro Zanatta", "", "leandrozanatta27@gmail.com"), "", "", new ArrayList<>()))
				.select().paths(PathSelectors.any()).build().enableUrlTemplating(Boolean.FALSE)
				.produces(getAllProduceContentTypes()).consumes(getAllConsumeContentTypes())
				.globalOperationParameters(operationParameters).produces(getAllProduceContentTypes())
				.groupName("Sysdesc PDV").select().apis(RequestHandlerSelectors.basePackage("br.com.sysdesc"))
				.paths(PathSelectors.any()).build();

	}

	private Set<String> getAllConsumeContentTypes() {
		Set<String> consumes = new HashSet<>();

		consumes.add(MediaType.APPLICATION_JSON_VALUE);
		consumes.add(MediaType.APPLICATION_XML_VALUE);

		return consumes;
	}

	private Set<String> getAllProduceContentTypes() {
		Set<String> produces = new HashSet<>();

		produces.add(MediaType.APPLICATION_JSON_VALUE);
		produces.add(MediaType.APPLICATION_XML_VALUE);

		return produces;
	}
}