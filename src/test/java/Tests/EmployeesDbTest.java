package Tests;

import java.sql.SQLException;

import org.testng.Assert;
import org.testng.annotations.Test;

import Utils.DBType;
import Utils.DBUtility;

public class EmployeesDbTest {
	
	@Test
	public void countTest() throws SQLException {
		//Connect to Oracle DB
		//Run following sql Query: "select * from employees where job_id = 'IT_PROG'
		//More than 0 records should be returned
		
		DBUtility.establishComment(DBType.ORACLE); //establish Connection
		
		String sql = "select * from employees where job_id = 'IT_PROG'";
		int rowsCount = DBUtility.getRowsCount(sql);//make sure returning more than zero
		Assert.assertTrue(rowsCount>0);
		
		DBUtility.closeConenctions();
		
	}

	

}
