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


  private ViewHandler viewHandler;

  public void init(ViewHandler viewHandler)
  {
    this.viewHandler = viewHandler;
  }


  // SQL statements to insert
  private final String insertManufacturerSql = "INSERT INTO \"solar_panels\".\"manufacturer\" (name, address, email, phone_number)" + "VALUES (?, ?, ?, ?)";

  private void addDataToDatabase() {
    Connection connection = null;
    try {
      connection = DatabaseConnection.getConnection();
      PreparedStatement insertManufacturerStmt = connection.prepareStatement(insertManufacturerSql);

      // Insert data into Manufacturer table
      insertManufacturerStmt.setString(1, nameField.getText());
      insertManufacturerStmt.setString(2, addressField.getText());
      insertManufacturerStmt.setString(3, emailField.getText());
      insertManufacturerStmt.setString(4, phoneNumberField.getText());
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
    } finally {
      if (connection != null) {
        try {
          connection.close();
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }
    }
  }


  private void clearInputFields() {
    nameField.clear();
    addressField.clear();
    emailField.clear();
    phoneNumberField.clear();
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

}

