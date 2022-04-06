package ru.dnoskov.rsifmo.webservice.services.rest;

import java.sql.SQLException;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ru.dnoskov.rsifmo.webservice.database.model.Person;
import ru.dnoskov.rsifmo.webservice.database.service.UpdateService;
import ru.dnoskov.rsifmo.webservice.exceptions.*;
import ru.dnoskov.rsifmo.webservice.services.throttling.ThrottlingException;

@RestController
public class PersonUpdateService implements ApplicationContextAware {

	ApplicationContext ctx;

	@Autowired
	Integer counter;

	@Value("${throttling.max-value}")
	int maxValue;

	@PutMapping("/updatePerson")
	public ResponseEntity updatePerson(@RequestBody Person person)
			throws WorkWithSQLException, IncorrectArgumentException, EmptyArgumentException, ThrottlingException {

		try {
			synchronized (counter) {
				counter = counter + 1;
				if (counter > maxValue)
					throw new ThrottlingException();
			}

			UpdateService service = ctx.getBean(UpdateService.class);

			if (person.getId() == 0) {
				throw new EmptyArgumentException("id");
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

			if (person.getAge() < 0) {
				throw new IncorrectArgumentException("age");
			}

			try {
				if (service.updatePerson(person)) {
					return new ResponseEntity(HttpStatus.OK);
				} else {
					return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
				}
			} catch (SQLException e) {
				e.printStackTrace();
				throw new WorkWithSQLException(e.getMessage(), e);
			}
		} finally {
			synchronized (counter) {
				counter--;
			}
		}
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.ctx = applicationContext;
	}
}
