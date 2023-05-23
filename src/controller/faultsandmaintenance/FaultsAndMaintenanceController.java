package controller.faultsandmaintenance;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import model.Fault;
import controller.DatabaseConnection;
import controller.ViewHandler;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import model.Maintenance;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FaultsAndMaintenanceController
{
  @FXML private Button registerMaintenanceButton;
  @FXML private Button registerFaultButton;
  @FXML private Button backButton;

  @FXML private TableView<Fault> faultsTableView;

  @FXML private TableColumn<Fault, Integer> faultIdColumn;

  @FXML private TableColumn<Fault, String> panelSerialNumberFaultColumn;

  @FXML private TableColumn<Fault, Timestamp> faultDateColumn;

  @FXML private TableColumn<Fault, String> faultTypeColumn;

  @FXML private TableColumn<Fault, String> faultDescriptionColumn;
  @FXML private TableView<Maintenance> maintenanceTableView;

  @FXML private TableColumn<Maintenance, Integer> maintenanceIdColumn;

  @FXML private TableColumn<Maintenance, String> panelSerialNumberMaintenanceColumn;

  @FXML private TableColumn<Maintenance, Timestamp> maintenanceDateColumn;

  @FXML private TableColumn<Maintenance, String> maintenanceTypeColumn;

  @FXML private TableColumn<Maintenance, String> maintenanceDescriptionColumn;

  private ViewHandler viewHandler;

  public void init(ViewHandler viewHandler)
  {
    this.viewHandler = viewHandler;

    // Initialize table columns
    faultIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
    panelSerialNumberFaultColumn.setCellValueFactory(new PropertyValueFactory<>("panel_serial_no"));
    faultDateColumn.setCellValueFactory(new PropertyValueFactory<>("fault_date"));
    faultTypeColumn.setCellValueFactory(new PropertyValueFactory<>("fault_type"));
    faultDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

    maintenanceIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
    panelSerialNumberMaintenanceColumn.setCellValueFactory(new PropertyValueFactory<>("panel_serial_no"));
    maintenanceDateColumn.setCellValueFactory(new PropertyValueFactory<>("maintenance_date"));
    maintenanceTypeColumn.setCellValueFactory(new PropertyValueFactory<>("maintenance_type"));
    maintenanceDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

    // Retrieve data from the database
    Connection connection = null;
    try {
      connection = DatabaseConnection.getConnection();
      List<Fault> faults = loadFaultsData(connection);
      List<Maintenance> maintenances = loadMaintenanceData(connection);
      // Populate the TableView with the retrieved data
      faultsTableView.getItems().addAll(faults);
      maintenanceTableView.getItems().addAll(maintenances);
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      if (connection != null) {
        DatabaseConnection.closeConnection();
      }
    }
  }

  private List<Fault> loadFaultsData(Connection connection) throws SQLException
  {
    List<Fault> faults = new ArrayList<>();

    try (PreparedStatement statement = connection.prepareStatement(
        "SELECT * FROM \"solar_panels\".\"faults\" LIMIT 100");
        ResultSet resultSet = statement.executeQuery())
    {
      while (resultSet.next())
      {
        int id = resultSet.getInt("id");
        int panelSerialNumber = resultSet.getInt("panel_serial_no");
        Timestamp faultDate = resultSet.getTimestamp("fault_date");
        String faultType = resultSet.getString("fault_type");
        String description = resultSet.getString("description");

        Fault fault = new Fault(id, panelSerialNumber, faultDate, faultType,
            description);
        faults.add(fault);
      }
    }

    return faults;
  }

  private List<Maintenance> loadMaintenanceData(Connection connection) throws SQLException
  {
    List<Maintenance> maintenances = new ArrayList<>();

    try (PreparedStatement statement = connection.prepareStatement(
        "SELECT * FROM \"solar_panels\".\"maintenance\" LIMIT 100");
        ResultSet resultSet = statement.executeQuery())
    {
      while (resultSet.next())
      {
        int id = resultSet.getInt("id");
        int panelSerialNumber = resultSet.getInt("panel_serial_no");
        Timestamp maintenanceDate = resultSet.getTimestamp("maintenance_date");
        String maintenanceType = resultSet.getString("maintenance_type");
        String description = resultSet.getString("description");

        Maintenance maintenance = new Maintenance(id, panelSerialNumber, maintenanceDate, maintenanceType,
            description);
        maintenances.add(maintenance);
      }
    }

    return maintenances;
  }

  public void onClick(ActionEvent event)
  {
    if (event.getSource() == backButton)
    {
      viewHandler.changeScene(viewHandler.MAIN_SCENE);
    }
    if (event.getSource() == registerFaultButton)
    {
      viewHandler.changeScene(viewHandler.REGISTER_FAULTS);
    }
  }
}
