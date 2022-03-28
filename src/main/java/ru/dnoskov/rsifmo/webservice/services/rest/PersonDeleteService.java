package ru.dnoskov.rsifmo.webservice.services.rest;

import java.sql.SQLException;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import ru.dnoskov.rsifmo.webservice.database.service.DeleteService;
import ru.dnoskov.rsifmo.webservice.exceptions.*;

@RestController
public class PersonDeleteService implements ApplicationContextAware {

	ApplicationContext ctx;

	@DeleteMapping("/deletePerson/{id}")
	public boolean deletePerson(@PathVariable int id) 
			throws WorkWithSQLException, IncorrectArgumentException {
		DeleteService service = ctx.getBean(DeleteService.class);

		if (id < 0) {
			throw new IncorrectArgumentException("id");
		}

		try {
			return service.deletePerson(id);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new WorkWithSQLException(e.getMessage(), e);
		}
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.ctx = applicationContext;
	}

}
