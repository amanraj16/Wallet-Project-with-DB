package com.capg.connection;
import java.sql.*;
public class EstablishConnection {
	public Connection getConnection() throws SQLException, ClassNotFoundException
	{
		try {
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","Capgemini123");
		
		return con;
	}catch(Exception e)
		{
		e.printStackTrace();
		}
			return null;
}
}
