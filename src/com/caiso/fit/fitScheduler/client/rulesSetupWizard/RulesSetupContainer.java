package com.caiso.fit.fitScheduler.client.rulesSetupWizard;

import java.util.ArrayList;

import com.caiso.fit.fitScheduler.client.rulesSetupWizard.cards.NullcheckConfigWizardCard;
import com.caiso.fit.fitScheduler.client.rulesSetupWizard.cards.RangeCheckConfigWizardCard;
import com.caiso.fit.fitScheduler.client.rulesSetupWizard.cards.RuleConfigurationWizardCard;
import com.caiso.fit.fitScheduler.client.rulesSetupWizard.cards.ScriptConfigWizardCard;

import com.caiso.fit.fitScheduler.shared.FitSchedulerServiceAsync;

public class RulesSetupContainer {

  //******************************************************
  // Method: showNewWizard
  //
  // ******************************************************
  public void showNewWizard(String probeName, FitSchedulerServiceAsync service) {
    ArrayList<RulesSetupWizardCard> wizardCards = new ArrayList<RulesSetupWizardCard>();
    
    RulesSetupWizardWindow wizardWindow = new RulesSetupWizardWindow(probeName, wizardCards, service);
   
    wizardCards.add(new RuleConfigurationWizardCard("Rule configuration"));
    wizardCards.add(new NullcheckConfigWizardCard("Null check configuration"));
    wizardCards.add(new RangeCheckConfigWizardCard("Range check configuration"));
    wizardCards.add(new ScriptConfigWizardCard("Script configuration"));
    
    wizardWindow.show();
  } 
}
