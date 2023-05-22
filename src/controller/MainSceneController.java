package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class MainSceneController
{
  private ViewHandler viewHandler;
  @FXML private Button solarPanel;
<<<<<<< HEAD
  @FXML private Button productionScene;
=======
  @FXML private Button manufacturersInformation;
  @FXML private Button productionChooseParameters;
>>>>>>> 5b0533206032708d37704ebfeecb52d318a395bc
  @FXML private Button exit;

  public void init(ViewHandler viewHandler)
  {
    this.viewHandler = viewHandler;
  }

  public void onClick(ActionEvent event)
  {
    if(event.getSource() == solarPanel)
    {
      viewHandler.changeScene(ViewHandler.SOLAR_PANELS);
    }
<<<<<<< HEAD
    else if(event.getSource() == productionScene)
    {
      viewHandler.changeScene(ViewHandler.CHOOSE_PRODUCTION_PARAMETERS);
    }
=======
    else if(event.getSource() == productionChooseParameters)
    {
      viewHandler.changeScene(ViewHandler.CHOOSE_PRODUCTION_PARAMETERS);
    }
    else if(event.getSource() == manufacturersInformation)
    {
      viewHandler.changeScene(ViewHandler.MANUFACTURER_INFORMATION);
    }
>>>>>>> 5b0533206032708d37704ebfeecb52d318a395bc
    else if(event.getSource() == exit)
    {
      System.exit(1);
    }

  }

}
