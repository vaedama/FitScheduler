package com.caiso.fit.fitScheduler.client.rulesSetupWizard.cards;

import com.caiso.fit.fitScheduler.client.rulesSetupWizard.RulesSetupWizardCard;

import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.NumberField;
import com.extjs.gxt.ui.client.widget.form.SimpleComboBox;
import com.extjs.gxt.ui.client.widget.form.ComboBox.TriggerAction;
import com.extjs.gxt.ui.client.widget.form.FormPanel.LabelAlign;

import com.extjs.gxt.ui.client.widget.layout.FormData;
import com.extjs.gxt.ui.client.widget.layout.FormLayout;

// ******************************************************
// Class: RangeCheckConfigWizardCard
//
// ******************************************************
public class RangeCheckConfigWizardCard extends RulesSetupWizardCard {
  private SimpleComboBox<String> attributeNameComboBox = null;
  private NumberField            minValueNumberField   = null;
  private NumberField            maxValueNumberField   = null;
  
  public RangeCheckConfigWizardCard(String cardtitle) {
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
    attributeNameComboBox.setItemId("attributeComboBox");
    attributeNameComboBox.setFieldLabel("Attribute Name*");
    attributeNameComboBox.setAllowBlank(false);
    attributeNameComboBox.setForceSelection(true);
    attributeNameComboBox.setEditable(false);
    attributeNameComboBox.setTriggerAction(TriggerAction.ALL);
    formPanel.add(attributeNameComboBox, formData);

    minValueNumberField = new NumberField();
    minValueNumberField.setAllowBlank(false);
    minValueNumberField.setAllowDecimals(true);
    minValueNumberField.setAllowNegative(true);
    minValueNumberField.setFieldLabel("Minimum value*");
    formPanel.add(minValueNumberField, formData);
    
    maxValueNumberField = new NumberField();
    maxValueNumberField.setAllowBlank(false);
    maxValueNumberField.setAllowDecimals(true);
    maxValueNumberField.setAllowNegative(true);
    maxValueNumberField.setFieldLabel("Maximum value*");
    formPanel.add(maxValueNumberField, formData);
    
    return formPanel;
  }
}