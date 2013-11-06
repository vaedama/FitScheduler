package com.caiso.fit.fitScheduler.server.services

import groovy.sql.Sql

import java.util.List
import javax.naming.InitialContext
import javax.sql.DataSource

import com.caiso.fit.fitScheduler.shared.entities.Rule
import com.caiso.fit.fitScheduler.shared.entities.RuleAudit

// ******************************************************
// Class: RulesService
//
// ******************************************************
public class RulesService {
  // **********
  // Constants
  // **********
  private static final String FIT_CONFIG_JNDI = 'FIT_CONFIG_TEST'
  
  // ******************************************************
  // Method: getColumnsFromMappings
  //
  // ******************************************************
   public List<String> getColumnsFromMappings(String probeName) {
     try {
       Log.info "Getting columns..."
     
       String queryStatement = "select * from FIT_COLUMN_CONFIG where PROBE_NAME_MAPPING_FOR_COLS = '$probeName'"
     
       return getSql(FIT_CONFIG_JNDI).rows(queryStatement).getAt("SOURCE_COLUMN") as List<String>
     
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
  // Method: saveRule
  //
  // ******************************************************
  public String saveRule(Rule rule) {
    try {
      Log.info "Saving rule for probe: $rule.probeName..."
    
      Integer ruleID =  getSql(FIT_CONFIG_JNDI).firstRow("select RULE_CONFIG_SEQ.nextval as RULE_ID from dual")?.RULE_ID as Integer
      
      String insertStatement = """\
        insert into RULES_CONFIG (RULE_ID,                  RULE_NAME,
                                  RULE_TYPE,                RULE_DESCRIPTION,
                                  ATTRIBUTE_NAME,           PROBE_NAME,
                                  MIN_VALUE,                MAX_VALUE,
                                  RULE_SCRIPT) values     ($ruleID,            
                                  ?.ruleName,              ?.ruleType,     
                                  ?.ruleDescription,       ?.attributeName,   
                                  ?.probeName,             ?.minValue,
                                  ?.maxValue,              ?.ruleScript
                                 )"""
      
      getSql(FIT_CONFIG_JNDI).execute(insertStatement, rule)
      
      return "Saved rules for probe: $rule.probeName..."
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
  // Method: getRulesAudit
  //
  // ******************************************************
  public List<RuleAudit> getRulesAudit(String probeName) {
    try {
      String queryStatement = "select * from RULES_AUDIT where PROBE_NAME = '$probeName'"
    
      List<Rule> ruleAuditList = new ArrayList<Rule>();
    
      getSql(FIT_CONFIG_JNDI).eachRow(queryStatement) { row->
      
        RuleAudit ruleAudit = new RuleAudit()
      
        ruleAudit.fireID          = row.RULE_LOAD_ID
        ruleAudit.ruleID          = row.RULE_ID
        ruleAudit.executionNotes  = row.RULE_EXEC_NOTES
        ruleAudit.executionStatus = row.RULE_EXEC_STATUS
        ruleAudit.executionTime   = row.RULE_EXEC_TIME
        
        ruleAuditList << ruleAudit
      }
      
      return ruleAuditList
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
  // Method: getSql
  //
  // ******************************************************
  private Sql getSql(String jndiName) {
    DataSource dataSource = (DataSource) new InitialContext().lookup("java:$jndiName")
    
    return (dataSource == null ? null : new Sql(dataSource))
  }
}
