package com.caiso.fit.fitScheduler.client.probeSetupWizard;

import java.util.ArrayList;
import java.util.List;

import com.caiso.fit.fitScheduler.shared.FitSchedulerServiceAsync;
import com.caiso.fit.fitScheduler.shared.entities.ColumnMapping;
import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.Style.SelectionMode;
import com.extjs.gxt.ui.client.data.BeanModel;
import com.extjs.gxt.ui.client.data.BeanModelLookup;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.Window;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.NumberField;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.grid.CellEditor;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.layout.FlowLayout;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;
import com.google.gwt.user.client.rpc.AsyncCallback;

// ******************************************************
// Class: ColumnMappingWindow
//
// ******************************************************
public class ColumnMappingWindow extends Window {
  
  public ColumnMappingWindow(final FitSchedulerServiceAsync service, 
                            List<ColumnMapping>             columnMappingList) {
    setLayout(new FlowLayout(10));
    setSize(550, 400);
    setClosable(true);
    setDraggable(false);
    setResizable(true);
    setModal(true);
    setMaximizable(true);
    setHeading("Column Mapping Configuration");
    
    List<ColumnConfig> configs = new ArrayList<ColumnConfig>();

    // *************
    // Column Name
    // *************
    TextField<String> sourceColumnNameTextField = new TextField<String>();
    sourceColumnNameTextField.setAllowBlank(false);

    ColumnConfig column = new ColumnConfig();
    column.setId("sourceColumnName");
    column.setHeader("Column Name");
    column.setAlignment(HorizontalAlignment.CENTER);
    column.setWidth(250);
    column.setEditor(new CellEditor(sourceColumnNameTextField));
    configs.add(column);

    // *************
    // Attribute ID
    // *************
    column = new ColumnConfig();
    column.setId("targetAttributeID");
    column.setHeader("Attribute ID");
    column.setWidth(150);
    column.setAlignment(HorizontalAlignment.CENTER);
    CellEditor cellEditor = new CellEditor(new NumberField());
    cellEditor.setRevertInvalid(false);
    column.setEditor(cellEditor);
    configs.add(column);

    final ListStore<BeanModel> store = new ListStore<BeanModel>();
    
    List<BeanModel> columnMappingBeanModelList = BeanModelLookup.get().getFactory(ColumnMapping.class).createModel(columnMappingList);
    
    store.add(columnMappingBeanModelList);
    
    ColumnModel cm = new ColumnModel(configs);

    ContentPanel cp = new ContentPanel();
    cp.setHeaderVisible(false);
    cp.setFrame(false);
    cp.setSize(500, 350);
    cp.setLayout(new FitLayout());

    final Grid<BeanModel> grid = new Grid<BeanModel>(store, cm);
    grid.setAutoExpandColumn("sourceColumnName");
    grid.setBorders(true);
    grid.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    cp.add(grid);

    ToolBar toolBar = new ToolBar();
    Button add = new Button("Add Column");
    
    add.addSelectionListener(new SelectionListener<ButtonEvent>() {
      
      @Override
      public void componentSelected(ButtonEvent ce) {
        ColumnMapping colConfig = new ColumnMapping();
        colConfig.setSourceColumnName("New Column 1");
        
        BeanModel columnConfigBeanModel = BeanModelLookup.get().getFactory(ColumnMapping.class).createModel(colConfig);
        
        store.insert(columnConfigBeanModel, 0);  
      }
    });
    
    toolBar.add(add);
    cp.setTopComponent(toolBar);
    cp.setButtonAlign(HorizontalAlignment.CENTER);
    
    cp.addButton(new Button("Reset", new SelectionListener<ButtonEvent>() {

      @Override
      public void componentSelected(ButtonEvent ce) {
        store.rejectChanges();
      }
    }));

    cp.addButton(new Button("Save", new SelectionListener<ButtonEvent>() {

      @Override
      public void componentSelected(ButtonEvent ce) {
        store.commitChanges();
        
        List<BeanModel> beanModelList = store.getModels();
        
        List<ColumnMapping> columnMappingList = new ArrayList<ColumnMapping>();
        
        for (int i = 0; i < beanModelList.size(); i++) {
          BeanModel beanModel = beanModelList.get(i);
         
          ColumnMapping columnMapping = new ColumnMapping();
          columnMapping.setJobName(beanModel.get("jobName").toString());
          columnMapping.setSourceColumnName(beanModel.get("sourceColumnName").toString());
          columnMapping.setTargetAttributeID((Integer)beanModel.get("targetAttributeID"));
          
          columnMappingList.add(columnMapping);
        }
        
        service.saveColumnMappings(columnMappingList, new AsyncCallback<String>() {

          @Override
          public void onFailure(Throwable caught) {
            MessageBox.alert("Failure!", "Could NOT save column mappings!", null);
          }

          @Override
          public void onSuccess(String result) {
            hide();
          }
        });
      }
    }));

    cp.addButton(new Button("Delete", new SelectionListener<ButtonEvent>() {

      @Override
      public void componentSelected(ButtonEvent ce) {
        store.remove(grid.getSelectionModel().getSelectedItem());
        store.commitChanges();  
      }
    }));
    
    add(cp);
  }
}
