package controller.solarPanels;

import controller.ViewHandler;
import javafx.scene.control.*;

import javafx.fxml.FXML;

import javafx.event.ActionEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import model.SolarPanel;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SolarPanelsSceneController
{
  @FXML private Button back;
  @FXML private Button insertModifySolarPanel;
  @FXML private Button modify;
  @FXML private TableView solarPanelsTable = new TableView<SolarPanel>();
  @FXML private TableColumn serialNo = new TableColumn<SolarPanel, Integer>("Serial No");
  @FXML private TableColumn panelType = new TableColumn<SolarPanel, String>("Type");
  @FXML private TableColumn roofPosition = new TableColumn<SolarPanel, String>("Roof Position");
  @FXML private TableColumn installationDate = new TableColumn<SolarPanel, Date>("Installation Date");
  @FXML private TableColumn manufacturer = new TableColumn<SolarPanel, String>("Manufacturer");
  @FXML private TableColumn activity = new TableColumn<SolarPanel, String>("Activity");

  private SolarPanel selectedSolarPanel;
  private ViewHandler viewHandler;

  public void init(ViewHandler viewHandler)
  {
    this.viewHandler = viewHandler;

    // Initialize table columns
    serialNo.setCellValueFactory(new PropertyValueFactory<>("serialNo"));
    panelType.setCellValueFactory(new PropertyValueFactory<>("panelType"));
    roofPosition.setCellValueFactory(new PropertyValueFactory<>("roofPosition"));
    installationDate.setCellValueFactory(new PropertyValueFactory<>("installationDate"));
    manufacturer.setCellValueFactory(new PropertyValueFactory<>("manufacturer"));
    activity.setCellValueFactory(new PropertyValueFactory<>("activity"));

    List<SolarPanel> solarPanels = getSolarPanels();


      // Populate the TableView with the retrieved data
    solarPanelsTable.getColumns().addAll(serialNo,panelType,roofPosition,installationDate,manufacturer,activity);
    solarPanelsTable.getItems().addAll(solarPanels);

    // Disable the delete button initially
    modify.setDisable(true);

    // Add a listener to track row selection in the TableView
    solarPanelsTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
      if (newSelection != null) {
        // Enable the delete button when a row is selected
        modify.setDisable(false);
        selectedSolarPanel = (SolarPanel) newSelection; // Store the selected manufacturer
      } else {
        // Disable the delete button when no row is selected
        modify.setDisable(true);
        selectedSolarPanel = null; // Reset the selected manufacturer
      }
    });
  }


  public void onClick(ActionEvent event)
  {
    if (event.getSource() == insertModifySolarPanel)
    {
      viewHandler.changeScene(ViewHandler.INSERT_MODIFY_SOLAR_PANEL);
    }
    else if (event.getSource() == back)
    {
      viewHandler.changeScene(ViewHandler.MAIN_SCENE);
    }
  }

  public List<SolarPanel> getSolarPanels()
  {
    List<SolarPanel> solarPanels = new ArrayList<SolarPanel>();

    try
    {
      Statement statement = viewHandler.getConnection().createStatement();

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

  // Class Modify for the button modify in the pop out table with the solar panels
  public void onModify()
  {
    List<SolarPanel> selectedItems = solarPanelsTable.getSelectionModel().getSelectedItems();
    if (selectedItems != null)
    {
     viewHandler.getInsertModifySolarPanelController().setModifying(true);
     viewHandler.changeScene(viewHandler.INSERT_MODIFY_SOLAR_PANEL);

      viewHandler.getInsertModifySolarPanelController().setSerialNo(selectedItems.get(0).getSerialNo());
    }
    else
    {
      Alert alert = new Alert(Alert.AlertType.INFORMATION);
      alert.setTitle("Alert");
      alert.setHeaderText("This is an alert message");
      alert.setContentText("Hello, world!");

      alert.showAndWait();
    }
  }

}
