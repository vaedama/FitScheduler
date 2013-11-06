package com.caiso.fit.fitScheduler.client.probeSetupWizard.cards;

import com.caiso.fit.fitScheduler.client.probeSetupWizard.ProbeSetupWizardCard;

import com.extjs.gxt.ui.client.widget.form.ComboBox.TriggerAction;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.FormPanel.LabelAlign;
import com.extjs.gxt.ui.client.widget.form.NumberField;
import com.extjs.gxt.ui.client.widget.form.Radio;
import com.extjs.gxt.ui.client.widget.form.RadioGroup;
import com.extjs.gxt.ui.client.widget.form.SimpleComboBox;
import com.extjs.gxt.ui.client.widget.form.TextField;

import com.extjs.gxt.ui.client.widget.layout.FormData;
import com.extjs.gxt.ui.client.widget.layout.FormLayout;

// ******************************************************
// Class: GenericConfigWizardCard
//
// ******************************************************
public class GenericConfigWizardCard extends ProbeSetupWizardCard {
  private TextField<String>      timestampColumnNameTextField = null;
  private NumberField            dataIntervalNumberField      = null;
  private NumberField            offsetNumberField            = null;
  private SimpleComboBox<String> timeZoneListComboBox         = null;
  private RadioGroup             estimateRadioGroup           = null;
  private NumberField            rowsExpectedNumberField      = null;
  
  public GenericConfigWizardCard(String cardtitle) {
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
    
    timestampColumnNameTextField = new TextField<String>();
    timestampColumnNameTextField.setFieldLabel("Timestamp Column Name*");
    timestampColumnNameTextField.setAllowBlank(false);
    formPanel.add(timestampColumnNameTextField, formData);
    
    dataIntervalNumberField = new NumberField();
    dataIntervalNumberField.setFieldLabel("Data Interval*");
    dataIntervalNumberField.setAllowBlank(false);
    dataIntervalNumberField.setAllowDecimals(false);
    dataIntervalNumberField.setAllowNegative(false);
    formPanel.add(dataIntervalNumberField, formData);

    offsetNumberField = new NumberField();
    offsetNumberField.setFieldLabel("Offset*");
    offsetNumberField.setAllowDecimals(false);
    offsetNumberField.setAllowNegative(true);
    offsetNumberField.setAllowBlank(false);
    formPanel.add(offsetNumberField, formData);

    timeZoneListComboBox = new SimpleComboBox<String>();
    timeZoneListComboBox.add("GMT");
    timeZoneListComboBox.add("PST");
    timeZoneListComboBox.setFieldLabel("Source Timezone*");
    timeZoneListComboBox.setAllowBlank(false);
    timeZoneListComboBox.setForceSelection(true);
    timeZoneListComboBox.setEditable(false);
    timeZoneListComboBox.setTriggerAction(TriggerAction.ALL);
    timeZoneListComboBox.setSimpleValue("GMT");
    formPanel.add(timeZoneListComboBox, formData);

    Radio estimateSet = new Radio();
    estimateSet.setBoxLabel("Yes");
    estimateSet.setValueAttribute("true");
    
    Radio estimateUnset = new Radio();
    estimateUnset.setBoxLabel("No");
    estimateUnset.setValueAttribute("false");
    
    estimateRadioGroup = new RadioGroup();
    estimateRadioGroup.setFieldLabel("Estimate Rows?");
    estimateRadioGroup.setSelectionRequired(true);
    estimateRadioGroup.add(estimateSet);
    estimateRadioGroup.add(estimateUnset);
    formPanel.add(estimateRadioGroup, formData);

    rowsExpectedNumberField = new NumberField();
    rowsExpectedNumberField.setAllowDecimals(false);
    rowsExpectedNumberField.setAllowNegative(false);
    rowsExpectedNumberField.setFieldLabel("Rows Expected");
    formPanel.add(rowsExpectedNumberField, formData);
    
    return formPanel;
  }
}
