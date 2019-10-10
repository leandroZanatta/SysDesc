package br.com.sysdesc.sysdesc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication(scanBasePackages = { "br.com.sysdesc" })
@PropertySource(ignoreResourceNotFound = false, value = "classpath:application.properties")
@PropertySource(ignoreResourceNotFound = true, value = "classpath:sysdesc-custom-${spring.profiles.active}.properties")
@PropertySource(ignoreResourceNotFound = true, value = "classpath:sysdesc-db-${spring.profiles.active}.properties")
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
