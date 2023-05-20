package controller;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import javafx.fxml.FXML;

import javafx.event.ActionEvent;


public class SolarPanelsSceneController
{
  @FXML private Button insert;
  @FXML private Button back;
  @FXML private Button modify;
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
  }

  public void onClick(ActionEvent event)
  {
    if (event.getSource() == modify)
    {
      viewHandler.changeScene(ViewHandler.MODIFY_SOLAR_PANEL);
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
