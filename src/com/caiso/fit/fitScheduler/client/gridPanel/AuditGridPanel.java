package com.caiso.fit.fitScheduler.client.gridPanel;

import com.caiso.fit.fitScheduler.client.toolbar.AuditGridToolBar;

import com.caiso.fit.fitScheduler.shared.FitSchedulerServiceAsync;
import com.caiso.fit.fitScheduler.shared.entities.ProbeAudit;

import com.extjs.gxt.ui.client.Style.SelectionMode;
import com.extjs.gxt.ui.client.Style.SortDir;

import com.extjs.gxt.ui.client.data.BeanModel;
import com.extjs.gxt.ui.client.data.BeanModelLookup;

import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.GridEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.SelectionListener;

import com.extjs.gxt.ui.client.store.ListStore;

import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.Info;
import com.extjs.gxt.ui.client.widget.MessageBox;

import com.extjs.gxt.ui.client.widget.button.Button;

import com.extjs.gxt.ui.client.widget.grid.Grid;

import com.extjs.gxt.ui.client.widget.layout.FitLayout;

import com.google.gwt.user.client.Timer;

import com.google.gwt.user.client.rpc.AsyncCallback;

// ******************************************************
// Class: AuditGridPanel
//
// ******************************************************
public class AuditGridPanel extends ContentPanel {
  private Grid<BeanModel> auditGrid = null;

  public AuditGridPanel(final ListStore<BeanModel> store, final FitSchedulerServiceAsync service) {
    setBodyBorder(false);
    setBorders(true);
    setFrame(false);
    setHeading("Probe Audit");
    setStyleName("headingName");
    setLayout(new FitLayout());

    store.sort("dataLoadID", SortDir.ASC);
    
    auditGrid = new Grid<BeanModel>(store, new ColumnModelBuilder().buildAuditGridColumns());
    auditGrid.setBorders(false);
    auditGrid.setStripeRows(true);
    auditGrid.setColumnResize(true);
    auditGrid.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    auditGrid.setTrackMouseOver(true);
    auditGrid.setColumnLines(true);

    final AuditGridToolBar auditGridToolBar = new AuditGridToolBar(store);

    // *******************************
    // CellClick event on audit grid
    // *******************************
    auditGrid.addListener(Events.CellClick, new Listener<GridEvent<BeanModel>>() {

      @Override
      public void handleEvent(final GridEvent<BeanModel> be) {
        Button rerunButton = (Button) auditGridToolBar.getItemByItemId("rerunButton");
        rerunButton.enable();

        rerunButton.addSelectionListener(new SelectionListener<ButtonEvent>() {

          @Override
          public void componentSelected(ButtonEvent ce) {
            Integer dataLoadID = (Integer) be.getModel().get("dataLoadID");

            service.rerunProbe(dataLoadID, new AsyncCallback<ProbeAudit>() {

              @Override
              public void onFailure(Throwable caught) {
                MessageBox.alert("Failure!", "Could NOT rerun the job!", null);
              }

              @Override
              public void onSuccess(ProbeAudit result) {
                showStatus();
                BeanModel model = BeanModelLookup.get().getFactory(ProbeAudit.class).createModel(result);
                store.add(model);
                store.getLoader().load();
              }
            });
          }
        });
      }
    });

    add(auditGrid);

    setBottomComponent(auditGridToolBar);
  }
  
  // *******************************************************
  // Method: showStatus
  //
  // ******************************************************
  private void showStatus() {
    final MessageBox savingMessageBox = MessageBox.wait("Progress", "please wait...", "Rerun in process...");
    Timer timer = new Timer() {

      @Override
      public void run() {
        Info.display("Message", "Rerun successful!");
        savingMessageBox.close();
      }
    };

    timer.schedule(1000);
  }
}
