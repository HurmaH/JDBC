package Maven.JDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class JDBCConnection {
	String oracleDbURL="jdbc:mysql://hostname:3306/qadbt?autoReconnect=true&useSSL=false";
	String oraclDbUsername="root";
	String oracleDbPassword="1234";
	
	@Test
	public void jdbcTest() throws SQLException {
		
		Connection connection = DriverManager.getConnection(oracleDbURL,
				                                            oraclDbUsername,
				                                            oracleDbPassword);
	
		connection.close();
	}

}
