package ru.dnoskov.rsifmo.webservice.services.rest;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.dnoskov.rsifmo.webservice.database.model.Person;

@Data
@AllArgsConstructor
public class ListPersonResponse {

	List<Person> persons;

}
