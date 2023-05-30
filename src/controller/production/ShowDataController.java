package controller.production;

import controller.ViewHandler;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import model.SolarPanelProduction;
import javafx.animation.FadeTransition;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class ShowDataController
{
  List<String> selectedModels;
  LocalDate startDate;
  double selectedPeriod;

  private ViewHandler viewHandler;
  @FXML private Circle liveCircle;
  @FXML private Label liveLabel;
  @FXML private Button backButton;
  @FXML private RadioButton radioButton;
  @FXML private LineChart<String, Number> lineChart;
  private XYChart.Series<String, Number> seriesTc = new XYChart.Series<>();
  private XYChart.Series<String, Number> seriesPv = new XYChart.Series<>();
  private XYChart.Series<String, Number> seriesLiveTc = new XYChart.Series<>();
  private XYChart.Series<String, Number> seriesLivePv = new XYChart.Series<>();
  private XYChart.Series<String, Number> seriesWTc = new XYChart.Series<>();
  private XYChart.Series<String, Number> seriesWPv = new XYChart.Series<>();
  private DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
  private Timeline timeline;
  private static final int REFRESH_INTERVAL_SECONDS = 10;
  private static final int NUMBER_OF_SAMPLES = 10;
  private int lowerBoundry;
  private int upperBoundry;
  public void init(ViewHandler viewHandler)
  {
    this.viewHandler = viewHandler;
  }

    public void setLiveParams(List<String> selectedValues)
  {
    this.lineChart.setTitle("Production Efficiency %");
    radioButton.setVisible(false);
    lineChart.getData().clear();
    displayLiveAnimation();
    lineChart.getData().clear();
    this.selectedModels = selectedValues;
    getLiveProductionData();
  }

  public void setParams(List<String> selectedValues, LocalDate selectedDate, double selectedPeriod)
  {
    if (timeline!=null)
    {
      timeline.stop();
    }
    this.lineChart.setTitle("Production Efficiency %");
    radioButton.setSelected(false);
    radioButton.setVisible(true);
    liveLabel.setVisible(false);
    liveCircle.setVisible(false);
    lineChart.getData().clear();
    this.selectedModels = selectedValues;
    this.startDate = selectedDate;
    this.selectedPeriod = selectedPeriod;
    getProductionData();
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

  public XYChart.Series<String, Number> updateChart(XYChart.Series<String, Number> series, String type)
  {
    LocalDateTime currentTime = LocalDateTime.now();
    if (type == "pv")
    {
      this.lowerBoundry = 75;
      this.upperBoundry = 85;
    }
    else if (type == "tc")
    {
      this.lowerBoundry = 80;
      this.upperBoundry = 90;
    }

    Random random = new Random();
    if (series.getData().isEmpty())
    {

      for (int i = 0; i < NUMBER_OF_SAMPLES; i++)
      {
        int randomValue = random.nextInt(this.upperBoundry - this.lowerBoundry + 1) + lowerBoundry;
        LocalDateTime newTime = currentTime.minusSeconds(REFRESH_INTERVAL_SECONDS * (NUMBER_OF_SAMPLES - i));
        String formattedNewTime = newTime.format(timeFormatter);
        series.getData().add(new XYChart.Data<>(formattedNewTime, randomValue));
      }

    } else {
      int randomValue = random.nextInt(this.upperBoundry - this.lowerBoundry + 1) + lowerBoundry;
      String formattedCurrentTime = currentTime.format(timeFormatter);

      series.getData().remove(0);
      series.getData().add(series.getData().size(), new XYChart.Data<>(formattedCurrentTime, randomValue));
    }
    series.setName(type);
    return series;

  }
  public void getLiveProductionData()
  {
    this.seriesLivePv = updateChart(seriesLivePv, "pv");
    this.seriesLiveTc = updateChart(seriesLiveTc, "tc");
    this.lineChart.getData().clear();
    this.lineChart.getData().addAll(this.seriesLivePv, this.seriesLiveTc);
    timeline = new Timeline(new KeyFrame(Duration.seconds(REFRESH_INTERVAL_SECONDS), event -> {
      this.seriesLivePv = updateChart(seriesLivePv, "pv");
      this.seriesLiveTc = updateChart(seriesLiveTc, "tc");
      this.lineChart.getData().clear();
      this.lineChart.getData().addAll(this.seriesLivePv, this.seriesLiveTc);
    }));
    timeline.setCycleCount(Timeline.INDEFINITE);
    timeline.play();
  }

  public void onClick(ActionEvent event)
  {
    if (event.getSource() == backButton)
    {
      viewHandler.changeScene(viewHandler.CHOOSE_PRODUCTION_PARAMETERS);
    }
    if (event.getSource() == radioButton)
    {
      if (radioButton.isSelected())
      {
        this.lineChart.setTitle("Production in W/kW");
        this.lineChart.getData().clear();
        this.lineChart.getData().addAll(this.seriesWPv, this.seriesWTc);
        this.lineChart.setCreateSymbols(false); //hide dots
      } else {
        this.lineChart.setTitle("Production Efficiency %");
        this.lineChart.getData().clear();
        this.lineChart.getData().addAll(this.seriesPv, this.seriesTc);
        this.lineChart.setCreateSymbols(false); //hide dots
      }

    }
  }


  public void getProductionData()
  {
    this.seriesPv = new XYChart.Series<>();
    this.seriesTc = new XYChart.Series<>();

    this.seriesWPv = new XYChart.Series<>();
    this.seriesWTc = new XYChart.Series<>();

    try
    {
      ObservableList<SolarPanelProduction> solarPanelProduction =  getProductionCapacity();
      seriesPv.setName("PV [eff %]");
      seriesTc.setName("TC [eff %]");
      seriesWPv.setName("PV [W]");
      seriesWTc.setName("TC [kW]");

      for (SolarPanelProduction spp : solarPanelProduction)
      {
        String type = spp.getPanelType();
        if (type.equals("TC"))
        {
          seriesTc.getData().add(
              new XYChart.Data<>(spp.getMeasurementDate().toString(), spp.getProductionValuePerc()));
          seriesWTc.getData().add(
              new XYChart.Data<>(spp.getMeasurementDate().toString(), spp.getProductionValueWatt()));
        } else if (type.equals("PV")) {
          seriesPv.getData().add(
              new XYChart.Data<>(spp.getMeasurementDate().toString(), spp.getProductionValuePerc()));
          seriesWPv.getData().add(
              new XYChart.Data<>(spp.getMeasurementDate().toString(), spp.getProductionValueWatt()));
        }
      }
    }
    catch (SQLException e)
    {
      throw new RuntimeException(e);
    }

    lineChart.getData().add(seriesPv);
    lineChart.getData().add(seriesTc);
    lineChart.setCreateSymbols(false); //hide dots
  }


  public ArrayList<Integer> extractSerialNo()
  {
    ArrayList<Integer> serialNumbers = new ArrayList<>();
    for (String model : selectedModels)
    {
      String[] arrOfStr = model.split("_");
      serialNumbers.add(Integer.parseInt(arrOfStr[2]));

    }
    return serialNumbers;
  }

  public ObservableList<SolarPanelProduction> getProductionCapacity()
      throws SQLException
  {
    ArrayList<Integer> serialNo = extractSerialNo();
    ObservableList<SolarPanelProduction> prod = FXCollections.observableArrayList();
    if (serialNo.isEmpty())
    {
      return prod;
    }
    try
    {
      Connection connection = viewHandler.getConnection();
      Statement statement = connection.createStatement();
      String sqlQuery = "SELECT measurement_date, sp.model_type, AVG(efficiency_perc) as average_eff, SUM(production_w_kw) as sum_watt"
          + " FROM solar_panels.production p left join"
          + " solar_panels.solar_panels sp on p.model_serial_no = sp.serial_no"
          + " WHERE p.model_serial_no in (";
      sqlQuery = buildSQLQuery(serialNo, sqlQuery);
      sqlQuery = sqlQuery + "and measurement_date BETWEEN '"+ startDate.toString() + "' and '"
          + (startDate.plusDays((int) Math.floor(selectedPeriod))).toString()
          + "' GROUP BY sp.model_type, measurement_date"
          + " ORDER BY measurement_date ASC;";
      ResultSet resultSet = statement.executeQuery(sqlQuery);
      while (resultSet.next()) {
        prod.add(new SolarPanelProduction(resultSet.getTimestamp("measurement_date"),
            resultSet.getString("model_type"), resultSet.getInt("average_eff"), resultSet.getFloat("sum_watt")));
      }
    }
    catch(SQLException e)
    {
      e.printStackTrace();
    }
    return prod;
  }

  public static String buildSQLQuery(ArrayList<Integer> positions, String sqlQuery)
  {
    StringBuilder sb = new StringBuilder();
    sb.append(sqlQuery);

    for (int i = 0; i < positions.size(); i++) {
      sb.append(positions.get(i));
      if (i != positions.size() - 1) {
        sb.append(",");
      }
    }

    sb.append(")");

    return sb.toString();
  }
}
