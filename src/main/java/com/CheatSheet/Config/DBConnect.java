package com.CheatSheet.Config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnect {
	
	 public static void main(String[] args) throws SQLException {
		    Connection con = getConnection();

		    if (con != null) {
		      System.out.println("Connection is working....");
		    }
		  }

		  public static Connection getConnection()throws SQLException {
		    Connection con = null;

		    try {
		      Class.forName("com.mysql.cj.jdbc.Driver");
//		      System.out.println(con);
		      
		    } catch (ClassNotFoundException e) {
		      System.out.println("Driver Error :" + e.getMessage());
		    }
		    
		    try {
		      con = DriverManager.getConnection("jdbc:mysql://localhost:3306/cheat_sheet", "root", "root");
		      System.out.println("Connection is sucessfully");
		    } catch (SQLException e) {
		      System.out.println("Connection Error :" + e.getMessage());
		    }
		    return con;
		  }
		}