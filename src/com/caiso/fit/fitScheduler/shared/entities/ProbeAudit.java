package com.caiso.fit.fitScheduler.shared.entities;

import java.io.Serializable;

import com.extjs.gxt.ui.client.data.BeanModelTag;

//******************************************************
//Class: Audit
//
//******************************************************
public class ProbeAudit implements BeanModelTag, Serializable {
  private String  probeName;
  private Integer dataLoadID;
  private Integer deletedDataLoadID;
  private Integer replacementDataLoadID;
  private String  runStartTime;
  private String  runEndTime;
  private Integer elapsedRunTime;
  private Integer rowsQueried;
  private Integer measurementsQueried;
  private Integer rowsInserted;
  private Integer rowsDeleted;
  private String  startTime;
  private String  endTime;
  private String  successFlag;
  
  private static final long serialVersionUID = 1L;

  public String getProbeName() {
    return probeName;
  }
  public void setProbeName(String probeName) {
    this.probeName = probeName;
  }
  public Integer getDataLoadID() {
    return dataLoadID;
  }
  public void setDataLoadID(Integer dataLoadID) {
    this.dataLoadID = dataLoadID;
  }
  public Integer getDeletedDataLoadID() {
    return deletedDataLoadID;
  }
  public void setDeletedDataLoadID(Integer deletedDataLoadID) {
    this.deletedDataLoadID = deletedDataLoadID;
  }
  public Integer getReplacementDataLoadID() {
    return replacementDataLoadID;
  }
  public void setReplacementDataLoadID(Integer replacementDataLoadID) {
    this.replacementDataLoadID = replacementDataLoadID;
  }
  public String getRunStartTime() {
    return runStartTime;
  }
  public void setRunStartTime(String runStartTime) {
    this.runStartTime = runStartTime;
  }
  public String getRunEndTime() {
    return runEndTime;
  }
  public void setRunEndTime(String runEndTime) {
    this.runEndTime = runEndTime;
  }
  public Integer getElapsedRunTime() {
    return elapsedRunTime;
  }
  public void setElapsedRunTime(Integer elapsedRunTime) {
    this.elapsedRunTime = elapsedRunTime;
  }
  public Integer getRowsQueried() {
    return rowsQueried;
  }
  public void setRowsQueried(Integer rowsQueried) {
    this.rowsQueried = rowsQueried;
  }
  public Integer getMeasurementsQueried() {
    return measurementsQueried;
  }
  public void setMeasurementsQueried(Integer measurementsQueried) {
    this.measurementsQueried = measurementsQueried;
  }
  public Integer getRowsInserted() {
    return rowsInserted;
  }
  public void setRowsInserted(Integer rowsInserted) {
    this.rowsInserted = rowsInserted;
  }
  public Integer getRowsDeleted() {
    return rowsDeleted;
  }
  public void setRowsDeleted(Integer rowsDeleted) {
    this.rowsDeleted = rowsDeleted;
  }
  public String getStartTime() {
    return startTime;
  }
  public void setStartTime(String startTime) {
    this.startTime = startTime;
  }
  public String getEndTime() {
    return endTime;
  }
  public void setEndTime(String endTime) {
    this.endTime = endTime;
  }
  public String getSuccessFlag() {
    return successFlag;
  }
  public void setSuccessFlag(String successFlag) {
    this.successFlag = successFlag;
  }
}