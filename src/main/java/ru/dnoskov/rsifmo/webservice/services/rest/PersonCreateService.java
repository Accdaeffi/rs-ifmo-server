package ru.dnoskov.rsifmo.webservice.services.rest;

import java.sql.SQLException;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ru.dnoskov.rsifmo.webservice.database.model.Person;
import ru.dnoskov.rsifmo.webservice.database.service.CreateService;
import ru.dnoskov.rsifmo.webservice.exceptions.*;

@RestController
public class PersonCreateService implements ApplicationContextAware {

	ApplicationContext ctx;

	@PostMapping("/createPerson")
	public Person createPerson(@RequestBody Person person) 
					throws IncorrectArgumentException, EmptyArgumentException, WorkWithSQLException {
		CreateService service = ctx.getBean(CreateService.class);

		if (person.getAge() < 0) {
			throw new IncorrectArgumentException("age");
		}

		if (person.getName() == null || person.getName().equals("")) {
			throw new EmptyArgumentException("name");
		}

		if (person.getSurname() == null || person.getSurname().equals("")) {
			throw new EmptyArgumentException("surname");
		}

		if (person.getPatronymic() == null || person.getPatronymic().equals("")) {
			throw new EmptyArgumentException("patronymic");
		}

		try {
			return service.createPerson(person);
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
