package com.caiso.fit.fitScheduler.client.gridPanel;

import java.util.ArrayList;
import java.util.List;

import com.caiso.fit.fitScheduler.shared.entities.RuleAudit;
import com.caiso.fit.fitScheduler.shared.FitSchedulerServiceAsync;
import com.extjs.gxt.ui.client.data.BaseListLoader;
import com.extjs.gxt.ui.client.data.BeanModel;
import com.extjs.gxt.ui.client.data.BeanModelReader;
import com.extjs.gxt.ui.client.data.ListLoadResult;
import com.extjs.gxt.ui.client.data.ListLoader;
import com.extjs.gxt.ui.client.data.RpcProxy;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.Window;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.google.gwt.user.client.rpc.AsyncCallback;

// ******************************************************
// Class: RulesGrid
//
// ******************************************************
public class RulesGrid extends Window {
  
  public RulesGrid(final String probeName, final FitSchedulerServiceAsync service) {
    
    RpcProxy<List<RuleAudit>> proxy = new RpcProxy<List<RuleAudit>>() {

      @Override
      protected void load(Object loadConfig, AsyncCallback<List<RuleAudit>> callback) {
        service.getRulesAudit(probeName, callback);
      }
    };
    
    BeanModelReader reader = new BeanModelReader();
    
    ListLoader<ListLoadResult<BeanModel>> loader = new BaseListLoader<ListLoadResult<BeanModel>>(proxy, reader);
    
    ListStore<BeanModel> store = new ListStore<BeanModel>(loader);
    
    loader.load();
    
    List<ColumnConfig> columns = new ArrayList<ColumnConfig>();
    columns.add(new ColumnConfig("fireID",          "Rule Fire ID", 75));
    columns.add(new ColumnConfig("fireID",          "Rule ID",      50));
    columns.add(new ColumnConfig("executionStatus", "Status",       50));
    columns.add(new ColumnConfig("executionTime",   "Fire Time",    175));
    columns.add(new ColumnConfig("executionNotes",  "Notes",        500));
    
    ColumnModel cm = new ColumnModel(columns);
    
    Grid<BeanModel> grid = new Grid<BeanModel>(store, cm);
    grid.setAutoExpandColumn("executionNotes");
    
    setHeading("Rules Audit");
    setLayout(new FitLayout());
    setClosable(true);
    setSize(850, 450);
    add(grid);
  }
}
