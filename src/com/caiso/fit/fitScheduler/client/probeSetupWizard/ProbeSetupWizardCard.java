package com.caiso.fit.fitScheduler.client.probeSetupWizard;

import java.util.ArrayList;

import com.caiso.fit.fitScheduler.shared.FitSchedulerServiceAsync;
import com.extjs.gxt.ui.client.Style.Orientation;
import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.event.EventType;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.layout.RowLayout;
import com.google.gwt.user.client.Element;

// ******************************************************
// Class: WizardCard
//
// ******************************************************
public abstract class ProbeSetupWizardCard extends LayoutContainer {
  private ArrayList<Listener<BaseEvent>> finishListeners = null;
  private String                         cardtitle       = null;
  private FormPanel                      panel           = null;
  private ProbeSetupWizardWindow         wizardWindow    = null;
  private String                         htmltext        = null;
  private FitSchedulerServiceAsync       service         = null;
  
  public ProbeSetupWizardCard(String cardtitle) {
    super();
    this.cardtitle = cardtitle;
    setLayout(new RowLayout(Orientation.VERTICAL));
  }
  
  @Override
  protected void onRender(Element parent, int pos) {
    super.onRender(parent, pos);
  }

  public void notifyFinishListeners() {
    if (finishListeners != null) {
      for (Listener<BaseEvent> listener : finishListeners) {
        listener.handleEvent(new BaseEvent(new EventType()));
      }
    }
  }

  public String getCardTitle() {
    return cardtitle;
  }

  public void setHtmlText(String htmltext) {
    this.htmltext = htmltext;
  }
  
  public String getHtmltext() {
    return htmltext;
  }
  
  public FormPanel getFormPanel() {
    return panel;
  }
  
  public Boolean setFormPanel(FormPanel panel) {
    this.panel = panel;
    
    return add(panel);
  }
  
  public void addFinishListener(Listener<BaseEvent> listener) {
    if (finishListeners == null) finishListeners = new ArrayList<Listener<BaseEvent>>();
    finishListeners.add(listener);
  }
  
  public ProbeSetupWizardWindow getWizardWindow() {
    return wizardWindow;
  }

  public void setWizardWindow(ProbeSetupWizardWindow wizardWindow) {
    this.wizardWindow = wizardWindow;
  }

  public Boolean refreshLayout(){
    return layout(true);
  }

  public FitSchedulerServiceAsync getService() {
    return service;
  }

  public void setService(FitSchedulerServiceAsync service) {
    this.service = service;  
  }
}