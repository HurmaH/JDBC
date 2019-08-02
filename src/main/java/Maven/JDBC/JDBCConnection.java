package Maven.JDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.testng.annotations.Test;

//Resultset methods
		/*
		 * 1. next()                 -> moves to next row
		 * 2.getObject(colName/index)-> read data from coumn
		 * 3.last()                  -> goes to last row
		 * 4.previous();             -> goes to previous row
		 * 5.first();                -> goes to first row
		 * 6.beforeFirst();
		 * 7.afterLast()
		 * 8.getRow()                -> returns int of the row
		 * 9.absolute(row)           -> jums to specific row
		 */
public class JDBCConnection {
	String oracleDbURL="jdbc:oracle:thin:@database:1521:xe";
	String oraclDbUsername="hr";
	String oracleDbPassword="hr";
	
	@Test
	public void jdbcTest() throws SQLException {
		
		Connection connection = DriverManager.getConnection(oracleDbURL,
				                                            oraclDbUsername,
				                                            oracleDbPassword);
		
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery("select * from countries");
		
		System.out.println(resultSet.getRow());//will return zero as it is outside
		resultSet.next();//will go inside the table
		System.out.println(resultSet.getRow());//will return 1 as it is inside
		
		//resultSet.previous();
		//resultSet.first();
		//resultSet.last();
		//resultSet.getRow();//-> will give error as it is going only forward and works with next()
		
		resultSet.next();//will go to next line
		System.out.println(resultSet.getString("country_name"));//country_name is column name, will read first row of this column, value is String
		System.out.println(resultSet.getInt("region_id"));//region_id is column name, will read first row of this column, value is int
		
		
		//find out how many records in resultSet
		resultSet.last();
		int rowsCount=resultSet.getRow();
		System.out.println("Number of rows:"+rowsCount);
		
		//Iterating
		resultSet.beforeFirst();//reslutSet.fist(); could be used but first value would be missed inside the while loop
		while (resultSet.next()) {//resultSet.next() will go to next row first then return boolean value:true/false
			
			System.out.println(resultSet.getString(1)+"-"+resultSet.getString("country_name")+"-"+resultSet.getInt("country_id"));
		}
		
		
		
		
		System.out.println();
		
		System.out.println();
	
		statement.close();
		resultSet.close();
		connection.close();
	}

}
