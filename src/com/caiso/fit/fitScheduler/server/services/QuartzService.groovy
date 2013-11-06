package com.caiso.fit.fitScheduler.server.services

import java.text.SimpleDateFormat

import java.util.List

import javax.naming.InitialContext

import javax.sql.DataSource

import groovy.sql.GroovyRowResult
import groovy.sql.Sql

import com.caiso.fit.fitScheduler.shared.entities.JobDefinition

// ******************************************************
// Class: QuartzService
//
// ******************************************************
public class QuartzService {
  // **********
  // Constants
  // **********
  private static final String FIT_CONFIG_JNDI = 'FIT_CONFIG_TEST'
  private static final String URL_CONTEXT     = "http://fsapomt1:8080/QuartzService/Jobs"
  
  
  // ******************************************************
  // Method: deleteProbe
  //
  // ******************************************************
  public String deleteProbe(String probeName) {
    try {
      "$URL_CONTEXT/Delete/$probeName".toURL().text
    
      Log.info("Successfully deleted the job with name: $probeName")
    
      GroovyRowResult row = getSql(FIT_CONFIG_JNDI).firstRow("select * from PROBE_CONFIG where PROBE_NAME = '$probeName'")
    
      if (row.PROBE_TYPE.toString().equals("Database Probe")) {
        
        getSql(FIT_CONFIG_JNDI).execute("delete from DATABASE_PROBE_CONFIG where database_probe_name = '$probeName'")
      
        getSql(FIT_CONFIG_JNDI).execute("delete from PROBE_CONFIG where probe_name = '$probeName'")
        
        getSql(FIT_CONFIG_JNDI).execute("delete from FIT_DATA_LOADS_AUDIT where DATA_PROBE_NAME = '$probeName'")
      }
      else {
        getSql(FIT_CONFIG_JNDI).execute("delete from SPREADSHEET_PROBE_CONFIG where spreadsheet_probe_name = '$probeName'")
      
        getSql(FIT_CONFIG_JNDI).execute("delete from PROBE_CONFIG where probe_name = '$probeName'")
        
        getSql(FIT_CONFIG_JNDI).execute("delete from FIT_DATA_LOADS_AUDIT where DATA_PROBE_NAME = '$probeName'")
      }
    }catch (Throwable t) {
      Log.error "Error occurred: $t"
      
      StringWriter stringWriter = new StringWriter()
      t.printStackTrace(new PrintWriter(stringWriter))
      
      Log.debug stringWriter.toString()
      
      getSql(FIT_CONFIG_JNDI)?.connection?.rollback()
    }
    finally {
      getSql(FIT_CONFIG_JNDI)?.connection?.commit()
      getSql(FIT_CONFIG_JNDI)?.connection?.close()
    }
    
    return "Deleted the job with name: $probeName"
  }
  
  // ******************************************************
  // Method: resumeProbe
  //
  // ******************************************************
  public List<JobDefinition> resumeProbe(String probeName) {
    String urlQuery = "$URL_CONTEXT/Start/$probeName"
    
    List<JobDefinition> jobDetailsList = new ArrayList<JobDefinition>()
    
    new XmlSlurper().parseText(urlQuery.toURL().text).job.each {
      
      JobDefinition job = new JobDefinition()
      
      job.jobName        = it.jobName.text()
      job.startDate      = getDateFromString(it.startDate.text())
      job.startTime      = getLongFromString(it.startTime.text())
      job.repeatInterval = getIntegerFromString(it.repeatInterval.text())
      job.endDate        = getDateFromString(it.endDate.text())
      job.endTime        = getLongFromString(it.endTime.text())
      job.status         = it.status.text()
      jobDetailsList << job
    }
    
    Log.info("Successfully resumed the job with name: $probeName");
    
    return jobDetailsList
  }
  
  // ******************************************************
  // Method: pauseProbe
  //
  // ******************************************************
  public List<JobDefinition> pauseProbe(String probeName) {
    String urlQuery = "$URL_CONTEXT/Stop/$probeName"
    
    List<JobDefinition> jobDetailsList = new ArrayList<JobDefinition>()
    
    new XmlSlurper().parseText(urlQuery.toURL().text).job.each {
      
      JobDefinition job = new JobDefinition()
      
      job.jobName        = it.jobName.text()
      job.startDate      = getDateFromString(it.startDate.text())
      job.startTime      = getLongFromString(it.startTime.text())
      job.repeatInterval = getIntegerFromString(it.repeatInterval.text())
      job.endDate        = getDateFromString(it.endDate.text())
      job.endTime        = getLongFromString(it.endTime.text())
      job.status         = it.status.text()
      jobDetailsList << job
    }
    
    Log.info("Successfully paused the job with name: $probeName");
    
    return jobDetailsList
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
  
  // ******************************************************
  // Method: getSql
  //
  // ******************************************************
  private Sql getSql(String jndiName) {
    DataSource dataSource = (DataSource) new InitialContext().lookup("java:$jndiName")
    
    return (dataSource == null ? null : new Sql(dataSource))
  }
}
