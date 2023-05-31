package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.sql.SQLException;

public class MainSceneController
{
  private ViewHandler viewHandler;
  @FXML private Button solarPanel;
  @FXML private Button faultsAndMaintenance;
  @FXML private Button productionScene;
  @FXML private Button manufacturersInformation;

  @FXML  private Button exit;

  public void init(ViewHandler viewHandler)
  {
    this.viewHandler = viewHandler;
  }

  public void onClick(ActionEvent event) throws SQLException
  {
    if(event.getSource() == solarPanel)
    {
      viewHandler.changeScene(ViewHandler.SOLAR_PANELS);
    }
    else if(event.getSource() == productionScene)
    {
      viewHandler.changeScene(ViewHandler.CHOOSE_PRODUCTION_PARAMETERS);
    }
    else if(event.getSource() == manufacturersInformation)
    {
      viewHandler.changeScene(ViewHandler.MANUFACTURER_INFORMATION);
    }
    else if(event.getSource() == faultsAndMaintenance)
    {
      viewHandler.changeScene(ViewHandler.FAULTS_AND_MAINTENANCE);
    }
    else if(event.getSource() == exit)
    {
      viewHandler.getConnection().close();
      System.out.println("Connection closed.");
      System.exit(1);
    }

  }

}
