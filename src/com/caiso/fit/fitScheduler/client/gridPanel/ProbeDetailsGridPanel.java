package com.caiso.fit.fitScheduler.client.gridPanel;

import gwtupload.client.IFileInput.FileInputType;
import gwtupload.client.IUploadStatus.Status;
import gwtupload.client.IUploader;
import gwtupload.client.IUploader.OnFinishUploaderHandler;
import gwtupload.client.IUploader.Utils;
import gwtupload.client.SingleUploader;

import java.util.List;

import com.caiso.fit.fitScheduler.client.probeSetupWizard.ProbeSetupContainer;
import com.caiso.fit.fitScheduler.client.toolbar.ProbeGridToolBar;

import com.caiso.fit.fitScheduler.shared.entities.DatabaseProbe;
import com.caiso.fit.fitScheduler.shared.entities.JobDefinition;
import com.caiso.fit.fitScheduler.shared.entities.Probe;
import com.caiso.fit.fitScheduler.shared.entities.ProbeAudit;
import com.caiso.fit.fitScheduler.shared.entities.SpreadsheetProbe;
import com.caiso.fit.fitScheduler.shared.FitSchedulerServiceAsync;

import com.extjs.gxt.ui.client.Style.Orientation;
import com.extjs.gxt.ui.client.Style.SelectionMode;

import com.extjs.gxt.ui.client.binding.FormBinding;

import com.extjs.gxt.ui.client.data.BaseListLoader;
import com.extjs.gxt.ui.client.data.BeanModel;
import com.extjs.gxt.ui.client.data.BeanModelLookup;
import com.extjs.gxt.ui.client.data.BeanModelReader;
import com.extjs.gxt.ui.client.data.ListLoadResult;
import com.extjs.gxt.ui.client.data.ListLoader;
import com.extjs.gxt.ui.client.data.RpcProxy;

import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.GridEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.MenuEvent;
import com.extjs.gxt.ui.client.event.SelectionChangedEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;

import com.extjs.gxt.ui.client.store.ListStore;

import com.extjs.gxt.ui.client.util.Margins;

import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.MessageBox;

import com.extjs.gxt.ui.client.widget.button.Button;

import com.extjs.gxt.ui.client.widget.form.FormPanel;

import com.extjs.gxt.ui.client.widget.grid.Grid;

import com.extjs.gxt.ui.client.widget.layout.CardLayout;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.layout.FormData;
import com.extjs.gxt.ui.client.widget.layout.RowData;
import com.extjs.gxt.ui.client.widget.layout.RowLayout;

import com.extjs.gxt.ui.client.widget.menu.Menu;
import com.extjs.gxt.ui.client.widget.menu.MenuItem;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.XMLParser;

// ******************************************************
// Class: ProbeDetailsGridPanel
//
// ******************************************************
public class ProbeDetailsGridPanel extends ContentPanel {
  private Grid<BeanModel>          probeGrid           = null;
  private GenericDetailsFormPanel  genericDetailsPanel = null;
  private ProbeDefinitionCardPanel probeDefCardPanel   = null;
  private ContentPanel             detailsPanel        = null;
  private FormBinding              genericDefBinding   = null;
  private FormBinding              dbProbeDefBinding   = null;
  private FormBinding              xlProbeDefBinding   = null;
  private ContentPanel             gridPanel           = null;
  private ProbeGridToolBar         gridToolbar         = null;
  
