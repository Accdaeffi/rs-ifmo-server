package ru.dnoskov.rsifmo.webservice.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.stereotype.Service;

@Service
public class ConnectionUtils {
	private static final String JDBC_URL = "jdbc:postgresql://localhost:5432/ifmo-ws";
	private static final String JDBC_USER = "ifmo-ws";
	private static final String JDBC_PASSWORD = "ifmo-ws";

	DataSource dataSource;
	
	public Connection getConnection() {
		Connection connection = null;
		
		try {
			connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
		} catch (SQLException ex) {
			System.out.println("Exception!");
			System.out.println(ex);
			// log.error("No database connection!");
		}
		return connection;
	 }

}
