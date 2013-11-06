package com.caiso.fit.fitScheduler.client.gridPanel;

import java.util.List;

import com.caiso.fit.fitScheduler.shared.FitSchedulerService;
import com.caiso.fit.fitScheduler.shared.FitSchedulerServiceAsync;
import com.caiso.fit.fitScheduler.shared.entities.ProbeAudit;

import com.extjs.gxt.ui.client.Style.Orientation;

import com.extjs.gxt.ui.client.data.BeanModel;
import com.extjs.gxt.ui.client.data.BeanModelLookup;

import com.extjs.gxt.ui.client.store.ListStore;

import com.extjs.gxt.ui.client.util.Margins;

import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.MessageBox;

import com.extjs.gxt.ui.client.widget.layout.RowData;
import com.extjs.gxt.ui.client.widget.layout.RowLayout;

import com.google.gwt.core.client.GWT;

import com.google.gwt.user.client.rpc.AsyncCallback;

// ******************************************************
// Class: MainPanel
//
// ******************************************************
public class MainPanel extends ContentPanel {
  private ProbeDetailsGridPanel           probeDetailsGridPanel = null;
  private AuditGridPanel                  auditGridPanel        = null;
  private FitSchedulerServiceAsync        service               = null;
  private AsyncCallback<List<ProbeAudit>> callbackOnAuditLoad   = null;
  private ListStore<BeanModel>            store                 = null;  
  
  public MainPanel() {
    setStyleAttribute("padding", "5px");
    setLayout(new RowLayout(Orientation.VERTICAL));
    setHeaderVisible(false);
    setBodyBorder(false);
    setBorders(false);
    
    this.service = (FitSchedulerServiceAsync) GWT.create(FitSchedulerService.class);
    
    this.store = new ListStore<BeanModel>();
    
    callbackOnAuditLoad = new AsyncCallback<List<ProbeAudit>>() {
      
      @Override
      public void onSuccess(List<ProbeAudit> result) {
        List<BeanModel> modelList = BeanModelLookup.get().getFactory(ProbeAudit.class).createModel(result);
        store.removeAll();
        store.add(modelList);
      }
      
      @Override
      public void onFailure(Throwable caught) {
        MessageBox.alert("Failure!", "Could NOT load Audit details for probe!", null);
      }
    };
    
    buildTopPanel(service, callbackOnAuditLoad);
    
    buildAuditGridPanel(store, service);
    
    add(probeDetailsGridPanel, new RowData(1.0D, 0.30D, new Margins(0)));
    
    add(auditGridPanel, new RowData(1.0D, 0.70D, new Margins(0)));
  }

  // ******************************************************
  // Method: buildNorthPanel
  //
  // ******************************************************
  private ProbeDetailsGridPanel buildTopPanel(FitSchedulerServiceAsync        service, 
                                              AsyncCallback<List<ProbeAudit>> callbackOnAuditLoad) { 
    this.probeDetailsGridPanel = new ProbeDetailsGridPanel(service,callbackOnAuditLoad);
    
    return probeDetailsGridPanel;
  }
  
  // ******************************************************
  // Method: buildSouthPanel
  //
  // ******************************************************
  private AuditGridPanel buildAuditGridPanel(ListStore<BeanModel> store, FitSchedulerServiceAsync service) {
    this.auditGridPanel = new AuditGridPanel(store, service);
    
    return auditGridPanel;
  }
}
