package controller.solarPanels;

import controller.ViewHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.sql.*;
import java.time.LocalDate;
import java.util.HashSet;

public class InsertModifySolarPanelController
{
  private final String insertManufacturerSql = "INSERT INTO Manufacturer (name, address, email, phone_number) " +
      "VALUES (?, ?, ?, ?)";
  private final String insertSolarPanelSql = "INSERT INTO SolarPanels (serial_No, model_type, roof_position, " +
      "date_installed, manufacturer, is_active) VALUES (?, ?, ?, ?, ?, ?)";
  private ViewHandler viewHandler;
  private boolean modifying;
  @FXML private TextField serialNoText = new TextField();
  @FXML private ComboBox typeCombo = new ComboBox<String>();
  @FXML private ComboBox roofCombo = new ComboBox<String>();
  @FXML private ComboBox manufacturer = new ComboBox<String>();
  @FXML private DatePicker installationDatePicker = new DatePicker();
  @FXML private ComboBox activity = new ComboBox<String>();
  @FXML private Button save;
  @FXML private Button back;


  // Class init for initializing the Scene and items
  public void init(ViewHandler viewHandler)
  {
    this.viewHandler = viewHandler;
    serialNoText.setText("");

    this.typeCombo.getItems().addAll("PV", "TC");
    for (int i = 1; i < 124; i++)
    { String position = "" + i;
      this.roofCombo.getItems().add(position); }

    this.activity.getItems().addAll("Active","Deactivated");

    try
    {
      Statement statement = viewHandler.getConnection().createStatement();
      String sqlQuery = "SELECT manufacturer FROM solar_panels.solar_panels";
      ResultSet resultSet = statement.executeQuery(sqlQuery);

      HashSet manufactorHash = new HashSet<String>();

      while (resultSet.next())
      {
          manufactorHash.add(resultSet.getString("manufacturer"));
      }
      manufacturer.getItems().addAll(manufactorHash);
    }

    catch(SQLException e)
    {
      e.printStackTrace();
    }
  }

  // Class setSerialNo for receiving values from the database based on given serial number
  public void setSerialNo(String serialNo)
  {
    serialNoText.setText(serialNo);
    try
    {
      Statement statement = viewHandler.getConnection().createStatement();
      String sqlQuery = "SELECT * FROM solar_panels.solar_panels WHERE solar_panels.serial_no = ";
      ResultSet resultSet = statement.executeQuery(sqlQuery + serialNo);
      while (resultSet.next()) {

        typeCombo.setValue(resultSet.getString("model_type"));

        roofCombo.setValue(resultSet.getString("roof_position"));

        installationDatePicker.setValue(Date.valueOf(
            resultSet.getDate("date_installed").toLocalDate()).toLocalDate());

        manufacturer.setValue(resultSet.getString("manufacturer"));

        if(resultSet.getBoolean("is_active"))
        {activity.setValue("Active");}
        else
        {activity.setValue("Deactivated");}
      }
    }

    catch(SQLException e)
    {
      e.printStackTrace();
    }
  }

  // Class onClick for the save and back button
  public void onClick(ActionEvent event)
  {
    //Inserting information into database
    if(event.getSource() == save)
    {
      try
      {
        if(modifying)
        {
          modifying = false;
          String updateQuery = "UPDATE solar_panels.solar_panels SET model_type = ?, roof_position = ?, date_installed = ?, manufacturer = ?, is_active = ?;";
          PreparedStatement statementUpdate = viewHandler.getConnection().prepareStatement(
              updateQuery);
          statementUpdate.setString(1, typeCombo.getValue().toString());
          statementUpdate.setInt(2, Integer.parseInt(roofCombo.getValue().toString()));
          statementUpdate.setDate(3, Date.valueOf(installationDatePicker.getValue()));
          statementUpdate.setString(4, manufacturer.getValue().toString());
          if (activity.getValue().toString() == "Active")
          {
            statementUpdate.setBoolean(5, true);
          }
          else
          {
            statementUpdate.setBoolean(5, false);
          }
          statementUpdate.executeUpdate();
        }

        else
        {
          String insertQuery = "INSERT INTO solar_panels.solar_panels (serial_no, model_type, roof_position, date_installed, manufacturer, is_active) VALUES (?, ?, ?, ?, ?, ?);";

          PreparedStatement statementInsert = viewHandler.getConnection().prepareStatement(insertQuery);

          statementInsert.setInt(1, Integer.parseInt(serialNoText.getText()));

          statementInsert.setString(2, typeCombo.getValue().toString());

          statementInsert.setInt(3,Integer.parseInt(roofCombo.getValue().toString()));

          statementInsert.setDate(4,Date.valueOf(installationDatePicker.getValue()));

          statementInsert.setString(5,manufacturer.getValue().toString());

          if(activity.getValue().toString() == "Active")
          {statementInsert.setBoolean(6,true);}
          else
          {statementInsert.setBoolean(6,false);}

          statementInsert.executeUpdate();

          System.out.println("Insert success");
        }
      }
      catch(SQLException e)
      {
        e.printStackTrace();
      }
      viewHandler.changeScene(viewHandler.SOLAR_PANELS);
    }
    // Getting back to solar panels
    else if(event.getSource() == back)
    {
      viewHandler.changeScene(viewHandler.SOLAR_PANELS);
      resetFields();
    }
  }

  // Class resetFields for resetting fields when starting to insert new solar panel
  public void resetFields()
  {
    serialNoText.setText("");
    typeCombo.setValue("");
    roofCombo.setValue("");
    installationDatePicker.setValue(LocalDate.now());
    manufacturer.setValue("");
  }

  //Class to determine if we are modifying or inserting the solar panel
  public void setModifying(boolean modifying)
  {
    this.modifying = modifying;
  }
}
