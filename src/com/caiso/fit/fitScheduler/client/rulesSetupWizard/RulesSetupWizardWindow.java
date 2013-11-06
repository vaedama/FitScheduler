package com.caiso.fit.fitScheduler.client.rulesSetupWizard;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.caiso.fit.fitScheduler.shared.FitSchedulerServiceAsync;
import com.caiso.fit.fitScheduler.shared.entities.Rule;

import com.extjs.gxt.ui.client.Style;
import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.Style.LayoutRegion;

import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.SelectionListener;

import com.extjs.gxt.ui.client.widget.CardPanel;
import com.extjs.gxt.ui.client.widget.HtmlContainer;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.ProgressBar;
import com.extjs.gxt.ui.client.widget.VerticalPanel;
import com.extjs.gxt.ui.client.widget.Window;

import com.extjs.gxt.ui.client.widget.button.Button;

import com.extjs.gxt.ui.client.widget.form.Field;
import com.extjs.gxt.ui.client.widget.form.NumberField;
import com.extjs.gxt.ui.client.widget.form.SimpleComboBox;

import com.extjs.gxt.ui.client.widget.layout.BorderLayout;
import com.extjs.gxt.ui.client.widget.layout.BorderLayoutData;
import com.extjs.gxt.ui.client.widget.layout.TableData;

import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.user.client.Element;

import com.google.gwt.user.client.rpc.AsyncCallback;

// ******************************************************
// Class: RulesSetupWizardWindow
//
// ******************************************************
public class RulesSetupWizardWindow extends Window {
  private   String                     previousButtonText = "< Previous";
  private   String                     nextButtonText     = "Next >";
  private   String                     cancelButtonText   = "Cancel";
  private   String                     finishButtonText   = "Finish";
  private   String                     addMoreButtonText  = "Add More";
  protected Integer                    currentStep        = 0;
  protected List<RulesSetupWizardCard> cards              = null;
  private   CardPanel                  cardPanel          = null;
  protected Button                     previousButton     = null;
  protected Button                     nextButton         = null;
  protected Button                     cancelButton       = null;
  protected Button                     addMoreButton      = null;
  private Header                       headerPanel        = null;
  private Indicator                    progressIndicator  = Indicator.DOT;
  private String                       probeName          = null;
  private FitSchedulerServiceAsync     service            = null;
  
  // *******************************************************
  // enum: Indicator
  //
  // ******************************************************
  public enum Indicator {
    NONE, DOT, PROGRESSBAR
  }

  // *******************************************************
  // Constructor: RulesSetupWizardWindow
  //
  // ******************************************************
  public RulesSetupWizardWindow(String                          probeName,
                                ArrayList<RulesSetupWizardCard> cards,
                                FitSchedulerServiceAsync        service) {
    super();
    this.cards     = cards != null ? cards : new LinkedList<RulesSetupWizardCard>();
    this.service   = service;
    this.probeName = probeName;
    
    for (RulesSetupWizardCard card : this.cards) {
      card.setWizardWindow(this);
      card.setService(service);
    }

    setSize(550, 400);
    setClosable(true);
    setDraggable(true);
    setResizable(true);
    setModal(true);
    setMaximizable(true);
    setHeading("Wizard to setup Rules for " + probeName);
  }

  // ******************************************************
  // Method: onRender
  //
  // ******************************************************
  @Override
  protected void onRender(Element parent, int pos) {
    previousButton = new Button(getPreviousButtonText());
    nextButton     = new Button(getNextButtonText());
    cancelButton   = new Button(getCancelButtonText());
    addMoreButton  = new Button(getAddMoreButtonText());
    
    SelectionListener<ButtonEvent> listener = new SelectionListener<ButtonEvent>() {
      public void componentSelected(ButtonEvent buttonEvent) {
        onButtonPressed(buttonEvent.getButton());
      }
    };

    previousButton.addSelectionListener(listener);
    nextButton.addSelectionListener(listener);
    cancelButton.addSelectionListener(listener);
    addMoreButton.addSelectionListener(listener);
    
    ToolBar buttonBar = new ToolBar();
    buttonBar.setSpacing(20);
    buttonBar.setAlignment(Style.HorizontalAlignment.RIGHT);
    buttonBar.add(previousButton);
    buttonBar.add(nextButton);
    buttonBar.add(cancelButton);
    buttonBar.add(addMoreButton);
    
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

    for (RulesSetupWizardCard wizardCard : cards) {
      cardPanel.add(wizardCard);
    }

    if (cards.size() > 0) {
      setComboBoxValues(cards.get(1));
      setComboBoxValues(cards.get(2));
      setComboBoxValues(cards.get(3));
      updateWizard();
    }
  }

  // ******************************************************
  // Method: setComboBoxValues
  //
  // ******************************************************
  private void setComboBoxValues(final RulesSetupWizardCard wc) {
    service.getColumnsFromMappings(probeName, new AsyncCallback<List<String>>() {
      
      @SuppressWarnings("unchecked")
      @Override
      public void onSuccess(List<String> result) {
        ((SimpleComboBox<String>)wc.getFormPanel().getItemByItemId("attributeComboBox")).add(result);
      }
      
      @Override
      public void onFailure(Throwable caught) {
        MessageBox.alert("Failure!", "Could not get values for atribute combobox!", null);
      }
    });
  }
  
