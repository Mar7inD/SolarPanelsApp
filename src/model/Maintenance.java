package model;

import java.sql.Timestamp;

public class Maintenance
{
  private int id;
  private int panel_serial_no ;
  private Timestamp maintenance_date;
  private String maintenance_type;
  private String description;

  // Constructor
  public Maintenance(int id, int panel_serial_no, Timestamp maintenance_date, String maintenance_type, String description) {
    this.id = id;
    this.panel_serial_no = panel_serial_no;
    this.maintenance_date = maintenance_date;
    this.maintenance_type = maintenance_type;
    this.description = description;
  }

  // Getters and setters
  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getPanel_serial_no() {
    return panel_serial_no;
  }

  public void setPanel_serial_no(int panel_serial_no) {
    this.panel_serial_no = panel_serial_no;
  }

  public Timestamp getMaintenance_date() {
    return maintenance_date;
  }

  public void setFault_date(Timestamp maintenance_date) {
    this.maintenance_date = maintenance_date;
  }

  public String getMaintenance_type() {
    return maintenance_type;
  }

  public void setFault_type(String maintenance_type) {
    this.maintenance_type = maintenance_type;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
}

