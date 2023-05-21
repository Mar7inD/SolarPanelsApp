package controller.manufacturer;

import controller.DatabaseConnection;
import controller.ViewHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javafx.scene.control.Button;

public class InsertManufacturerPageController
{
  @FXML private Button backButton;
  @FXML private Button saveButton;
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

  public void onClick(ActionEvent event)
  {
    if (event.getSource() == backButton)
    {
      viewHandler.changeScene(viewHandler.MANUFACTURER_INFORMATION);
    }
    if (event.getSource() == saveButton)
    {
      addDataToDatabase();
    }
  }


  // SQL statements to insert
  private final String insertManufacturerSql = "INSERT INTO Manufacturer (name, address, email, phone_number) " +
      "VALUES (?, ?, ?, ?)";


  private void addDataToDatabase() {
    try (Connection connection = DatabaseConnection.getConnection();
        PreparedStatement insertManufacturerStmt = connection.prepareStatement(insertManufacturerSql)) {

      // Insert data into Manufacturer table
      insertManufacturerStmt.setString(1, nameField.getText());
      insertManufacturerStmt.setString(2, addressField.getText());
      insertManufacturerStmt.setString(3, emailField.getText());
      insertManufacturerStmt.setString(4, phoneNumberField.getText());
      insertManufacturerStmt.executeUpdate();

      // Clear input fields after successful insertion
      clearInputFields();

      // Show a success message or perform any desired action
      System.out.println("Data inserted successfully!");
    } catch (SQLException e) {
      // Failed data insert
      System.err.println("Failed to insert data into the database: " + e.getMessage());
      e.printStackTrace();
    }
  }

  private void clearInputFields() {
    nameField.clear();
    addressField.clear();
    emailField.clear();
    phoneNumberField.clear();
  }
}

