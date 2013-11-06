package com.caiso.fit.fitScheduler.client.probeSetupWizard.cards;

import java.util.Date;

import com.caiso.fit.fitScheduler.client.probeSetupWizard.ProbeSetupWizardCard;

import com.extjs.gxt.ui.client.widget.form.DateField;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.FormPanel.LabelAlign;
import com.extjs.gxt.ui.client.widget.form.NumberField;
import com.extjs.gxt.ui.client.widget.form.Time;
import com.extjs.gxt.ui.client.widget.form.TimeField;

import com.extjs.gxt.ui.client.widget.layout.FormData;
import com.extjs.gxt.ui.client.widget.layout.FormLayout;

import com.google.gwt.i18n.client.DateTimeFormat;

// ******************************************************
// Class: ScheduleConfigWizardCard
//
// ******************************************************
public class ScheduleConfigWizardCard extends ProbeSetupWizardCard {
  private DateField   startDateDateField        = null;
  private TimeField   startTimeTimeField        = null;
  private NumberField repeatIntervalNumberField = null;
  private DateField   endDateDateField          = null;
  private TimeField   endTimeTimeField          = null;
  
  public ScheduleConfigWizardCard(String cardtitle) {
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
   
    formPanel.addText("You can schedule your probe in future by double-clicking the probe on FIT Probes Grid.<br/><br/>"
        + "Clicking \"Finish\" will close this wizard.<br/><br/>");
    
    startDateDateField = new DateField();
    startDateDateField.setFieldLabel("Start Date");
    startDateDateField.setOriginalValue(new Date());
    startDateDateField.getPropertyEditor().setFormat(DateTimeFormat.getFormat("yyyyMMdd"));
    formPanel.add(startDateDateField, formData);

    startTimeTimeField = new TimeField();
    startTimeTimeField.setFieldLabel("Start Time");
    startTimeTimeField.setOriginalValue(new Time());
    startTimeTimeField.setIncrement(1);
    startTimeTimeField.setFormat(DateTimeFormat.getFormat("HHmm"));
    formPanel.add(startTimeTimeField, formData);

    repeatIntervalNumberField = new NumberField();
    repeatIntervalNumberField.setFieldLabel("Repeat Interval");
    repeatIntervalNumberField.setOriginalValue(1);
    formPanel.add(repeatIntervalNumberField, formData);

    endDateDateField = new DateField();
    endDateDateField.setFieldLabel("End Date");
    endDateDateField.setOriginalValue(new Date());
    endDateDateField.getPropertyEditor().setFormat(DateTimeFormat.getFormat("yyyyMMdd"));
    formPanel.add(endDateDateField, formData);

    endTimeTimeField = new TimeField();
    endTimeTimeField.setFieldLabel("End Time");
    endTimeTimeField.setOriginalValue(new Time());
    endTimeTimeField.setIncrement(1);
    endTimeTimeField.setFormat(DateTimeFormat.getFormat("HHmm"));
    formPanel.add(endTimeTimeField, formData);

    return formPanel;
  }
}
