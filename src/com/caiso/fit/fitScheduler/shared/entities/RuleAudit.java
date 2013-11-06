package com.caiso.fit.fitScheduler.shared.entities;

import java.io.Serializable;

import com.extjs.gxt.ui.client.data.BeanModelTag;

//******************************************************
//Class: RuleAudit
//
//******************************************************
public class RuleAudit implements Serializable, BeanModelTag {
  private Integer ruleID;
  private Integer fireID;
  private Integer executionStatus;
  private String  executionTime;
  private String  executionNotes;
  
  private static final long serialVersionUID = 1L;
  
  public Integer getFireID() {
    return fireID;
  }
  public void setFireID(Integer fireID) {
    this.fireID = fireID;
  }
  public Integer getExecutionStatus() {
    return executionStatus;
  }
  public void setExecutionStatus(Integer executionStatus) {
    this.executionStatus = executionStatus;
  }
  public String getExecutionTime() {
    return executionTime;
  }
  public void setExecutionTime(String executionTime) {
    this.executionTime = executionTime;
  }
  public String getExecutionNotes() {
    return executionNotes;
  }
  public void setExecutionNotes(String executionNotes) {
    this.executionNotes = executionNotes;
  }
  public Integer getRuleID() {
    return ruleID;
  }
  public void setRuleID(Integer ruleID) {
    this.ruleID = ruleID;
  }
}
