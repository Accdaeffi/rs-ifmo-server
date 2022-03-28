package ru.dnoskov.rsifmo.webservice.services.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Test {

	@GetMapping("/")
	public String test() {
		return "hello world!";
	}

}
