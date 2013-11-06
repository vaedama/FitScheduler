package com.caiso.fit.fitScheduler.client.probeSetupWizard.cards;

import com.caiso.fit.fitScheduler.client.probeSetupWizard.ProbeSetupWizardCard;

import com.extjs.gxt.ui.client.widget.form.ComboBox.TriggerAction;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.FormPanel.LabelAlign;
import com.extjs.gxt.ui.client.widget.form.CheckBox;
import com.extjs.gxt.ui.client.widget.form.SimpleComboBox;
import com.extjs.gxt.ui.client.widget.form.TextArea;

import com.extjs.gxt.ui.client.widget.layout.FormData;
import com.extjs.gxt.ui.client.widget.layout.FormLayout;

// ******************************************************
// Class: DatabaseProbeConfigWizardCard
//
// ******************************************************
public class DatabaseProbeConfigWizardCard extends ProbeSetupWizardCard {
  private SimpleComboBox<String> availableJndiNamesComboBox = null;
  private TextArea               sourceSqlQueryTextArea     = null;
  private CheckBox               setupRulesCheckBox         = null;
  
  public DatabaseProbeConfigWizardCard(String cardtitle) {
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

    availableJndiNamesComboBox = new SimpleComboBox<String>();
    availableJndiNamesComboBox.add("FIT_DATA_TEST");
    availableJndiNamesComboBox.add("FIT_SOURCE_TEST");
    availableJndiNamesComboBox.add("FIT_CONFIG_TEST");
    availableJndiNamesComboBox.setAllowBlank(false);
    availableJndiNamesComboBox.setForceSelection(true);
    availableJndiNamesComboBox.setEditable(false);
    availableJndiNamesComboBox.setTriggerAction(TriggerAction.ALL);
    availableJndiNamesComboBox.setFieldLabel("Source JNDI*");
    availableJndiNamesComboBox.setSimpleValue("FIT_CONFIG_TEST");
    formPanel.add(availableJndiNamesComboBox, formData);

    sourceSqlQueryTextArea = new TextArea();
    sourceSqlQueryTextArea.setFieldLabel("Sql Query*");
    sourceSqlQueryTextArea.setAllowBlank(false);
    formPanel.add(sourceSqlQueryTextArea, new FormData(314, 200));
    
    setupRulesCheckBox = new CheckBox();
    setupRulesCheckBox.setFieldLabel("Setup Rules?");
    setupRulesCheckBox.setBoxLabel("");
    formPanel.add(setupRulesCheckBox, formData);
    return formPanel;
  }
}
