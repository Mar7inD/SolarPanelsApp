import controller.DatabaseConnection;
import javafx.application.Application;
import javafx.stage.Stage;
import controller.ViewHandler;

public class Main extends Application
{
  @Override public void start(Stage primaryStage)
  {
    ViewHandler viewHandler = new ViewHandler(primaryStage);
  }

  public static void main(String[] args)
  {
    Application.launch(Main.class);
  }

}
