package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;


import java.awt.*;

public class ManufacturerInformationController
{

  @FXML private Button backButton;
  @FXML private Button insertButton;
  private ViewHandler viewHandler;

  public void init(ViewHandler viewHandler)
  {
    this.viewHandler = viewHandler;
  }

  public void onClick(ActionEvent event)
  {
    if (event.getSource() == backButton)
    {
      viewHandler.changeScene(viewHandler.MAIN_SCENE);
    }
    if (event.getSource() == insertButton)
    {
      viewHandler.changeScene(viewHandler.INSERT_MANUFACTURER_PAGE);
    }
  }
}
