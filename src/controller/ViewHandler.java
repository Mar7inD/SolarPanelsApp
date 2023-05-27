package controller;

import controller.faultsandmaintenance.RegisterFaultsController;
import controller.faultsandmaintenance.RegisterMaintenanceController;
import controller.manufacturer.InsertManufacturerPageController;
import controller.manufacturer.ManufacturerInformationController;
import controller.faultsandmaintenance.FaultsAndMaintenanceController;
import controller.solarPanels.InsertModifySolarPanelController;
import controller.solarPanels.SolarPanelsSceneController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class ViewHandler
  {
    private static Connection connection;
    private final Stage primaryStage;
    private Scene main;
    private Scene solar_panels;
    private Scene chooseProductionParameters;
    private Scene showData;
    private Scene insertModifySolarPanel;
    private Scene manufacturerInformation;
    private Scene insertManufacturerPage;
    private Scene faultsAndMaintenance;
    private Scene registerFaults;
    private Scene registerMaintenance;
    private MainSceneController mainSceneController;
    private SolarPanelsSceneController solarPanelsSceneController;
    private ProductionChooseParametersController productionChooseParametersController;
    private ShowDataController showDataController;
    private InsertModifySolarPanelController insertModifySolarPanelController;
    private ManufacturerInformationController manufacturerInformationController;
    private InsertManufacturerPageController insertManufacturerPageController;
    private FaultsAndMaintenanceController faultsAndMaintenanceController;
    private RegisterFaultsController registerFaultsController;
    private RegisterMaintenanceController registerMaintenanceController;
    public static final String MAIN_SCENE = "MAIN_SCENE";
    public static final String SOLAR_PANELS = "SOLAR_PANELS";
    public static final String CHOOSE_PRODUCTION_PARAMETERS = "CHOOSE_PRODUCTION_PARAMETERS";
    public static final String SHOW_DATA = "SHOW_DATA";
    public static final String INSERT_MODIFY_SOLAR_PANEL = "INSERT_MODIFY_SOLAR_PANEL";
    public static final String MANUFACTURER_INFORMATION = "MANUFACTURER_INFORMATION";
    public static final String INSERT_MANUFACTURER_PAGE = "INSERT_MANUFACTURER_PAGE";
    public static final String FAULTS_AND_MAINTENANCE = "FAULTS_AND_MAINTENANCE";
    public static final String REGISTER_FAULTS = "REGISTER_FAULTS";
    public static final String REGISTER_MAINTENANCE = "REGISTER_MAINTENANCE";

    public ViewHandler(Stage primaryStage, Connection connection)
    {
      this.primaryStage = primaryStage;
      this.connection = connection;

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
      catch (SQLException e)
      {
        throw new RuntimeException(e);
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

      // Loading ManufacturerInformation.fxml into manufacturerInformation
      loader = new FXMLLoader();
      loader.setLocation(getClass().getResource("../view/Dimitar/ManufacturerInformation.fxml"));
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

      // Loading InsertManufacturerPage.fxml into insertManufacturerPage
      loader = new FXMLLoader();
      loader.setLocation(getClass().getResource("../view/Dimitar/InsertManufacturerPage.fxml"));
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
      // Loading FaultsAndMaintenance.fxml into faultsAndMaintenance
      loader = new FXMLLoader();
      loader.setLocation(getClass().getResource("../view/Dimitar/FaultsAndMaintenance.fxml"));
      try
      {
        faultsAndMaintenance = new Scene(loader.load());
        faultsAndMaintenanceController = loader.getController();
        faultsAndMaintenanceController.init(this);
      }
      catch(IOException e)
      {
        System.out.println("Failed to load FaultsAndMaintenance.fxml");
        System.exit(1);
      }
      // Loading RegisterFaults.fxml into registerFaults
      loader = new FXMLLoader();
      loader.setLocation(getClass().getResource("../view/Dimitar/RegisterFaults.fxml"));
      try
      {
        registerFaults = new Scene(loader.load());
        registerFaultsController = loader.getController();
        registerFaultsController.init(this);
      }
      catch(IOException e)
      {
        System.out.println("Failed to load RegisterFaults.fxml");
        System.exit(1);
      }
      // Loading RegisterMaintenance.fxml into registerMaintenance
      loader = new FXMLLoader();
      loader.setLocation(getClass().getResource("../view/Dimitar/RegisterMaintenance.fxml"));
      try
      {
        registerMaintenance = new Scene(loader.load());
        registerMaintenanceController = loader.getController();
        registerMaintenanceController.init(this);
      }
      catch(IOException e)
      {
        System.out.println("Failed to load RegisterMaintenance.fxml");
        System.exit(1);
      }

      changeScene(MAIN_SCENE);
    }

      public void changeScene(String sceneName)
      {
        if (MAIN_SCENE.equals(sceneName))
        {
          primaryStage.setTitle("Main Menu");
          primaryStage.setScene(main);
          primaryStage.show();
        }
        else if (SOLAR_PANELS.equals(sceneName))
        {
          primaryStage.setTitle("Solar Panels Information");
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
          primaryStage.setTitle("Solar Panels");
          primaryStage.setScene(insertModifySolarPanel);
          primaryStage.show();
        }
        else if (INSERT_MANUFACTURER_PAGE.equals(sceneName))
        {
          primaryStage.setTitle("Insert Manufacturer Information");
          primaryStage.setScene(insertManufacturerPage);
          primaryStage.show();
        }
        else if (MANUFACTURER_INFORMATION.equals(sceneName))
        {
          primaryStage.setTitle("Manufacturer Information");
          primaryStage.setScene(manufacturerInformation);
          primaryStage.show();
        }
        else if (FAULTS_AND_MAINTENANCE.equals(sceneName))
        {
          primaryStage.setTitle("Faults And Maintenance");
          primaryStage.setScene(faultsAndMaintenance);
          primaryStage.show();
        }
        else if (REGISTER_FAULTS.equals(sceneName))
        {
          primaryStage.setTitle("Register Fault");
          primaryStage.setScene(registerFaults);
          primaryStage.show();
        }
        else if (REGISTER_MAINTENANCE.equals(sceneName))
        {
          primaryStage.setTitle("Register Maintenance");
          primaryStage.setScene(registerMaintenance);
          primaryStage.show();
        }
      }

      public Connection getConnection()
      {
        return connection;
      }

      public InsertModifySolarPanelController getInsertModifySolarPanelController()
      {
        return insertModifySolarPanelController;
      }
    public SolarPanelsSceneController getSolarPanelsSceneController()
    {
      return solarPanelsSceneController;
    }
    }

