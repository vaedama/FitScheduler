package com.caiso.fit.fitScheduler.client.rulesSetupWizard.cards;

import com.caiso.fit.fitScheduler.client.rulesSetupWizard.RulesSetupWizardCard;

import com.extjs.gxt.ui.client.widget.form.ComboBox.TriggerAction;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.FormPanel.LabelAlign;
import com.extjs.gxt.ui.client.widget.form.SimpleComboBox;
import com.extjs.gxt.ui.client.widget.form.TextArea;
import com.extjs.gxt.ui.client.widget.form.TextField;

import com.extjs.gxt.ui.client.widget.layout.FormData;
import com.extjs.gxt.ui.client.widget.layout.FormLayout;

// ******************************************************
// Class: RuleConfigurationWizardCard
//
// ******************************************************
public class RuleConfigurationWizardCard extends RulesSetupWizardCard {
  private TextField<String>      ruleNameTextField       = null;
  private SimpleComboBox<String> ruleTypeComboBox        = null;
  private TextArea               ruleDescriptionTextArea = null;
  
  public RuleConfigurationWizardCard(String cardtitle) {
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
    
    ruleNameTextField = new TextField<String>();
    ruleNameTextField.setFieldLabel("Rule Name*");
    ruleNameTextField.setAllowBlank(false);
    formPanel.add(ruleNameTextField, formData);

    ruleTypeComboBox = new SimpleComboBox<String>();
    ruleTypeComboBox.add("NULL_CHECK");
    ruleTypeComboBox.add("RANGE_CHECK");
    ruleTypeComboBox.add("CUSTOM_SCRIPT");
    ruleTypeComboBox.setFieldLabel("Rule Type*");
    ruleTypeComboBox.setAllowBlank(false);
    ruleTypeComboBox.setForceSelection(true);
    ruleTypeComboBox.setEditable(false);
    ruleTypeComboBox.setTriggerAction(TriggerAction.ALL);
    formPanel.add(ruleTypeComboBox, formData);
      
    ruleDescriptionTextArea = new TextArea();
    ruleDescriptionTextArea.setFieldLabel("Description");
    ruleDescriptionTextArea.setAllowBlank(true);
    formPanel.add(ruleDescriptionTextArea, new FormData(314, 100));
    
    return formPanel;
  }
}
