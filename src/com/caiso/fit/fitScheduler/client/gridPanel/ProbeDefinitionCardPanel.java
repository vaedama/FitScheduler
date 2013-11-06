package com.caiso.fit.fitScheduler.client.gridPanel;

import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.FieldEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.widget.CardPanel;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.Popup;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.FormPanel.LabelAlign;
import com.extjs.gxt.ui.client.widget.form.HtmlEditor;
import com.extjs.gxt.ui.client.widget.form.NumberField;
import com.extjs.gxt.ui.client.widget.form.TextArea;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.layout.CardLayout;
import com.extjs.gxt.ui.client.widget.layout.CenterLayout;
import com.extjs.gxt.ui.client.widget.layout.FormData;
import com.extjs.gxt.ui.client.widget.layout.FormLayout;

// ******************************************************
// Class: ProbeDefinitionCardPanel
// 
// ******************************************************
public class ProbeDefinitionCardPanel extends CardPanel {
  private TextField<String>  sourceJndiNameField     = null;
  private TextField<String>  timestampNameField      = null;
  private TextArea           sqlQueryField          = null;
  private TextField<String>  spreadsheetNameField    = null;
  private TextField<Integer> sheetNumberField        = null;
  private TextField<String>  timestampCellNameField  = null;
  private TextField<String>  timestampFormatField    = null;
  private NumberField        startRowNumberField     = null;
  private NumberField        endRowNumberField       = null;
  private LayoutContainer    dbProbeDetailsContainer = null;
  private LayoutContainer    xlProbeDetailsContainer = null;
  private LayoutContainer    blankContainer          = null;
  private CardLayout         cardLayout              = null;
  
  public ProbeDefinitionCardPanel() {
    cardLayout = new CardLayout();
    
    setBorders(false);
    setLayout(cardLayout);
    
    dbProbeDetailsContainer = new LayoutContainer();
    dbProbeDetailsContainer.add(getDatabaseProbeConfigFormPanel());
    
    xlProbeDetailsContainer = new LayoutContainer();
    xlProbeDetailsContainer.add(getSpreadsheetProbeConfigFormPanel());
    
    blankContainer = new LayoutContainer();
    
    add(blankContainer);
    add(dbProbeDetailsContainer);
    add(xlProbeDetailsContainer);
    
    cardLayout.setActiveItem(getItem(0));
  }
  
  // ******************************************************
  // Method: getDatabaseProbeConfigFormPanel
  // 
  // ******************************************************
  private FormPanel getDatabaseProbeConfigFormPanel() { 
    FormPanel formPanel = new FormPanel();
    formPanel.setAutoHeight(false);
    formPanel.setAutoWidth(false);
    formPanel.setLayout(new FormLayout(LabelAlign.LEFT));
    formPanel.setFrame(false);
    formPanel.setBorders(true);
    formPanel.setBodyBorder(true);
    formPanel.setHeaderVisible(true);
    formPanel.setHeading("Database Probe Definition");
    FormData formData = new FormData("100%");

    sourceJndiNameField = new TextField<String>();
    sourceJndiNameField.setName("sourceJndiName");
    sourceJndiNameField.setFieldLabel("Source JNDI");
    sourceJndiNameField.setReadOnly(true);
    formPanel.add(sourceJndiNameField, formData);

    timestampNameField = new TextField<String>();
    timestampNameField.setName("timestampColumnName");
    timestampNameField.setFieldLabel("Timestamp name");
    timestampNameField.setReadOnly(true);
    formPanel.add(timestampNameField, formData);

    sqlQueryField = new TextArea();
    sqlQueryField.setName("sqlQuery");
    sqlQueryField.setFieldLabel("Sql Query");
    sqlQueryField.setReadOnly(true);
    
    sqlQueryField.addListener(Events.OnClick, new Listener<FieldEvent>() {

      @Override
      public void handleEvent(FieldEvent be) {
        Popup popupWindow = new Popup();
        popupWindow.setLayout(new CenterLayout());
        
        HtmlEditor htmlEditor = new HtmlEditor();
        htmlEditor.setShowToolbar(false);
        htmlEditor.setReadOnly(true);
        htmlEditor.setValue(sqlQueryField.getValue());
        
        popupWindow.add(htmlEditor);
        popupWindow.center();
        popupWindow.show();
      }
    });
    
    formPanel.add(sqlQueryField, new FormData(120, 125));
    
    return formPanel;
  }
  
  // ******************************************************
  // Method: getSpreadsheetProbeConfigFormPanel
  //
  // *****************************************************
  private FormPanel getSpreadsheetProbeConfigFormPanel() {
    FormPanel formPanel = new FormPanel();
    formPanel.setAutoHeight(true);
    formPanel.setAutoWidth(true);
    formPanel.setLayout(new FormLayout(LabelAlign.LEFT));
    formPanel.setFrame(false);
    formPanel.setBorders(false);
    formPanel.setBodyBorder(true);
    formPanel.setHeaderVisible(true);
    formPanel.setHeading("Spreadsheet Probe Definition");
    
    FormData formData = new FormData("100%");
    
    spreadsheetNameField = new TextField<String>();
    spreadsheetNameField.setName("sourceSpreadsheetName");
    spreadsheetNameField.setFieldLabel("Sheet Name");
    spreadsheetNameField.setReadOnly(true);
    formPanel.add(spreadsheetNameField, formData);

    sheetNumberField = new TextField<Integer>();
    sheetNumberField.setName("sheetNumber");
    sheetNumberField.setFieldLabel("Sheet Number");
    sheetNumberField.setReadOnly(true);
    formPanel.add(sheetNumberField, formData);

    timestampCellNameField = new TextField<String>();
    timestampCellNameField.setName("timestampColumnName");
    timestampCellNameField.setFieldLabel("Timestamp Name");
    timestampCellNameField.setReadOnly(true);
    formPanel.add(timestampCellNameField, formData);

    timestampFormatField = new TextField<String>();
    timestampFormatField.setName("timestampColumnFormat");
    timestampFormatField.setFieldLabel("Date Format");
    timestampFormatField.setReadOnly(true);
    formPanel.add(timestampFormatField, formData);

    startRowNumberField = new NumberField();
    startRowNumberField.setName("startRow");
    startRowNumberField.setFieldLabel("Start Row");
    startRowNumberField.setReadOnly(true);
    formPanel.add(startRowNumberField, formData);

    endRowNumberField = new NumberField();
    endRowNumberField.setName("endRow");
    endRowNumberField.setFieldLabel("End Row");
    endRowNumberField.setReadOnly(true);
    formPanel.add(endRowNumberField, formData);
    
    return formPanel;
  }
}