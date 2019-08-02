package Utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class DBUtility {
	private static Connection connection;
	private static Statement statement;
	private static ResultSet resultSet;
	private static int rowsCount;
	
	String oracleDbURL="jdbc:oracle:thin:@database:1521:xe";
	String oraclDbUsername="hr";
	String oracleDbPassword="hr";
	
	public static void establishComment (DBType dbType) throws SQLException{
		switch (dbType) {
		case ORACLE:
			connection = DriverManager.getConnection(ConfigReader.getProperty("oracleDbURL"), 
					                                 ConfigReader.getProperty("oraclDbUsername"), 
					                                 ConfigReader.getProperty("oracleDbPassword"));
			
			break;

		default:
			connection=null;
		}
	}
	
	public static int getRowsCount(String sql) throws SQLException {//to know row num
		
		statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		resultSet = statement.executeQuery(sql);
		resultSet.last();
		return resultSet.getRow();
		
	}
	
	public static List<Map<String, Object>> runSQLQuery(String sql) throws SQLException {
		statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		resultSet = statement.executeQuery(sql);
		
		List <Map<String, Object>> list = new ArrayList<>();
		ResultSetMetaData rsMdata = resultSet.getMetaData();
		
		int colCount = rsMdata.getColumnCount();
		
		while(resultSet.next()) {
			Map<String, Object> rowMap = new HashMap<>();
			
			for (int col=1; col<=colCount; col++) {
				rowMap.put(rsMdata.getColumnName(col), resultSet.getObject(col));
			}
			
			list.add(rowMap);
		}
		
		return list;
	}
	
	public static void closeConenctions() {
		try {
			if (resultSet != null) {
				resultSet.close();
			}
			if (statement != null) {
				statement.close();
			}
			if (connection != null) {
				connection.close();
			}
						
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	enum DBType{ //constants listed
		ORACLE, MYSQL, MARIAADB
	}

}
