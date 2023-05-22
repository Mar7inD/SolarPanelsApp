package controller;

import controller.solarPanels.SolarPanel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.Properties;

public class DatabaseConnection //implements AutoCloseable
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

  public static void closeConnection()
  {
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

  public static ObservableList<SolarPanel> getSolarPanels()
      throws SQLException
  {
    ObservableList<SolarPanel> solarPanels = FXCollections.observableArrayList();
    try(Connection connection = getConnection())
    {
      Statement statement = connection.createStatement();
      String sqlQuery = "SELECT * FROM solar_panels.solarpanels";
      ResultSet resultSet = statement.executeQuery(sqlQuery);
      while (resultSet.next()) {
        solarPanels.add(new SolarPanel(resultSet.getString("serial_no"),
            resultSet.getString("model_type"),resultSet.getString("roof_position"),resultSet.getDate("date_installed"),resultSet.getString("manufacturer"),resultSet.getBoolean("is_active")));
      }
      System.out.println("CONNECTION WHILE IN GETSOLARPANELS");
      System.out.println(connection);
      closeConnection();
    }
    catch(SQLException e)
    {
      e.printStackTrace();
    }
    return solarPanels;
  }
    /*@Override
    public void close() throws SQLException {
      if (connection != null) {
        connection.close();
      }
    }*/
}
