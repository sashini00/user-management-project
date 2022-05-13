package com;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.ws.rs.GET;
import javax.ws.rs.Path;


public class User {
	
	private Connection connect() {
		Connection con = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			// Provide the correct details: DBServer/DBName, UName, password
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/usermanage?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}

	public String insertUser(String UName, String UPhoneNo, String UEmail, String UsrName, String UPass)  
	{   
		String output = ""; 	 
		try   
		{    
			Connection con = connect(); 
	 
			if (con == null)    
			{return "Error while connecting to the database for inserting."; } 
	 
			// create a prepared statement 
			String query = " insert into user1(`UsID`,`UName`,`UPhoneNo`,`UEmail`,`UsrName`,`UPass`)" + " values (?, ?, ?, ?, ?, ?)"; 
			PreparedStatement preparedStmt = con.prepareStatement(query); 
	 
			// binding values    
			 preparedStmt.setInt(1, 0);
			 preparedStmt.setString(2, UName);
			 preparedStmt.setString(3, UPhoneNo);
			 preparedStmt.setString(4, UEmail);
			 preparedStmt.setString(5, UsrName);
			 preparedStmt.setString(6, UPass);
			
			// execute the statement    
			preparedStmt.execute();    
			con.close(); 
	   
			String newUser = readUser(); 
			output =  "{\"status\":\"success\", \"data\": \"" + newUser + "\"}";    
		}   
		catch (Exception e)   
		{    
			output =  "{\"status\":\"error\", \"data\": \"Error while inserting the User.\"}";  
			System.err.println(e.getMessage());   
		} 		
	  return output;  
	} 	
	
	public String readUser()  
	{   
		String output = ""; 
		try   
		{    
			Connection con = connect(); 
		
			if (con == null)    
			{
				return "Error while connecting to the database for reading."; 
			} 
	 
			// Prepare the html table to be displayed    
			output = "<table border=\'1\'><tr><th>Name</th><th>Phone No</th><th>Email</th><th>Username</th><th>Password</th><th>Update</th><th>Remove</th></tr>";
	 
			String query = "select * from user1";    
			Statement stmt = (Statement) con.createStatement();
			ResultSet rs = ((java.sql.Statement) stmt).executeQuery(query);
	 
			// iterate through the rows in the result set    
			while (rs.next())    
			{     
				 String UsID = Integer.toString(rs.getInt("UsID"));
				 String UName = rs.getString("UName");
				 String UPhoneNo = rs.getString("UPhoneNo");
				 String UEmail = rs.getString("UEmail");
				 String UsrName = rs.getString("UsrName");
				 String UPass = rs.getString("UPass");
				 
				// Add into the html table 
				output += "<tr><td><input id=\'hidUserIDUpdate\' name=\'hidUserIDUpdate\' type=\'hidden\' value=\'" + UsID + "'>" 
							+ UName + "</td>"; 
				output += "<td>" + UPhoneNo + "</td>";
				output += "<td>" + UEmail + "</td>";
				output += "<td>" + UsrName + "</td>";
				output += "<td>" + UPass + "</td>";
	 
				// buttons     
				output +="<td><input name='btnUpdate' type='button' value='Update' class='btnUpdate btn btn-secondary'></td>"       
						+ "<td><input name='btnRemove' type='button' value='Remove' class='btnRemove btn btn-danger' data-userid='" + UsID + "'>" + "</td></tr>"; 
			
			}
			con.close(); 
	   
			output += "</table>";   
		}   
		catch (Exception e)   
		{    
			output = "Error while reading the User.";    
			System.err.println(e.getMessage());   
		} 	 
		return output;  
	}
	
	public String updateUser(String UsID, String UName, String UPhoneNo, String UEmail, String UsrName, String UPass)  
	{   
		String output = "";  
		try   
		{    
			Connection con = connect(); 
	 
			if (con == null)    
			{return "Error while connecting to the database for updating."; } 
	 
			// create a prepared statement    
			String query = "UPDATE user1 SET UName=?,UPhoneNo=?,UEmail=?,UsrName=?,UPass=?"  + "WHERE UsID=?";  	 
			PreparedStatement preparedStmt = con.prepareStatement(query); 
	 
			// binding values    
			preparedStmt.setString(1, UName);
			 preparedStmt.setString(2, UPhoneNo);
			 preparedStmt.setString(3, UEmail);
			 preparedStmt.setString(4, UsrName);
			 preparedStmt.setString(5, UPass);
			 preparedStmt.setInt(6, Integer.parseInt(UsID)); 
	 
			// execute the statement    
			preparedStmt.execute();    
			con.close();  
			String newUser = readUser();    
			output = "{\"status\":\"success\", \"data\": \"" + newUser + "\"}";    
		}   
		catch (Exception e)   
		{    
			output =  "{\"status\":\"error\", \"data\": \"Error while updating the User.\"}";   
			System.err.println(e.getMessage());   
		} 	 
	  return output;  
	} 
	
	public String deleteUser(String UsID)   
	{   
		String output = ""; 
	 
		try   
		{    
			Connection con = connect(); 
	 
			if (con == null)    
			{
				return "Error while connecting to the database for deleting."; 			
			} 
	 
			// create a prepared statement    
			String query = "delete from user1 where UsID=?"; 
			PreparedStatement preparedStmt = con.prepareStatement(query); 
	 
			// binding values    
			preparedStmt.setInt(1, Integer.parseInt(UsID)); 
	 
			// execute the statement    
			preparedStmt.execute();    
			con.close(); 
	 
			String newUser = readUser();    
			output = "{\"status\":\"success\", \"data\": \"" +  newUser + "\"}";    
		}   
		catch (Exception e)   
		{    
			output = "Error while deleting the User.";    
			System.err.println(e.getMessage());   
		} 
	 
		return output;  
	}
	
}
