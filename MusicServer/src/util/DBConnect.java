package util;

import java.sql.*;

public class DBConnect {

	private static Connection con = null;	
	private static String URL="jdbc:sqlite:server.db";

	public static Connection getCon()throws ClassNotFoundException,SQLException{
		if (con==null) {
			Class.forName("org.sqlite.JDBC");
			con=DriverManager.getConnection(URL);
		}
		return con;
	}
}
