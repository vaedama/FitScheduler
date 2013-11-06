package com.caiso.fit.fitScheduler.client.probeSetupWizard;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.caiso.fit.fitScheduler.client.probeSetupWizard.ColumnMappingWindow;
import com.caiso.fit.fitScheduler.client.probeSetupWizard.configBuilder.JobConfigBuilder;
import com.caiso.fit.fitScheduler.client.probeSetupWizard.configBuilder.ProbeConfigBuilder;
import com.caiso.fit.fitScheduler.client.rulesSetupWizard.RulesSetupContainer;

import com.caiso.fit.fitScheduler.shared.FitSchedulerServiceAsync;
import com.caiso.fit.fitScheduler.shared.entities.ColumnMapping;
import com.caiso.fit.fitScheduler.shared.entities.DatabaseProbe;
import com.caiso.fit.fitScheduler.shared.entities.JobDefinition;
import com.caiso.fit.fitScheduler.shared.entities.Probe;
import com.caiso.fit.fitScheduler.shared.entities.SpreadsheetProbe;

import com.extjs.gxt.ui.client.Style;
import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.Style.LayoutRegion;

import com.extjs.gxt.ui.client.data.BeanModel;
import com.extjs.gxt.ui.client.data.BeanModelLookup;

import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.SelectionListener;

import com.extjs.gxt.ui.client.store.ListStore;

import com.extjs.gxt.ui.client.widget.CardPanel;
import com.extjs.gxt.ui.client.widget.HtmlContainer;
import com.extjs.gxt.ui.client.widget.Info;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.ProgressBar;
import com.extjs.gxt.ui.client.widget.VerticalPanel;
import com.extjs.gxt.ui.client.widget.Window;

import com.extjs.gxt.ui.client.widget.button.Button;

import com.extjs.gxt.ui.client.widget.form.CheckBox;
import com.extjs.gxt.ui.client.widget.form.DateField;
import com.extjs.gxt.ui.client.widget.form.Field;
import com.extjs.gxt.ui.client.widget.form.NumberField;
import com.extjs.gxt.ui.client.widget.form.SimpleComboBox;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.form.TimeField;

import com.extjs.gxt.ui.client.widget.layout.BorderLayout;
import com.extjs.gxt.ui.client.widget.layout.BorderLayoutData;
import com.extjs.gxt.ui.client.widget.layout.TableData;

import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;

// ******************************************************
// Class: ProbeSetupWizardWindow
//
// ******************************************************
public class ProbeSetupWizardWindow extends Window {
  private   String                 previousButtonText = "< Previous";
  private   String                 nextButtonText     = "Next >";
  private   String                 cancelButtonText   = "Cancel";
  private   String                 finishButtonText   = "Finish";
  protected Integer                currentStep        = 0;
  protected List<ProbeSetupWizardCard> cards          = null;
  private   CardPanel              cardPanel          = null;
  protected Button                 previousButton     = null;
  protected Button                 nextButton         = null;
  protected Button                 cancelButton       = null;
  private Header                   headerPanel        = null;
  private Indicator                progressIndicator  = Indicator.DOT;
  private FitSchedulerServiceAsync service            = null;
  private ListStore<BeanModel>     store              = null;
  
  // *******************************************************
  // enum: Indicator
  //
  // ******************************************************
  public enum Indicator {
    NONE, DOT, PROGRESSBAR
  }

  // *******************************************************
  // Constructor: ProbeSetupWizardWindow
  //
  // ******************************************************
  public ProbeSetupWizardWindow(ArrayList<ProbeSetupWizardCard>    cards,
                                FitSchedulerServiceAsync service, 
                                ListStore<BeanModel>     mainStore) {
    super();
    this.cards = cards != null ? cards : new LinkedList<ProbeSetupWizardCard>();

    this.service = service;

    this.store = mainStore;
    
    for (ProbeSetupWizardCard card : this.cards) {
      card.setWizardWindow(this);
      card.setService(service);
    }

    setSize(550, 400);
    setClosable(true);
    setDraggable(true);
    setResizable(true);
    setModal(true);
    setMaximizable(true);
    setHeading("Wizard to setup new FIT Probes");
  }

