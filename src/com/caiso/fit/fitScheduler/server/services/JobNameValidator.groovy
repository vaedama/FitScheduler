package com.caiso.fit.fitScheduler.server.services

import groovy.sql.Sql
import javax.naming.InitialContext
import javax.sql.DataSource

// ******************************************************
// Class: JobNameValidator
//
// ******************************************************
public class JobNameValidator {
 // **********
 // Constants
 // **********
  private static final String FIT_CONFIG_JNDI = 'FIT_CONFIG_TEST'
  
  // ******************************************************
  // Method: checkForUniqueJobName
  //
  // ******************************************************
  public Boolean checkForUniqueJobName(String jobName) {
    try {
      Log.info("checkForUniqueJobName.. running service")
      Boolean uniqueJob = null
      
      if (!getSql(FIT_CONFIG_JNDI).firstRow("select PROBE_NAME from PROBE_CONFIG where PROBE_NAME = '$jobName'")) {
        uniqueJob = Boolean.TRUE
      }
      else { 
        uniqueJob = Boolean.FALSE
      }
     
      return uniqueJob
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
  // Method: getSql
  //
  // ******************************************************
  private Sql getSql(String jndiName) {
    DataSource dataSource = (DataSource) new InitialContext().lookup("java:$jndiName")
    
    return (dataSource == null ? null : new Sql(dataSource))
  }
}
