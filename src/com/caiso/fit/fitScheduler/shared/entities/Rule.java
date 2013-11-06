package com.caiso.fit.fitScheduler.shared.entities;

import java.io.Serializable;

import com.extjs.gxt.ui.client.data.BeanModelTag;

// ******************************************************
// Class: Rule
//
// ******************************************************
public class Rule implements BeanModelTag, Serializable {
  private Integer ruleID;
  private String  ruleName;
  private String  ruleType;
  private String  ruleDescription;
  private String  attributeName;
  private String  probeName;
  private Integer minValue;
  private Integer maxValue;
  private String  ruleScript;
  
  private static final long serialVersionUID = 1L;
 
  public Integer getRuleID() {
    return ruleID;
  }
  public void setRuleID(Integer ruleID) {
    this.ruleID = ruleID;
  }
  public String getRuleName() {
    return ruleName;
  }
  public void setRuleName(String ruleName) {
    this.ruleName = ruleName;
  }
  public String getRuleType() {
    return ruleType;
  }
  public void setRuleType(String ruleType) {
    this.ruleType = ruleType;
  }
  public String getRuleDescription() {
    return ruleDescription;
  }
  public void setRuleDescription(String ruleDescription) {
    this.ruleDescription = ruleDescription;
  }
  public String getProbeName() {
    return probeName;
  }
  public void setProbeName(String probeName) {
    this.probeName = probeName;
  }
  public String getAttributeName() {
    return attributeName;
  }
  public void setAttributeName(String attributeName) {
    this.attributeName = attributeName;
  }
  public Integer getMinValue() {
    return minValue;
  }
  public void setMinValue(Integer minValue) {
    this.minValue = minValue;
  }
  public Integer getMaxValue() {
    return maxValue;
  }
  public void setMaxValue(Integer maxvalue) {
    this.maxValue = maxvalue;
  }
  public String getRuleScript() {
    return ruleScript;
  }
  public void setRuleScript(String ruleScript) {
    this.ruleScript = ruleScript;
  }
}
