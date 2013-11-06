package com.caiso.fit.fitScheduler.client.toolbar;

import com.caiso.fit.fitScheduler.client.probeSetupWizard.ProbeSetupContainer;
import com.caiso.fit.fitScheduler.shared.entities.Probe;
import com.caiso.fit.fitScheduler.shared.FitSchedulerServiceAsync;
import com.extjs.gxt.ui.client.data.BeanModel;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.store.Store;
import com.extjs.gxt.ui.client.util.IconHelper;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.SimpleComboBox;
import com.extjs.gxt.ui.client.widget.form.StoreFilterField;
import com.extjs.gxt.ui.client.widget.form.ComboBox.TriggerAction;
import com.extjs.gxt.ui.client.widget.layout.FlowData;
import com.extjs.gxt.ui.client.widget.toolbar.LabelToolItem;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;

public class ProbeGridToolBar extends ToolBar {
  
  public ProbeGridToolBar(final ListStore<BeanModel>     mainStore, 
                          final FitSchedulerServiceAsync service) {
    final SimpleComboBox<String> filterUsing = new SimpleComboBox<String>();
    filterUsing.setTriggerAction(TriggerAction.ALL);
    filterUsing.setEditable(false);
    filterUsing.setWidth(100);
    filterUsing.add("Probe Name");
    filterUsing.add("Probe Type");
    filterUsing.add("Start Date");
    filterUsing.add("Start Time");
    filterUsing.add("End Date");
    filterUsing.add("End Time");
    filterUsing.add("Repeat Interval");
    filterUsing.setSimpleValue("Probe Type");

    StoreFilterField<BeanModel> field = new StoreFilterField<BeanModel>() {
      
      @Override
      protected boolean doSelect(Store<BeanModel> store, BeanModel parent, BeanModel record, String property, String filter) {
        Probe probe = record.getBean();
          
        switch (filterUsing.getSelectedIndex()) {
          case 0:
            String probeName = probe.getProbeName().toLowerCase();
              
            if (probeName.indexOf(filter.toLowerCase()) != -1) {
              return true;
            }
            break;
          case 1:
            String probeType = probe.getProbeType().toLowerCase();
              
            if (probeType.indexOf(filter.toLowerCase()) != -1) {
              return true;
            }
            break;
          case 2:
            String startDate = probe.getStartDate().toString().toLowerCase();
            
            if (startDate.indexOf(filter.toLowerCase()) != -1) {
              return true;
            }
            break;
          case 3:
            String startTime = probe.getStartTime().toString().toLowerCase();
            
            if (startTime.indexOf(filter.toLowerCase()) != -1) {
              return true;
            }
            break;
          case 4:
            String endDate = probe.getEndDate().toString().toLowerCase();
            
            if (endDate.indexOf(filter.toLowerCase()) != -1) {
              return true;
            }
            break;
          case 5:
            String endTime = probe.getEndTime().toString().toLowerCase();
            
            if (endTime.indexOf(filter.toLowerCase()) != -1) {
              return true;
            }
            break;
          case 6:
            Integer repeatInterval = probe.getRepeatInterval();
            
            if (repeatInterval == Integer.valueOf(filter)) {
              return true;
            }
            break;
          }
          return false;
        }
      };
    field.setWidth(200);
    field.bind(mainStore);
      
    Button addButton        = new Button("Add");
    Button deleteButton     = new Button("Delete");
    Button pauseButton      = new Button("Pause");
    Button resumeButton     = new Button("Resume");
    Button rulesButton      = new Button("Rules");
    
    addButton.setWidth("90px");
    addButton.setItemId("addButton");
    addButton.setIcon(IconHelper.create("resources/images/default/dd/add.gif"));
    
    deleteButton.setWidth("90px");
    deleteButton.disable();
    deleteButton.setItemId("deleteButton");
    deleteButton.setIcon(IconHelper.create("resources/images/default/dd/delete.gif"));
    
    pauseButton.setWidth("90px");
    pauseButton.disable();
    pauseButton.setItemId("pauseButton");
    pauseButton.setIcon(IconHelper.create("resources/images/default/dd/pause.gif"));
    
    resumeButton.setWidth("90px");
    resumeButton.disable();
    resumeButton.setItemId("resumeButton");
    resumeButton.setIcon(IconHelper.create("resources/images/default/dd/resume.gif"));
    
    rulesButton.setWidth("90px");
    rulesButton.disable();
    rulesButton.setItemId("rulesButton");
    rulesButton.setIcon(IconHelper.create("resources/images/default/dd/rules.gif"));
    
    addButton.addSelectionListener(new SelectionListener<ButtonEvent>() {

      @Override
      public void componentSelected(ButtonEvent ce) {
        new ProbeSetupContainer().showNewWizard(service, mainStore);
      }
    });
    
    setSpacing(4);
    setStyleName("toolbarButtons");
    setLayoutData(new FlowData(5));
    add(new LabelToolItem("Filter:"));
    add(field);
    add(new LabelToolItem("Using:"));
    add(filterUsing);
    
    add(addButton);
    add(deleteButton);
    add(pauseButton);
    add(resumeButton);
    add(rulesButton);
  }
}
