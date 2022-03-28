package ru.dnoskov.rsifmo.webservice.services.rest;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ru.dnoskov.rsifmo.webservice.database.model.Person;
import ru.dnoskov.rsifmo.webservice.database.service.ReadService;
import ru.dnoskov.rsifmo.webservice.exceptions.*;

@RestController
public class PersonReadService implements ApplicationContextAware {
	
	ApplicationContext ctx;
	

	@GetMapping("/all")
	public List<Person> getAllPersons() throws WorkWithSQLException {

		ReadService service = ctx.getBean(ReadService.class);
		try {
			return service.getAllPersons();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new WorkWithSQLException(e.getMessage(), e);
		}
	}

	@GetMapping("/{id}")
	public Person getPersonById(@PathVariable int id) throws WorkWithSQLException, PersonWithSuchIdNotFoundException {

		ReadService service = ctx.getBean(ReadService.class);

		Optional<Person> optionalPerson;
		try {
			optionalPerson = service.getById(id);

			if (optionalPerson.isPresent()) {
				return optionalPerson.get();
			} else {
				throw new PersonWithSuchIdNotFoundException(id);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new WorkWithSQLException(e.getMessage(), e);
		}

	}

	@GetMapping("/byName")
	public List<Person> getPersonsByName(@RequestBody String name) throws WorkWithSQLException, EmptyArgumentException {
		ReadService service = ctx.getBean(ReadService.class);

		if (name == null || name.equals("")) {
			throw new EmptyArgumentException("name");
		}

		try {
			return service.getByName(name);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new WorkWithSQLException(e.getMessage(), e);
		}
	}

	@GetMapping("/bySurname")
	public List<Person> getPersonsBySurname(@RequestBody String surname)
			throws WorkWithSQLException, EmptyArgumentException {
		ReadService service = ctx.getBean(ReadService.class);

		if (surname == null || surname.equals("")) {
			throw new EmptyArgumentException("surname");
		}

		try {
			return service.getBySurname(surname);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new WorkWithSQLException(e.getMessage(), e);
		}
	}

	@GetMapping("/byPatronymic")
	public List<Person> getPersonsByPatronymic(@RequestBody String patronymic)
			throws WorkWithSQLException, EmptyArgumentException {
		ReadService service = ctx.getBean(ReadService.class);

		if (patronymic == null || patronymic.equals("")) {
			throw new EmptyArgumentException("patronymic");
		}

		try {
			return service.getByPatronymic(patronymic);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new WorkWithSQLException(e.getMessage(), e);
		}
	}

	@GetMapping("/byAge")
	public List<Person> getPersonsByAge(@RequestBody int age) throws WorkWithSQLException, IncorrectArgumentException {
		if (age < 0) {
			throw new IncorrectArgumentException("age");
		}

		ReadService service = ctx.getBean(ReadService.class);

		try {
			return service.getByAge(age);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new WorkWithSQLException(e.getMessage(), e);
		}
	}

	@GetMapping("/byNameAndSurname")
	public List<Person> getPersonsByNameAndSurname(@RequestBody String name, @RequestBody String surname)
			throws WorkWithSQLException, EmptyArgumentException {
		ReadService service = ctx.getBean(ReadService.class);

		if (name == null || name.equals("")) {
			throw new EmptyArgumentException("name");
		}

		if (surname == null || surname.equals("")) {
			throw new EmptyArgumentException("surname");
		}

		try {
			return service.getByNameAndSurname(name, surname);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new WorkWithSQLException(e.getMessage(), e);
		}
	}

	@GetMapping("/byFullName")
	public List<Person> getPersonsByFullName(@RequestBody String name, @RequestBody String surname,
			@RequestBody String patronymic) throws WorkWithSQLException, EmptyArgumentException {
		ReadService service = ctx.getBean(ReadService.class);

		if (name == null || name.equals("")) {
			throw new EmptyArgumentException("name");
		}

		if (surname == null || surname.equals("")) {
			throw new EmptyArgumentException("surname");
		}

		if (patronymic == null || patronymic.equals("")) {
			throw new EmptyArgumentException("patronymic");
		}

		try {
			return service.getByFullName(name, surname, patronymic);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new WorkWithSQLException(e.getMessage(), e);
		}
	}

	@GetMapping("/youngerThan")
	public List<Person> getPersonsYoungerThan(@RequestBody int age)
			throws WorkWithSQLException, IncorrectArgumentException {

		if (age < 0) {
			throw new IncorrectArgumentException("age");
		}

		ReadService service = ctx.getBean(ReadService.class);

		try {
			return service.getYoungerThan(age);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new WorkWithSQLException(e.getMessage(), e);
		}
	}

	@GetMapping("/olderThan")
	public List<Person> getPersonsOlderThan(@RequestBody int age)
			throws WorkWithSQLException, IncorrectArgumentException {

		if (age < 0) {
			throw new IncorrectArgumentException("age");
		}

		ReadService service = ctx.getBean(ReadService.class);

		try {
			return service.getOlderThan(age);
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
