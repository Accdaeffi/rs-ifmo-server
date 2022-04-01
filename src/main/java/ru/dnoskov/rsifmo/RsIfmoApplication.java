package ru.dnoskov.rsifmo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.web.context.WebApplicationContext;

import ru.dnoskov.rsifmo.webservice.services.rest.PersonReadService;

@SpringBootApplication
public class RsIfmoApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		WebApplicationContext ctx = (WebApplicationContext) SpringApplication.run(RsIfmoApplication.class, args);
		ctx.getBean(PersonReadService.class);
	}
        
        
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(RsIfmoApplication.class);
    }

}
