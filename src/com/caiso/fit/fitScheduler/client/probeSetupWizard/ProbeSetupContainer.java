package com.caiso.fit.fitScheduler.client.probeSetupWizard;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.caiso.fit.fitScheduler.client.probeSetupWizard.cards.DatabaseProbeConfigWizardCard;
import com.caiso.fit.fitScheduler.client.probeSetupWizard.cards.GenericConfigWizardCard;
import com.caiso.fit.fitScheduler.client.probeSetupWizard.cards.JobConfigWizardCard;
import com.caiso.fit.fitScheduler.client.probeSetupWizard.cards.ScheduleConfigWizardCard;
import com.caiso.fit.fitScheduler.client.probeSetupWizard.cards.SpreadsheetProbeConfigWizardCard;

import com.caiso.fit.fitScheduler.shared.FitSchedulerServiceAsync;

import com.extjs.gxt.ui.client.data.BeanModel;

import com.extjs.gxt.ui.client.store.ListStore;

import com.extjs.gxt.ui.client.widget.form.DateField;
import com.extjs.gxt.ui.client.widget.form.Time;
import com.extjs.gxt.ui.client.widget.form.TimeField;
import com.extjs.gxt.ui.client.widget.form.Field;
import com.extjs.gxt.ui.client.widget.form.NumberField;
import com.extjs.gxt.ui.client.widget.form.RadioGroup;
import com.extjs.gxt.ui.client.widget.form.SimpleComboBox;
import com.extjs.gxt.ui.client.widget.form.TextArea;
import com.extjs.gxt.ui.client.widget.form.TextField;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.rpc.AsyncCallback;

// ******************************************************
// Class: ProbeSetupContainer
//
// ******************************************************
public class ProbeSetupContainer { 
  
  // ******************************************************
  // Method: showNewWizard
  //
  // ******************************************************
  public void showNewWizard(FitSchedulerServiceAsync service, ListStore<BeanModel> mainStore) {
    
    ArrayList<ProbeSetupWizardCard> wizardCards = new ArrayList<ProbeSetupWizardCard>();
    
    ProbeSetupWizardWindow wizardWindow = new ProbeSetupWizardWindow(wizardCards, service, mainStore);
    
    wizardCards.add(new JobConfigWizardCard("Job Configuration"));
    wizardCards.add(new DatabaseProbeConfigWizardCard("Database Probe Configuration"));
    wizardCards.add(new SpreadsheetProbeConfigWizardCard("Spreadsheet Probe Configuration"));
    wizardCards.add(new GenericConfigWizardCard("Generic Configuration Details"));
    wizardCards.add(new ScheduleConfigWizardCard("Schedule Configuration Details"));
    wizardWindow.show();
  }
  
