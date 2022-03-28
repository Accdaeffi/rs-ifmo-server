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

@Service
@Scope("prototype")
public class DeleteService {

	@Autowired
	private ConnectionUtils utils;
	
	private Connection connection;

	public DeleteService() {
	}
	
	public DeleteService(DataSource dataSource) throws SQLException {
		this.connection = dataSource.getConnection(); 
	}
	
	@PostConstruct
	public void initialize() {    
		connection = utils.getConnection();
	}
	
	public boolean deletePerson(int id) throws SQLException {	
		String query = String.format("delete from person where person.id = %d", 
				id);
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
