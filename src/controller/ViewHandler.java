package controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
  public class ViewHandler
  {
    private final Stage primaryStage;
    private Scene main;
    private Scene solar_panels;

    private MainSceneController mainSceneController;
    private SolarPanelsSceneController solarPanelsSceneController;

    public static final String MAIN_SCENE = "MAIN_SCENE";
    public static final String SOLAR_PANELS = "SOLAR_PANELS";

    public ViewHandler(Stage primaryStage)
    {
      this.primaryStage = primaryStage;

      // Loading MainScene.fxml into main
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(getClass().getResource("../view/Martin/MainScene.fxml"));
      try
      {
        main = new Scene(loader.load());
        mainSceneController = loader.getController();
        mainSceneController.init(this);
      }
      catch (IOException e)
      {
        e.printStackTrace(System.out);
        System.out.println("Failed to load MainScene.fxml");
        System.exit(1);
      }

      // Loading SolarPanelsScene.fxml into solar_panels
      loader = new FXMLLoader();
      loader.setLocation(getClass().getResource("../view/Martin/SolarPanelsScene.fxml"));
      try
      {
        solar_panels = new Scene(loader.load());
        solarPanelsSceneController = loader.getController();
        solarPanelsSceneController.init(this);
      }
      catch(IOException e)
      {
        System.out.println("Failed to load SolarPanelsScene.fxml");
        System.exit(1);
      }
      changeScene(MAIN_SCENE);
    }

      public void changeScene(String sceneName)
      {
        if (MAIN_SCENE.equals(sceneName))
        {
          primaryStage.setTitle("Main page");
          primaryStage.setScene(main);
          primaryStage.show();
        }
        else if (SOLAR_PANELS.equals(sceneName))
        {
          primaryStage.setTitle("Solar Panels");
          primaryStage.setScene(solar_panels);
          primaryStage.show();
        }
      }
    }

