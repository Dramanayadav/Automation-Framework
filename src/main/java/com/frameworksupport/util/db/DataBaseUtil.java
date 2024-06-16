package com.frameworksupport.util.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import com.frameworksupport.setup.AutomationBaseActionDriver;
import com.frameworksupport.util.general.CommonUtil;

public class DataBaseUtil 
{
	private Statement stmt;
	private Connection con;
	private ResultSet rs;
	
	public Statement connection() throws SQLException
	{
		
	// Make the database connection
  	 try 
  	 {
		Class.forName(CommonUtil.getConfigProperty("db_connection"));
		con = DriverManager.getConnection(CommonUtil.getConfigProperty("db_url"),
				CommonUtil.getConfigProperty("db_username"),CommonUtil.getConfigProperty("db_password"));
		    
	} 
  	 catch (ClassNotFoundException e) 
  	 {
		e.printStackTrace();
  	 }
     // Get connection to DB
     if(!con.isClosed())
     {
   	  AutomationBaseActionDriver.getCommonUtil().log("Successfully connected to the database");
   	  stmt = con.createStatement();
     }
     return stmt;
	}
	
	public synchronized HashMap<String, String> executeSelectQuery(String query) throws SQLException
	{
		 AutomationBaseActionDriver.getCommonUtil().log("Executing Select Query");
		 HashMap<String, String> data_map = new HashMap<>();
		try 
		{
			rs = stmt.executeQuery(query);
		} catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
  	  ResultSetMetaData rsmd = (ResultSetMetaData) rs.getMetaData();
  	  
  	  while(rs.next())
  	  {
  		//Print one row          
  			for(int i = 1 ; i <= rsmd.getColumnCount(); i++)
  			{
  			     data_map.put(rsmd.getColumnName(i), rs.getString(i));  			    
  			}
  			AutomationBaseActionDriver.getCommonUtil().log(data_map);
  			//System.out.println(data_map);//Move to the next line to print the next row. 
  			}
  	  	AutomationBaseActionDriver.getCommonUtil().log("Successfully executed: "+query);
  	  	return data_map;
		
    }
	
	public void executeInsertQuery(String insertquery) throws SQLException
	{
		int result;
		
			result = stmt.executeUpdate(insertquery);
		if (result > 0)
		{
			AutomationBaseActionDriver.getCommonUtil().log("Successfully inserted: "+insertquery);
		}
		
        else
        {
        	AutomationBaseActionDriver.getCommonUtil().log("Unsucessful insertion: "+insertquery);
        }
	}
	
	public void executeUpdate(String updatequery)
	{
		PreparedStatement p = null;
        try 
        {
            p = con.prepareStatement(updatequery);
            p.execute();
            AutomationBaseActionDriver.getCommonUtil().log("Successfully executed: "+updatequery);
        }
        catch (SQLException e) 
        {
            System.out.println(e);
        }
	}
}
