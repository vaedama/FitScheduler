package com.caiso.fit.fitScheduler.client.toolbar;

import com.caiso.fit.fitScheduler.shared.entities.ProbeAudit;
import com.extjs.gxt.ui.client.data.BeanModel;
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

// ******************************************************
// Class: AuditGridToolBar
//
// ******************************************************
public class AuditGridToolBar extends ToolBar {
  
  public AuditGridToolBar(ListStore<BeanModel> store) {
    final SimpleComboBox<String> filterUsing = new SimpleComboBox<String>();
    filterUsing.setTriggerAction(TriggerAction.ALL);
    filterUsing.setEditable(false);
    filterUsing.setWidth(150);
    filterUsing.add("Success Flag");
    filterUsing.add("Elapsed Load Time");
    filterUsing.add("Data Load ID");
    filterUsing.add("Rows Queried");
    filterUsing.add("Measurements Queried");
    filterUsing.add("Rows Inserted");
    filterUsing.add("Rows Deleted");
    filterUsing.setSimpleValue("Success Flag");

    StoreFilterField<BeanModel> field = new StoreFilterField<BeanModel>() {
      
      @Override
      protected boolean doSelect(Store<BeanModel> store, BeanModel parent, BeanModel record, String property, String filter) {
        ProbeAudit probeAudit = record.getBean();
          
        switch (filterUsing.getSelectedIndex()) {
          case 0:
            String successFlag = probeAudit.getSuccessFlag();
            
            if (successFlag == String.valueOf(filter)) {
              return true;
            }
            break;
          case 1:
            Integer elapsedRunTime = probeAudit.getElapsedRunTime();
              
            if (elapsedRunTime == Integer.valueOf(filter)) {
              return true;
            }
            break;
          case 2:
            Integer dataLoadID = probeAudit.getDataLoadID();
            
            if (dataLoadID == Integer.valueOf(filter)) {
              return true;
            }
            break;
          case 3:
            Integer rowsQueried = probeAudit.getRowsQueried();
            
            if (rowsQueried == Integer.valueOf(filter)) {
              return true;
            }
            break;
          case 4:
            Integer measurementsQueried = probeAudit.getMeasurementsQueried();
            
            if (measurementsQueried == Integer.valueOf(filter)) {
              return true;
            }
            break;
          case 5:
            Integer rowsInserted = probeAudit.getRowsInserted();
            
            if (rowsInserted == Integer.valueOf(filter)) {
              return true;
            }
            break;
          case 6:
            Integer rowsDeleted = probeAudit.getRowsDeleted();
            
            if (rowsDeleted == Integer.valueOf(filter)) {
              return true;
            }
            break;
          }
          return false;
        }
      };
    field.setWidth(200);
    field.bind(store);
      
    Button rerunButton = new Button("Rerun");
    rerunButton.setItemId("rerunButton");
    rerunButton.setWidth("100px");
    rerunButton.setIcon(IconHelper.create("resources/images/default/dd/rerun.gif"));
    rerunButton.disable();
    
    setSpacing(5);
    setStyleName("toolbarButtons");
    setLayoutData(new FlowData(5));
    add(new LabelToolItem("Filter:"));
    add(field);
    add(new LabelToolItem("Using:"));
    add(filterUsing);
    
    add(rerunButton);
  }
}