  // ******************************************************
  // Method: getWizard
  //
  // ******************************************************
  @SuppressWarnings("unchecked")
  public void getWizard(BeanModel beanModel, FitSchedulerServiceAsync service, ListStore<BeanModel> store, @SuppressWarnings("rawtypes") AsyncCallback asyncCallback) {
    ArrayList<ProbeSetupWizardCard> wizardCards = new ArrayList<ProbeSetupWizardCard>();
   
    ProbeSetupWizardWindow wizardWindow = new ProbeSetupWizardWindow(wizardCards, service, store);
    
    // ***************
    // Wizard cards
    // ***************
    JobConfigWizardCard              jobConfigWizardCard      = new JobConfigWizardCard("Job Configiguration");
    DatabaseProbeConfigWizardCard    dbProbeConfigWizardCard  = new DatabaseProbeConfigWizardCard("Database Probe Configuration");
    SpreadsheetProbeConfigWizardCard xlConfigWizardCard       = new SpreadsheetProbeConfigWizardCard("Spreadsheet Probe Configuration");
    GenericConfigWizardCard          genericConfigWizardCard  = new GenericConfigWizardCard("Generic Configuration Details");
    ScheduleConfigWizardCard         scheduleConfigWizardCard = new ScheduleConfigWizardCard("Schedule Configuration Details");
    
    // *********************
    // Form fields in cards
    // *********************
    List<Field<?>> jobConfigFormFields      = jobConfigWizardCard.getFormPanel().getFields();
    List<Field<?>> xlProbeConfigFormFields  = xlConfigWizardCard.getFormPanel().getFields();
    List<Field<?>> dbProbeConfigFormFields  = dbProbeConfigWizardCard.getFormPanel().getFields();
    List<Field<?>> scheduleConfigFormFields = scheduleConfigWizardCard.getFormPanel().getFields();
    List<Field<?>> genericConfigFormFields  = genericConfigWizardCard.getFormPanel().getFields();
    
    // ***********************
    // Job config form fields
    // ***********************
    ((TextField<String>)jobConfigFormFields.get(0)).setValue(beanModel.get("probeName").toString());
   
    SimpleComboBox<String> probeTypeField = (SimpleComboBox<String>)jobConfigFormFields.get(1);
    
    if (beanModel.get("probeType").toString().equals("Database Probe")) {
      probeTypeField.setSimpleValue("DATABASE_PROBE");
    }
    else {
      probeTypeField.setSimpleValue("SPREADSHEET_PROBE");
    }
    
    ((TextArea)jobConfigFormFields.get(2)).setValue(beanModel.get("probeDescription").toString());
    
    // ***********************************
    // Database probe config form fields
    // ***********************************
    if (beanModel.get("probeType").equals("Database Probe")) {
      ((SimpleComboBox<String>)dbProbeConfigFormFields.get(0)).setSimpleValue(beanModel.get("sourceJndiName").toString());
      ((TextArea)              dbProbeConfigFormFields.get(2)).setValue(beanModel.get("sqlQuery").toString());
    }
    // *************************************
    // Spreadsheet probe config form fields
    // *************************************
    else {
      ((TextField<String>)      xlProbeConfigFormFields.get(0)).setValue(beanModel.get("sourceSpreadsheetName").toString());
      ((SimpleComboBox<Integer>)xlProbeConfigFormFields.get(1)).setSimpleValue((Integer)beanModel.get("sheetNumber"));
      ((SimpleComboBox<String>) xlProbeConfigFormFields.get(3)).setSimpleValue(beanModel.get("timestampColumnFormat").toString());
      ((NumberField)            xlProbeConfigFormFields.get(4)).setValue((Integer)beanModel.get("startRow"));
      ((NumberField)            xlProbeConfigFormFields.get(5)).setValue((Integer)beanModel.get("endRow"));
    }
    
    // *************************************
    // Generic config details form fields
    // *************************************
    ((TextField<String>)     genericConfigFormFields.get(0)).setValue(beanModel.get("timestampColumnName").toString());
    ((NumberField)           genericConfigFormFields.get(1)).setValue((Integer)beanModel.get("dataInterval"));
    ((NumberField)           genericConfigFormFields.get(2)).setValue((Integer)beanModel.get("offset"));
    ((SimpleComboBox<String>)genericConfigFormFields.get(3)).setSimpleValue(beanModel.get("sourceTimezone").toString());
    RadioGroup estimateRadioGroup = (RadioGroup)genericConfigFormFields.get(4);
    
    estimateRadioGroup.updateOriginalValue(estimateRadioGroup.getValue());
    
    ((NumberField)genericConfigFormFields.get(5)).setValue((Integer)beanModel.get("rowsExpected"));
    
    // *************************************
    // Schedule config details form fields
    // *************************************
    ((DateField)scheduleConfigFormFields.get(0)).setValue(DateTimeFormat.getFormat("EEE MMM dd HH:mm:ss z yyyy").parseStrict(beanModel.get("startDate").toString()));
    
    String startTimeText = DateTimeFormat.getFormat("HHmm").format(DateTimeFormat.getFormat("HH:mm").parseStrict(beanModel.get("startTime").toString()));
    Date   startTime     = DateTimeFormat.getFormat("HHmm").parseStrict(startTimeText);
    ((TimeField)scheduleConfigFormFields.get(1)).setValue(new Time(startTime, startTimeText));
    
    ((NumberField)scheduleConfigFormFields.get(2)).setValue((Integer)beanModel.get("repeatInterval"));
    ((DateField)  scheduleConfigFormFields.get(3)).setValue(DateTimeFormat.getFormat("EEE MMM dd HH:mm:ss z yyyy").parseStrict(beanModel.get("endDate").toString()));
   
    String endTimeText = DateTimeFormat.getFormat("HHmm").format(DateTimeFormat.getFormat("HH:mm").parseStrict(beanModel.get("endTime").toString()));
    Date   endTime     = DateTimeFormat.getFormat("HHmm").parseStrict(endTimeText);
    ((TimeField)scheduleConfigFormFields.get(4)).setValue(new Time(endTime, endTimeText));
    
    wizardCards.add(jobConfigWizardCard);
    wizardCards.add(dbProbeConfigWizardCard);
    wizardCards.add(xlConfigWizardCard);
    wizardCards.add(genericConfigWizardCard);
    wizardCards.add(scheduleConfigWizardCard);
    
    wizardWindow.show();
  }
}
