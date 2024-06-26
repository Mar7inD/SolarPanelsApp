package model;

import java.util.Date;

public class SolarPanel
{
  private String serialNo;
  private String panelType;
  private String roofPosition;
  private Date installationDate;
  private String manufacturer;
  private String activity;


  public SolarPanel(String serialNo, String panelType, String roofPosition, Date installationDate, String manufacturer, boolean active)
  {
    this.serialNo = serialNo;
    this.panelType = panelType;
    this.roofPosition = roofPosition;
    this.installationDate = installationDate;
    this.manufacturer = manufacturer;
    if (active)
    {
      this.activity = "Active";
    }
    else
    {
      this.activity = "Deactivated";
    }
  }

  public String getSerialNo()
  {
    return serialNo;
  }

  public void setSerialNo(String serialNo)
  {
    this.serialNo = serialNo;
  }

  public String getPanelType()
  {
    return panelType;
  }

  public void setPanelType(String panelType)
  {
    this.panelType = panelType;
  }

  public String getRoofPosition()
  {
    return roofPosition;
  }

  public void setRoofPosition(String roofPosition)
  {
    this.roofPosition = roofPosition;
  }

  public Date getInstallationDate()
  {
    return installationDate;
  }

  public void setInstallationDate(Date installationDate)
  {
    this.installationDate = installationDate;
  }

  public String getManufacturer()
  {
    return manufacturer;
  }

  public void setManufacturer(String manufacturer)
  {
    this.manufacturer = manufacturer;
  }

  public String getActivity()
  {
    return activity;
  }
  public void setActivity(boolean active)
  {
    if (active)
    {
      this.activity = "Active";
    }
    else
    {
      this.activity = "Deactivated";
    }
  }
}
