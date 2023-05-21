package controller.solarPanels;

import controller.DatabaseConnection;
import controller.ViewHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.sql.*;
import java.time.LocalDate;
import java.time.ZoneId;

public class InsertModifySolarPanelController
{
  private final String insertManufacturerSql = "INSERT INTO Manufacturer (name, address, email, phone_number) " +
      "VALUES (?, ?, ?, ?)";
  private final String insertSolarPanelSql = "INSERT INTO SolarPanels (serial_No, model_type, roof_position, " +
      "date_installed, manufacturer, is_active) VALUES (?, ?, ?, ?, ?, ?)";
  private ViewHandler viewHandler;
  private String serialNo;
  @FXML private TextField serialNoText = new TextField();
  @FXML private ComboBox typeCombo = new ComboBox<String>();
  @FXML private ComboBox roofCombo = new ComboBox<String>();
  @FXML private ComboBox manufacturer = new ComboBox<String>();
  @FXML private DatePicker installationDatePicker = new DatePicker();
  @FXML private Button save;
  @FXML private Button back;

  public void init(ViewHandler viewHandler)
  {
    this.viewHandler = viewHandler;
    serialNoText.setText("");
    this.typeCombo.getItems().addAll("PV", "TC");
    for (int i = 0; i < 220; i++)
    { String position = "" + i;
      this.roofCombo.getItems().add(position); }
  }

  public void setSerialNo(String serialNo)
  {
    serialNoText.setText(serialNo);
    try (Connection connection = DatabaseConnection.getConnection())
    {
      Statement statement = connection.createStatement();
      String sqlQuery = "SELECT * FROM solar_panels.solarpanels WHERE solarpanels.serial_no = ";
      ResultSet resultSet = statement.executeQuery(sqlQuery + serialNo);
      while (resultSet.next()) {
        typeCombo.setValue(resultSet.getString("model_type"));
        roofCombo.setValue(resultSet.getString("roof_position"));
        installationDatePicker.setValue(LocalDate.of(resultSet.getDate("date_installed").getYear(),
            resultSet.getDate("date_installed").getMonth(),resultSet.getDate("date_installed").getDay()));
        manufacturer.setValue(resultSet.getString("manufacturer"));
      }
    }
    catch(SQLException e)
    {
      e.printStackTrace();
    }
  }
  public void onClick(ActionEvent event)
  {
    //Inserting information into database
    if(event.getSource() == save)
    {
      Connection connection = DatabaseConnection.getConnection();

      String manufacturer = "INSERT INTO solarpanels (name, address , email, phone) VALUES (?, ?, ?, ?)";
      String solarPanel = "INSERT INTO solarpanels (serial_no, model_type, roof_position, date_installed, manufacturer) VALUES (?, ?, ?, ?, SELECT id FROM manufacturer WHERE name = ?)";

    }
    else if(event.getSource() == back)
    {
      viewHandler.changeScene(viewHandler.SOLAR_PANELS);
      serialNo = null;
    }
  }
}
