package controller;

import controller.manufacturer.InsertManufacturerPageController;
import controller.manufacturer.ManufacturerInformationController;
import controller.solarPanels.InsertModifySolarPanelController;
import controller.solarPanels.SolarPanelsSceneController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
  public class ViewHandler
  {
    private final Stage primaryStage;
    private Scene main;
    private Scene solar_panels;
    private Scene chooseProductionParameters;
    private Scene showData;
    private Scene insertModifySolarPanel;
    private Scene manufacturerInformation;
    private Scene insertManufacturerPage;
    private MainSceneController mainSceneController;
    private SolarPanelsSceneController solarPanelsSceneController;
    private ProductionChooseParametersController productionChooseParametersController;
    private ShowDataController showDataController;
    private InsertModifySolarPanelController insertModifySolarPanelController;
    private ManufacturerInformationController manufacturerInformationController;
    private InsertManufacturerPageController insertManufacturerPageController;
    public static final String MAIN_SCENE = "MAIN_SCENE";
    public static final String SOLAR_PANELS = "SOLAR_PANELS";
    public static final String CHOOSE_PRODUCTION_PARAMETERS = "CHOOSE_PRODUCTION_PARAMETERS";
    public static final String SHOW_DATA = "SHOW_DATA";
    public static final String INSERT_MODIFY_SOLAR_PANEL = "INSERT_MODIFY_SOLAR_PANEL";
    public static final String MANUFACTURER_INFORMATION = "MANUFACTURER_INFORMATION";
    public static final String INSERT_MANUFACTURER_PAGE = "INSERT_MANUFACTURER_PAGE";


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
      // Loading ProductionChooseParameters.fxml into chooseProductionParameters
      loader = new FXMLLoader();
      loader.setLocation(getClass().getResource("../view/Aleks/ProductionChooseParameters.fxml"));
      try
      {
        chooseProductionParameters = new Scene(loader.load());
        productionChooseParametersController = loader.getController();
        productionChooseParametersController.init(this);
      }
      catch(IOException e)
      {
        System.out.println(e);
        // System.out.println("Failed to load ProductionChooseParameters.fxml");
        System.exit(1);
      }
      // Loading ShowData.fxml into showData
      loader = new FXMLLoader();
      loader.setLocation(getClass().getResource("../view/Aleks/ShowData.fxml"));
      try
      {
        showData = new Scene(loader.load());
        showDataController = loader.getController();
        showDataController.init(this);
      }
      catch(IOException e)
      {
        System.out.println("Failed to load ShowData.fxml");
        System.exit(1);
      }

      // Loading InsertModifySolarPanel.fxml into insertModifySolarPanel
      loader = new FXMLLoader();
      loader.setLocation(getClass().getResource("../view/Martin/InsertModifySolarPanel.fxml"));
      try
      {
        insertModifySolarPanel = new Scene(loader.load());
        insertModifySolarPanelController = loader.getController();
        insertModifySolarPanelController.init(this);
      }
      catch(IOException e)
      {
        System.out.println("Failed to load InsertModifySolarPanel.fxml");
        System.exit(1);
      }

      // Loading ManufacturerInformation.fxml into modifySolarPanel
      loader = new FXMLLoader();
      loader.setLocation(getClass().getResource("../view/Martin/ManufacturerInformation.fxml"));
      try
      {
        manufacturerInformation = new Scene(loader.load());
        manufacturerInformationController = loader.getController();
        manufacturerInformationController.init(this);
      }
      catch(IOException e)
      {
        System.out.println("Failed to load ManufacturerInformation.fxml");
        System.exit(1);
      }

      //
      loader = new FXMLLoader();
      loader.setLocation(getClass().getResource("../view/Martin/InsertManufacturerPage.fxml"));
      try
      {
        insertManufacturerPage = new Scene(loader.load());
        insertManufacturerPageController = loader.getController();
        insertManufacturerPageController.init(this);
      }
      catch(IOException e)
      {
        System.out.println("Failed to load InsertManufacturerPage.fxml");
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

        // PRODUCTION CAPACITY SCENES
        else if (CHOOSE_PRODUCTION_PARAMETERS.equals(sceneName))
        {
          primaryStage.setTitle("Choose Parameters");
          primaryStage.setScene(chooseProductionParameters);
          primaryStage.show();
        }
        else if (SHOW_DATA.equals(sceneName))
        {
          primaryStage.setTitle("Data");
          primaryStage.setScene(showData);
          primaryStage.show();
        }
        else if (INSERT_MODIFY_SOLAR_PANEL.equals(sceneName))
        {
          primaryStage.setTitle("Modify Page");
          primaryStage.setScene(insertModifySolarPanel);
          primaryStage.show();
        }
        else if (INSERT_MANUFACTURER_PAGE.equals(sceneName))
        {
          primaryStage.setTitle("Insert Manufacturer data");
          primaryStage.setScene(insertManufacturerPage);
          primaryStage.show();
        }
        else if (MANUFACTURER_INFORMATION.equals(sceneName))
        {
          primaryStage.setTitle("Show Manufacturer data");
          primaryStage.setScene(manufacturerInformation);
          primaryStage.show();
        }
      }

      public InsertModifySolarPanelController getInsertModifySolarPanelController()
      {
        return insertModifySolarPanelController;
      }
    }

