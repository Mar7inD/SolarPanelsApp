import javafx.application.Application;
import javafx.stage.Stage;
import controller.ViewHandler;

import java.sql.SQLException;

public class Main extends Application
{
  @Override public void start(Stage primaryStage)
  {
    ViewHandler viewHandler = new ViewHandler(primaryStage);
  }

  public static void main(String[] args)
  {
    Application.launch(Main.class);

    try (DatabaseConnection dbConnection = new DatabaseConnection()) {

    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

}
