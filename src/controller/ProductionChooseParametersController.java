package controller;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.fxml.FXML;

import java.net.URL;

import javafx.event.ActionEvent;
import javafx.scene.text.Text;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ProductionChooseParametersController implements Initializable
{
  @FXML private Button backButton;
  @FXML private Button showButton;
  @FXML private Button chooseButton;
  @FXML private Button removeButton;
  @FXML private Button refreshButton;
  @FXML private CheckBox liveData;
  @FXML private Slider period;
  @FXML private DatePicker startDate;
  @FXML private Text daysNum;
  @FXML private ListView<String> modelList;
  @FXML private ListView<String> chosenList;
  private ViewHandler viewHandler;
  private ObservableList<SolarPanel> solarPanels = FXCollections.observableArrayList();
  private ArrayList<String> solarPanelsId = new ArrayList<>();
  public void init(ViewHandler viewHandler) throws SQLException
  {
    this.viewHandler = viewHandler;
  }

  @Override public void initialize(URL location, ResourceBundle resources)
  {
    liveData.setOnAction(event -> {
      boolean selected = liveData.isSelected();
      period.setDisable(selected);
      startDate.setDisable(selected);
    });
    period.valueProperty().addListener((observable, oldValue, newValue) -> {
      // Update the text of the Label with the new value
      daysNum.setText(String.valueOf(newValue));
    });    Platform.runLater(() -> {
      try
      {
        this.solarPanels = DatabaseConnection.getSolarPanels();
      }
      catch (SQLException e)
      {
        throw new RuntimeException(e);
      }
      this.solarPanelsId = identifySolarPanels();
      modelList.getItems().addAll(this.solarPanelsId);

    });
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
      String chosenItem = modelList.getSelectionModel().getSelectedItem();
      if (chosenItem != null)
      {
        modelList.getItems().remove(chosenItem);
        chosenList.getItems().add(chosenItem);
      }
    }
    else if (event.getSource() == removeButton)
    {
      String chosenItem = chosenList.getSelectionModel().getSelectedItem();
      if (chosenItem != null) {
      chosenList.getItems().remove(chosenItem);
      modelList.getItems().add(chosenItem);
      }
    }
    else if (event.getSource() == refreshButton)
    {
      chosenList.getItems().clear();
      modelList.getItems().clear();

      try
      {
        this.solarPanels = DatabaseConnection.getSolarPanels();
      }
      catch (SQLException e)
      {
        throw new RuntimeException(e);
      }
      this.solarPanelsId = identifySolarPanels();
      modelList.getItems().addAll(this.solarPanelsId);
    }
    else if (event.getSource() == liveData)
    {
      if (liveData.isSelected())
      {
        startDate.setDisable(true);
        period.setDisable(true);
      }
      else
      {
        startDate.setDisable(false);
        period.setDisable(false);
      }
    }
  }

}
