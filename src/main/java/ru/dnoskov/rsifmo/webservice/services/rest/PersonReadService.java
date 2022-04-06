package ru.dnoskov.rsifmo.webservice.services.rest;

import java.sql.SQLException;
import java.util.Optional;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ru.dnoskov.rsifmo.webservice.database.model.Person;
import ru.dnoskov.rsifmo.webservice.database.service.ReadService;
import ru.dnoskov.rsifmo.webservice.exceptions.EmptyArgumentException;
import ru.dnoskov.rsifmo.webservice.exceptions.IncorrectArgumentException;
import ru.dnoskov.rsifmo.webservice.exceptions.PersonWithSuchIdNotFoundException;
import ru.dnoskov.rsifmo.webservice.exceptions.WorkWithSQLException;
import ru.dnoskov.rsifmo.webservice.services.throttling.ThrottlingException;

@RestController
public class PersonReadService implements ApplicationContextAware {

	ApplicationContext ctx;

	@Autowired
	Integer counter;

	@Value("${throttling.max-value}")
	int maxValue;

	@GetMapping("/all")
	public ListPersonResponse getAllPersons() 
			throws WorkWithSQLException, ThrottlingException {

		try {
			synchronized (counter) {
				counter = counter + 1;
				if (counter > maxValue)
					throw new ThrottlingException();
			}

			ReadService service = ctx.getBean(ReadService.class);
			try {
				return new ListPersonResponse(service.getAllPersons());
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

	@GetMapping("/person/{id}")
	public Person getPersonById(@PathVariable int id) 
			throws WorkWithSQLException, PersonWithSuchIdNotFoundException, ThrottlingException {

		try {
			synchronized (counter) {
				counter = counter + 1;
				if (counter > maxValue)
					throw new ThrottlingException();
			}
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
		} finally {
			synchronized (counter) {
				counter--;
			}
		}

	}

	@GetMapping("/byName")
	public ListPersonResponse getPersonsByName(@RequestParam String name)
			throws WorkWithSQLException, EmptyArgumentException, ThrottlingException {

		try {
			synchronized (counter) {
				counter = counter + 1;
				if (counter > maxValue)
					throw new ThrottlingException();
			}

			ReadService service = ctx.getBean(ReadService.class);

			if (name == null || name.equals("")) {
				throw new EmptyArgumentException("name");
			}

			try {
				return new ListPersonResponse(service.getByName(name));
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

	@GetMapping("/bySurname")
	public ListPersonResponse getPersonsBySurname(@RequestParam String surname)
			throws WorkWithSQLException, EmptyArgumentException, ThrottlingException {

		try {
			synchronized (counter) {
				counter = counter + 1;
				if (counter > maxValue)
					throw new ThrottlingException();
			}

			ReadService service = ctx.getBean(ReadService.class);

			if (surname == null || surname.equals("")) {
				throw new EmptyArgumentException("surname");
			}

			try {
				return new ListPersonResponse(service.getBySurname(surname));
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

	@GetMapping("/byPatronymic")
	public ListPersonResponse getPersonsByPatronymic(@RequestParam String patronymic)
			throws WorkWithSQLException, EmptyArgumentException, ThrottlingException {

		try {
			synchronized (counter) {
				counter = counter + 1;
				if (counter > maxValue)
					throw new ThrottlingException();
			}

			ReadService service = ctx.getBean(ReadService.class);

			if (patronymic == null || patronymic.equals("")) {
				throw new EmptyArgumentException("patronymic");
			}

			try {
				return new ListPersonResponse(service.getByPatronymic(patronymic));
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

	@GetMapping("/byAge")
	public ListPersonResponse getPersonsByAge(@RequestParam int age)
			throws WorkWithSQLException, IncorrectArgumentException, ThrottlingException {

		try {
			synchronized (counter) {
				counter = counter + 1;
				if (counter > maxValue)
					throw new ThrottlingException();
			}

			if (age < 0) {
				throw new IncorrectArgumentException("age");
			}

			ReadService service = ctx.getBean(ReadService.class);

			try {
				return new ListPersonResponse(service.getByAge(age));
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

	@GetMapping("/byNameAndSurname")
	public ListPersonResponse getPersonsByNameAndSurname(@RequestParam String name, @RequestParam String surname)
			throws WorkWithSQLException, EmptyArgumentException, ThrottlingException {

		try {
			synchronized (counter) {
				counter = counter + 1;
				if (counter > maxValue)
					throw new ThrottlingException();
			}

			ReadService service = ctx.getBean(ReadService.class);

			if (name == null || name.equals("")) {
				throw new EmptyArgumentException("name");
			}

			if (surname == null || surname.equals("")) {
				throw new EmptyArgumentException("surname");
			}

			try {
				return new ListPersonResponse(service.getByNameAndSurname(name, surname));
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

	@GetMapping("/byFullName")
	public ListPersonResponse getPersonsByFullName(@RequestParam String name, @RequestParam String surname,
			@RequestParam String patronymic) 
					throws WorkWithSQLException, EmptyArgumentException, ThrottlingException {

		try {
			synchronized (counter) {
				counter = counter + 1;
				if (counter > maxValue)
					throw new ThrottlingException();
			}

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
				return new ListPersonResponse(service.getByFullName(name, surname, patronymic));
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

	@GetMapping("/youngerThan")
	public ListPersonResponse getPersonsYoungerThan(@RequestParam int age)
			throws WorkWithSQLException, IncorrectArgumentException, ThrottlingException {

		try {
			synchronized (counter) {
				counter = counter + 1;
				if (counter > maxValue)
					throw new ThrottlingException();
			}

			if (age < 0) {
				throw new IncorrectArgumentException("age");
			}

			ReadService service = ctx.getBean(ReadService.class);

			try {
				return new ListPersonResponse(service.getYoungerThan(age));
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

	@GetMapping("/olderThan")
	public ListPersonResponse getPersonsOlderThan(@RequestParam int age)
			throws WorkWithSQLException, IncorrectArgumentException, ThrottlingException {

		try {
			synchronized (counter) {
				counter = counter + 1;
				if (counter > maxValue)
					throw new ThrottlingException();
			}

			if (age < 0) {
				throw new IncorrectArgumentException("age");
			}

			ReadService service = ctx.getBean(ReadService.class);

			try {
				return new ListPersonResponse(service.getOlderThan(age));
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
