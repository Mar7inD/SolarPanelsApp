package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class ShowDataController
{
  private ViewHandler viewHandler;
  @FXML private Button backButton;
  public void init(ViewHandler viewHandler)
  {
    this.viewHandler = viewHandler;
  }
  public void onClick(ActionEvent event)
  {
    if (event.getSource() == backButton)
    {
      viewHandler.changeScene(viewHandler.CHOOSE_PRODUCTION_PARAMETERS);
    }
  }
}
