package com.caiso.fit.fitScheduler.shared.entities;

import java.io.Serializable;
import java.util.Date;

import com.extjs.gxt.ui.client.data.BeanModelTag;

public class JobDefinition implements BeanModelTag, Serializable {
  private String  jobName;
  private String  jobType;
  private String  jobDescription;
  private Date    startDate; 
  private Long    startTime;
  private Date    endDate;
  private Long    endTime; 
  private Integer repeatInterval;
  private String  status;
  
  private static final long serialVersionUID = 1L;
  
  public String getJobName() {
    return jobName;
  }
  public void setJobName(String jobName) {
    this.jobName = jobName;
  }
  public String getJobType() {
    return jobType;
  }
  public void setJobType(String jobType) {
    this.jobType = jobType;
  }
  public String getJobDescription() {
    return jobDescription;
  }
  public void setJobDescription(String jobDescription) {
    this.jobDescription = jobDescription;
  }
  public Date getStartDate() {
    return startDate;
  }
  public void setStartDate(Date startDate) {
    this.startDate = startDate;
  }
  public Long getStartTime() {
    return startTime;
  }
  public void setStartTime(Long startTime) {
    this.startTime = startTime;
  }
  public Date getEndDate() {
    return endDate;
  }
  public void setEndDate(Date endDate) {
    this.endDate = endDate;
  }
  public Long getEndTime() {
    return endTime;
  }
  public void setEndTime(Long endTime) {
    this.endTime = endTime;
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
