package controller;

import controller.solarPanels.SolarPanel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.SolarPanelProduction;

import java.sql.*;
import java.util.ArrayList;
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
      String sqlQuery = "SELECT * FROM solar_panels.solar_panels";
      ResultSet resultSet = statement.executeQuery(sqlQuery);
      while (resultSet.next()) {
        solarPanels.add(new SolarPanel(resultSet.getString("serial_no"),
            resultSet.getString("model_type"),resultSet.getString("roof_position"),resultSet.getDate("date_installed"),resultSet.getString("manufacturer"),resultSet.getBoolean("is_active")));
      }
      closeConnection();
    }
    catch(SQLException e)
    {
      e.printStackTrace();
    }
    return solarPanels;
  }
  public static ObservableList<SolarPanelProduction> getProductionCapacity(ArrayList<Integer> positions, String panel_type)
      throws SQLException
  {
    ObservableList<SolarPanelProduction> prod = FXCollections.observableArrayList();
    if (positions.isEmpty())
    {
      return prod;
    }
    try(Connection connection = getConnection())
    {
      Statement statement = connection.createStatement();
      String sqlQuery = "SELECT measurement_time, AVG(" + panel_type + ") as measurement_value FROM solar_panels.live_data";
//          + " WHERE position in (";
//      sqlQuery = buildSQLQuery(positions, sqlQuery);
//      sqlQuery = sqlQuery + " GROUP BY measurement_date;";
      ResultSet resultSet = statement.executeQuery(sqlQuery);
      while (resultSet.next()) {
        prod.add(new SolarPanelProduction(resultSet.getDate("measurement_date"),
            resultSet.getFloat("measurement_value")));
      }
      closeConnection();
    }
    catch(SQLException e)
    {
      e.printStackTrace();
    }
    return prod;
  }

  public static String buildSQLQuery(ArrayList<Integer> positions, String sqlQuery)
  {
    StringBuilder sb = new StringBuilder();
    sb.append(sqlQuery);

    for (int i = 0; i < positions.size(); i++) {
      sb.append(positions.get(i));
      if (i != positions.size() - 1) {
        sb.append(",");
      }
    }

    sb.append(")");

    return sb.toString();
  }
    /*@Override
    public void close() throws SQLException {
      if (connection != null) {
        connection.close();
      }
    }*/
}