  public ProbeDetailsGridPanel(final FitSchedulerServiceAsync  service, 
                               AsyncCallback<List<ProbeAudit>> callbackOnAuditLoad) {
    setHeaderVisible(false);
    setBodyBorder(false);
    setBorders(true);
    setFrame(false);
    setLayout(new RowLayout(Orientation.HORIZONTAL));
    
    RpcProxy<List<Probe>> proxy = new RpcProxy<List<Probe>>() {  
    
      @Override  
      public void load(Object loadConfig, AsyncCallback<List<Probe>> callback) {  
        service.getAllProbes(callback);  
      }  
    };  
    
    ListLoader<ListLoadResult<BeanModel>> loader = new BaseListLoader<ListLoadResult<BeanModel>>(proxy, new BeanModelReader());
    
    final ListStore<BeanModel> store = new ListStore<BeanModel>(loader);  
    
    loader.load();  
    
    detailsPanel = new ContentPanel();
    detailsPanel.setBodyBorder(false);
    detailsPanel.setBorders(true);
    detailsPanel.setHeading("Probe Details");
    detailsPanel.setStyleName("headingName");
    detailsPanel.setLayout(new RowLayout(Orientation.HORIZONTAL));
    
    detailsPanel.add(buildGenericDetailsFormPanel(), new RowData(0.50D, 1.0D, new Margins(0)));
    
    detailsPanel.add(buildProbeDefinitionCardPanel(), new RowData(0.50D, 1.0D, new Margins(0)));
    
    dbProbeDefBinding = new FormBinding((FormPanel)((LayoutContainer) probeDefCardPanel.getItem(1)).getItem(0), true);
    
    xlProbeDefBinding = new FormBinding((FormPanel)((LayoutContainer) probeDefCardPanel.getItem(2)).getItem(0), true);

    genericDefBinding = new FormBinding(genericDetailsPanel, true);

    genericDefBinding.setStore(store);
    dbProbeDefBinding.setStore(store);
    xlProbeDefBinding.setStore(store);
    
    gridPanel = new ContentPanel();
    gridPanel.setBodyBorder(false);
    gridPanel.setBorders(true);
    gridPanel.setHeading("FIT Probes");
    gridPanel.setStyleName("headingName");
    gridPanel.setLayout(new FitLayout());
    
    gridPanel.add(buildGrid(store,               service, 
                            genericDefBinding,   dbProbeDefBinding, 
                            xlProbeDefBinding,   probeDefCardPanel,
                            callbackOnAuditLoad));
    
    add(gridPanel, new RowData(0.60D, 1.0D, new Margins(0)));
    
    add(detailsPanel, new RowData(0.40D, 1.0D, new Margins(0)));
    
    setTopComponent(buildProbeGridToolBar(store, service));
  }

  //******************************************************
  // Method: buildGenericDetailsFormPanel
  //
  // ******************************************************
  private GenericDetailsFormPanel buildGenericDetailsFormPanel() {
    this.genericDetailsPanel = new GenericDetailsFormPanel();
   
    return genericDetailsPanel;
  }

  // ******************************************************
  // Method: buildSpecificDetailsCardPanel
  //
  // ******************************************************
  private ProbeDefinitionCardPanel buildProbeDefinitionCardPanel() {
    this.probeDefCardPanel = new ProbeDefinitionCardPanel();
  
    return probeDefCardPanel;
  }
  