  // ******************************************************
  // Method: onRender
  //
  // ******************************************************
  @Override
  protected void onRender(Element parent, int pos) {
    previousButton = new Button(getPreviousButtonText());
    nextButton = new Button(getNextButtonText());
    cancelButton = new Button(getCancelButtonText());

    SelectionListener<ButtonEvent> listener = new SelectionListener<ButtonEvent>() {
      public void componentSelected(ButtonEvent buttonEvent) {
        onButtonPressed(buttonEvent.getButton());
      }
    };

    previousButton.addSelectionListener(listener);
    nextButton.addSelectionListener(listener);
    cancelButton.addSelectionListener(listener);

    ToolBar buttonBar = new ToolBar();
    buttonBar.setSpacing(20);
    buttonBar.setAlignment(Style.HorizontalAlignment.RIGHT);

    buttonBar.add(previousButton);
    buttonBar.add(nextButton);
    buttonBar.add(cancelButton);

    setBottomComponent(buttonBar);

    super.onRender(parent, pos);
    setLayout(new BorderLayout());

    headerPanel = new Header();
    add(headerPanel, new BorderLayoutData(LayoutRegion.NORTH, 60));

    add(buttonBar, new BorderLayoutData(LayoutRegion.SOUTH, 30));

    cardPanel = new CardPanel();
    cardPanel.setStyleAttribute("padding", "5px 5px 5px 5px");
    cardPanel.setStyleAttribute("backgroundColor", "#F6F6F6");

    add(cardPanel, new BorderLayoutData(LayoutRegion.CENTER));

    for (ProbeSetupWizardCard wizardCard : cards) {
      cardPanel.add(wizardCard);
    }

    if (cards.size() > 0) {
      updateWizard();
    }
  }

  // ******************************************************
  // Method: onButtonPressed
  //
  // ******************************************************
  protected void onButtonPressed(Button button) {
    if (button == cancelButton) {
      hide(button);
      return;
    }

    if (button == previousButton) {
      doPrevious();
    }

    if (button == nextButton) {
      doNext();
    }
  }

