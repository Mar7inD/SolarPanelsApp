package controller.solarPanels;

import controller.ViewHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class InsertModifySolarPanelController
{
  private ViewHandler viewHandler;
  private boolean modifying;
  @FXML private TextField serialNoText = new TextField();
  @FXML private ComboBox<String> typeCombo = new ComboBox<>();
  @FXML private ComboBox<String> roofCombo = new ComboBox<>();
  @FXML private ComboBox<String> manufacturer = new ComboBox<>();
  @FXML private DatePicker installationDatePicker = new DatePicker();
  @FXML private ComboBox<String> activity = new ComboBox<>();
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

    resetFields();
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
      { checkSerialNumber(serialNoText.getText());
        checkDuplicatesActivity(Integer.parseInt(roofCombo.getValue()),activity.getValue());
        if(modifying)
        {
          modifying = false;
          String updateQuery = "UPDATE solar_panels.solar_panels SET model_type = ?, roof_position = ?, date_installed = ?, manufacturer = ?, is_active = ? WHERE serial_no = ?;";
          PreparedStatement statementUpdate = viewHandler.getConnection().prepareStatement(
              updateQuery);
          statementUpdate.setString(1, typeCombo.getValue());
          statementUpdate.setInt(2, Integer.parseInt(roofCombo.getValue()));
          statementUpdate.setDate(3, Date.valueOf(installationDatePicker.getValue()));
          statementUpdate.setString(4, manufacturer.getValue());
          statementUpdate.setBoolean(5, activity.getValue().equals("Active"));
          statementUpdate.setInt(6, Integer.parseInt(serialNoText.getText()));
          statementUpdate.executeUpdate();
        }

        else
        {
          String insertQuery = "INSERT INTO solar_panels.solar_panels (serial_no, model_type, roof_position, date_installed, manufacturer, is_active) VALUES (?, ?, ?, ?, ?, ?);";

          PreparedStatement statementInsert = viewHandler.getConnection().prepareStatement(insertQuery);

          statementInsert.setInt(1, Integer.parseInt(serialNoText.getText()));

          statementInsert.setString(2, typeCombo.getValue());

          statementInsert.setInt(3,Integer.parseInt(roofCombo.getValue()));

          statementInsert.setDate(4,Date.valueOf(installationDatePicker.getValue()));

          statementInsert.setString(5,manufacturer.getValue());

          statementInsert.setBoolean(6,
              activity.getValue().equals("Active"));

          statementInsert.executeUpdate();

          System.out.println("Insert success");
        }
      }
      catch(SQLException e)
      {
        e.printStackTrace();
      }
      viewHandler.getSolarPanelsSceneController().refreshTableView();
      viewHandler.changeScene(ViewHandler.SOLAR_PANELS);
    }
    // Getting back to solar panels
    else if(event.getSource() == back)
    {
      viewHandler.changeScene(ViewHandler.SOLAR_PANELS);
    }
  }

  // Class resetFields for resetting fields when starting to insert new solar panel
  public void resetFields()
  {
    serialNoText.setText("");
    typeCombo.setValue("PV");
    roofCombo.setValue("1");
    installationDatePicker.setValue(LocalDate.now());
    activity.getItems().clear();
    activity.getItems().addAll("Active","Deactivated");
    activity.setValue("Active");
    manufacturer.getItems().clear();

    try
    {
      ArrayList<String> values = new ArrayList<>();
      Statement statement = viewHandler.getConnection().createStatement();
      String sqlQuery = "SELECT name FROM solar_panels.manufacturer;";
      ResultSet resultSet = statement.executeQuery(sqlQuery);

      while (resultSet.next())
      {
        values.add(resultSet.getString("name"));
      }

      manufacturer.getItems().addAll(values);
      manufacturer.setValue(values.get(0));
    }

    catch(SQLException e)
    {
      e.printStackTrace();
    }
  }

  //Class to determine if we are modifying or inserting the solar panel
  public void setModifying(boolean modifying)
  {
    this.modifying = modifying;
  }

  public void checkSerialNumber(String serialNo)
  {
    if(serialNo.length() != 6)
    {
      // Creating and configuring the alert
      Alert alert = new Alert(Alert.AlertType.ERROR);
      alert.setTitle("Input Error");
      alert.setHeaderText("Incorrect Value");
      alert.setContentText("The input value cannot be negative.");

      // Display the alert
      alert.showAndWait();
    }
  }
  public void checkDuplicatesActivity(int roofPosition, String activity)
  {
    Connection connection = viewHandler.getConnection();
    try
    {
      if(activity.equals("Active"))
      {
        String Query = "UPDATE solar_panels.solar_panels SET is_active = FALSE WHERE roof_position = ?;";
        PreparedStatement statement = connection.prepareStatement(Query);
        statement.setInt(1, roofPosition);
        statement.executeUpdate();
      }
    }
    catch(SQLException e)
    {
      e.printStackTrace();
    }
  }
}
