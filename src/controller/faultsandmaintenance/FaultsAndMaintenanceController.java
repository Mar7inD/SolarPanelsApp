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
import model.Manufacturer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FaultsAndMaintenanceController
{
  @FXML private Button registerMaintenanceButton;
  @FXML private Button registerFaultButton;
  @FXML private Button backButton;
  @FXML private Button deleteButton;
  @FXML private Button refreshMaintenanceButton;
  @FXML private Button refreshFaultButton;
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
  private Fault selectedFault;
  private Maintenance selectedMaintenance;

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

    faultsTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
      if (newSelection != null) {
        // Enable the delete button when a row is selected in faultsTable
        deleteButton.setDisable(false);
        selectedFault = newSelection; // Store the selected fault
      }else {
        // Disable the delete button when no row is selected in either table
        deleteButton.setDisable(true);
        selectedFault = null; // Reset the selected Fault
      }
    });

    maintenanceTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
      if (newSelection != null) {
        // Enable the delete button when a row is selected in faultsTable
        deleteButton.setDisable(false);
        selectedMaintenance = newSelection; // Store the selected fault
      } else {
        // Disable the delete button when no row is selected in either table
        deleteButton.setDisable(true);
        selectedMaintenance = null; // Reset the selected Maintenance
      }
    });

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

  private void refreshFaultsTableView() {
    faultsTableView.getItems().clear(); // Clear the existing fault items

    Connection connection = null;
    try {
      connection = DatabaseConnection.getConnection();
      List<Fault> faults = loadFaultsData(connection);

      // Populate the TableView with the retrieved data
      faultsTableView.getItems().addAll(faults);
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      if (connection != null) {
        DatabaseConnection.closeConnection();
      }
    }
  }

  private void refreshMaintenanceTableView() {
    maintenanceTableView.getItems().clear(); // Clear the existing maintenance items

    Connection connection = null;
    try {
      connection = DatabaseConnection.getConnection();
      List<Maintenance> maintenanceList = loadMaintenanceData(connection);

      // Populate the TableView with the retrieved data
      maintenanceTableView.getItems().addAll(maintenanceList);
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      if (connection != null) {
        DatabaseConnection.closeConnection();
      }
    }
  }

  @FXML
  private void deleteEntry() {
    if (selectedFault != null) {
      deleteFault();
    } else if (selectedMaintenance != null) {
      deleteMaintenance();
    }
  }

  private void deleteFault() {
    if (selectedFault != null) {
      // Delete the selected fault from the database
      Connection connection = null;
      try {
        connection = DatabaseConnection.getConnection();
        deleteFaultFromDatabase(connection, selectedFault);
      } catch (SQLException e) {
        e.printStackTrace();
      } finally {
        if (connection != null) {
          DatabaseConnection.closeConnection();
        }
      }
      // Refresh the table view after deletion
      refreshFaultsTableView();
    }
  }

  private void deleteMaintenance() {
    if (selectedMaintenance != null) {
      // Delete the selected maintenance from the database
      Connection connection = null;
      try {
        connection = DatabaseConnection.getConnection();
        deleteMaintenanceFromDatabase(connection, selectedMaintenance);
      } catch (SQLException e) {
        e.printStackTrace();
      } finally {
        if (connection != null) {
          DatabaseConnection.closeConnection();
        }
      }
      // Refresh the table view after deletion
      refreshMaintenanceTableView();
    }
  }

  private void deleteFaultFromDatabase(Connection connection, Fault fault) throws SQLException {
    String deleteQuery = "DELETE FROM \"solar_panels\".\"faults\" WHERE id = ?";
    try (PreparedStatement statement = connection.prepareStatement(deleteQuery)) {
      statement.setInt(1, fault.getId());
      statement.executeUpdate();
    }
  }

  private void deleteMaintenanceFromDatabase(Connection connection, Maintenance maintenance) throws SQLException {
    String deleteQuery = "DELETE FROM \"solar_panels\".\"maintenance\" WHERE id = ?";
    try (PreparedStatement statement = connection.prepareStatement(deleteQuery)) {
      statement.setInt(1, maintenance.getId());
      statement.executeUpdate();
    }
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
    if (event.getSource() == registerMaintenanceButton)
    {
      viewHandler.changeScene(viewHandler.REGISTER_MAINTENANCE);
    }
    if (event.getSource() == deleteButton)
    {
      deleteEntry();
    }
    if (event.getSource() == refreshMaintenanceButton)
    {
      refreshMaintenanceTableView();
    }
    if (event.getSource() == refreshFaultButton)
    {
      refreshFaultsTableView();
    }
  }
}
