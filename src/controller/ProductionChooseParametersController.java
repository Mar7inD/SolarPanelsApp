package controller;
import controller.solarPanels.SolarPanel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import controller.DatabaseConnection;
import controller.solarPanels.SolarPanelsSceneController;
import javafx.fxml.FXML;
import java.sql.Connection;

import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;

public class ProductionChooseParametersController
{
  @FXML private Button backButton;
  @FXML private Button showButton;
  @FXML private Button chooseButton;
  @FXML private Button removeButton;
  @FXML private CheckBox liveData;
  @FXML private Slider period;
  @FXML private DatePicker startDate;
  @FXML private ListView<String> modelList;
  @FXML private ListView<String> chosenList;
  @FXML private DatePicker datePicker;
  private ViewHandler viewHandler;
  private static Connection connection;
  private SolarPanelsSceneController solarPanelsController;
  private ObservableList<SolarPanel> solarPanels = FXCollections.observableArrayList();
  public void init(ViewHandler viewHandler)
  {

    this.solarPanels = solarPanelsController.getSolarPanels();
    this.viewHandler = viewHandler;
  }

  public ArrayList<String> identifySolarPanels()
  {
    ArrayList<String> solarPanelsIds = new ArrayList<>();
    for (SolarPanel sp : this.solarPanels)
    {
      String panelType = sp.getPanelType();
      String position = sp.getRoofPosition();
      solarPanelsIds.add(panelType + '_' + position);
    }
    return solarPanelsIds;
  }
  public void onClick(ActionEvent event)
  {
    if (event.getSource() == backButton)
    {
      viewHandler.changeScene(viewHandler.MAIN_SCENE);
    }
    else if (event.getSource() == showButton)
    {
      viewHandler.changeScene(viewHandler.SHOW_DATA);
    }
    else if (event.getSource() == chooseButton)
    {

    }
    else if (event.getSource() == removeButton)
    {

    }
  }

}
