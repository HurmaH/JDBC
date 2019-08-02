package Tests;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.annotations.Test;

public class Metadata {
	String oracleDbURL = "jdbc:oracle:thin:@database:1521:xe";
	String oraclDbUsername = "hr";
	String oracleDbPassword = "hr";

	@Test
	public void jdbcMetadata() throws Exception {
		Connection connection = DriverManager.getConnection(oracleDbURL, oraclDbUsername, oracleDbPassword);
		Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CLOSE_CURSORS_AT_COMMIT);

		// String sql = "select employee_id, last_name, job_id, salary from employees";
		String sql = "select * from employees";
		ResultSet resultSet = statement.executeQuery(sql);

		// DataBase metadata
		DatabaseMetaData dbMetadata = connection.getMetaData();
		System.out.println("User:" + dbMetadata.getUserName());
		System.out.println("Database type:" + dbMetadata.getDatabaseProductName());

		// resultSet metadata
		ResultSetMetaData rsMetadata = resultSet.getMetaData();
		System.out.println("Columns count:" + rsMetadata.getColumnCount());
		System.out.println(rsMetadata.getColumnName(1));

		// print all column names:
		for (int i = 1; i <= rsMetadata.getColumnCount(); i++) {
			System.out.println(i + " -> " + rsMetadata.getColumnName(i));
		}

		// Throw resultSet into a List of Maps
		// 1.Create List of Maps
		List<Map<String, Object>> list = new ArrayList<>();
		ResultSetMetaData rsMetadata1 = resultSet.getMetaData();

		// checking col number
		int colCount = rsMetadata1.getColumnCount();

		while (resultSet.next()) {//will go each row
			Map<String, Object> rowMap = new HashMap<>();//each Map object

			for (int col = 1; col < colCount; col++) {// to go through column

				rowMap.put(rsMetadata1.getColumnName(col), resultSet.getObject(col));//putting key and value into Map
			}
			
			list.add(rowMap);
		}
		
		//print all employee ids from a list of maps
		for(int i=0; i<list.size(); i++) {
			list.get(i).get("EMPLOYEE_ID");//java reads key name upper case, sql is not case sensitive but java is
		}
		
		//OR
		for (Map<String, Object> map:list) {
			map.get("EMPLOYEE_ID");
		}

		statement.close();
		resultSet.close();
		connection.close();
	}

}
