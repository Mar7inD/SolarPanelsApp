import javafx.application.Application;
import javafx.stage.Stage;
import controller.ViewHandler;

public class Main extends Application
{
  private ViewHandler viewHandler;

  @Override public void start(Stage primaryStage)
  {
    viewHandler = new ViewHandler(primaryStage);
  }

  public static void main(String[] args)
  {
    Application.launch(Main.class);
  }

}
