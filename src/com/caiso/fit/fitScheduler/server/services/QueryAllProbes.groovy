package com.caiso.fit.fitScheduler.server.services

import groovy.sql.GroovyRowResult
import groovy.sql.Sql

import java.text.SimpleDateFormat

import java.util.List

import javax.naming.InitialContext

import javax.sql.DataSource

import com.caiso.fit.fitScheduler.shared.entities.DatabaseProbe
import com.caiso.fit.fitScheduler.shared.entities.JobDefinition
import com.caiso.fit.fitScheduler.shared.entities.Probe
import com.caiso.fit.fitScheduler.shared.entities.SpreadsheetProbe

// ******************************************************
// Class: QueryAllProbes
//
// ******************************************************
public class QueryAllProbes {
  // **********
  // Constants
  // **********
  private static final String FIT_CONFIG_JNDI = 'FIT_CONFIG_TEST'
  private static final String URL_CONTEXT     = "http://fsapomt1:8080/QuartzService"
  
  // ******************************************************
  // Method: getAllProbes
  //
  // ******************************************************
  public List<Probe> getAllProbes() {
    try {
      Log.info("Trying to get all probes...")
    
      List<Probe> probeList = new ArrayList<Probe>()
    
      List<JobDefinition> quartzJobList = getAllQuartzJobs()
    
      getSql(FIT_CONFIG_JNDI).eachRow("select * from PROBE_CONFIG") { row ->
      
        if (row.PROBE_TYPE.equals("Database Probe")) {
          probeList << getDatabaseProbe(row, quartzJobList)
        }
      
        else {
          probeList << getSpreadsheetProbe(row, quartzJobList)
        }
      }
      Log.info("returning all probes...")
      
      return probeList
    }
    catch (Throwable t) {
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
  }
  
  // ******************************************************
  // Method: getAllQuartzJobs
  //
  // ******************************************************
  private List<JobDefinition> getAllQuartzJobs() {
    List<JobDefinition> jobDetailsList = new ArrayList<JobDefinition>()
    
    String urlQuery = "$URL_CONTEXT/Jobs"
    
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
    
    return jobDetailsList
  }
  
  // ******************************************************
  // Method: getDatabaseProbe
  //
  // ******************************************************
  private DatabaseProbe getDatabaseProbe(Object row, List<JobDefinition> quartzJobList) {
    DatabaseProbe dbProbe = new DatabaseProbe()
    
    Log.info("Getting all generic details for Database Probe found!")
    
    dbProbe.probeName        = row.PROBE_NAME
    dbProbe.probeType        = row.PROBE_TYPE
    dbProbe.probeID          = row.PROBE_ID
    dbProbe.probeDescription = row.PROBE_DESCRIPTION
    dbProbe.rowsExpected     = row.ROWS_EXPECTED
    dbProbe.offset           = row.OFFSET_FROM_FIRETIME
    dbProbe.estimateFlag     = row.ESTIMATE_FLAG
    dbProbe.dataInterval     = row.DATA_INTERVAL
    dbProbe.sourceTimezone   = row.SOURCE_TIMEZONE
    
    String selectStatement = "select * from DATABASE_PROBE_CONFIG where DATABASE_PROBE_NAME = '$dbProbe.probeName'"
    
    GroovyRowResult groovyRowResult = getSql(FIT_CONFIG_JNDI).firstRow(selectStatement)
    
    Log.info("Getting probe definition details for '$dbProbe.probeName'")
    
    dbProbe.sourceJndiName      = groovyRowResult.SOURCE_JNDI_NAME
    dbProbe.timestampColumnName = groovyRowResult.SOURCE_TIMESTAMP_COLUMN_NAME
    dbProbe.sqlQuery            = groovyRowResult.SOURCE_QUERY
    
    for (int i = 0; i < quartzJobList.size(); i++) {
      
      JobDefinition job = quartzJobList.get(i) as JobDefinition
      
      if (job.jobName.equals(dbProbe.probeName)) {
        
        Log.info("Setting job details for probe...")
      
        dbProbe.startDate        = job.startDate
        dbProbe.startTime        = getTextFromMillis(job.startTime)
        dbProbe.repeatInterval   = job.repeatInterval
        dbProbe.endDate          = job.endDate
        dbProbe.endTime          = getTextFromMillis(job.endTime)
        dbProbe.status           = job.status
      }
    }
    
    return dbProbe
  }
  
  // ******************************************************
  // Method: getSpreadsheetProbe
  //
  // ******************************************************
  private SpreadsheetProbe getSpreadsheetProbe(Object row, List<JobDefinition> quartzJobList) {
    SpreadsheetProbe xlProbe = new SpreadsheetProbe()
    
    Log.info("Getting all generic details for Spreadsheet Probe found!")
        
    xlProbe.probeName        = row.PROBE_NAME
    xlProbe.probeType        = row.PROBE_TYPE
    xlProbe.probeID          = row.PROBE_ID
    xlProbe.probeDescription = row.PROBE_DESCRIPTION
    xlProbe.rowsExpected     = row.ROWS_EXPECTED
    xlProbe.offset           = row.OFFSET_FROM_FIRETIME
    xlProbe.estimateFlag     = row.ESTIMATE_FLAG
    xlProbe.dataInterval     = row.DATA_INTERVAL
    xlProbe.sourceTimezone   = row.SOURCE_TIMEZONE
        
    String selectStatement = "select * from SPREADSHEET_PROBE_CONFIG where SPREADSHEET_PROBE_NAME = '$xlProbe.probeName'"
       
    GroovyRowResult groovyRowResult = getSql(FIT_CONFIG_JNDI).firstRow(selectStatement)
       
    Log.info("Getting probe definition details for '$xlProbe.probeName'")
        
    xlProbe.sourceSpreadsheetName = groovyRowResult.SPREADSHEET_NAME
    xlProbe.sheetNumber           = groovyRowResult.SHEET_NUMBER
    xlProbe.timestampColumnName   = groovyRowResult.SOURCE_TIMESTAMP_COLUMN_NAME
    xlProbe.timestampColumnFormat = groovyRowResult.SOURCE_TIMESTAMP_COLUMN_FORMAT
    xlProbe.startRow              = groovyRowResult.START_ROW
    xlProbe.endRow                = groovyRowResult.END_ROW
        
    for (int i = 0; i < quartzJobList.size(); i++) {
      
      JobDefinition job = quartzJobList.get(i) as JobDefinition
      
      if (job.jobName.equals(xlProbe.probeName)) {
        
        Log.info("Setting job details for probe...")
      
        xlProbe.startDate        = job.startDate
        xlProbe.startTime        = getTextFromMillis(job.startTime)
        xlProbe.repeatInterval   = job.repeatInterval
        xlProbe.endDate          = job.endDate
        xlProbe.endTime          = getTextFromMillis(job.endTime)
        xlProbe.status           = job.status
      }
    }
    
    return xlProbe
  }
  
  // ******************************************************
  // Method: getTextFromMillis
  //
  // ******************************************************
  private String getTextFromMillis(Long millis) {
    
    return new SimpleDateFormat("HH:mm").format(new Date(millis))
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
