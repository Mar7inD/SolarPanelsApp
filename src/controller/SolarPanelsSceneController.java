package controller;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import javafx.fxml.FXML;

import javafx.event.ActionEvent;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SolarPanelsSceneController
{
  @FXML private Button insert;
  @FXML private Button back;
  @FXML private Button insertModifySolarPanel;
  @FXML private TableView<String> solarPanelsTable;
  @FXML private TableColumn<String, Integer> serialNo;
  @FXML private TableColumn<String, String> panelType;
  @FXML private TableColumn<String, String> roofPosition;
  @FXML private TableColumn<String, String> installationDate;
  @FXML private TableColumn<String, String> manufacturer;
  @FXML private TableColumn<String, String> activity;
  @FXML private ComboBox solarPanelPosition;

  private ViewHandler viewHandler;

  public void init(ViewHandler viewHandler)
  {
    this.viewHandler = viewHandler;
    try (Connection connection = DatabaseConnection.getConnection())
    {
      Statement statement = connection.createStatement();
      String sqlQuery = "SELECT * FROM solarpanel";
      ResultSet resultSet = statement.executeQuery(sqlQuery);
      while (resultSet.next()) {
        //serialNo = resultSet.getInt("serial_no");
        int column2Value = resultSet.getInt("column2");
        // Process the retrieved data
      }
    }
    catch(SQLException e)
    {
      e.printStackTrace();
    }
  }

  public void onClick(ActionEvent event)
  {
    if (event.getSource() == insertModifySolarPanel)
    {
      viewHandler.changeScene(ViewHandler.INSERT_MODIFY_SOLAR_PANEL);
    }
    else if (event.getSource() == back)
    {
      viewHandler.changeScene(ViewHandler.MAIN_SCENE);
    }
    else if (event.getSource() == insert)
    {
      viewHandler.changeScene(ViewHandler.INSERT_PAGE_CONTROLLER);
    }
  }
}
