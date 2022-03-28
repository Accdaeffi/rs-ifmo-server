package ru.dnoskov.rsifmo.webservice.database.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import ru.dnoskov.rsifmo.webservice.database.ConnectionUtils;
import ru.dnoskov.rsifmo.webservice.database.model.Person;

@Service
@Scope("prototype")
public class ReadService {

	@Autowired
	private ConnectionUtils utils;
	
	private Connection connection;

	public ReadService() {
	}
	
	public ReadService(DataSource dataSource) throws SQLException {
		this.connection = dataSource.getConnection(); 
	}
	
	@PostConstruct
	public void initialize() {    
		connection = utils.getConnection();
	}
	
	public Optional<Person> getById(int id) 
			throws SQLException {
		Person result = null;
		
		String query = String.format("select * from person where id = %d", id);
		ArrayList<Person> resultArray = executeQuery(query);
		
		if (resultArray.size()>0) {
			result = resultArray.get(0);
		}

		return Optional.ofNullable(result);
	}
	
	public ArrayList<Person> getAllPersons() 
			throws SQLException {
		String query = String.format("select * from person");
		
		ArrayList<Person> result = executeQuery(query);

		return result;
	}
	
	public ArrayList<Person> getByName(String name) 
			throws SQLException {
		String query = String.format("select * from person where name = '%s'", name);
		
		ArrayList<Person> result = executeQuery(query);

		return result;
	}
	
	public ArrayList<Person> getBySurname(String surname) 
			throws SQLException {
		String query = String.format("select * from person where surname = '%s'", surname);
		
		ArrayList<Person> result = executeQuery(query);

		return result;
	}
	
	public ArrayList<Person> getByPatronymic(String patronymic) 
			throws SQLException {
		String query = String.format("select * from person where patronymic = '%s'", patronymic);
		
		ArrayList<Person> result = executeQuery(query);

		return result;
	}
	
	public ArrayList<Person> getByAge(int age) 
			throws SQLException {
		String query = String.format("select * from person where age = %d", age);
		
		ArrayList<Person> result = executeQuery(query);

		return result;
	}
	
	public ArrayList<Person> getByNameAndSurname(String name, String surname) 
			throws SQLException {
		String query = String.format("select * from person where name = '%s' and surname = '%s'", 
				name, surname);
		
		ArrayList<Person> result = executeQuery(query);

		return result;
	}
	
	public ArrayList<Person> getByFullName(String name, String surname, String patronymic) 
			throws SQLException {
		String query = String.format("select * from person where name = '%s' and surname = '%s' and patronymic = '%s'",
				name, surname, patronymic);
		
		ArrayList<Person> result = executeQuery(query);

		return result;
	}
	
	public ArrayList<Person> getYoungerThan(int age) throws SQLException {
		String query = String.format("select * from person where age < %d", age);
		
		ArrayList<Person> result = executeQuery(query);

		return result;
	}
	
	public ArrayList<Person> getOlderThan(int age) throws SQLException {
		String query = String.format("select * from person where age > %d", age);
		
		ArrayList<Person> result = executeQuery(query);

		return result;
	}
	
	
	private ArrayList<Person> executeQuery(String query) 
			throws SQLException {
		
		if (query.contains(";")) {
			throw new SQLException("SQL Injection detected!");
		}
		
		ArrayList<Person> result = new ArrayList<>();
		
		PreparedStatement statement = connection.prepareStatement(query);

		statement.execute();
			
		result = getAllPersonsFromResultSet(statement.getResultSet());
		
		return result;
	}
	
	
	
	private ArrayList<Person> getAllPersonsFromResultSet(ResultSet resultSet) throws SQLException {
		ArrayList<Person> result = new ArrayList<>();
		
		while (resultSet.next()) {
			Person per = new Person(resultSet.getInt("id"), 
					resultSet.getString("name"),
					resultSet.getString("surname"),
					resultSet.getString("patronymic"),
					resultSet.getInt("age"));	
			
			result.add(per);	
		}
		
		return result;
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
