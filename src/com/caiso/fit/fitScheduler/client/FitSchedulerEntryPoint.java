package com.caiso.fit.fitScheduler.client;

import com.caiso.fit.fitScheduler.client.gridPanel.MainPanel;
import com.extjs.gxt.ui.client.widget.Viewport;

import com.extjs.gxt.ui.client.widget.layout.FitLayout;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;

// ******************************************************
// Class: FitSchedulerEntryPoint
//
// ******************************************************
public class FitSchedulerEntryPoint implements EntryPoint {
  private MainPanel mainPanel;
  
  // ******************************************************
  // Method: onModuleLoad
  //
  // ******************************************************
  @Override
  public void onModuleLoad() {
    FitSchedulerEntryPoint.this.getRootPanel();
  }
  
  // ******************************************************
  // Method: getRootPanel
  //
  // ******************************************************
  private void getRootPanel() {
    this.mainPanel = new MainPanel();
    
    Viewport viewport = new Viewport();
    viewport.setLayout(new FitLayout());
    
    viewport.add(this.mainPanel);
    
    RootPanel.get().add(viewport);
    RootPanel.get().addStyleName("rootPanel");
  }
}