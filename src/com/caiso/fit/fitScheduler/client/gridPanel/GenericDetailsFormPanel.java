package com.caiso.fit.fitScheduler.client.gridPanel;

import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.NumberField;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.layout.FormData;
import com.extjs.gxt.ui.client.widget.layout.FormLayout;

// ******************************************************
// Class: GenericDetailsFormPanel
//
// ******************************************************
public class GenericDetailsFormPanel extends FormPanel {
  private NumberField       dataIntervalField = null;
  private NumberField       offsetField       = null;
  private TextField<String> timeZoneField     = null;
  private NumberField       estimateField     = null;
  private NumberField       rowsExpectedField = null;
  
  public GenericDetailsFormPanel() {
    setAutoHeight(false);
    setAutoWidth(false);
    setLayout(new FormLayout(LabelAlign.LEFT));
    setFrame(false);
    setBorders(false);
    setBodyBorder(false);
    setHeaderVisible(true);
    setHeading("Generic Configuration Details");
    FormData formData = new FormData("90%");

    dataIntervalField = new NumberField();
    dataIntervalField.setName("dataInterval");
    dataIntervalField.setFieldLabel("Data Interval");
    dataIntervalField.setReadOnly(true);
    add(dataIntervalField, formData);

    offsetField = new NumberField();
    offsetField.setName("offset");
    offsetField.setFieldLabel("Offset");
    offsetField.setReadOnly(true);
    add(offsetField, formData);

    timeZoneField = new TextField<String>();
    timeZoneField.setName("sourceTimezone");
    timeZoneField.setFieldLabel("Source Timezone");
    timeZoneField.setReadOnly(true);
    add(timeZoneField, formData);
    
    estimateField = new NumberField();
    estimateField.setName("estimateFlag");
    estimateField.setFieldLabel("Estimate Flag");
    estimateField.setReadOnly(true);
    add(estimateField, formData);

    rowsExpectedField = new NumberField();
    rowsExpectedField.setName("rowsExpected");
    rowsExpectedField.setFieldLabel("Rows Expected");
    rowsExpectedField.setReadOnly(true);
    add(rowsExpectedField, formData);
  }
}