  // *******************************************************
  // Method: doNext
  //
  // ******************************************************
  public void doNext() {
    if (currentStep == 0) {
      final ProbeSetupWizardCard wizardCard = cards.get(0);

      JobDefinition jobDefinition = new JobConfigBuilder().buildJobDefinition(wizardCard);
      
      final String jobName = jobDefinition.getJobName();
      
      wizardCard.addFinishListener(new Listener<BaseEvent>() {

        @Override
        public void handleEvent(BaseEvent be) {
          service.checkForUniqueJobName(jobName, new AsyncCallback<Boolean>() {

            @Override
            public void onSuccess(Boolean result) {
            }

            @Override
            public void onFailure(Throwable caught) {
              MessageBox.alert("Failure!", caught.getMessage(), null);
            }
          });
        }
      });
      
      wizardCard.notifyFinishListeners();
    }

    if (currentStep == 3) {
      final JobDefinition jobDefinition = new JobConfigBuilder().buildJobDefinition(cards.get(0));
      
      final ProbeSetupWizardCard scheduleConfigWizardCard = cards.get(4);
      List<Field<?>> scheduleConfigFields = scheduleConfigWizardCard.getFormPanel().getFields();

      jobDefinition.setStartDate     (((DateField)   scheduleConfigFields.get(0)).getValue());
      jobDefinition.setStartTime     (((TimeField)   scheduleConfigFields.get(1)).getValue().getHour() * 60 * 60 * 1000l + ((TimeField) scheduleConfigFields.get(1)).getValue().getMinutes() * 60 * 1000l);
      jobDefinition.setRepeatInterval(((NumberField) scheduleConfigFields.get(2)).getValue().intValue());
      jobDefinition.setEndDate       (((DateField)   scheduleConfigFields.get(3)).getValue());
      jobDefinition.setEndTime       ((((TimeField)  scheduleConfigFields.get(4)).getValue().getHour() * 60 * 60 * 1000l + ((TimeField) scheduleConfigFields.get(4)).getValue().getMinutes() * 60 * 1000l));

      final Probe probe = new ProbeConfigBuilder().build(cards, jobDefinition);
      
      scheduleConfigWizardCard.addFinishListener(new Listener<BaseEvent>() {

        @Override
        public void handleEvent(BaseEvent be) {
          service.saveProbeDefinition(probe, new AsyncCallback<Probe>() {
            
            @Override
            public void onSuccess(Probe result) {
              List<Field<?>> scheduleConfigFields = cards.get(4).getFormPanel().getFields();
              
              // *************************************
              // Calling updateJobDefinition service
              // *************************************
              if (!(cards.get(0).getFormPanel().getFields().get(0)).isDirty()) {
                service.updateJobDefinition(jobDefinition, new AsyncCallback<JobDefinition>() {
                  
                  @Override
                  public void onSuccess(JobDefinition result) {
                    queryDetails(probe);
                  }
                  
                  @Override
                  public void onFailure(Throwable caught) {
                    MessageBox.alert("Failure!", "Could not update job definition successfully!", null);
                  }
                });
              }
              
              else {
                // **********************************************
                // Calling saveJobDefinitionWithEndDate service
                // **********************************************
                if (scheduleConfigFields.get(0).isDirty() && scheduleConfigFields.get(0).isDirty() && 
                    scheduleConfigFields.get(0).isDirty() && scheduleConfigFields.get(0).isDirty() && 
                    scheduleConfigFields.get(0).isDirty()) {
                  service.saveJobDefinitionWithEndDate(jobDefinition, new AsyncCallback<JobDefinition>() {
                    
                    @Override
                    public void onSuccess(JobDefinition result) {
                      queryDetails(probe);
                    }

                    @Override
                    public void onFailure(Throwable caught) {
                      MessageBox.alert("Failure!", "Could not save the configuration for Job: " + jobDefinition.getJobName() + "Error: " + caught.getMessage(), null);
                    }
                  });
                }
                
                else if (scheduleConfigFields.get(0).isDirty() && scheduleConfigFields.get(0).isDirty() && 
                         scheduleConfigFields.get(0).isDirty() && !scheduleConfigFields.get(0).isDirty() && 
                         !scheduleConfigFields.get(0).isDirty()) {
                  // *************************************************
                  // Calling saveJobDefinitionWithoutEndDate service
                  // *************************************************
                  service.saveJobDefinitionWithoutEndDate(jobDefinition, new AsyncCallback<JobDefinition>() {
                    
                    @Override
                    public void onSuccess(JobDefinition result) {
                      queryDetails(probe);
                    }
                    
                    @Override
                    public void onFailure(Throwable caught) {
                      MessageBox.alert("Failure!", "Could not save the configuration for Job: " + jobDefinition.getJobName() + "Error: " + caught.getMessage(), null);
                    }
                  });
                }
                
                else {
                  // *************************************************
                  // Calling saveJobDefinitionWithoutTrigger service
                  // *************************************************
                  service.saveJobDefinitionWithoutTrigger(jobDefinition, new AsyncCallback<JobDefinition>() {
                    
                    @Override
                    public void onSuccess(JobDefinition result) {
                      queryDetails(probe);
                    }
                    
                    @Override
                    public void onFailure(Throwable caught) {
                      MessageBox.alert("Failure!", "Could not save the configuration for Job: " + jobDefinition.getJobName() + "Error: " + caught.getMessage(), null);
                    }
                  });
                }
              }
            }
            
            @Override
            public void onFailure(Throwable caught) {
              MessageBox.alert("Failure", "Could NOT save probe definition", null);
            }
          });
        }
      });
      
      scheduleConfigWizardCard.notifyFinishListeners();
     
      hide();

      showSaving();
    }
    else {
      currentStep++;
      updateWizard();
    }
  }
  
