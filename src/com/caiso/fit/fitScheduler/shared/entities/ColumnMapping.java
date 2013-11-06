package com.caiso.fit.fitScheduler.shared.entities;

import java.io.Serializable;

import com.extjs.gxt.ui.client.data.BeanModelTag;

// ******************************************************
// Class: ColumnConfiguration
//
// ******************************************************
public class ColumnMapping implements BeanModelTag, Serializable {
  private String  jobName;
  private Integer targetAttributeID;
  private String  sourceColumnName;

  private static final long serialVersionUID = 1L;
  
  public String getJobName() {
    return jobName;
  }
  public void setJobName(String jobName) {
    this.jobName = jobName;
  }
  public Integer getTargetAttributeID() {
    return targetAttributeID;
  }
  public void setTargetAttributeID(Integer targetAttributeID) {
    this.targetAttributeID = targetAttributeID;
  }
  public String getSourceColumnName() {
    return sourceColumnName;
  }
  public void setSourceColumnName(String sourceColumn) {
    this.sourceColumnName = sourceColumn;
  }
}
