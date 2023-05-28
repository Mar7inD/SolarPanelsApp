package controller.production;
import controller.ViewHandler;
import model.SolarPanel;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.fxml.FXML;

import java.net.URL;

import javafx.event.ActionEvent;
import javafx.scene.text.Text;
import javafx.util.StringConverter;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
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
  @FXML private Text errorText;
  @FXML private ListView<String> modelList;
  @FXML private ListView<String> chosenList;
  private static ViewHandler viewHandler;
  private Scene showData;
  private ShowDataController showDataController;
  private ObservableList<SolarPanel> solarPanels = FXCollections.observableArrayList();
  private ArrayList<String> solarPanelsId = new ArrayList<>();
  public void init(ViewHandler viewHandler) throws SQLException
  {
    this.viewHandler = viewHandler;
  }

  @Override public void initialize(URL location, ResourceBundle resources)
  {
    // Set a custom StringConverter to display integer values
    period.setLabelFormatter(new StringConverter<Double>() {
      @Override
      public String toString(Double value) {
        return String.valueOf(value.intValue());
      }
      @Override
      public Double fromString(String string) {
        throw new UnsupportedOperationException("Not supported");
      }

    });
    liveData.setOnAction(event -> {
      boolean selected = liveData.isSelected();
      period.setDisable(selected);
      startDate.setDisable(selected);
    });
    // Add a listener to the Slider value property
    period.valueProperty().addListener((observable, oldValue, newValue) -> {
      // Update the text of the Label with the new value
      daysNum.setText(String.valueOf(newValue.intValue()));
    });

    Platform.runLater(() -> {
      try
      {
        this.solarPanels = getSolarPanels();
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
      String serial_no = sp.getSerialNo();
      String active = sp.getActivity();
      solarPanelsIds.add(panelType + '_' + position + '_' + serial_no + '_' + active);
    }
    return solarPanelsIds;
  }

  public void onClick(ActionEvent event)
  {
    if (event.getSource() == backButton)
    {
      viewHandler.changeScene(viewHandler.MAIN_SCENE);
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
      if (chosenItem != null)
      {
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
        this.solarPanels = getSolarPanels();
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
    else if (event.getSource() == showButton)
    {
      if (!chosenList.getItems().isEmpty())
      {
        if (liveData.isSelected())
        {
          // LIVE DATA
          List<String> selectedValues = chosenList.getItems();
          showDataController = viewHandler.getShowDataController();
          showDataController.setLiveParams(selectedValues);
          viewHandler.changeScene(viewHandler.SHOW_DATA);
        }

        else if (startDate.getValue() != null)
        {
          // TIME SET
          List<String> selectedValues = chosenList.getItems();
          LocalDate selectedDate = startDate.getValue();
          double selectedPeriod = period.getValue();
          showDataController = viewHandler.getShowDataController();
          showDataController.setParams(selectedValues, selectedDate,
              selectedPeriod);
          viewHandler.changeScene(viewHandler.SHOW_DATA);
        }
        else
        {
          errorText.setText("Choose the parameters!");
        }
      }
      else
      {
        errorText.setText("Choose the parameters!");
      }
    }
  }

  public ObservableList<SolarPanel> getSolarPanels()
      throws SQLException
  {
    ObservableList<SolarPanel> solarPanels = FXCollections.observableArrayList();
    try
    {
      Connection connection = viewHandler.getConnection();
      Statement statement = connection.createStatement();
      String sqlQuery = "SELECT * FROM solar_panels.solar_panels";
      ResultSet resultSet = statement.executeQuery(sqlQuery);
      while (resultSet.next()) {
        solarPanels.add(new SolarPanel(resultSet.getString("serial_no"),
            resultSet.getString("model_type"),resultSet.getString("roof_position"),resultSet.getDate("date_installed"),resultSet.getString("manufacturer"),resultSet.getBoolean("is_active")));
      }
    }
    catch(SQLException e)
    {
      e.printStackTrace();
    }
    return solarPanels;
  }

}