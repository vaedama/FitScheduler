package com.caiso.fit.fitScheduler.shared.entities;

import java.io.Serializable;

import com.extjs.gxt.ui.client.data.BeanModelTag;

// ******************************************************
// Class: DatabaseProbe
//
// ******************************************************
public class DatabaseProbe extends Probe implements BeanModelTag, Serializable {
  private Integer dbProbeID;
  private String  sourceJndiName;
  private String  timestampColumnName;
  private String  sqlQuery;

  private static final long serialVersionUID = 1L;

  public Integer getDbProbeID() {
    return dbProbeID;
  }
  public void setDbProbeID(Integer dbProbeID) {
    this.dbProbeID = dbProbeID;
  }
  public String getSourceJndiName() {
    return sourceJndiName;
  }
  public void setSourceJndiName(String sourceJndiName) {
    this.sourceJndiName = sourceJndiName;
  }
  public String getTimestampColumnName() {
    return timestampColumnName;
  }
  public void setTimestampColumnName(String timestampColumnName) {
    this.timestampColumnName = timestampColumnName;
  }
  public String getSqlQuery() {
    return sqlQuery;
  }
  public void setSqlQuery(String sqlQuery) {
    this.sqlQuery = sqlQuery;
  }
}
