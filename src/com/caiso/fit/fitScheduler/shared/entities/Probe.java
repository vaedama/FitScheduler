package com.caiso.fit.fitScheduler.shared.entities;

import java.io.Serializable;
import java.util.Date;

import com.extjs.gxt.ui.client.data.BeanModelTag;

// ******************************************************
// Class: Probe
//
// ******************************************************
public abstract class Probe implements BeanModelTag, Serializable {
  private String  probeName;
  private String  probeType;
  private String  probeDescription;
  private Integer probeID;
  private Integer dataInterval;
  private Integer offset;
  private String  sourceTimezone;
  private Integer estimateFlag;
  private Integer rowsExpected;
  private Date    startDate;
  private String  startTime;
  private Date    endDate;
  private String  endTime;
  private Integer repeatInterval;
  private String  status;
  
  private static final long serialVersionUID = 1L;
  
  public String getProbeName() {
    return probeName;
  }
  public void setProbeName(String probeName) {
    this.probeName = probeName;
  }
  public String getProbeType() {
    return probeType;
  }
  public void setProbeType(String probeType) {
    this.probeType = probeType;
  }
  public String getProbeDescription() {
    return probeDescription;
  }
  public void setProbeDescription(String probeDescription) {
    this.probeDescription = probeDescription;
  }
  public Integer getProbeID() {
    return probeID;
  }
  public void setProbeID(Integer probeID) {
    this.probeID = probeID;
  }
  public Integer getDataInterval() {
    return dataInterval;
  }
  public void setDataInterval(Integer dataInterval) {
    this.dataInterval = dataInterval;
  }
  public Integer getOffset() {
    return offset;
  }
  public void setOffset(Integer offset) {
    this.offset = offset;
  }
  public String getSourceTimezone() {
    return sourceTimezone;
  }
  public void setSourceTimezone(String sourceTimezone) {
    this.sourceTimezone = sourceTimezone;
  }
  public Integer getEstimateFlag() {
    return estimateFlag;
  }
  public void setEstimateFlag(Integer estimateFlag) {
    this.estimateFlag = estimateFlag;
  }
  public Integer getRowsExpected() {
    return rowsExpected;
  }
  public void setRowsExpected(Integer rowsExpected) {
    this.rowsExpected = rowsExpected;
  }
  public Date getStartDate() {
    return startDate;
  }
  public void setStartDate(Date startDate) {
    this.startDate = startDate;
  }
  public String getStartTime() {
    return startTime;
  }
  public void setStartTime(String string) {
    this.startTime = string;
  }
  public Date getEndDate() {
    return endDate;
  }
  public void setEndDate(Date endDate) {
    this.endDate = endDate;
  }
  public String getEndTime() {
    return endTime;
  }
  public void setEndTime(String string) {
    this.endTime = string;
  }
  public Integer getRepeatInterval() {
    return repeatInterval;
  }
  public void setRepeatInterval(Integer repeatInterval) {
    this.repeatInterval = repeatInterval;
  }
  public String getStatus() {
    return status;
  }
  public void setStatus(String status) {
    this.status = status;
  }
}
