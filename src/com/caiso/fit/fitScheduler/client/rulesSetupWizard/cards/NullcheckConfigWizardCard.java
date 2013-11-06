package com.caiso.fit.fitScheduler.client.rulesSetupWizard.cards;

import com.caiso.fit.fitScheduler.client.rulesSetupWizard.RulesSetupWizardCard;

import com.extjs.gxt.ui.client.widget.form.ComboBox.TriggerAction;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.FormPanel.LabelAlign;
import com.extjs.gxt.ui.client.widget.form.SimpleComboBox;

import com.extjs.gxt.ui.client.widget.layout.FormData;
import com.extjs.gxt.ui.client.widget.layout.FormLayout;

// ******************************************************
// Class: NullcheckConfigWizardCard
//
// ******************************************************
public class NullcheckConfigWizardCard extends RulesSetupWizardCard {
  private SimpleComboBox<String> attributeNameComboBox = null;

  public NullcheckConfigWizardCard(String cardtitle) {
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
    
    attributeNameComboBox = new SimpleComboBox<String>();
    attributeNameComboBox.setFieldLabel("Attribute Name*");
    attributeNameComboBox.setAllowBlank(false);
    attributeNameComboBox.setForceSelection(true);
    attributeNameComboBox.setEditable(false);
    attributeNameComboBox.setTriggerAction(TriggerAction.ALL);
    attributeNameComboBox.setItemId("attributeComboBox");
    formPanel.add(attributeNameComboBox, formData);
    
    return formPanel;
  }
}