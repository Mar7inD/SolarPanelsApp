package model;

import java.sql.Timestamp;

public class Fault
{
  private int id;
  private int panel_serial_no ;
  private Timestamp fault_date;
  private String fault_type;
  private String description;

  // Constructor
  public Fault(int id, int panel_serial_no, Timestamp fault_date, String fault_type, String description) {
    this.id = id;
    this.panel_serial_no = panel_serial_no;
    this.fault_date = fault_date;
    this.fault_type = fault_type;
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

  public Timestamp getFault_date() {
    return fault_date;
  }

  public void setFault_date(Timestamp fault_date) {
    this.fault_date = fault_date;
  }

  public String getFault_type() {
    return fault_type;
  }

  public void setFault_type(String fault_type) {
    this.fault_type = fault_type;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
}