  // ******************************************************
  // Method: updateWizard
  //
  // ******************************************************
  @SuppressWarnings({ "unchecked" })
  public void updateWizard() {
    if (currentStep == 0) {
      RulesSetupWizardCard wc = cards.get(0);
      headerPanel.updateIndicatorStep("Rule Configuration");
      wc.refreshLayout();
      this.cardPanel.setActiveItem(wc);
      nextButton.setText(getNextButtonText());
      previousButton.setEnabled(false);
      addMoreButton.setEnabled(false);
    }
    else if (currentStep == 1) {
      String ruleType = ((SimpleComboBox<String>) cards.get(0).getFormPanel().getFields().get(1)).getSimpleValue();

      if (ruleType.equals("NULL_CHECK")) {
        headerPanel.updateIndicatorStep("Setup Null check");
        setActivePanel(cards.get(1));
      }
      else if (ruleType.equals("RANGE_CHECK")){
        headerPanel.updateIndicatorStep("Setup Range check");
        setActivePanel(cards.get(2));
      }
      else {
        headerPanel.updateIndicatorStep("Setup Rule Script");
        setActivePanel(cards.get(3));
      }
    }
    else {
      String ruleType = ((SimpleComboBox<String>) cards.get(0).getFormPanel().getFields().get(1)).getSimpleValue();

      if (ruleType.equals("NULL_CHECK")) {
        finishListenerForCard(cards.get(1), ruleType);
      }
      else if (ruleType.equals("RANGE_CHECK")) {
        finishListenerForCard(cards.get(1), ruleType);
      }
      else {
        finishListenerForCard(cards.get(1), ruleType);
      }
    }
  }
  
  // ******************************************************
  // Method: setActivePanel
  //
  // ******************************************************
  private void setActivePanel(RulesSetupWizardCard wc) {
    wc.refreshLayout();
    this.cardPanel.setActiveItem(wc);
    nextButton.setText(getFinishButtonText());
    previousButton.setEnabled(true);
    addMoreButton.setEnabled(true);
  }
  
  // ******************************************************
  // Method: finishListenerForCard
  //
  // ******************************************************
  private void finishListenerForCard(RulesSetupWizardCard wc, String ruleType) {
    wc.addFinishListener(new Listener<BaseEvent>() {
      
      @SuppressWarnings("unchecked")
      @Override
      public void handleEvent(BaseEvent be) {
        List<Field<?>> card0Fields = cards.get(0).getFormPanel().getFields();
        
        Rule rule = new Rule();
        
        rule.setProbeName(probeName);
        rule.setRuleName(card0Fields.get(0).getValue().toString());
        rule.setRuleType(((SimpleComboBox<String>)card0Fields.get(1)).getSimpleValue());
        rule.setRuleDescription(card0Fields.get(2).getValue().toString());
        
        if (rule.getRuleType().equals("NULL_CHECK")) {
          rule.setAttributeName(((SimpleComboBox<String>)cards.get(1).getFormPanel().getFields().get(0)).getSimpleValue());
        }
        else if (rule.getRuleType().equals("RANGE_CHECK")) {
          List<Field<?>> card2Fields = cards.get(2).getFormPanel().getFields();
          rule.setAttributeName(((SimpleComboBox<String>)card2Fields.get(0)).getSimpleValue());
          rule.setMinValue(((NumberField)card2Fields.get(1)).getValue().intValue());
          rule.setMaxValue(((NumberField)card2Fields.get(2)).getValue().intValue());
        }
        else {
          rule.setRuleScript(cards.get(3).getFormPanel().getFields().get(0).getValue().toString());
        }
        
        service.saveRule(rule, new AsyncCallback<String>() {
          
          @Override
          public void onSuccess(String result) {
            hide();
          }
          
          @Override
          public void onFailure(Throwable caught) {
            MessageBox.alert("Failure!", "Could NOT save the current rule!", null);
          }
        });
      }
    });
    
    wc.notifyFinishListeners();
  }
  
  // ******************************************************
  // Method: onButtonPressed
  //
  // ******************************************************
  @SuppressWarnings("unchecked")
  protected void onButtonPressed(Button button) {
    if (button == cancelButton) {
      hide(button);
      return;
    }

    if (button == previousButton) {
      if (currentStep == 1) {
        currentStep--;
        updateWizard();
      }
    }

    if (button == nextButton) {
      currentStep++;
      updateWizard();
    }
    
    if (button == addMoreButton) {
      String ruleType = ((SimpleComboBox<String>) cards.get(0).getFormPanel().getFields().get(1)).getSimpleValue();

      if (ruleType.equals("NULL_CHECK")) {
        finishListenerForCard(cards.get(1), ruleType);
      }
      else if (ruleType.equals("RANGE_CHECK")) {
        finishListenerForCard(cards.get(2), ruleType);
      }
      else {
        finishListenerForCard(cards.get(3), ruleType);
      }
      
      RulesSetupContainer rulesSetupContainer = new RulesSetupContainer();
      rulesSetupContainer.showNewWizard(probeName, service);
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

  public List<RulesSetupWizardCard> getCards() {
    return cards;
  }

  public Indicator getProgressIndicator() {
    return progressIndicator;
  }

  public void setProgressIndicator(Indicator progressIndicator) {
    this.progressIndicator = progressIndicator;
  }

  public String getAddMoreButtonText() {
    return addMoreButtonText;
  }

  public void setAddMoreButtonText(String addMoreButtonText) {
    this.addMoreButtonText = addMoreButtonText;
  }

  public String getProbeName() {
    return probeName;
  }

  public void setProbeName(String probeName) {
    this.probeName = probeName;
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
      final String stepStr = "Step " + (1 + currentStep) + " " + "of" + " " + (cards.size() - 2) + " : " + cardtitle;
      final double stepRatio = (double) (1 + currentStep) / (double) (cards.size() - 2);

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