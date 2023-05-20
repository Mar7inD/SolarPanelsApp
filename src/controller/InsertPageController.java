package controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InsertPageController
{
  @FXML
  private TextField nameField;
  @FXML
  private TextField addressField;
  @FXML
  private TextField emailField;
  @FXML
  private TextField phoneNumberField;
  @FXML
  private TextField serialNoField;
  @FXML
  private ComboBox<String> modelTypeField;
  @FXML
  private ComboBox<String> roofPositionField;
  @FXML
  private DatePicker dateInstalledPicker;

  private ViewHandler viewHandler;

  public void init(ViewHandler viewHandler)
  {
    this.viewHandler = viewHandler;


  }


  // SQL statements to insert data
  private final String insertManufacturerSql = "INSERT INTO Manufacturer (name, address, email, phone_number) " +
      "VALUES (?, ?, ?, ?)";
  private final String insertSolarPanelSql = "INSERT INTO SolarPanels (serial_No, model_type, roof_position, " +
      "date_installed, manufacturer) " +
      "VALUES (?, ?, ?, ?, (SELECT id FROM Manufacturer WHERE name = ?), ?)";
  

  private void addDataToDatabase() {
    try (Connection connection = DatabaseConnection.getConnection();
        PreparedStatement insertManufacturerStmt = connection.prepareStatement(insertManufacturerSql);
        PreparedStatement insertSolarPanelStmt = connection.prepareStatement(insertSolarPanelSql)) {

      // Insert data into Manufacturer table
      insertManufacturerStmt.setString(1, nameField.getText());
      insertManufacturerStmt.setString(2, addressField.getText());
      insertManufacturerStmt.setString(3, emailField.getText());
      insertManufacturerStmt.setString(4, phoneNumberField.getText());
      insertManufacturerStmt.executeUpdate();

      // Insert data into SolarPanels table
      insertSolarPanelStmt.setString(1, serialNoField.getText());
      String modelType = modelTypeField.getSelectionModel().getSelectedItem();
      insertSolarPanelStmt.setString(2, modelType);
      int roofPosition = Integer.parseInt(
          roofPositionField.getSelectionModel().getSelectedItem());
      insertSolarPanelStmt.setInt(3, roofPosition);
      insertSolarPanelStmt.setDate(4, java.sql.Date.valueOf(dateInstalledPicker.getValue()));
      insertSolarPanelStmt.setString(5, nameField.getText());
      insertSolarPanelStmt.executeUpdate();

      // Clear input fields after successful insertion
      clearInputFields();

      // Show a success message or perform any desired action
      System.out.println("Data inserted successfully!");
    } catch (SQLException e) {
      // Handle any exceptions or display an error message
      e.printStackTrace();
    }
  }

  private void clearInputFields() {
    nameField.clear();
    addressField.clear();
    emailField.clear();
    phoneNumberField.clear();
    serialNoField.clear();
    modelTypeField.setValue(null);
    roofPositionField.setValue(null);
    dateInstalledPicker.setValue(null);
  }
}
