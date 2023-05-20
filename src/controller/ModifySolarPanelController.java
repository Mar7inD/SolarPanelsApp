package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ModifySolarPanelController
{
  private final String insertManufacturerSql = "INSERT INTO Manufacturer (name, address, email, phone_number) " +
      "VALUES (?, ?, ?, ?)";
  private final String insertSolarPanelSql = "INSERT INTO SolarPanels (serial_No, model_type, roof_position, " +
      "date_installed, manufacturer, is_active) VALUES (?, ?, ?, ?, ?, ?)";
  private ViewHandler viewHandler;
  @FXML private TextField serialNoText;
  @FXML private TextField nameText;
  @FXML private TextField addressText;
  @FXML private TextField emailText;
  @FXML private TextField phoneText;
  @FXML private ComboBox typeCombo;
  @FXML private ComboBox roofCombo;
  @FXML private Button save;
  @FXML private Button back;

  public void init(ViewHandler viewHandler)
  {
    this.viewHandler = viewHandler;
  }
  public void onClick(ActionEvent event)
  {
    //Inserting information into database
    if(event.getSource() == save)
    {
      Connection connection = DatabaseConnection.getConnection();

      String sql = "INSERT INTO solarpanels (serial_no, model_type, roof_position, date_installed, manufacturer) VALUES (?, ?, ?, ?, ?)";

      try (PreparedStatement statement = connection.prepareStatement(sql)) {
        statement.setString(1, serialNoText);
        statement.setString(2, modelType);

        int rowsInserted = statement.executeUpdate();
        if (rowsInserted > 0) {
          System.out.println("Insert successful");
        } else {
          System.out.println("Insert failed");
        }
      } catch (SQLException e) {
        e.printStackTrace();
      }

      DatabaseConnection.closeConnection();
    }
    else if(event.getSource() == back)
    {
      viewHandler.changeScene(viewHandler.SOLAR_PANELS);
    }
  }
}
