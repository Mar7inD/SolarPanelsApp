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
  @FXML private Button insertSolarPanel;
  @FXML private Button modify;
  @FXML private TableView<SolarPanel> solarPanelsTable;
  @FXML private TableColumn<SolarPanel, Integer> serialNo;
  @FXML private TableColumn<SolarPanel, String> panelType;
  @FXML private TableColumn<SolarPanel, String> roofPosition;
  @FXML private TableColumn<SolarPanel, Date> installationDate;
  @FXML private TableColumn<SolarPanel, String> manufacturer;
  @FXML private TableColumn<SolarPanel, String> activity;

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

    refreshTableView();

    // Disable the modify button initially
    modify.setDisable(true);

    // Add a listeners to track row selection in the TableView
    solarPanelsTable.getSelectionModel().selectedItemProperty().addListener
        ((obs, oldSelection, newSelection) -> modify.setDisable(newSelection == null));
  }


  public void onClick(ActionEvent event)
  {
    if (event.getSource() == insertSolarPanel)
    {
      viewHandler.changeScene(ViewHandler.INSERT_MODIFY_SOLAR_PANEL);
      viewHandler.getInsertModifySolarPanelController().resetFields();
    }
    else if (event.getSource() == back)
    {
      viewHandler.changeScene(ViewHandler.MAIN_SCENE);
    }
  }

  public List<SolarPanel> getSolarPanels()
  {
    List<SolarPanel> solarPanels = new ArrayList<>();

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

  public void refreshTableView() {
    solarPanelsTable.getItems().clear(); // Clear the existing items

      List<SolarPanel> solarPanels = getSolarPanels();

      // Populate the TableView with the retrieved data
      solarPanelsTable.getItems().addAll(solarPanels);
  }

  // Class Modify for the button modify in the pop out table with the solar panels
  public void onModify()
  {
    List<SolarPanel> selectedItems = solarPanelsTable.getSelectionModel().getSelectedItems();
    {
     viewHandler.getInsertModifySolarPanelController().setModifying(true);
     viewHandler.getInsertModifySolarPanelController().resetFields();
     viewHandler.changeScene(ViewHandler.INSERT_MODIFY_SOLAR_PANEL);

      viewHandler.getInsertModifySolarPanelController().setSerialNo(selectedItems.get(0).getSerialNo());
    }
  }

}
