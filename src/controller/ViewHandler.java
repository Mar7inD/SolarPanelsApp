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
    private final Stage PRIMARY_STAGE;
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
    private ShowDataController showDataController;
    private SolarPanelsSceneController solarPanelsSceneController;
    private InsertModifySolarPanelController insertModifySolarPanelController;
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
      this.PRIMARY_STAGE = primaryStage;
      ViewHandler.connection = connection;

      // Loading MainScene.fxml into main
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(getClass().getResource("../view/Martin/MainScene.fxml"));
      try
      {
        main = new Scene(loader.load());
        MainSceneController mainSceneController = loader.getController();
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
        ProductionChooseParametersController productionChooseParametersController = loader.getController();
        productionChooseParametersController.init(this);
      }
      catch(IOException e)
      {
        System.out.println("Failed to load ProductionChooseParameters.fxml");
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
        ManufacturerInformationController manufacturerInformationController = loader.getController();
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
        InsertManufacturerPageController insertManufacturerPageController = loader.getController();
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
        FaultsAndMaintenanceController faultsAndMaintenanceController = loader.getController();
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
        RegisterFaultsController registerFaultsController = loader.getController();
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
        RegisterMaintenanceController registerMaintenanceController = loader.getController();
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
          PRIMARY_STAGE.setTitle("Main Menu");
          PRIMARY_STAGE.setScene(main);
          PRIMARY_STAGE.show();
        }
        else if (SOLAR_PANELS.equals(sceneName))
        {
          PRIMARY_STAGE.setTitle("Solar Panels Page");
          PRIMARY_STAGE.setScene(solar_panels);
          PRIMARY_STAGE.show();
        }

        // PRODUCTION CAPACITY SCENES
        else if (CHOOSE_PRODUCTION_PARAMETERS.equals(sceneName))
        {
          PRIMARY_STAGE.setTitle("Choose Parameters");
          PRIMARY_STAGE.setScene(chooseProductionParameters);
          PRIMARY_STAGE.show();
        }
        else if (SHOW_DATA.equals(sceneName))
        {
          PRIMARY_STAGE.setTitle("Data");
          PRIMARY_STAGE.setScene(showData);
          PRIMARY_STAGE.show();
        }
        else if (INSERT_MODIFY_SOLAR_PANEL.equals(sceneName))
        {
          PRIMARY_STAGE.setTitle("Solar Panels Page");
          PRIMARY_STAGE.setScene(insertModifySolarPanel);
          PRIMARY_STAGE.show();
        }
        else if (INSERT_MANUFACTURER_PAGE.equals(sceneName))
        {
          PRIMARY_STAGE.setTitle("Insert Manufacturer Information");
          PRIMARY_STAGE.setScene(insertManufacturerPage);
          PRIMARY_STAGE.show();
        }
        else if (MANUFACTURER_INFORMATION.equals(sceneName))
        {
          PRIMARY_STAGE.setTitle("Manufacturer Information");
          PRIMARY_STAGE.setScene(manufacturerInformation);
          PRIMARY_STAGE.show();
        }
        else if (FAULTS_AND_MAINTENANCE.equals(sceneName))
        {
          PRIMARY_STAGE.setTitle("Faults And Maintenance");
          PRIMARY_STAGE.setScene(faultsAndMaintenance);
          PRIMARY_STAGE.show();
        }
        else if (REGISTER_FAULTS.equals(sceneName))
        {
          PRIMARY_STAGE.setTitle("Register Fault");
          PRIMARY_STAGE.setScene(registerFaults);
          PRIMARY_STAGE.show();
        }
        else if (REGISTER_MAINTENANCE.equals(sceneName))
        {
          PRIMARY_STAGE.setTitle("Register Maintenance");
          PRIMARY_STAGE.setScene(registerMaintenance);
          PRIMARY_STAGE.show();
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
      public ShowDataController getShowDataController()
      {
        return showDataController;
      }
      public Stage getStage()
      {
        return PRIMARY_STAGE;
      }
    }

