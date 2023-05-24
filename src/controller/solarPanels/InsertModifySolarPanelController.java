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
  @FXML private ComboBox<String> manufacturer = new ComboBox<String>();
  @FXML private DatePicker installationDatePicker = new DatePicker();
  @FXML private Button save;
  @FXML private Button back;

  public void init(ViewHandler viewHandler)
  {
    this.viewHandler = viewHandler;
    serialNoText.setText("");
    this.typeCombo.getItems().addAll("PV", "TC");
    for (int i = 1; i < 124; i++)
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
        installationDatePicker.setValue(Date.valueOf(
            resultSet.getDate("date_installed").toLocalDate()).toLocalDate());
        manufacturer.setValue(resultSet.getString("manufacturer"));
      }
    }
    catch(SQLException e)
    {
      e.printStackTrace();
    }
    DatabaseConnection.closeConnection();
  }
  public void onClick(ActionEvent event)
  {
    //Inserting information into database
    if(event.getSource() == save)
    {
      try (Connection connection = DatabaseConnection.getConnection())
      {
        Statement statement = connection.createStatement();
        String sqlQuery = "SELECT * FROM solar_panels.solarpanels WHERE solarpanels.serial_no = ";
        ResultSet resultSet = statement.executeQuery(sqlQuery + serialNo);

        if (resultSet.next())
        {
         String updateQuery = "UPDATE solar_panels.solarpanels SET model_type = ?, roof_position = ?, date_installed = ?, manufacturer = ?";
          PreparedStatement statementUpdate = connection.prepareStatement(updateQuery);
          statementUpdate.setString(1, typeCombo.getValue().toString());
          statementUpdate.setString(2,roofCombo.getValue().toString());
          statementUpdate.setDate(3,Date.valueOf(installationDatePicker.getValue()));
          statementUpdate.setString(4,manufacturer.getValue());
          statementUpdate.executeUpdate();
        }

        else
        {
          String insertQuery = "INSERT INTO solar_panels.solarpanels (serial_no, model_type, roof_position, date_installed, manufacturer) "
              + "VALUES (?, ?, ?, ?, ?)";
          PreparedStatement statementInsert = connection.prepareStatement(insertQuery);
          statementInsert.setInt(1, Integer.parseInt(serialNoText.getText()));
          statementInsert.setString(2, typeCombo.getValue().toString());
          statementInsert.setString(3,roofCombo.getValue().toString());
          statementInsert.setDate(4,Date.valueOf(installationDatePicker.getValue()));
          statementInsert.setString(5,manufacturer.getValue());
          statementInsert.executeUpdate();
          System.out.println("Insert success");
        }
      }
      catch(SQLException e)
      {
        e.printStackTrace();
      }
      DatabaseConnection.closeConnection();
      viewHandler.changeScene(viewHandler.SOLAR_PANELS);
    }
    // Getting back to solar panels
    else if(event.getSource() == back)
    {
      viewHandler.changeScene(viewHandler.SOLAR_PANELS);
      resetFields();
    }
  }

  public void resetFields()
  {
    serialNoText.setText("");
    typeCombo.setValue("1");
    roofCombo.setValue("1");
    installationDatePicker.setValue(LocalDate.now());
    manufacturer.setValue("");
  }
}
