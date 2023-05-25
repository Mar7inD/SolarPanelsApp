package model;

import java.util.Date;

public class SolarPanelProduction
{
  private Date measurementDate;
  private float productionValue;

  public SolarPanelProduction(Date measurementDate, float productionValue)
  {
    this.measurementDate = measurementDate;
    this.productionValue = productionValue;
  }

  public Date getMeasurementDate()
  {
    return measurementDate;
  }

  public float getProductionValue()
  {
    return productionValue;
  }

}