 //******************************************************
 // Method: queryDetails
 //
 // ******************************************************
  private void queryDetails(Probe probe) {
    service.getProbeDetails(probe, new AsyncCallback<Probe>() {
      
      @Override
      public void onSuccess(Probe result) {
        if (result.getProbeType().equals("Database Probe")) {
          store.add(BeanModelLookup.get().getFactory(DatabaseProbe.class).createModel(result));
        }
        else {
          store.add(BeanModelLookup.get().getFactory(SpreadsheetProbe.class).createModel(result));
        }
      }
      
      @Override
      public void onFailure(Throwable caught) {
        MessageBox.alert("Failure!", "Could not retrieve the probe configuration details to display on grid!" + "Error: " + caught.getMessage(), null);
      }
    });
  }
  
  // ******************************************************
  // Method: doPrevious
  //
  // ******************************************************
  public void doPrevious() {
    if (this.currentStep > 0) {
      currentStep--;
      updateWizard();
    }
  }

  // *******************************************************
  // Method: showSaving
  //
  // ******************************************************
  private void showSaving() {
    final MessageBox savingMessageBox = MessageBox.wait("Progress", "Saving your configuration, please wait...", "Saving...");
    Timer timer = new Timer() {

      @Override
      public void run() {
        Info.display("Message", "Your data configuration was saved successfully!");
        savingMessageBox.close();
      }
    };

    timer.schedule(1000);
  }

  // ******************************************************
  // Method: updateWizard
  //
  // ******************************************************
  @SuppressWarnings({ "unchecked" })
  public void updateWizard() {
    if (currentStep == 0) {
      ProbeSetupWizardCard wc = cards.get(0);
      headerPanel.updateIndicatorStep("Job Configuration");
      wc.refreshLayout();
      this.cardPanel.setActiveItem(wc);
    }

    if (currentStep == 1) {
      String jobType = ((SimpleComboBox<String>) cards.get(0).getFormPanel().getFields().get(1)).getSimpleValue();

      if (jobType.equals("DATABASE_PROBE")) {
        ProbeSetupWizardCard wc = cards.get(1);
        headerPanel.updateIndicatorStep("Database Probe Configuration");
        wc.refreshLayout();
        this.cardPanel.setActiveItem(wc);
      }

      else {
        ProbeSetupWizardCard wc = cards.get(2);
        headerPanel.updateIndicatorStep("Spreadsheet Probe Configuration");
        wc.refreshLayout();
        this.cardPanel.setActiveItem(wc);
      }
    }

    if (currentStep == 2) {
      final String jobName = ((TextField<String>) cards.get(0).getFormPanel().getFields().get(0)).getValue();
            String jobType = ((SimpleComboBox<String>) cards.get(0).getFormPanel().getFields().get(1)).getSimpleValue();
      
      if (jobType.equals("DATABASE_PROBE")) {
        showColumnMappingWindowForDbProbe(jobName);
      }
      
      ProbeSetupWizardCard wc = cards.get(3);
      headerPanel.updateIndicatorStep("Generic Configuration Details");
      wc.refreshLayout();
      this.cardPanel.setActiveItem(wc);
    }

    if (currentStep == 3) {
      ProbeSetupWizardCard wc = cards.get(4);
      headerPanel.updateIndicatorStep("Schedule Configuration Details");
      wc.refreshLayout();
      this.cardPanel.setActiveItem(wc);  
    }

    if (currentStep == 3) {
      nextButton.setText(getFinishButtonText());
    } 
    else {
      nextButton.setText(getNextButtonText());
    }

    if (currentStep == 0) {
      previousButton.setEnabled(false);
    } 
    else {
      previousButton.setEnabled(true);
    }
  }

  public String getPreviousButtonText() {
    return previousButtonText;
  }

  public String getNextButtonText() {
    return nextButtonText;
  }

  public String getCancelButtonText() {
    return cancelButtonText;
  }

  public String getFinishButtonText() {
    return finishButtonText;
  }

