package ru.dnoskov.rsifmo.webservice.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.annotation.Resource;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import javax.sql.DataSource;

import org.springframework.stereotype.Service;

@Service
public class ConnectionUtils {

    private DataSource dataSource;
    
    static ConnectionUtils instance = null;
        
    private ConnectionUtils() {
    	try {
    		InitialContext context = new InitialContext();
    		dataSource = (DataSource) context.lookup("jdbc/ifmo-ws");
                
     	} catch (NamingException ex) {
            ex.printStackTrace();
        }
            
    }
	
    public Connection getConnection() {
        Connection result = null;
		
	try {
            result = dataSource.getConnection();
	} catch (SQLException ex) {
            System.out.println("Exception!");
            System.out.println(ex);
	}
            return result;
    }

}