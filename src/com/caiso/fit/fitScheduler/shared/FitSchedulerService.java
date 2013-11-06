package com.caiso.fit.fitScheduler.shared;

import java.util.List;

import com.caiso.fit.fitScheduler.shared.entities.ColumnMapping;
import com.caiso.fit.fitScheduler.shared.entities.JobDefinition;
import com.caiso.fit.fitScheduler.shared.entities.Probe;
import com.caiso.fit.fitScheduler.shared.entities.ProbeAudit;
import com.caiso.fit.fitScheduler.shared.entities.Rule;
import com.caiso.fit.fitScheduler.shared.entities.RuleAudit;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

// ******************************************************
// Interface: FitSchedulerService
//
// ******************************************************
@RemoteServiceRelativePath("fitSchedulerService")
public interface FitSchedulerService extends RemoteService {

  public JobDefinition saveJobDefinitionWithoutTrigger(JobDefinition jobDefinition);
  
  public JobDefinition saveJobDefinitionWithoutEndDate(JobDefinition jobDefinition);
  
  public JobDefinition saveJobDefinitionWithEndDate(JobDefinition jobDefinition);
  
  public JobDefinition updateJobDefinition(JobDefinition jobDefinition);
  
  public Probe saveProbeDefinition(Probe probe);

  public Boolean checkForUniqueJobName(String jobName);

  public List<ProbeAudit> getProbeAudit(String probeName);
  
  public String saveRule(Rule rule);
  
  public List<Probe> getAllProbes();
  
  public Probe getProbeDetails(Probe probe);
  
  public ProbeAudit rerunProbe(Integer dataLoadID);
  
  public String deleteProbe(String probeName);
  
  public List<JobDefinition> resumeProbe(String probeName);
  
  public List<JobDefinition> pauseProbe(String probeName);
  
  public List<RuleAudit> getRulesAudit(String probeName);
  
  public List<String> getColumnsFromMappings(String probeName);
  
  public String saveColumnMappings(List<ColumnMapping> colConfigList); 
  
  public List<ColumnMapping> getColumnMappingsFromSqlQuery(String sqlQuery, String jobName);
}
