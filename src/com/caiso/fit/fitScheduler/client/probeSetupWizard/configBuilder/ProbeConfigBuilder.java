package com.caiso.fit.fitScheduler.client.probeSetupWizard.configBuilder;

import java.util.List;

import com.caiso.fit.fitScheduler.client.probeSetupWizard.ProbeSetupWizardCard;

import com.caiso.fit.fitScheduler.shared.entities.DatabaseProbe;
import com.caiso.fit.fitScheduler.shared.entities.JobDefinition;
import com.caiso.fit.fitScheduler.shared.entities.Probe;
import com.caiso.fit.fitScheduler.shared.entities.SpreadsheetProbe;

import com.extjs.gxt.ui.client.widget.form.Field;
import com.extjs.gxt.ui.client.widget.form.NumberField;
import com.extjs.gxt.ui.client.widget.form.RadioGroup;
import com.extjs.gxt.ui.client.widget.form.SimpleComboBox;

// *******************************************************
// Class: ProbeConfigBuilder
//
// *******************************************************
public class ProbeConfigBuilder {
  // *******************************************************
  // Method: build
  //
  // *******************************************************
  public Probe build(List<ProbeSetupWizardCard> cards, JobDefinition jobDefinition) {
    Probe probe;
    
    if (jobDefinition.getJobType().equals("DATABASE_PROBE")) {
      probe = buildDatabaseProbe(cards, jobDefinition);
    }
    else {
      probe = buildSpreadsheetProbe(cards, jobDefinition);
    }
    
    return probe;
  }

  // ******************************************************
  // Method: buildSpreadsheetProbe
  //
  // ******************************************************
  @SuppressWarnings({ "unchecked" })
  private SpreadsheetProbe buildSpreadsheetProbe(List<ProbeSetupWizardCard> cards, JobDefinition jobDefinition) {
    List<Field<?>> xlConfigFields = cards.get(2).getFormPanel().getFields();
    List<Field<?>> genericFields  = cards.get(3).getFormPanel().getFields();
  
    SpreadsheetProbe spreadsheetProbe = new SpreadsheetProbe();
    
    spreadsheetProbe.setProbeName            (jobDefinition.getJobName());
    spreadsheetProbe.setProbeType            ("Spreadsheet Probe");
    spreadsheetProbe.setProbeDescription     (jobDefinition.getJobDescription());
    spreadsheetProbe.setSourceSpreadsheetName(xlConfigFields.get(0).getValue().toString());
    spreadsheetProbe.setSheetNumber          ((((SimpleComboBox<Integer>)xlConfigFields.get(1)).getSimpleValue()));
    spreadsheetProbe.setTimestampColumnFormat((((SimpleComboBox<String>)xlConfigFields.get(2)).getSimpleValue()));
    spreadsheetProbe.setStartRow             (((NumberField)xlConfigFields.get(3)).getValue().intValue());
    spreadsheetProbe.setEndRow               (((NumberField)xlConfigFields.get(4)).getValue().intValue());
    spreadsheetProbe.setTimestampColumnName  (genericFields.get(0).getValue().toString());
    spreadsheetProbe.setDataInterval         ((((NumberField)genericFields.get(1)).getValue().intValue()));
    spreadsheetProbe.setOffset               ((((NumberField)genericFields.get(2)).getValue().intValue()));
    spreadsheetProbe.setSourceTimezone       ((((SimpleComboBox<String>)genericFields.get(3)).getSimpleValue()));
  
    RadioGroup estimateRadioGroup = (RadioGroup)genericFields.get(4);
    
    Boolean estimate = Boolean.parseBoolean(estimateRadioGroup.getValue().getValueAttribute());
    
    if (estimate.equals(Boolean.TRUE)) {
      spreadsheetProbe.setEstimateFlag(1);
    }
    else {
      spreadsheetProbe.setEstimateFlag(0);
    }

    spreadsheetProbe.setRowsExpected(((NumberField)genericFields.get(5)).getValue().intValue());
    
    spreadsheetProbe.setStartDate     (jobDefinition.getStartDate());
    spreadsheetProbe.setStartTime     (longToTime(jobDefinition.getStartTime()));
    spreadsheetProbe.setRepeatInterval(jobDefinition.getRepeatInterval());
    spreadsheetProbe.setEndDate       (jobDefinition.getEndDate());
    spreadsheetProbe.setEndTime       (longToTime(jobDefinition.getEndTime()));
    
    return spreadsheetProbe;
  }

  // ******************************************************
  // Method: buildDatabaseProbe
  //
  // ******************************************************
  @SuppressWarnings({ "unchecked" })
  private DatabaseProbe buildDatabaseProbe(List<ProbeSetupWizardCard> cards, JobDefinition jobDefinition) {
    List<Field<?>> dbConfigFields = cards.get(1).getFormPanel().getFields();
    List<Field<?>> genericFields  = cards.get(3).getFormPanel().getFields();
  
    DatabaseProbe databaseProbe = new DatabaseProbe();
  
    databaseProbe.setProbeName          (jobDefinition.getJobName());
    databaseProbe.setProbeType          ("Database Probe");
    databaseProbe.setProbeDescription   (jobDefinition.getJobDescription());
    databaseProbe.setSourceJndiName     ((((SimpleComboBox<String>)dbConfigFields.get(0)).getSimpleValue()));
    databaseProbe.setSqlQuery           (dbConfigFields.get(1).getValue().toString());
    databaseProbe.setTimestampColumnName(genericFields.get(0).getValue().toString());
    databaseProbe.setDataInterval       (((NumberField)genericFields.get(1)).getValue().intValue());
    databaseProbe.setOffset             (((NumberField)genericFields.get(2)).getValue().intValue());
    databaseProbe.setSourceTimezone     ((((SimpleComboBox<String>)genericFields.get(3)).getSimpleValue()));
  
    RadioGroup estimateRadioGroup = (RadioGroup)genericFields.get(4);
    
    Boolean estimate = Boolean.parseBoolean(estimateRadioGroup.getValue().getValueAttribute());
    if (estimate.equals(Boolean.TRUE)) {
      databaseProbe.setEstimateFlag(1);
    }
    else {
      databaseProbe.setEstimateFlag(0);
    }
    
    databaseProbe.setRowsExpected(((NumberField)genericFields.get(5)).getValue().intValue());
    
    databaseProbe.setStartDate     (jobDefinition.getStartDate());
    databaseProbe.setStartTime     (longToTime(jobDefinition.getStartTime()));
    databaseProbe.setRepeatInterval(jobDefinition.getRepeatInterval());
    databaseProbe.setEndDate       (jobDefinition.getEndDate());
    databaseProbe.setEndTime       (longToTime(jobDefinition.getEndTime()));
    
    return databaseProbe;
  }
  
  // ******************************************************
  // Method: longToTime
  //
  // ******************************************************
  private String longToTime(Long millis) {
    long timeMillis = millis;
    long time       = timeMillis / 1000;
    String seconds  = Integer.toString((int)(time % 60));
    String minutes  = Integer.toString((int)((time % 3600) / 60));
    String hours    = Integer.toString((int)(time / 3600));
    
    for (int i = 0; i < 2; i++) {
      
      if (seconds.length() < 2) {
        seconds = "0" + seconds;
      }
      
      if (minutes.length() < 2) {
        minutes = "0" + minutes;
      }
      
      if (hours.length() < 2) {
        hours = "0" + hours;
      }
    }
    
    return hours +":"+ minutes;
  }
}