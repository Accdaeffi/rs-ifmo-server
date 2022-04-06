package ru.dnoskov.rsifmo.webservice.services.rest;

import java.sql.SQLException;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import ru.dnoskov.rsifmo.webservice.database.service.DeleteService;
import ru.dnoskov.rsifmo.webservice.exceptions.*;
import ru.dnoskov.rsifmo.webservice.services.throttling.ThrottlingException;

@RestController
public class PersonDeleteService implements ApplicationContextAware {

	ApplicationContext ctx;

	@Autowired
	Integer counter;

	@Value("${throttling.max-value}")
	int maxValue;

	@DeleteMapping("/deletePerson/{id}")
	public ResponseEntity deletePerson(@PathVariable int id)
			throws WorkWithSQLException, IncorrectArgumentException, ThrottlingException {

		try {
			synchronized (counter) {
				counter = counter + 1;
				if (counter > maxValue)
					throw new ThrottlingException();
			}

			DeleteService service = ctx.getBean(DeleteService.class);

			if (id < 0) {
				throw new IncorrectArgumentException("id");
			}

			try {
				if (service.deletePerson(id)) {
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
