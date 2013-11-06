package com.caiso.fit.fitScheduler.server.services

import java.sql.ResultSetMetaData

import com.caiso.fit.fitScheduler.shared.entities.ColumnMapping
import groovy.sql.Sql

import javax.naming.InitialContext

import javax.sql.DataSource

// ******************************************************
// Class: ColumnMapper
//
// ******************************************************
public class ColumnMapper {
  //**********
  // Constants
  // **********
  private static final String FIT_CONFIG_JNDI = 'FIT_CONFIG_TEST'
   
  // ******************************************************
  // Method: saveColumnMappings
  //
  // ******************************************************
  public String saveColumnMappings(List colConfigList) {
    try {
      Sql sql = getSql(FIT_CONFIG_JNDI)
      
      for (int i = 0; i < colConfigList.size(); i++) {
      
        String  probeName        = colConfigList.get(i).getAt("jobName") as String
        String  sourceColumnName = colConfigList.get(i).getAt("sourceColumnName") as String
        Integer attributeID      = colConfigList.get(i).getAt("targetAttributeID") as Integer
      
        String insertStatement = """\
          insert into FIT_COLUMN_CONFIG 
            (PROBE_NAME_MAPPING_FOR_COLS,   SOURCE_COLUMN,         TARGET_ATTRIBUTE_ID) values 
            ('$probeName',                 '$sourceColumnName',    $attributeID)
        """
        sql.updateCount += sql.executeUpdate(insertStatement)
      }
      
      return "Saved mappings for " + sql.updateCount + " columns"
      
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
  // Method: getColumnMappingsFromSqlQuery
  //
  // ******************************************************
  public List<ColumnMapping> getColumnMappingsFromSqlQuery(String sqlQuery, String jobName) {
    try {
      List<ColumnMapping> columnMappingList = new ArrayList<ColumnMapping>()
    
      getSql(FIT_CONFIG_JNDI).execute("create sequence FIT_COLUMN_CONFIG_SEQ start with 1 increment by 1 nocache");
      
      String queryStatement = "select FIT_COLUMN_CONFIG_SEQ.nextval as TARGET_ATTRIBUTE_ID from dual"
      
      getSql(FIT_CONFIG_JNDI).rows(sqlQuery.substring(0, sqlQuery.indexOf("where", 0)) + " where 1 = 2") { ResultSetMetaData meta->
        (1..meta.columnCount).collect {
         
         ColumnMapping columnMapping = new ColumnMapping()
         
         columnMapping.jobName           = jobName
         columnMapping.sourceColumnName  = meta.getColumnName(it)
         columnMapping.targetAttributeID = getSql(FIT_CONFIG_JNDI).firstRow(queryStatement)?.TARGET_ATTRIBUTE_ID as Integer
         
         columnMappingList << columnMapping
        }
      }
      columnMappingList.remove(0)
      
      return columnMappingList
    }catch (Throwable t) {
      Log.error "Error occurred: $t"
      
      StringWriter stringWriter = new StringWriter()
      t.printStackTrace(new PrintWriter(stringWriter))
      
      Log.debug stringWriter.toString()
      
      getSql(FIT_CONFIG_JNDI)?.connection?.rollback()
    }
    finally {
      getSql(FIT_CONFIG_JNDI).execute("drop sequence FIT_COLUMN_CONFIG_SEQ")
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