 //******************************************************
 // Method: buildGrid
 //
 // ******************************************************
  @SuppressWarnings("rawtypes")
  private Grid<BeanModel> buildGrid(final ListStore<BeanModel>      store, 
                                    final FitSchedulerServiceAsync  service,
                                    final FormBinding               genericDefBinding,
                                    final FormBinding               dbProbeDefBinding,
                                    final FormBinding               xlProbeDefBinding, 
                                    final ProbeDefinitionCardPanel  probeDefCardPanel, 
                                    final AsyncCallback<List<ProbeAudit>> callbackOnAuditLoad) {
    probeGrid = new Grid<BeanModel>(store, new ColumnModelBuilder().buildMainGridColumns());
    probeGrid.setBorders(false);
    probeGrid.setStripeRows(true);
    probeGrid.setColumnResize(true);
    probeGrid.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    probeGrid.setTrackMouseOver(true);
    probeGrid.setColumnLines(true);
    
    // *********************************
    // CellDoubleClick event listener
    // *********************************
    probeGrid.addListener(Events.CellDoubleClick, new Listener<GridEvent<BeanModel>>() {

      @Override
      public void handleEvent(GridEvent<BeanModel> be) {
        
        final BeanModel beanModel = be.getModel();
        
        new ProbeSetupContainer().getWizard(beanModel, service, store, new AsyncCallback<Probe>() {

          @Override
          public void onFailure(Throwable caught) {
            MessageBox.alert("Failure!", "Double-click operation failed!", null);
          }

          @Override
          public void onSuccess(Probe probe) {
            store.remove(beanModel);
            store.commitChanges();
            
            if (probe.getProbeType().equals("Database Probe")) {  
              store.add(BeanModelLookup.get().getFactory(DatabaseProbe.class).createModel(probe));
              store.commitChanges();
            }
             
            else {
              store.add(BeanModelLookup.get().getFactory(SpreadsheetProbe.class).createModel(probe));
              store.commitChanges();
            }
          }
        });
      }
    });
    
    // *****************************************************
    // RightClick event handler only on spreadsheet probes
    // *****************************************************
    final MenuItem uploadItem = new MenuItem("Upload");
    
    Menu menu = new Menu();
    menu.add(uploadItem);
    
    probeGrid.setContextMenu(menu);
    
    probeGrid.addListener(Events.ContextMenu, new Listener<GridEvent>() {

      @Override
      public void handleEvent(GridEvent be) {
        if (be.getModel().get("probeType").equals("Spreadsheet Probe")) {
          uploadItem.addSelectionListener(new SelectionListener<MenuEvent>() {

            @Override
            public void componentSelected(MenuEvent ce) {
              getUploaderWindow().show();
            }
          });
        }
      }
    });
    
    // *********************************
    // SelectionChange event listener
    // *********************************
    probeGrid.getSelectionModel().addListener(Events.SelectionChange, new Listener<SelectionChangedEvent<BeanModel>>() {
      
      @Override
      public void handleEvent(final SelectionChangedEvent<BeanModel> be) {
        
        if (be.getSelection().size() > 0) {  
          genericDefBinding.bind(be.getSelection().get(0));
          dbProbeDefBinding.bind(be.getSelection().get(0));
          xlProbeDefBinding.bind(be.getSelection().get(0));
          
          if (be.getSelectedItem().get("probeType").toString().equals("Database Probe")) {
            ((CardLayout)probeDefCardPanel.getLayout()).setActiveItem(probeDefCardPanel.getItem(1));
          }
          else {
            ((CardLayout)probeDefCardPanel.getLayout()).setActiveItem(probeDefCardPanel.getItem(2));
          }
          
          String probeName = be.getSelectedItem().get("probeName").toString();
          
          service.getProbeAudit(probeName, new AsyncCallback<List<ProbeAudit>>() {
            
            @Override
            public void onSuccess(List<ProbeAudit> result) {
              callbackOnAuditLoad.onSuccess(result);
            }
            
            @Override
            public void onFailure(Throwable caught) {
              MessageBox.alert("Failure!", "Could NOT get audit for probe!", null);
            }
          });
          
          // *********************************
          // Delete button selection listener
          // *********************************
          Button deleteButton = (Button)gridToolbar.getItemByItemId("deleteButton");
          deleteButton.enable();
          
          deleteButton.addSelectionListener(new SelectionListener<ButtonEvent>() {
            
            @Override
            public void componentSelected(ButtonEvent ce) {
              service.deleteProbe(be.getSelectedItem().get("probeName").toString(), new AsyncCallback<String>() {
                
                @Override
                public void onSuccess(String result) {
                  store.remove(be.getSelectedItem());
                  store.commitChanges();
                }
                
                @Override
                public void onFailure(Throwable caught) {
                  MessageBox.alert("Failure", "Could NOT delete probe!", null);
                }
              });
            }
          });
          
          // *********************************
          // Pause button selection listener
          // *********************************
          Button pauseButton = (Button)gridToolbar.getItemByItemId("pauseButton");
          pauseButton.enable();
          
          pauseButton.addSelectionListener(new SelectionListener<ButtonEvent>() {

            @Override
            public void componentSelected(ButtonEvent ce) {
              service.pauseProbe(be.getSelectedItem().get("probeName").toString(), new AsyncCallback<List<JobDefinition>>() {

                @Override
                public void onFailure(Throwable caught) {
                  MessageBox.alert("Failure", "Could NOT pause the probe!", null);
                }

                @Override
                public void onSuccess(List<JobDefinition> result) {
                }
              });
            }
          });
          
          // *********************************
          // Resume button selection listener
          // *********************************
          Button resumeButton = (Button)gridToolbar.getItemByItemId("resumeButton");
          resumeButton.enable();
          
          resumeButton.addSelectionListener(new SelectionListener<ButtonEvent>() {

            @Override
            public void componentSelected(ButtonEvent ce) {
              service.resumeProbe(be.getSelectedItem().get("probeName").toString(), new AsyncCallback<List<JobDefinition>>() {

                @Override
                public void onFailure(Throwable caught) {
                  MessageBox.alert("Failure", "Could NOT resume the probe!", null);
                }

                @Override
                public void onSuccess(List<JobDefinition> result) {
                }
              });
            }
          });
          
          // *********************************
          // Rules button selection listener
          // *********************************
          Button rulesButton = (Button)gridToolbar.getItemByItemId("rulesButton");
          rulesButton.enable();
          
          rulesButton.addSelectionListener(new SelectionListener<ButtonEvent>() {

            @Override
            public void componentSelected(ButtonEvent ce) {
              new RulesGrid(be.getSelectedItem().get("probeName").toString(), service).show();
            }
          });
        }
        else {
          genericDefBinding.unbind();
          dbProbeDefBinding.unbind();
          xlProbeDefBinding.unbind();
        }
      }
    });
    
    return probeGrid;
  }

