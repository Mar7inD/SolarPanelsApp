package controller;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

import javafx.fxml.FXML;

import javafx.event.ActionEvent;

public class SolarPanelsSceneController
{
  @FXML private Button back;
  @FXML private Button cancel;
  @FXML private ComboBox cSituation;
  @FXML private ComboBox infrastructure;

  private ViewHandler viewHandler;

  public void init(ViewHandler viewHandler)
  {
    this.viewHandler = viewHandler;
  }

  public void onClick(ActionEvent event)
  {
    if (event.getSource() == back)
    {
      viewHandler.changeScene(viewHandler.MAIN_SCENE);
    }
  }
}
