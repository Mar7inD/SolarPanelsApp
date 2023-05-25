package controller.solarPanels;

import controller.ViewHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;

import javafx.fxml.FXML;

import javafx.event.ActionEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.SolarPanel;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

public class SolarPanelsSceneController
{
  @FXML private Button back;
  @FXML private Button insertModifySolarPanel;
  private Button closeButton;
  @FXML private TableView<SolarPanel> solarPanelsTable;
  @FXML private TableColumn<SolarPanel, Integer> serialNo = new TableColumn<SolarPanel, Integer>("Serial No");
  @FXML private TableColumn<SolarPanel, String> panelType = new TableColumn<SolarPanel, String>("Type");
  @FXML private TableColumn<SolarPanel, String> roofPosition = new TableColumn<SolarPanel, String>("Roof Position");
  @FXML private TableColumn installationDate = new TableColumn<SolarPanel, Date>("Installation Date");
  @FXML private TableColumn manufacturer = new TableColumn<SolarPanel, String>("Manufacturer");
  @FXML private TableColumn activity = new TableColumn<SolarPanel, String>("Activity");
  @FXML private ComboBox<String> solarPanelPosition;

  private ViewHandler viewHandler;

  public void init(ViewHandler viewHandler)
  {
    this.viewHandler = viewHandler;
    for (int i = 1; i < 123; i++)
        { String position = "" + i;
          this.solarPanelPosition.getItems().add(position); }

    serialNo.setCellValueFactory(new PropertyValueFactory<>("serialNo"));
    panelType.setCellValueFactory(new PropertyValueFactory<>("panelType"));
    roofPosition.setCellValueFactory(new PropertyValueFactory<>("roofPosition"));
    installationDate.setCellValueFactory(new PropertyValueFactory<>("installationDate"));
    manufacturer.setCellValueFactory(new PropertyValueFactory<>("manufacturer"));
    activity.setCellValueFactory(new PropertyValueFactory<>("active"));
  }

  public void onShow(ActionEvent event)
  {
    Stage tableView = new Stage();
    solarPanelsTable = new TableView<>();
    solarPanelsTable.setItems(getSolarPanels());
    solarPanelsTable.getColumns().addAll(serialNo,panelType,roofPosition,installationDate,manufacturer,activity);

    Button modifyButton = new Button("Modify");
    modifyButton.setOnAction(eventModify -> {
      Modify();
    });

    closeButton = new Button("Close");

    closeButton.setOnAction(eventClose -> {
      Stage currentStage = (Stage) closeButton.getScene().getWindow();
      currentStage.close();
    });

    VBox layout= new VBox(10);
    layout.getChildren().addAll(solarPanelsTable,modifyButton,closeButton);

    Scene table = new Scene(layout, 600, 250);

    tableView.setScene(table);

    tableView.showAndWait();
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

  public ObservableList<SolarPanel> getSolarPanels()
  {
    ObservableList<SolarPanel> solarPanels = FXCollections.observableArrayList();

    try
    {

      Statement statement = viewHandler.getConnection().createStatement();

      String sqlQuery = "SELECT * FROM solar_panels.solar_panels WHERE solar_panels.roof_position = ";

      ResultSet resultSet = statement.executeQuery(sqlQuery + solarPanelPosition.getValue());

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
  public void Modify() throws IndexOutOfBoundsException
  {
    ObservableList<SolarPanel> selectedItems = solarPanelsTable.getSelectionModel().getSelectedItems();
    if (selectedItems != null)
    {
     viewHandler.getInsertModifySolarPanelController().setModifying(true);
     viewHandler.changeScene(viewHandler.INSERT_MODIFY_SOLAR_PANEL);

      viewHandler.getInsertModifySolarPanelController().setSerialNo(selectedItems.get(0).getSerialNo());
      Stage currentStage = (Stage) closeButton.getScene().getWindow();
      currentStage.close();
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
