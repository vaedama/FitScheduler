package com.caiso.fit.fitScheduler.client.probeSetupWizard.configBuilder;

import java.util.List;

import com.caiso.fit.fitScheduler.client.probeSetupWizard.ProbeSetupWizardCard;

import com.caiso.fit.fitScheduler.shared.entities.JobDefinition;

import com.extjs.gxt.ui.client.widget.form.Field;

import com.extjs.gxt.ui.client.widget.form.SimpleComboBox;

// ******************************************************
// Class: JobConfigBuilder
//
// ******************************************************
public class JobConfigBuilder {
  // ******************************************************
  // Method: buildJobDefinition
  //
  // ******************************************************
  @SuppressWarnings("unchecked")
  public JobDefinition buildJobDefinition(ProbeSetupWizardCard wizardCard) {
    JobDefinition jobDefinition = new JobDefinition();
    
    List<Field<?>> fields = wizardCard.getFormPanel().getFields();
    
    jobDefinition.setJobName       (fields.get(0).getValue().toString());
    jobDefinition.setJobType       (((SimpleComboBox<String>)fields.get(1)).getSimpleValue().toString());
    jobDefinition.setJobDescription(fields.get(2).getValue().toString());
  
    return jobDefinition;
  }
}
