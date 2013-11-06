package com.caiso.fit.fitScheduler.client.probeSetupWizard.cards;

import com.caiso.fit.fitScheduler.client.probeSetupWizard.ProbeSetupWizardCard;
import com.extjs.gxt.ui.client.widget.form.CheckBox;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.NumberField;
import com.extjs.gxt.ui.client.widget.form.SimpleComboBox;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.form.ComboBox.TriggerAction;
import com.extjs.gxt.ui.client.widget.form.FormPanel.LabelAlign;
import com.extjs.gxt.ui.client.widget.layout.FormData;
import com.extjs.gxt.ui.client.widget.layout.FormLayout;

// ******************************************************
// Class: SpreadsheetProbeConfigWizardCard
//
// ******************************************************
public class SpreadsheetProbeConfigWizardCard extends ProbeSetupWizardCard {
  private TextField<String>       sourceSpreadsheetNameTextField = null;
  private SimpleComboBox<Integer> sheetNumberComboBox            = null;
  private SimpleComboBox<String>  timestampFormatComboBox        = null;
  private NumberField             startRowNumberField            = null;
  private NumberField             endRowNumberField              = null;
  private CheckBox                setupRulesCheckBox             = null;
  
  public SpreadsheetProbeConfigWizardCard(String cardtitle) {
    super(cardtitle);
    this.setFormPanel(getPanel());
  }
  
  // ******************************************************
  // Method: getPanel
  //
  // *****************************************************
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
    
    sourceSpreadsheetNameTextField = new TextField<String>();
    sourceSpreadsheetNameTextField.setFieldLabel("Spreadsheet Name*");
    sourceSpreadsheetNameTextField.setAllowBlank(true);
    formPanel.add(sourceSpreadsheetNameTextField, formData);

    sheetNumberComboBox = new SimpleComboBox<Integer>();
    sheetNumberComboBox.setFieldLabel("Sheet Number*");
    sheetNumberComboBox.add(1);
    sheetNumberComboBox.add(2);
    sheetNumberComboBox.add(3);
    sheetNumberComboBox.setAllowBlank(false);
    sheetNumberComboBox.setForceSelection(true);
    sheetNumberComboBox.setEditable(false);
    sheetNumberComboBox.setTriggerAction(TriggerAction.ALL);
    sheetNumberComboBox.setSimpleValue(1);
    formPanel.add(sheetNumberComboBox, formData);

    timestampFormatComboBox = new SimpleComboBox<String>();
    timestampFormatComboBox.setFieldLabel("Timestamp Format");
    timestampFormatComboBox.add("DateCell");
    timestampFormatComboBox.add("String");
    timestampFormatComboBox.setSimpleValue("DateCell");
    timestampFormatComboBox.setAllowBlank(false);
    timestampFormatComboBox.setForceSelection(true);
    timestampFormatComboBox.setEditable(false);
    timestampFormatComboBox.setTriggerAction(TriggerAction.ALL);
    formPanel.add(timestampFormatComboBox, formData);

    startRowNumberField = new NumberField();
    startRowNumberField.setFieldLabel("Start Row");
    startRowNumberField.setAllowBlank(true);
    startRowNumberField.setAllowDecimals(false);
    startRowNumberField.setAllowNegative(false);
    formPanel.add(startRowNumberField, formData);

    endRowNumberField = new NumberField();
    endRowNumberField.setFieldLabel("End Row");
    endRowNumberField.setAllowBlank(true);
    endRowNumberField.setAllowDecimals(false);
    endRowNumberField.setAllowNegative(false);
    formPanel.add(endRowNumberField, formData);
    
    setupRulesCheckBox = new CheckBox();
    setupRulesCheckBox.setFieldLabel("Setup Rules?");
    setupRulesCheckBox.setBoxLabel("");
    formPanel.add(setupRulesCheckBox, formData);
    return formPanel;
  }
}