  public List<ProbeSetupWizardCard> getCards() {
    return cards;
  }

  public Indicator getProgressIndicator() {
    return progressIndicator;
  }

  public void setProgressIndicator(Indicator progressIndicator) {
    this.progressIndicator = progressIndicator;
  }

  // ******************************************************
  // Method: showColumnMappingWindowForDbProbe
  //
  // ******************************************************
  private void showColumnMappingWindowForDbProbe(final String jobName) {
    final String sqlQuery = cards.get(1).getFormPanel().getFields().get(1).getValue().toString();
    
    service.getColumnMappingsFromSqlQuery(sqlQuery, jobName, new AsyncCallback<List<ColumnMapping>>() {

      @Override
      public void onFailure(Throwable caught) {
        MessageBox.alert("Failure!", "Could NOT get meta data from SQL query!", null);
      }

      @Override
      public void onSuccess(final List<ColumnMapping> columnMappingList) {
        ColumnMappingWindow columnConfigPanel = new ColumnMappingWindow(service, columnMappingList);
        columnConfigPanel.show();
        
        columnConfigPanel.addListener(Events.Hide, new Listener<BaseEvent>() {

          @Override
          public void handleEvent(BaseEvent be) {
            Boolean ruleSelectionCheckbox = ((CheckBox) cards.get(1).getFormPanel().getFields().get(2)).getValue();
            
            if (ruleSelectionCheckbox.equals(Boolean.TRUE)) {
              new RulesSetupContainer().showNewWizard(jobName, service);
            }
          }
        });
        
        columnConfigPanel.addListener(Events.Close, new Listener<BaseEvent>() {

          @Override
          public void handleEvent(BaseEvent be) {
            Boolean ruleSelectionCheckbox = ((CheckBox) cards.get(1).getFormPanel().getFields().get(2)).getValue();
            
            if (ruleSelectionCheckbox.equals(Boolean.TRUE)) {
              new RulesSetupContainer().showNewWizard(jobName, service);
            }
          }
        });
      }
    });
  }
  
  // ******************************************************
  // Class: Header
  //
  // ******************************************************
  protected class Header extends VerticalPanel {
    private ProgressBar indicatorBar;
    private HtmlContainer titleHTML;

    protected Header() {
      super();
      setTableWidth("100%");
      setTableHeight("100%");
      setStyleName("wizardHeader");
      setBorders(true);

      titleHTML = new HtmlContainer("");
      titleHTML.setStyleAttribute("font-weight", "bold");
      titleHTML.setStyleAttribute("padding", "4px 0px 0px 4px");
      titleHTML.setStyleName("wizardHeaderTitle");
      add(titleHTML);

      if (progressIndicator == Indicator.PROGRESSBAR || progressIndicator == Indicator.DOT) {
        indicatorBar = new ProgressBar();
        LayoutContainer lc = new LayoutContainer();
        lc.add(indicatorBar);
        lc.setWidth("50%");
        TableData td = new TableData();
        td.setHorizontalAlign(HorizontalAlignment.RIGHT);
        td.setPadding(5);
        add(lc, td);
      }
    }

    // ******************************************************
    // Method: updateIndicatorStep
    //
    // ******************************************************
    protected void updateIndicatorStep(String cardtitle) {
      final String stepStr = "Step " + (1 + currentStep) + " " + "of" + " " + (cards.size() - 1) + " : " + cardtitle;
      final double stepRatio = (double) (1 + currentStep) / (double) (cards.size() - 1);

      if (progressIndicator == Indicator.PROGRESSBAR || progressIndicator == Indicator.DOT) {
        Scheduler.get().scheduleDeferred(new ScheduledCommand() {

          @Override
          public void execute() {
            indicatorBar.updateProgress(stepRatio, stepStr);
          }
        });
      }
    }

    @Override
    protected void onRender(Element parent, int pos) {
      super.onRender(parent, pos);
      setStyleAttribute("borderLeft", "none");
      setStyleAttribute("borderRight", "none");
      setStyleAttribute("borderTop", "none");
    }
  }
}