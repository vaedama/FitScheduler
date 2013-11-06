package com.caiso.fit.fitScheduler.server.services

import com.caiso.fit.fitScheduler.shared.entities.JobDefinition

import java.text.SimpleDateFormat

// ******************************************************
// Class: CreateJob
//
// ******************************************************
public class CreateJob {
  // ************
  // Constants
  // ************
  private static final String URL_CONTEXT = "http://fsapomt1:8080/QuartzService/Jobs/Create"
  
  // ******************************************************
  // Method: createWithoutTrigger
  //
  // ******************************************************
  public JobDefinition createWithoutTrigger(JobDefinition jobDefinition) {
    Log.info "Trying to save Job Definition (Without Trigger)..."
    
    String jobName = getJobNameWithoutSpaces(jobDefinition.jobName)
 
    String urlQuery = "$URL_CONTEXT/$jobName"
   
    JobDefinition job = new JobDefinition()
    
    new XmlSlurper().parseText(urlQuery.toURL().text).job.each {
      job.jobName = it.jobName.text()
    }
   
    Log.info "Successfully saved and returned Job Definition for $job.jobName"
   
    return job
  }
  
  // ******************************************************
  // Method: createWithoutEndDate
  //
  // ******************************************************
  public JobDefinition createWithoutEndDate(JobDefinition jobDefinition) {
    Log.info "Trying to save Job Definition (No End Date)..."
    
    String        jobName       = getJobNameWithoutSpaces(jobDefinition.jobName)
    StringBuilder textStartDate = new StringBuilder(new SimpleDateFormat('yyyyMMdd').format(jobDefinition.startDate))
    String        textStartTime = getTextTime(jobDefinition.startTime)
    
    String urlQuery = "$URL_CONTEXT/$jobName/$textStartDate/$textStartTime/$jobDefinition.repeatInterval"
          
    JobDefinition job = new JobDefinition()
    
    new XmlSlurper().parseText(urlQuery.toURL().text).job.each {
      job.jobName        = it.jobName.text()
      job.startDate      = getDateFromString(it.startDate.text())
      job.startTime      = getLongFromString(it.startTime.text())
      job.repeatInterval = getIntegerFromString(it.repeatInterval.text())
    }
    
    Log.info "Successfully saved and returned Job Definition for $job.jobName"
    
    return job
  }
  
  // ******************************************************
  // Method: createWithEndDate
  //
  // ******************************************************
  public JobDefinition createWithEndDate(JobDefinition jobDefinition) {
    Log.info "Trying to save Job Definition (With End Date)..."
    
    String        jobName       = getJobNameWithoutSpaces(jobDefinition.jobName)
    StringBuilder textStartDate = new StringBuilder(new SimpleDateFormat('yyyyMMdd').format(jobDefinition.startDate))
    String        textStartTime = getTextTime(jobDefinition.startTime)
    StringBuilder textEndDate   = new StringBuilder(new SimpleDateFormat('yyyyMMdd').format(jobDefinition.endDate))
    String        textEndTime   = getTextTime(jobDefinition.endTime)
    
    String urlQuery = "$URL_CONTEXT/$jobName/$textStartDate/$textStartTime/$jobDefinition.repeatInterval/$textEndDate/$textEndTime"

    JobDefinition job = new JobDefinition()
    
    new XmlSlurper().parseText(urlQuery.toURL().text).job.each {
      job.jobName        = it.jobName.text()
      job.startDate      = getDateFromString(it.startDate.text())
      job.startTime      = getLongFromString(it.startTime.text())
      job.repeatInterval = getIntegerFromString(it.repeatInterval.text())
      job.endDate        = getDateFromString(it.endDate.text())
      job.endTime        = getLongFromString(it.endTime.text())
    }
    
    Log.info "Successfully saved and returned Job Definition for $job.jobName"
    
    return job
  }
  
  // ******************************************************
  // Method: getJobNameWithoutSpaces
  //
  // Quartz Service does not accept spaces in job name
  // ******************************************************
  private String getJobNameWithoutSpaces(String jobName) {
    return jobName.replaceAll(" ", "");
  }
  
  // ******************************************************
  // Method: getTextTime
  //
  // ******************************************************
  private String getTextTime(Long time) {
    String hourField = ' '
    String minsField = ' '
    Integer millisPerHour = 1000 * 60 * 60
    Integer millisPerMin  = 1000 * 60
   
    Integer hours = time.toInteger() / millisPerHour
    Integer mins  = (time.toInteger() % millisPerHour) / millisPerMin
    
    if (String.valueOf(hours).length() == 1) {
     hourField = '0' + hours.toString()
    }
    else {
      hourField = hours.toString()
    }
    
    if (String.valueOf(mins).length() == 1) {
      minsField = '0' + mins.toString()
    }
    else {
      minsField = mins.toString()
    }
    
    return "$hourField"+"$minsField"
  }
  
  // ******************************************************
  // Method: getDateFromString
  //
  // ******************************************************
  private Date getDateFromString(String textDate) {
    return new SimpleDateFormat("yyyyMMdd").parse(textDate);
  }
  
  // ******************************************************
  // Method: getLongFromString
  //
  // ******************************************************
  private Long getLongFromString(String textTime) {
    return new SimpleDateFormat("HHmm").parse(textTime).time;
  }
  
  // ******************************************************
  // Method: getIntegerFromString
  //
  // Repeat Interval produced in XML is of type String
  // ******************************************************
  private Long getIntegerFromString(String textRepeatInterval) {
    return Integer.parseInt(textRepeatInterval);
  }
}
