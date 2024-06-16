package com.database.testplan;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.frameworksupport.setup.AutomationBaseActionDriver;
import com.frameworksupport.setup.TestInfo;
import com.frameworksupport.util.db.DBJson;
import com.frameworksupport.util.db.DataBaseUtil;
import com.swaglabs.desktop.web.config.Author;

public class DatabaseTest extends AutomationBaseActionDriver
{
	
		@BeforeClass(alwaysRun = true)
		public void initialSetup()
		{
			initializeDbTest("Connect to DB...");
		}
	
		//DBJson du = new DBJson();
		DataBaseUtil du = new DataBaseUtil();			
		@TestInfo(author=Author.SRINIVAS)
		@Test
		public void dbtest() throws Exception
		{
			du.connection();
			du.executeSelectQuery("SELECT * FROM orders");
			//du.executeInsertQuery("INSERT INTO Orders VALUES(1021,'2022-05-25','KL Rahul')");
			//du.executeUpdate("UPDATE Orders set CustomerName='David Warner' WHERE OrderID=1005");
		}
		@Override
		protected void cleanPageObjects() {
			// TODO Auto-generated method stub
			
		}
}
