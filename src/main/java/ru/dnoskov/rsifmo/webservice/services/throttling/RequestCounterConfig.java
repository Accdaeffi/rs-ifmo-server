package ru.dnoskov.rsifmo.webservice.services.throttling;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RequestCounterConfig {

	@Bean
	public Integer getCounter() {
		return new Integer(0);
	}

	
	
}