  // ******************************************************
  // Method: getUploaderWindow
  //
  // ******************************************************
  private com.extjs.gxt.ui.client.widget.Window getUploaderWindow() {
    SingleUploader uploader = new SingleUploader(FileInputType.LABEL);
    uploader.setServletPath("uploader.fileUpload");
    
    uploader.addOnFinishUploadHandler(new OnFinishUploaderHandler() {
      
      @Override
      public void onFinish(IUploader uploader) {
        if (uploader.getStatus() == Status.SUCCESS) {
          String response = uploader.getServerResponse();
          
          if (response != null) {
            Document doc = XMLParser.parse(response);
            String message = Utils.getXmlNodeValue(doc, "message");
            String finished = Utils.getXmlNodeValue(doc, "finished");
            
            Window.alert("Server response:" + message + "\n" + "Finished: " + finished);
          } 
          else {
            Window.alert("Unaccessible server response");
            uploader.reset();
          }
        } 
        else {
          Window.alert("Uploader Status: \n" + uploader.getStatus());
        }
      }
    });
    
    FormPanel uploaderFormPanel = new FormPanel();
    uploaderFormPanel.setHeaderVisible(false);
    FormData  uploaderFormData  = new FormData();
    uploaderFormPanel.add(uploader, uploaderFormData);
    
    LayoutContainer layoutContainer = new LayoutContainer();
    layoutContainer.add(uploaderFormPanel, uploaderFormData);
    
    com.extjs.gxt.ui.client.widget.Window window = new com.extjs.gxt.ui.client.widget.Window();
    window.setHeaderVisible(false);
    window.add(layoutContainer);
    
    return window;
  }

  // ******************************************************
  // Method: buildProbeGridToolBar
  //
  // ******************************************************
  private ProbeGridToolBar buildProbeGridToolBar(ListStore<BeanModel>     mainStore, 
                                                 FitSchedulerServiceAsync service) {
    this.gridToolbar = new ProbeGridToolBar(mainStore, service);
   
    return gridToolbar;
  }
}
