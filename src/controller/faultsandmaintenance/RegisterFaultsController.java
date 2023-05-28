package controller.faultsandmaintenance;

import controller.ViewHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

public class RegisterFaultsController
{
  @FXML private Button backButton;
  @FXML private Button saveButton;
  @FXML
  private TextField panelSerialNumberField;
  @FXML
  private TextField faultDateField;
  @FXML
  private TextField faultTypeField;
  @FXML
  private TextArea descriptionField;
  private ViewHandler viewHandler;

  public void init(ViewHandler viewHandler)
  {
    this.viewHandler = viewHandler;
  }

  // SQL statements to insert
  private final String insertFaultSql = "INSERT INTO \"solar_panels\".\"faults\" (panel_serial_no, fault_date, fault_type, description)" + "VALUES (?, ?, ?, ?)";

  private void addFaultsToDatabase() {
    Connection connection = viewHandler.getConnection();

    String faultDate = faultDateField.getText();
    if(!isValidFaultDate(String.valueOf(faultDate))){
      showErrorDialog("Invalid Date Format", "Please enter the fault date in the format YYYY-MM-DD HH:MM:SS");
      return; // Abort insertion
    }

    String panelSerialNumber = panelSerialNumberField.getText();
    if (!isValidPanelSerialNumber(panelSerialNumber)) {
      showErrorDialog("Invalid Panel Serial Number", "Please enter a 6-digit panel serial number.");
      return; // Abort insertion
    }

    try {
      PreparedStatement insertManufacturerStmt = connection.prepareStatement(insertFaultSql);


      // Insert data into Manufacturer table
      insertManufacturerStmt.setInt(1, Integer.parseInt(panelSerialNumberField.getText()));
      insertManufacturerStmt.setTimestamp(2, Timestamp.valueOf(faultDateField.getText()));
      insertManufacturerStmt.setString(3, faultTypeField.getText());
      insertManufacturerStmt.setString(4, descriptionField.getText());
      int rowsAffected = insertManufacturerStmt.executeUpdate();

      // Clear input fields after successful insertion
      clearInputFields();

      if (rowsAffected > 0) {
        // Show a success message or perform any desired action
        System.out.println("Data inserted successfully!");
      } else {
        // Show an error message or perform appropriate error handling
        System.out.println("Failed to insert data into the database.");
      }
    } catch (SQLException e) {
      // Failed data insert
      System.err.println("Failed to insert data into the database: " + e.getMessage());
      e.printStackTrace();
    }

  }


  private void clearInputFields() {
    panelSerialNumberField.clear();
    faultDateField.clear();
    faultTypeField.clear();
    descriptionField.clear();
  }

  private boolean isValidPanelSerialNumber(String panelSerialNumber) {
    String panelSerialNumberValid = "\\d{6}"; // 6-digit panel serial number
    return panelSerialNumber.matches(panelSerialNumberValid);
  }

  private boolean isValidFaultDate(String faultDate) {
    String faultDateValid = "\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}"; // YYYY-MM-DD HH:MM:SS
    return faultDate.matches(faultDateValid);
  }

  private void showErrorDialog(String title, String message) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
  }

  public void onClick(ActionEvent event)
  {
    if (event.getSource() == backButton)
    {
      viewHandler.changeScene(viewHandler.FAULTS_AND_MAINTENANCE);
    }
    if (event.getSource() == saveButton)
    {
      addFaultsToDatabase();
    }
  }
}
