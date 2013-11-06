package com.caiso.fit.fitScheduler.server.services

import groovy.sql.Sql

import java.sql.Timestamp

import java.text.SimpleDateFormat

import java.util.List

import javax.naming.InitialContext

import javax.sql.DataSource

import com.caiso.fit.fitScheduler.shared.entities.ProbeAudit

// ******************************************************
// Class: QueryAudit
//
// ******************************************************
public class QueryAudit {
  // **********
  // Constants
  // **********
  private static final String FIT_CONFIG_JNDI = 'FIT_CONFIG_TEST'
  
  // ******************************************************
  // Method: getAuditForProbe
  //
  // ******************************************************
  public List<ProbeAudit> getAuditForProbe(String probeName) {
    try {
      Log.info("getting audit for $probeName.. running service")
      
      List<ProbeAudit> probeRunAuditlist = new ArrayList<ProbeAudit>()
    
      String queryStatementForAudit = "select * from FIT_DATA_LOADS_AUDIT where DATA_PROBE_NAME = '$probeName'"
     
      getSql(FIT_CONFIG_JNDI).eachRow(queryStatementForAudit) { row ->
      
        ProbeAudit probeAudit = new ProbeAudit()
      
        probeAudit.dataLoadID            = row?.DATA_LOAD_ID
        probeAudit.deletedDataLoadID     = row?.DELETED_DATA_LOAD_ID
        probeAudit.replacementDataLoadID = row?.REPLACEMENT_DATA_LOAD_ID
        probeAudit.runStartTime          = row?.RUN_START_TIME
        probeAudit.runEndTime            = row?.RUN_END_TIME
        probeAudit.elapsedRunTime        = row?.ELAPSED_RUN_TIME
        probeAudit.rowsQueried           = row?.ROWS_QUERIED
        probeAudit.measurementsQueried   = row?.MEASUREMENTS_QUERIED
        probeAudit.rowsInserted          = row?.ROWS_INSERTED
        probeAudit.rowsDeleted           = row?.ROWS_DELETED
        probeAudit.startTime             = textToReadableFormat(row?.START_TIME)
        probeAudit.endTime               = textToReadableFormat(row?.END_TIME)
        probeAudit.successFlag           = row?.SUCCESS_FLAG
      
        probeRunAuditlist << probeAudit
      }
    
      return probeRunAuditlist
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
  // Method: timestampToReadableFormat
  //
  // ******************************************************
  private String timestampToReadableFormat(String timestamp) {
    Date sourceDate = new SimpleDateFormat("dd-MMM-yy hh.mm.ss.SSS a").parse(timestamp)    
    
    Log.info("Parsed date:" + sourceDate.toString());
    
    return new SimpleDateFormat("HH:mm:ss:SSS a").format(sourceDate)
  }
  
  // ******************************************************
  // Method: textToReadableFormat
  //
  // ******************************************************
  private String textToReadableFormat(String timestamp) {
    Date sourceDate = new SimpleDateFormat("yyyyMMddHHmmss").parse(timestamp)
    
    Log.info("Parsed date:" + sourceDate.toString());
    
    return new SimpleDateFormat("HH:mm:ss a").format(sourceDate)
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