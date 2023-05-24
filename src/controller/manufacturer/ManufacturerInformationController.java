package controller.manufacturer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import controller.DatabaseConnection;
import controller.ViewHandler;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import java.sql.Connection;
import java.sql.ResultSet;
import model.Manufacturer;

public class ManufacturerInformationController {
  @FXML private Button backButton;
  @FXML private Button insertButton;
  @FXML private Button deleteButton;
  @FXML private Button refreshButton;
  @FXML private TableView<Manufacturer> manufacturerTableView;
  @FXML private TableColumn<Manufacturer, String> nameColumn;
  @FXML private TableColumn<Manufacturer, String> addressColumn;
  @FXML private TableColumn<Manufacturer, String> emailColumn;
  @FXML private TableColumn<Manufacturer, String> phoneNumberColumn;

  private Manufacturer selectedManufacturer; // Store the selected manufacturer
  private ViewHandler viewHandler;

  public void init(ViewHandler viewHandler) {
    this.viewHandler = viewHandler;

    // Initialize table columns
    nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
    addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
    emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
    phoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));

    // Retrieve data from the database
    Connection connection = null;
    try {
      connection = DatabaseConnection.getConnection();
      List<Manufacturer> manufacturers = getAllManufacturers(connection);

      // Populate the TableView with the retrieved data
      manufacturerTableView.getItems().addAll(manufacturers);
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      if (connection != null) {
        DatabaseConnection.closeConnection();
      }
    }

    // Disable the delete button initially
    deleteButton.setDisable(true);

    // Add a listener to track row selection in the TableView
    manufacturerTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
      if (newSelection != null) {
        // Enable the delete button when a row is selected
        deleteButton.setDisable(false);
        selectedManufacturer = newSelection; // Store the selected manufacturer
      } else {
        // Disable the delete button when no row is selected
        deleteButton.setDisable(true);
        selectedManufacturer = null; // Reset the selected manufacturer
      }
    });
  }

  // Retrieve the manufacturer data and populate the TableView accordingly
  private List<Manufacturer> getAllManufacturers(Connection connection) throws SQLException {
    List<Manufacturer> manufacturers = new ArrayList<>();

    try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM \"solar_panels\".\"manufacturer\" LIMIT 100");
        ResultSet resultSet = statement.executeQuery()) {
      while (resultSet.next()) {
        String name = resultSet.getString("name");
        String address = resultSet.getString("address");
        String email = resultSet.getString("email");
        String phoneNumber = resultSet.getString("phone_number");

        Manufacturer manufacturer = new Manufacturer(name, address, email, phoneNumber);
        manufacturers.add(manufacturer);
      }
    }

    return manufacturers;
  }


  public void refreshTableView() {
    manufacturerTableView.getItems().clear(); // Clear the existing items

    Connection connection = null;
    try {
        connection = DatabaseConnection.getConnection();
      List<Manufacturer> manufacturers = getAllManufacturers(connection);

      // Populate the TableView with the retrieved data
      manufacturerTableView.getItems().addAll(manufacturers);
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      if (connection != null) {
        DatabaseConnection.closeConnection();
      }
    }
  }

  @FXML private void deleteManufacturer() {
    if (selectedManufacturer != null) {
      // Delete the selected manufacturer from the database
      Connection connection = null;
      try {
        connection = DatabaseConnection.getConnection();
        deleteManufacturerFromDatabase(connection, selectedManufacturer);
      } catch (SQLException e) {
        e.printStackTrace();
      } finally {
        if (connection != null) {
          DatabaseConnection.closeConnection();
        }
      }
      // Refresh the table view after deletion
      refreshTableView();
    }
  }

  private void deleteManufacturerFromDatabase(Connection connection, Manufacturer manufacturer) throws SQLException {
    String deleteQuery = "DELETE FROM \"solar_panels\".\"manufacturer\" WHERE name = ?";
    try (PreparedStatement statement = connection.prepareStatement(deleteQuery)) {
      statement.setString(1, manufacturer.getName());
      statement.executeUpdate();
    }
  }

  public void onClick(ActionEvent event) {
    if (event.getSource() == backButton) {
      viewHandler.changeScene(viewHandler.MAIN_SCENE);
    }
    if (event.getSource() == insertButton) {
      viewHandler.changeScene(viewHandler.INSERT_MANUFACTURER_PAGE);
    }
    if (event.getSource() == refreshButton) {
      refreshTableView();
    }
    if (event.getSource() == deleteButton) {
      deleteManufacturer();
    }
  }
}