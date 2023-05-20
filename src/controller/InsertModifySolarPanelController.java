package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InsertModifySolarPanelController
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
  @FXML private ComboBox<String> typeCombo;
  @FXML private ComboBox<String> roofCombo;
  @FXML private DatePicker installationDatePicker;
  @FXML private Button save;
  @FXML private Button back;

  public void init(ViewHandler viewHandler)
  {
    this.viewHandler = viewHandler;
    this.typeCombo.getItems().addAll("PV", "TC");
    for (int i = 0; i < 220; i++)
    { String position = "" + i;
      this.roofCombo.getItems().add(position); }
  }
  public void onClick(ActionEvent event)
  {
    //Inserting information into database
    if(event.getSource() == save)
    {
      Connection connection = DatabaseConnection.getConnection();

      String manufacturer = "INSERT INTO solarpanels (name, address , email, phone) VALUES (?, ?, ?, ?)";
      String solarPanel = "INSERT INTO solarpanels (serial_no, model_type, roof_position, date_installed, manufacturer) VALUES (?, ?, ?, ?, SELECT id FROM manufacturer WHERE name = ?)";

      try (PreparedStatement statementSP = connection.prepareStatement(solarPanel)) {
        statementSP.setInt(1, Integer.parseInt(serialNoText.getText()));
        statementSP.setString(2, typeCombo.getValue());
        statementSP.setString(3, roofCombo.getValue());
        statementSP.setDate(4, Date.valueOf(installationDatePicker.getValue()));
        statementSP.setString(5, nameText.getText());

        int rowsInserted = statementSP.executeUpdate();
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
