package controller;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import javafx.fxml.FXML;

import javafx.event.ActionEvent;


public class SolarPanelsSceneController
{
  @FXML private Button back;
  @FXML private Button cancel;
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
    if (event.getSource() == back)
    {
      viewHandler.changeScene(ViewHandler.MAIN_SCENE);
    }
  }
}
