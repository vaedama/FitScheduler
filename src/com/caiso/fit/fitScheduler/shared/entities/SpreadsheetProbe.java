package com.caiso.fit.fitScheduler.shared.entities;

import java.io.Serializable;

import com.extjs.gxt.ui.client.data.BeanModelTag;

// ******************************************************
// Class: SpreadsheetProbe
//
// ******************************************************
public class SpreadsheetProbe extends Probe implements BeanModelTag, Serializable {
  private Integer xlProbeID;
  private String  sourceSpreadsheetName;
  private Integer sheetNumber;
  private String  timestampColumnName;
  private String  timestampColumnFormat;
  private Integer startRow;
  private Integer endRow;

  private static final long serialVersionUID = 1L;

  public Integer getXlProbeID() {
    return xlProbeID;
  }
  public void setXlProbeID(Integer xlProbeID) {
    this.xlProbeID = xlProbeID;
  }
  public String getSourceSpreadsheetName() {
    return sourceSpreadsheetName;
  }
  public void setSourceSpreadsheetName(String sourceSpreadsheetName) {
    this.sourceSpreadsheetName = sourceSpreadsheetName;
  }
  public Integer getSheetNumber() {
    return sheetNumber;
  }
  public void setSheetNumber(Integer sheetNumber) {
    this.sheetNumber = sheetNumber;
  }
  public String getTimestampColumnName() {
    return timestampColumnName;
  }
  public void setTimestampColumnName(String timestampColumnName) {
    this.timestampColumnName = timestampColumnName;
  }
  public String getTimestampColumnFormat() {
    return timestampColumnFormat;
  }
  public void setTimestampColumnFormat(String timestampColumnFormat) {
    this.timestampColumnFormat = timestampColumnFormat;
  }
  public Integer getStartRow() {
    return startRow;
  }
  public void setStartRow(Integer startRow) {
    this.startRow = startRow;
  }
  public Integer getEndRow() {
    return endRow;
  }
  public void setEndRow(Integer endRow) {
    this.endRow = endRow;
  }
}
