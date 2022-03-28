package ru.dnoskov.rsifmo.webservice.database.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import ru.dnoskov.rsifmo.webservice.database.ConnectionUtils;
import ru.dnoskov.rsifmo.webservice.database.model.Person;

@Service
@Scope("prototype")
public class CreateService {
	
	@Autowired
	private ConnectionUtils utils;
	
	private Connection connection;

	public CreateService() {
	}
	
	public CreateService(DataSource dataSource) throws SQLException {
		this.connection = dataSource.getConnection(); 
	}
	
	@PostConstruct
	public void initialize() {    
		connection = utils.getConnection();
	}
	
	public Person createPerson(Person person) throws SQLException {	
		String query = String.format("insert into person (name, surname, patronymic, age) "
				+ "values ('%s', '%s', '%s', %d)", 
				person.getName(), person.getSurname(), person.getPatronymic(), person.getAge());
		
		if (query.contains(";")) {
			throw new SQLException("SQL Injection detected!");
		}
		
		Statement statement = connection.createStatement();

		statement.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
			
		try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
			
			if (generatedKeys.next()) {
				person.setId((int) generatedKeys.getLong(1));
			} else {
	        	throw new SQLException("Creating user failed, no ID obtained.");
	        }
	    }
			
		return person;
	}
	
	@Override
	protected void finalize() {
		try {
			connection.close();
		} catch (SQLException e) {
			// log.error("Error during closing of connection!");
			e.printStackTrace();
		}
	}

}
