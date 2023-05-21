package controller.manufacturer;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import controller.DatabaseConnection;
import controller.ViewHandler;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import java.sql.Connection;
import java.sql.ResultSet;
import model.Manufacturer;

public class ManufacturerInformationController
{
  @FXML private Button backButton;
  @FXML private Button insertButton;
  @FXML
  private TableView<Manufacturer> manufacturerTableView;
  @FXML
  private TableColumn<Manufacturer, Integer> idColumn;
  @FXML
  private TableColumn<Manufacturer, String> nameColumn;
  @FXML
  private TableColumn<Manufacturer, String> addressColumn;
  @FXML
  private TableColumn<Manufacturer, String> emailColumn;
  @FXML
  private TableColumn<Manufacturer, String> phoneNumberColumn;
  private ViewHandler viewHandler;

  public void init(ViewHandler viewHandler)
  {
    this.viewHandler = viewHandler;

    // Initialize table columns
    idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
    nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
    addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
    emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
    phoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));

    // Retrieve data from the database
    try {
      List<Manufacturer> manufacturers = getAllManufacturers();

      // Populate the TableView with the retrieved data
      manufacturerTableView.getItems().addAll(manufacturers);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  // Retrieve the manufacturer data and populate the TableView accordingly
  private List<Manufacturer> getAllManufacturers() throws SQLException {
    List<Manufacturer> manufacturers = new ArrayList<>();

    try (Connection connection = DatabaseConnection.getConnection();
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM \"solar_panels\".\"manufacturer\" LIMIT 100");
        ResultSet resultSet = statement.executeQuery()) {
      while (resultSet.next()) {
        int id = resultSet.getInt("id");
        String name = resultSet.getString("name");
        String address = resultSet.getString("address");
        String email = resultSet.getString("email");
        String phoneNumber = resultSet.getString("phone_number");

        Manufacturer manufacturer = new Manufacturer(id, name, address, email, phoneNumber);
        manufacturers.add(manufacturer);
      }
    }

    return manufacturers;
  }



  public void onClick(ActionEvent event)
  {
    if (event.getSource() == backButton)
    {
      viewHandler.changeScene(viewHandler.MAIN_SCENE);
    }
    if (event.getSource() == insertButton)
    {
      viewHandler.changeScene(viewHandler.INSERT_MANUFACTURER_PAGE);
    }
  }



}
