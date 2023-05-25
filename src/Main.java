import javafx.application.Application;
import javafx.stage.Stage;
import controller.ViewHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Main extends Application
{
  private static Connection connection;
  @Override public void start(Stage primaryStage)
  {
    new ViewHandler(primaryStage, connection);
  }

  public static void main(String[] args)
  {
      try {
        String url = "jdbc:postgresql://snuffleupagus.db.elephantsql.com:5432/icgiivdi";
        Properties props = new Properties();
        props.setProperty("user", "icgiivdi");
        props.setProperty("password", "5JBbbzO0mgAwhwoUtAkf3QH8zaL4Lo-n");

        // Create the connection
        connection = DriverManager.getConnection(url,props);

        // Start application
        Application.launch(Main.class);

      } catch (SQLException e) {
        System.err.println("Failed to connect to the database: " + e.getMessage());

        e.printStackTrace();
      }
  }
}
