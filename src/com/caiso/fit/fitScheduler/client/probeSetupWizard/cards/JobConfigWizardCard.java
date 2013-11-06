package com.caiso.fit.fitScheduler.client.probeSetupWizard.cards;

import com.caiso.fit.fitScheduler.client.probeSetupWizard.ProbeSetupWizardCard;

import com.extjs.gxt.ui.client.widget.form.ComboBox.TriggerAction;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.FormPanel.LabelAlign;
import com.extjs.gxt.ui.client.widget.form.SimpleComboBox;
import com.extjs.gxt.ui.client.widget.form.TextArea;
import com.extjs.gxt.ui.client.widget.form.TextField;

import com.extjs.gxt.ui.client.widget.layout.FormData;
import com.extjs.gxt.ui.client.widget.layout.FormLayout;

// ******************************************************
// Class: JobConfigWizardCard
//
// ******************************************************
public class JobConfigWizardCard extends ProbeSetupWizardCard {
  private TextField<String>      jobNameTextField        = null;
  private SimpleComboBox<String> jobTypeComboBox         = null;
  private TextArea               jobDescriptionTextField = null;
  
  public JobConfigWizardCard(String cardtitle) {
    super(cardtitle);
    this.setFormPanel(getPanel());
  }

  // ******************************************************
  // Method: getPanel
  //
  // ******************************************************
  private FormPanel getPanel() {
    FormLayout formLayout = new FormLayout(LabelAlign.LEFT);
    formLayout.setLabelWidth(160);

    FormPanel formPanel = new FormPanel();
    formPanel.setHeight(350);
    formPanel.setLayout(formLayout);
    formPanel.setPadding(10);
    formPanel.setFrame(false);
    formPanel.setBorders(false);
    formPanel.setBodyBorder(false);
    formPanel.setHeaderVisible(false);
    
    FormData formData = new FormData("95%");
    
    jobNameTextField = new TextField<String>();
    jobNameTextField.setFieldLabel("Job Name*");
    jobNameTextField.setAllowBlank(false);
    formPanel.add(jobNameTextField, formData);

    jobTypeComboBox = new SimpleComboBox<String>();
    jobTypeComboBox.add("DATABASE_PROBE");
    jobTypeComboBox.add("SPREADSHEET_PROBE");
    jobTypeComboBox.setFieldLabel("Job Type*");
    jobTypeComboBox.setAllowBlank(false);
    jobTypeComboBox.setForceSelection(true);
    jobTypeComboBox.setEditable(false);
    jobTypeComboBox.setTriggerAction(TriggerAction.ALL);
    jobTypeComboBox.setSimpleValue("DATABASE_PROBE");
    formPanel.add(jobTypeComboBox, formData);
    
    jobDescriptionTextField = new TextArea();
    jobDescriptionTextField.setFieldLabel("Description");
    jobDescriptionTextField.setAllowBlank(true);
    formPanel.add(jobDescriptionTextField, new FormData(314, 100));

    return formPanel;
  }
}
