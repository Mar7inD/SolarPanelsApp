package controller;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import model.SolarPanelProduction;
import javafx.animation.FadeTransition;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.time.LocalTime;

public class ShowDataController implements Initializable
{
  List<String> selectedModels;
  LocalDate startDate;
  double selectedPeriod;

  private ViewHandler viewHandler;
  @FXML Circle liveCircle;
  @FXML Label liveLabel;
  @FXML private Button backButton;
  @FXML private LineChart<String, Number> lineChart;
  public void init(ViewHandler viewHandler)
  {
    this.viewHandler = viewHandler;
  }

  public void setLiveParams(List<String> selectedValues)
  {
    this.selectedModels = selectedValues;
    //addChartData();
  }

  public void setParams(List<String> selectedValues, LocalDate selectedDate, double selectedPeriod)
  {
    this.selectedModels = selectedValues;
    this.startDate = selectedDate;
    this.selectedPeriod = selectedPeriod;
    //addChartData();
  }

  public void getProductionData()
  {
    // for pv
    ArrayList<Integer> positions = extractPositions("PV");
    XYChart.Series<String, Number> seriesPv = new XYChart.Series<>();
    try
    {
      ObservableList<SolarPanelProduction> solarPanelProductionPV =  DatabaseConnection.getProductionCapacity(positions, "pv_measurements");
      seriesPv.setName("PV");
      for (SolarPanelProduction spp : solarPanelProductionPV)
      {
        seriesPv.getData().add(new XYChart.Data<>(spp.getMeasurementDate().toString(), spp.getProductionValue()));
      }
    }
    catch (SQLException e)
    {
      throw new RuntimeException(e);
    }
    // for tc
    positions = extractPositions("TC");
    XYChart.Series<String, Number> seriesTc = new XYChart.Series<>();
    try
    {
      ObservableList<SolarPanelProduction> solarPanelProductionTC =  DatabaseConnection.getProductionCapacity(positions, "tc_measurements");
      seriesTc.setName("TC");
      for (SolarPanelProduction spp : solarPanelProductionTC)
      {
        seriesTc.getData().add(new XYChart.Data<>(spp.getMeasurementDate().toString(), spp.getProductionValue()));
      }
    }
    catch (SQLException e)
    {
      throw new RuntimeException(e);
    }
    lineChart.getData().add(seriesPv);
    lineChart.getData().add(seriesTc);
  }

  public ArrayList<Integer> extractPositions(String type)
  {
    ArrayList<Integer> positions = new ArrayList<>();
    for (String model : selectedModels)
    {
      String[] arrOfStr = model.split("_");
      if (arrOfStr[0] == type)
      {
        positions.add(Integer.parseInt(arrOfStr[1]));
      }
    }
    return positions;
  }

  public void displayLiveAnimation()
  {
    liveLabel.setVisible(true);
    liveCircle.setVisible(true);
    FadeTransition fadeTransition = new FadeTransition(Duration.seconds(2), liveCircle);
    fadeTransition.setFromValue(1.0);
    fadeTransition.setToValue(0.0);
    fadeTransition.setAutoReverse(true);
    fadeTransition.setCycleCount(FadeTransition.INDEFINITE);

    fadeTransition.play();
  }

  public void getLiveProductionData()
  {

  }

  @Override public void initialize(URL location, ResourceBundle resources)
  {
    Platform.runLater(() -> {
      if (this.startDate == null)
      {
        System.out.println("IN STARTDATE NULL");
        displayLiveAnimation();
        getLiveProductionData();
      }
      else
      {
        System.out.println("IN STARTDATE DEFINED");
        getProductionData();
      }
    });
  }


  public void onClick(ActionEvent event)
  {
    if (event.getSource() == backButton)
    {
      viewHandler.changeScene(viewHandler.CHOOSE_PRODUCTION_PARAMETERS);
    }
  }
}
