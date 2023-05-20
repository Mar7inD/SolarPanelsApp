package controller;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import javafx.collections.FXCollections;


public class ManufacturerInformationController
{
  @FXML private Button backButton;
  @FXML private Button insertButton;
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
    if (event.getSource() == insertButton)
    {
      viewHandler.changeScene(viewHandler.INSERT_MANUFACTURER_PAGE);
    }
  }

  public class DynamicTable extends Application {

    // TABLE VIEW AND DATA
    private ObservableList<ObservableList<String>> data;
    private TableView<ObservableList<String>> tableview;

    // MAIN EXECUTOR
    public static void main(String[] args) {
      launch(args);
    }

    // CONNECTION DATABASE
    public void buildData() {
      data = FXCollections.observableArrayList();
      try {
        Connection connection = DatabaseConnection.getConnection();
        // SQL FOR SELECTING ALL OF CUSTOMER
        String SQL = "SELECT * from Manufacturer";
        // ResultSet
        ResultSet rs = connection.createStatement().executeQuery(SQL);

        /**
         * ********************************
         * TABLE COLUMN ADDED DYNAMICALLY *
         *********************************
         */
        for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
          final int j = i;
          TableColumn<ObservableList<String>, String> col = new TableColumn<>(rs.getMetaData().getColumnName(i + 1));
          col.setCellValueFactory(
              new Callback<CellDataFeatures<ObservableList<String>, String>, ObservableValue<String>>() {
                public ObservableValue<String> call(CellDataFeatures<ObservableList<String>, String> param) {
                  return new SimpleStringProperty(param.getValue().get(j));
                }
              });

          tableview.getColumns().add(col);
          System.out.println("Column [" + i + "] ");
        }

        /**
         * ******************************
         * Data added to ObservableList *
         *******************************
         */
        while (rs.next()) {
          List<String> row = new ArrayList<>();
          for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
            row.add(rs.getString(i));
          }
          System.out.println("Row added: " + row);
          data.add(FXCollections.observableArrayList(row));
        }

        // FINALLY ADDED TO TableView
        tableview.setItems(data);
      } catch (Exception e) {
        e.printStackTrace();
        System.out.println("Error on Building Data");
      }
    }

    @Override
    public void start(Stage stage) throws Exception {
      // TableView
      tableview = new TableView<>();
      buildData();

      // Main Scene
      Scene scene = new Scene(tableview);

      stage.setScene(scene);
      stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
        @Override
        public void handle(WindowEvent event) {
          Platform.exit();
          System.exit(0);
        }
      });
      stage.show();
    }
  }

}
