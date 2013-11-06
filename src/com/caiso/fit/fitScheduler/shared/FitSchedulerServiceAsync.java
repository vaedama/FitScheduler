package com.caiso.fit.fitScheduler.shared;

import java.util.List;

import com.caiso.fit.fitScheduler.shared.entities.ColumnMapping;
import com.caiso.fit.fitScheduler.shared.entities.JobDefinition;
import com.caiso.fit.fitScheduler.shared.entities.Probe;
import com.caiso.fit.fitScheduler.shared.entities.ProbeAudit;
import com.caiso.fit.fitScheduler.shared.entities.Rule;
import com.caiso.fit.fitScheduler.shared.entities.RuleAudit;

import com.google.gwt.user.client.rpc.AsyncCallback;

// ******************************************************
// Interface: FitSchedulerServiceAsync
//
// ******************************************************
public interface FitSchedulerServiceAsync {
  
  public void saveJobDefinitionWithoutTrigger(JobDefinition jobDefinition, AsyncCallback<JobDefinition> asyncCallback);

  public void saveJobDefinitionWithoutEndDate(JobDefinition jobDefinition, AsyncCallback<JobDefinition> asyncCallback);
  
  public void saveJobDefinitionWithEndDate(JobDefinition jobDefinition, AsyncCallback<JobDefinition> asyncCallback);
  
  public void updateJobDefinition(JobDefinition jobDefinition, AsyncCallback<JobDefinition> asyncCallback);
  
  public void saveProbeDefinition(Probe probe, AsyncCallback<Probe> asyncCallback);

  public void checkForUniqueJobName(String jobName, AsyncCallback<Boolean> asyncCallback);

  public void getProbeAudit(String probeName, AsyncCallback<List<ProbeAudit>> callback);

  public void saveRule(Rule rule, AsyncCallback<String> callback);

  public void getAllProbes(AsyncCallback<List<Probe>> callback);

  public void getProbeDetails(Probe probe, AsyncCallback<Probe> callback);

  public void rerunProbe(Integer dataLoadID, AsyncCallback<ProbeAudit> asyncCallback);

  public void deleteProbe(String probeName, AsyncCallback<String> asyncCallback);

  public void resumeProbe(String probeName, AsyncCallback<List<JobDefinition>> asyncCallback);
  
  public void pauseProbe(String probeName, AsyncCallback<List<JobDefinition>> asyncCallback);

  public void getRulesAudit(String probeName, AsyncCallback<List<RuleAudit>> callback);

  public void getColumnsFromMappings(String probeName, AsyncCallback<List<String>> asyncCallback);

  public void saveColumnMappings(List<ColumnMapping> columnMappingList, AsyncCallback<String> asyncCallback);

  public void getColumnMappingsFromSqlQuery(String sqlQuery, String jobName, AsyncCallback<List<ColumnMapping>> asyncCallback);
}

