package model;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalTime;
import java.util.Date;

public class SolarPanelProduction
{
  private Timestamp measurementDate;
  private int productionValuePerc;
  private float productionValueWatt;
  private String panelType;

  public SolarPanelProduction(Timestamp measurementDate, String model_type, int productionValuePerc, float productionValueWatt)
  {
    this.measurementDate = measurementDate;
    this.panelType = model_type;
    this.productionValuePerc = productionValuePerc;
    this.productionValueWatt = productionValueWatt;
  }

  public Timestamp getMeasurementDate()
  {
    return measurementDate;
  }

  public int getProductionValuePerc()
  {
    return productionValuePerc;
  }

  public float getProductionValueWatt() { return productionValueWatt; }
  public String getPanelType() {return panelType;}
}
