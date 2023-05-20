package controller;
import javafx.scene.control.*;

import javafx.fxml.FXML;

import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;

public class ProductionChooseParametersController
{
  @FXML private Button backButton;
  @FXML private Button showButton;
  @FXML private Button chooseButton;
  @FXML private Button removeButton;
  @FXML private CheckBox liveData;
  @FXML private Slider period;
  @FXML private DatePicker startDate;
  @FXML private ListView modelList;
  @FXML private ListView chosenList;
  @FXML private DatePicker datePicker;
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
