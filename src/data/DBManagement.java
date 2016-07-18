package data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Map;

public class DBManagement {
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost/EMP";

	// Database credentials
	static final String USER = "Golabi";
	static final String PASS = "123456";

	// public void createtable() {
	// String query = createTableQuery("DEVELOP", new String[] { "ID",
	// "PROJECTNAME", "FROM", "TO" }, new String[] { "INT", "VARCHAR(255)",
	// "DATE", "DATE" }, new String[] { "", "", "", "" }, new String {"ID"},
	// foreignKey, refrence)
	// ,
	// new String[] { String.valueOf(id), projectName, from.toString(),
	// to.toString() }
	// update(query);
	// }

	public String createTableQuery(String tableName, String fields[], String type[], String options[],
			String primaryKey[], String foreignKey[], String refrence[]) {
		String query = "create table " + tableName + "( ";
		for (int i = 0; i < fields.length; i++)
			query += fields[i] + " " + type[i] + " " + options[i] + ", ";
		query += "PRIMARY KEY ( ";
		for (int i = 0; i < primaryKey.length; i++)
			if (i == primaryKey.length - 1)
				query += primaryKey[i] + " )";
			else
				query += primaryKey[i] + ", ";
		if (foreignKey != null) {
			for (int i = 0; i < foreignKey.length; i++) {
				query += "FOREIGN KEY ( " + foreignKey + " ) REFERENCES" + refrence[i] + " ";
				if (i != foreignKey.length - 1)
					query += ", ";
			}
		}
		query += ");";
		return query;

	}

	public String generateAddQuery(String table, String[] values) {
		String query = "insert into " + table + " values(";
		for (int i = 0; i < values.length; i++) {
			if (!values[i].equalsIgnoreCase("null"))
				if (i == values.length - 1)
					query += "\'" + values[i] + "\');";
				else
					query += "\'" + values[i] + "\', ";
			else {
				if (i == values.length - 1)
					query += " " + values[i] + ");";
				else
					query += " " + values[i] + ", ";
			}
		}
		return query;
	}

	public String generateDeleteQuery(String table, String[] values, String[] fields) {
		String query = "delete from " + table + " where ";
		for (int i = 0; i < values.length; i++) {
			if (!values[i].equalsIgnoreCase("null"))
				if (i == values.length - 1)
					query += fields[i] + " = \'" + values[i] + "\';";
				else
					query += fields[i] + " = \'" + values[i] + "\' and ";
			else {
				if (i == values.length - 1)
					query += fields[i] + " = " + values[i] + ";";
				else
					query += fields[i] + " = " + values[i] + " and ";
			}
		}
		return query;
	}

	public String generateSelectQuery(String table, String[] col, String[] values, String[] fields) {
		String query = "select ";
		for (int i = 0; i < col.length; i++) {
			if (i == col.length - 1)
				query += col[i] + " ";
			else
				query += col[i] + ", ";
		}
		query += "from " + table;
		if (values != null) {
			query += " where ";
			for (int i = 0; i < values.length; i++) {
				if (!values[i].equalsIgnoreCase("null"))
					if (i == values.length - 1)
						query += fields[i] + " = \'" + values[i] + "\';";
					else
						query += fields[i] + " = \'" + values[i] + "\' and ";
				else {
					if (i == values.length - 1)
						query += fields[i] + " = " + values[i] + ";";
					else
						query += fields[i] + " = " + values[i] + " and ";
				}

			}
		} else
			query += ";";
		return query;
	}

	public String generateUpdateQuery(String table, String[] newValues, String[] newValuesFileds, String[] oldValues,
			String[] oldValuesFields) {
		String query = "update " + table + " set ";
		for (int i = 0; i < newValues.length; i++)
			if (i == newValues.length - 1)
				query += newValuesFileds[i] + " = \"" + newValues[i] + "\" ";
			else
				query += newValuesFileds[i] + " = \"" + newValues[i] + "\", ";
		if (oldValues.length > 0) {
			query += "where ";
			for (int i = 0; i < oldValues.length; i++) 
				if(!oldValues[i].equalsIgnoreCase("null"))
					if (i == oldValues.length - 1)
						query += oldValuesFields[i] + " = \"" + oldValues[i] + "\";";
					else
						query += oldValuesFields[i] + " = \"" + oldValues[i] + "\" and ";
				else {
					if (i == oldValues.length - 1)
						query += oldValuesFields[i] + " = " + oldValues[i] + ";";
					else
						query += oldValuesFields[i] + " = " + oldValues[i] + " and ";
				}
		} else
			query += ";";
		return query;
	}

	public ResultSet getQuery(String query) {
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

	public boolean update(String query) {
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

	public boolean execute(String query) {
		Connection conn = null;
		Statement stmt = null;
		try {
			// STEP 2: Register JDBC driver

			Class.forName("com.mysql.jdbc.Driver");

			// STEP 3: Open a connection
			System.out.println("\nConnecting to database...");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			stmt = conn.createStatement();
			stmt.execute(query);
			return true;
			// STEP 5: Extract data from result set
		} catch (Exception e) {
			System.out.println("Result is NULL");
		}
		return false;
	}

	public static void showResult(String statement, ResultSet rs, Map<String, Integer> tn, String[] col) {
		try {
			for (int i = 0; i < col.length; i++)
				System.out.print(col[i] + "\t");
			System.out.println();
			while (rs.next()) {
				for (int i = 0; i < col.length; i++) {
					int type = tn.get(col[i]);
					if (type == 1) {
						System.out.print(rs.getInt(col[i]) + "\t");
					} else if (type == 2) {
						System.out.println(rs.getString(col[i]) + "\t");
					} else if (type == 3) {
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
