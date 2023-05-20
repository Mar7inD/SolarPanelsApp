package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection implements AutoCloseable
{
  private static Connection connection;
  public static Connection getConnection() {
    if (connection == null) {
      try {
        // Create the connection
        connection = DriverManager.getConnection("jdbc:postgresql://snuffleupagus.db.elephantsql.com:5432/icgiivdi", "icgiivdi", "5JBbbzO0mgAwhwoUtAkf3QH8zaL4Lo-n");
      } catch (SQLException e) {
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
