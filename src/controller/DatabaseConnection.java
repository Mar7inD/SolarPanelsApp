package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnection implements AutoCloseable
{
  private static Connection connection;
  public static Connection getConnection() {
    if (connection == null) {
      try {
        String url = "jdbc:postgresql://snuffleupagus.db.elephantsql.com:5432/icgiivdi";
        Properties props = new Properties();
        props.setProperty("user", "icgiivdi");
        props.setProperty("password", "5JBbbzO0mgAwhwoUtAkf3QH8zaL4Lo-n");
        Connection conn = DriverManager.getConnection(url, props);
        // Create the connection
        connection = DriverManager.getConnection(url,props);
      } catch (SQLException e) {
        System.err.println("Failed to connect to the database: " + e.getMessage());

        e.printStackTrace();
      }
    }
    return connection;
  }

  public static void closeConnection() {
    if (connection != null) {
      try {
        // Close the connection
        connection.close();
        connection = null;
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }
    @Override
    public void close() throws SQLException {
      if (connection != null) {
        connection.close();
      }
    }
}
