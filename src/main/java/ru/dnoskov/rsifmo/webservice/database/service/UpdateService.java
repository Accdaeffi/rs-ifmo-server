package ru.dnoskov.rsifmo.webservice.database.service;

import java.sql.Connection;
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
public class UpdateService {

	@Autowired
	private ConnectionUtils utils;
	
	private Connection connection;

	public UpdateService() {
	}
	
	public UpdateService(DataSource dataSource) throws SQLException {
		this.connection = dataSource.getConnection(); 
	}
	
	@PostConstruct
	public void initialize() {    
		connection = utils.getConnection();
	}
	
	public boolean updatePerson(Person person) 
			throws SQLException {
		String query = String.format("update person set name='%s', surname='%s', patronymic='%s', age=%d "
				+ "where id = %d", 
				person.getName(), person.getSurname(), person.getPatronymic(), person.getAge(), person.getId());
		
		if (query.contains(";")) {
			throw new SQLException("SQL Injection detected!");
		}
		
		Statement statement = connection.createStatement();

		statement.executeUpdate(query);
			
		return true;	
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
