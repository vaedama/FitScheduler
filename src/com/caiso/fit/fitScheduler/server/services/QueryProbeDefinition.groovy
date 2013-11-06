package com.caiso.fit.fitScheduler.server.services

import java.text.DateFormat
import java.text.SimpleDateFormat

import com.caiso.fit.fitScheduler.shared.entities.DatabaseProbe
import com.caiso.fit.fitScheduler.shared.entities.JobDefinition
import com.caiso.fit.fitScheduler.shared.entities.Probe
import com.caiso.fit.fitScheduler.shared.entities.SpreadsheetProbe

import groovy.sql.GroovyRowResult
import groovy.sql.Sql

import javax.naming.InitialContext

import javax.sql.DataSource

// ******************************************************
// Class: QueryProbeDefinition
//
// ******************************************************
public class QueryProbeDefinition {
  // **********
  // Constants
  // **********
  private static final String FIT_CONFIG_JNDI = 'FIT_CONFIG_TEST'
  private static final String URL_CONTEXT     = "http://fsapomt1:8080/QuartzService"
  
  // ******************************************************
  // Method: getProbeDetails
  //
  // ******************************************************
  public Probe getProbeDetails(Probe probe) {
    try {
      if (probe.probeType.equals("Database Probe")) {
        probe = queryDatabaseProbeConfig(probe) as Probe
      }
      else {
        probe = querySpreadsheetProbeConfig(probe) as Probe
      }
      
      return probe
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
  }
  
  // ******************************************************
  // Method: selectDatabaseProbeConfig
  //
  // ******************************************************
  private DatabaseProbe queryDatabaseProbeConfig(Probe probe) {
    DatabaseProbe dbProbe = new DatabaseProbe()
    
    GroovyRowResult row = getSql(FIT_CONFIG_JNDI).firstRow("select * from PROBE_CONFIG where PROBE_NAME = '$probe.probeName'");
    dbProbe.probeName        = row.PROBE_NAME
    dbProbe.probeType        = row.PROBE_TYPE
    dbProbe.probeID          = row.PROBE_ID
    dbProbe.probeDescription = row.PROBE_DESCRIPTION
    dbProbe.rowsExpected     = row.ROWS_EXPECTED
    dbProbe.offset           = row.OFFSET_FROM_FIRETIME
    dbProbe.estimateFlag     = row.ESTIMATE_FLAG
    dbProbe.dataInterval     = row.DATA_INTERVAL
    dbProbe.sourceTimezone   = row.SOURCE_TIMEZONE
    
    GroovyRowResult row1 = getSql(FIT_CONFIG_JNDI).firstRow("select * from DATABASE_PROBE_CONFIG where DATABASE_PROBE_NAME = '$probe.probeName'")
    
    dbProbe.dbProbeID           = row1.DATABASE_PROBE_ID
    dbProbe.sourceJndiName      = row1.SOURCE_JNDI_NAME
    dbProbe.timestampColumnName = row1.SOURCE_TIMESTAMP_COLUMN_NAME
    dbProbe.sqlQuery            = row1.SOURCE_QUERY
    
    JobDefinition job = getJobDefinition(dbProbe.probeName)
    
    dbProbe.startDate      = job.startDate
    dbProbe.startTime      = getTextFromMillis(job.startTime)
    dbProbe.repeatInterval = job.repeatInterval
    dbProbe.endDate        = job.endDate
    dbProbe.endTime        = getTextFromMillis(job.endTime)
    
    return dbProbe
  }
  
  // ******************************************************
  // Method: selectSpreadsheetProbeConfig
  //
  // ******************************************************
  private SpreadsheetProbe querySpreadsheetProbeConfig(Probe probe) {
    SpreadsheetProbe xlProbe = new SpreadsheetProbe()
          
    GroovyRowResult row = getSql(FIT_CONFIG_JNDI).firstRow("select * from PROBE_CONFIG where PROBE_NAME = '$probe.probeName'");
    xlProbe.probeName        = row.PROBE_NAME
    xlProbe.probeType        = row.PROBE_TYPE
    xlProbe.probeID          = row.PROBE_ID
    xlProbe.probeDescription = row.PROBE_DESCRIPTION
    xlProbe.rowsExpected     = row.ROWS_EXPECTED
    xlProbe.offset           = row.OFFSET_FROM_FIRETIME
    xlProbe.estimateFlag     = row.ESTIMATE_FLAG
    xlProbe.dataInterval     = row.DATA_INTERVAL
    xlProbe.sourceTimezone   = row.SOURCE_TIMEZONE

    GroovyRowResult row1 = getSql(FIT_CONFIG_JNDI).firstRow("select * from SPREADSHEET_PROBE_CONFIG where SPREADSHEET_PROBE_NAME = '$probe.probeName'")
          
    xlProbe.xlProbeID             = row1.SPREADSHEET_PROBE_ID
    xlProbe.sourceSpreadsheetName = row1.SPREADSHEET_NAME
    xlProbe.sheetNumber           = row1.SHEET_NUMBER
    xlProbe.timestampColumnName   = row1.SOURCE_TIMESTAMP_COLUMN_NAME
    xlProbe.timestampColumnFormat = row1.SOURCE_TIMESTAMP_COLUMN_FORMAT
    xlProbe.startRow              = row1.START_ROW
    xlProbe.endRow                = row1.END_ROW
    
    JobDefinition job = getJobDefinition(xlProbe.probeName)
    
    xlProbe.startDate      = job.startDate
    xlProbe.startTime      = getTextFromMillis(job.startTime)
    xlProbe.repeatInterval = job.repeatInterval
    xlProbe.endDate        = job.endDate
    xlProbe.endTime        = getTextFromMillis(job.endTime)
    
    return xlProbe
  }
  
  // ******************************************************
  // Method: getJobDefinition
  //
  // ******************************************************
  private JobDefinition getJobDefinition(String jobName) {
    String urlQuery = "$URL_CONTEXT/Jobs/Query/$jobName"
    
    JobDefinition job = new JobDefinition()
    
    new XmlSlurper().parseText(urlQuery.toURL().text).job.each {
      
      job.jobName        = it.jobName.text()
      job.startDate      = getDateFromString(it.startDate.text())
      job.startTime      = getLongFromString(it.startTime.text())
      job.repeatInterval = getIntegerFromString(it.repeatInterval.text())
      job.endDate        = getDateFromString(it.endDate.text())
      job.endTime        = getLongFromString(it.endTime.text())
    }
    
    return job
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
