package Data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Map;

public class DatabaseInterface {
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost/EMP";

	// Database credentials
	static final String USER = "Golabi";
	static final String PASS = "123456";

	public static ResultSet getQuery(String query) {
		ResultSet rs;
		Connection conn = null;
		Statement stmt = null;
		try {
			// STEP 2: Register JDBC driver

			Class.forName("com.mysql.jdbc.Driver");

			// STEP 3: Open a connection
			System.out.println("\nConnecting to database...");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
			return rs;
			// STEP 5: Extract data from result set
		} catch (Exception e) {
			System.out.println("Result is NULL");
		}
		return null;
	}
	
	public static boolean update(String query) {
		Connection conn = null;
		Statement stmt = null;
		try {
			// STEP 2: Register JDBC driver

			Class.forName("com.mysql.jdbc.Driver");

			// STEP 3: Open a connection
			System.out.println("\nConnecting to database...");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			stmt = conn.createStatement();
			stmt.executeUpdate(query);
			return true;
			// STEP 5: Extract data from result set
		} catch (Exception e) {
			System.out.println("Result is NULL");
		}
		return false;
	}

	public static void showResult(String statement, ResultSet rs, Map<String, Integer> tn, String [] col) {
		try {
			for(int i = 0; i < col.length; i++)
				System.out.print(col[i] + "\t");
			System.out.println();
			while (rs.next()) {
				for(int i = 0; i < col.length; i++) {
					int type = tn.get(col[i]);
					if(type == 1) {
						System.out.print(rs.getInt(col[i]) + "\t");
					} else if(type == 2) {
						System.out.println(rs.getString(col[i]) + "\t");
					} else if(type == 3) {
						System.out.println(rs.getDate(col[i]) + "\t");
					}
				}
			}
			System.out.println();
		} catch (Exception e) {
			System.out.println("No results for " + statement);
		}
	}
}
