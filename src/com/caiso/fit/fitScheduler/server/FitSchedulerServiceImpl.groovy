package com.caiso.fit.fitScheduler.server

import java.text.SimpleDateFormat
import java.util.List

import javax.naming.InitialContext
import javax.naming.NamingException

import com.caiso.fit.fitScheduler.server.services.*

import com.caiso.fit.fitScheduler.shared.entities.*

import com.caiso.fit.fitScheduler.shared.FitSchedulerService

import com.google.gwt.user.server.rpc.RemoteServiceServlet

// ******************************************************
// Class: FitSchedulerServiceImpl
//
// ******************************************************
public class FitSchedulerServiceImpl extends RemoteServiceServlet implements FitSchedulerService {
  private static final long   serialVersionUID = 1L
  private static final String URL_CONTEXT      = "http://fsapomt1:8080/QuartzService"
  
  // ******************************************************
  // Method: saveJobDefinitionWithoutTrigger
  //
  // ******************************************************
  @Override
  public JobDefinition saveJobDefinitionWithoutTrigger(JobDefinition jobDefinition) {
    
    return new CreateJob().createWithoutTrigger(jobDefinition)
  }
  
  // ******************************************************
  // Method: saveJobDefinitionWithoutEndDate
  //
  // ******************************************************
  @Override
  public JobDefinition saveJobDefinitionWithoutEndDate(JobDefinition jobDefinition) {
    
    return new CreateJob().createWithoutEndDate(jobDefinition)
  }
  
  // ******************************************************
  // Method: saveJobDefinitionWithEndDate
  //
  // ******************************************************
  @Override
  public JobDefinition saveJobDefinitionWithEndDate(JobDefinition jobDefinition) {
    
    return new CreateJob().createWithEndDate(jobDefinition)
  }
  
  // ******************************************************
  // Method: saveProbeDefinition
  //
  // ******************************************************
  @Override
  public Probe saveProbeDefinition(Probe probe) {
    
    return new SaveProbe().saveProbeToConfig(probe)
  }

  // ******************************************************
  // Method: checkForUniqueJobName
  //
  // ******************************************************
  @Override
  public Boolean checkForUniqueJobName(String jobName) {
    
    return new JobNameValidator().checkForUniqueJobName(jobName)
  }

  // ******************************************************
  // Method: getProbeAudit
  //
  // ******************************************************
  @Override
  public List<ProbeAudit> getProbeAudit(String probeName) {
    
    return new QueryAudit().getAuditForProbe(probeName)
  }

  // ******************************************************
  // Method: updateJobDefinition
  //
  // ******************************************************
  @Override
  public JobDefinition updateJobDefinition(JobDefinition jobDefinition) {
    Log.info "Trying to update Job Definition...Deleting the existing schedule config!"
    
    String jobName = getJobNameWithoutSpaces(jobDefinition.jobName)
    
    String urlQuery = "$URL_CONTEXT/Jobs/Delete/$jobName"
    
    String statusMsg = urlQuery.toURL().text
    
    if (!jobDefinition.startDate && !jobDefinition.startTime && !jobDefinition.endDate && !jobDefinition.endTime && !jobDefinition.repeatInterval) {
      saveJobDefinitionWithoutTrigger(jobDefinition)
    }  
    else if (!jobDefinition.endDate && !jobDefinition.endTime) {
      saveJobDefinitionWithoutEndDate(jobDefinition)
    }
    else {
      saveJobDefinitionWithEndDate(jobDefinition)
    }
    
    return jobDefinition;
  }

  // ******************************************************
  // Method: saveRule
  //
  // ******************************************************
  @Override
  public String saveRule(Rule rule) {
    
    return new RulesService().saveRule(rule)
  }

  // ******************************************************
  // Method: getAllProbes
  //
  // ******************************************************
  @Override
  public List<Probe> getAllProbes() {
    
    return new QueryAllProbes().getAllProbes()
  }

  // ******************************************************
  // Method: getProbeDetails
  //
  // ******************************************************
  @Override
  public Probe getProbeDetails(Probe probe) {
   
    return new QueryProbeDefinition().getProbeDetails(probe)
  }

  // ******************************************************
  // Method: rerunProbe
  //
  // ******************************************************
  @Override
  public ProbeAudit rerunProbe(Integer dataLoadID) {
    
    return new ProbeRerunner().rerun(dataLoadID)
  }

  // ******************************************************
  // Method: deleteProbe
  //
  // ******************************************************
  @Override
  public String deleteProbe(String probeName) {
    
    return new QuartzService().deleteProbe(probeName)
  }
  
  // ******************************************************
  // Method: resumeProbe
  //
  // ******************************************************
  @Override
  public List<JobDefinition> resumeProbe(String probeName) {
    
    return new QuartzService().resumeProbe(probeName)
  }
  
  // ******************************************************
  // Method: pauseProbe
  //
  // ******************************************************
  @Override
  public List<JobDefinition> pauseProbe(String probeName) {
    
    return new QuartzService().pauseProbe(probeName)
  }

  // ******************************************************
  // Method: getRules
  //
  // ******************************************************
  @Override
  public List<RuleAudit> getRulesAudit(String probeName) {
    
    return new RulesService().getRulesAudit(probeName)
  }

  // ******************************************************
  // Method: getColumnsFromMappings
  //
  // ******************************************************
  @Override
  public List<String> getColumnsFromMappings(String probeName) {
    
    return new RulesService().getColumnsFromMappings(probeName)
  }
  
  // ******************************************************
  // Method: saveColumnMappings
  //
  // ******************************************************
  @Override
  public String saveColumnMappings(List<ColumnMapping> colConfigList) {
    
    return new ColumnMapper().saveColumnMappings(colConfigList)
  }
  
  // ******************************************************
  // Method: getMetaDataFromQuery
  //
  // ******************************************************
  @Override
  public List<String> getColumnMappingsFromSqlQuery(String sqlQuery, String jobName) {
    
    return new ColumnMapper().getColumnMappingsFromSqlQuery(sqlQuery, jobName)
  }
  
  // ******************************************************
  // Method: getJobNameWithoutSpaces
  //
  // Quartz Service does not accept spaces in job name
  // ******************************************************
  private String getJobNameWithoutSpaces(String jobName) {
    
    return jobName.replaceAll(" ", "");
  }
}