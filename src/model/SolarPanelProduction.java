package model;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalTime;
import java.util.Date;

public class SolarPanelProduction
{
  private Timestamp measurementDate;
  private float productionValue;
  private String panelType;

  public SolarPanelProduction(Timestamp measurementDate, String model_type, float productionValue)
  {
    this.measurementDate = measurementDate;
    this.panelType = model_type;
    this.productionValue = productionValue;
  }

  public Timestamp getMeasurementDate()
  {
    return measurementDate;
  }

  public float getProductionValue()
  {
    return productionValue;
  }
  public String getPanelType() {return panelType;}
}
