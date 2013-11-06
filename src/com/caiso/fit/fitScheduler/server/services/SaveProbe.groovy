package com.caiso.fit.fitScheduler.server.services

import groovy.sql.Sql

import javax.naming.InitialContext

import javax.sql.DataSource

import com.caiso.fit.fitScheduler.shared.entities.DatabaseProbe
import com.caiso.fit.fitScheduler.shared.entities.Probe
import com.caiso.fit.fitScheduler.shared.entities.SpreadsheetProbe

// ******************************************************
// Class: SaveProbe
//
// ******************************************************
public class SaveProbe {
  //**********
  // Constants
  // **********
   private static final String FIT_CONFIG_JNDI = 'FIT_CONFIG_TEST'
   
  // ******************************************************
  // Method: saveProbeToConfig
  //
  // ******************************************************
  public Probe saveProbeToConfig(Probe probe) {
    try {
      String probeName = getSql(FIT_CONFIG_JNDI)?.firstRow("select PROBE_NAME from PROBE_CONFIG where PROBE_NAME = $probe.probeName and PROBE_ID is not null")?.PROBE_NAME as String
    
      if (probeName) {
        executeGenericDetailsUpdate(probe)
        
        if (probe.probeType.equals('Database Probe')) {
          executeDatabaseProbeConfigUpdate(probe)
        }
        else {
          executeSpreadsheetProbeConfigUpdate(probe)
        }
      }
      
      else {
        executeGenericDetailsInsert(probe)
        
        if (probe.probeType.equals('Database Probe')) {
          executeDatabaseProbeConfigInsert(probe)
        }
        else {
          executeSpreadsheetProbeConfigInsert(probe)
        }
      }
      
      return probe
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
  // Method: executeGenericDetailsUpdate
  //
  // ******************************************************
  private void executeGenericDetailsUpdate(Probe probe) {
    String updateStatement = """\
      update PROBE_CONFIG
        set PROBE_TYPE      = '$probe.probeType',           PROBE_DESCRIPTION    = '$probe.probeDescription',
            ROWS_EXPECTED   =  $probe.rowsExpected,         OFFSET_FROM_FIRETIME =  $probe.offset,
            ESTIMATE_FLAG   =  $probe.estimateFlag,         DATA_INTERVAL        =  $probe.dataInterval,
            SOURCE_TIMEZONE = '$probe.sourceTimezone' where PROBE_NAME           = '$probe.probeName'"""
    
    getSql(FIT_CONFIG_JNDI).executeUpdate(updateStatement)
  }
  
  // ******************************************************
  // Method: executeDatabaseProbeConfigUpdate
  //
  // ******************************************************
  private void executeDatabaseProbeConfigUpdate(Probe probe) {
    def dbProbe = probe as DatabaseProbe
    
    String updateStatement = """\
      update DATABASE_PROBE_CONFIG
       set SOURCE_JNDI_NAME = '$dbProbe.sourceJndiName',      SOURCE_TIMESTAMP_COLUMN_NAME = '$dbProbe.timestampColumnName',
           SOURCE_QUERY     = '$dbProbe.sqlQuery'       where DATABASE_PROBE_NAME          = '$dbProbe.probeName'"""
    
    getSql(FIT_CONFIG_JNDI).executeUpdate(updateStatement)
  }
  
  // ******************************************************
  // Method: executeSpreadsheetProbeConfigUpdate
  //
  // ******************************************************
  private void executeSpreadsheetProbeConfigUpdate(Probe probe) {
    def xlProbe = probe as SpreadsheetProbe
    
    String updateStatement = """\
      update SPREADSHEET_PROBE_CONFIG
      set SPREADSHEET_NAME           = '$xlProbe.sourceSpreadsheetName',  SHEET_NUMBER                   =  $xlProbe.sheetNumber,
        SOURCE_TIMESTAMP_COLUMN_NAME = '$xlProbe.timestampColumnName',    SOURCE_TIMESTAMP_COLUMN_FORMAT = '$xlProbe.timestampColumnFormat',
        START_ROW                    =  $xlProbe.startRow,                END_ROW                        =  $xlProbe.endRow
      where SPREADSHEET_PROBE_NAME   = '$xlProbe.probeName'"""
      
   getSql(FIT_CONFIG_JNDI).executeUpdate(updateStatement)
  }
  
  // ******************************************************
  // Method: executeGenericDetailsInsert
  //
  // ******************************************************
  private void executeGenericDetailsInsert(Probe probe) {
    Integer probeId = getSql(FIT_CONFIG_JNDI).firstRow("select FIT_DATA_PROBE_SEQ.nextval from dual").NEXTVAL as Integer
    
    String insertStatement = """\
      insert into PROBE_CONFIG (PROBE_NAME,                  PROBE_TYPE,
                                PROBE_ID,                    PROBE_DESCRIPTION,
                                ROWS_EXPECTED,               OFFSET_FROM_FIRETIME,
                                ESTIMATE_FLAG,               DATA_INTERVAL,
                                SOURCE_TIMEZONE) values
                               ('$probe.probeName',         '$probe.probeType',
                                 $probeId,                  '$probe.probeDescription',
                                 $probe.rowsExpected,        $probe.offset,
                                 $probe.estimateFlag,        $probe.dataInterval,
                                '$probe.sourceTimezone')"""

    getSql(FIT_CONFIG_JNDI).executeInsert(insertStatement)
  }
  
  // ******************************************************
  // Method: executeDatabaseProbeConfigInsert
  //
  // ******************************************************
  private void executeDatabaseProbeConfigInsert(Probe probe) {
    Log.info "Trying to save Database Probe Definition parameters..."
    
    def dbProbe = probe as DatabaseProbe
    
    Integer dbProbeID = getSql(FIT_CONFIG_JNDI).firstRow("select PROBE_ID from PROBE_CONFIG where PROBE_NAME = $probe.probeName").PROBE_ID as Integer
      
    String insertStatement = """\
      insert into DATABASE_PROBE_CONFIG (DATABASE_PROBE_ID,                   DATABASE_PROBE_NAME,
                                         SOURCE_JNDI_NAME,                    SOURCE_TIMESTAMP_COLUMN_NAME,
                                         SOURCE_QUERY) values
                                        ($dbProbeID,                          '$dbProbe.probeName',
                                        '$dbProbe.sourceJndiName',            '$dbProbe.timestampColumnName',
                                        '$dbProbe.sqlQuery')"""
    
    getSql(FIT_CONFIG_JNDI).executeInsert(insertStatement)
  }
 
  // ******************************************************
  // Method: executeSpreadsheetProbeConfigInsert
  //
  // ******************************************************
  private void executeSpreadsheetProbeConfigInsert(Probe probe) {
    Log.info "Trying to save Spreadsheet Probe Definition parameters..."
    
    def xlProbe = probe as SpreadsheetProbe
   
    Integer xlProbeID = getSql(FIT_CONFIG_JNDI).firstRow("select PROBE_ID from PROBE_CONFIG where PROBE_NAME = $probe.probeName").PROBE_ID as Integer
      
    String queryStatement = """\
      insert into SPREADSHEET_PROBE_CONFIG (SPREADSHEET_PROBE_ID,                   SPREADSHEET_PROBE_NAME,
                                            SPREADSHEET_NAME,                       SHEET_NUMBER,
                                            SOURCE_TIMESTAMP_COLUMN_NAME,           SOURCE_TIMESTAMP_COLUMN_FORMAT,
                                            START_ROW,                              END_ROW) values
                                           ( $xlProbeID,                           '$xlProbe.probeName',
                                            '$xlProbe.sourceSpreadsheetName',       $xlProbe.sheetNumber,
                                            '$xlProbe.timestampColumnName',        '$xlProbe.timestampColumnFormat',
                                             $xlProbe.startRow,                     $xlProbe.endRow)"""
    
    getSql(FIT_CONFIG_JNDI).executeInsert(queryStatement)
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
