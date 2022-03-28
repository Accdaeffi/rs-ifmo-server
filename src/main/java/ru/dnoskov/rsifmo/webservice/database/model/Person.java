package ru.dnoskov.rsifmo.webservice.database.model;

import org.springframework.lang.Nullable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Person {
	
	@Nullable
	int id;
	
	String name;
	String surname;
	String patronymic;
	int age;
	
	public Person(String name, String surname, String patronymic, int age) {
		this.name = name;
		this.surname = surname;
		this.patronymic=patronymic;
		this.age = age;
	}

}
