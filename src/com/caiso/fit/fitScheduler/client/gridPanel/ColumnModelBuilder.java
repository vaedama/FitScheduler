package com.caiso.fit.fitScheduler.client.gridPanel;

import java.util.ArrayList;
import java.util.List;

import com.extjs.gxt.ui.client.Style.HorizontalAlignment;

import com.extjs.gxt.ui.client.data.BeanModel;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnData;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.grid.GridCellRenderer;
import com.google.gwt.i18n.client.DateTimeFormat;

// ******************************************************
// Class: ColumnModelBuilder
//
// ******************************************************
public class ColumnModelBuilder {
  // ******************************************************
  // Method: buildMainGridColumns
  //
  // ******************************************************
  public ColumnModel buildMainGridColumns() {
    List<ColumnConfig> probeGridColumns = new ArrayList<ColumnConfig>();

    probeGridColumns.add(getColumnConfig    ("probeName",           "Probe Name",        120));
    probeGridColumns.add(getColumnConfig    ("probeType",           "Probe Type",        100));
    probeGridColumns.add(getColumnConfig    ("probeDescription",    "Probe Description", 120));
    probeGridColumns.add(getDateColumnConfig("startDate",           "Start Date",        70));
    probeGridColumns.add(getColumnConfig    ("startTime",           "Start Time",        65));
    probeGridColumns.add(getDateColumnConfig("endDate",             "End Date",          70));
    probeGridColumns.add(getColumnConfig    ("endTime",             "End Time",          65));
    probeGridColumns.add(getColumnConfig    ("repeatInterval",      "Repeat Interval",   85));
    probeGridColumns.add(getColumnConfig    ("status",              "State",             60));
    
    return new ColumnModel(probeGridColumns);
  }

  public ColumnModel buildAuditGridColumns() {
    List<ColumnConfig> auditGridColumns = new ArrayList<ColumnConfig>();
    
    auditGridColumns.add(getColumnConfig("dataLoadID",            "Data Load ID",         75));
    auditGridColumns.add(getColumnConfig("deletedDataLoadID",     "Deleted Load ID",      90));
    auditGridColumns.add(getColumnConfig("replacementDataLoadID", "Replacement Load ID",  115));
    auditGridColumns.add(getColumnConfig("runStartTime",          "Data Load Start Time", 135));
    auditGridColumns.add(getColumnConfig("runEndTime",            "Data Load End Time",   135));
    auditGridColumns.add(getColumnConfig("elapsedRunTime",        "Elapsed Time",         75));
    auditGridColumns.add(getColumnConfig("rowsQueried",           "Rows Queried",         85));
    auditGridColumns.add(getColumnConfig("measurementsQueried",   "Measurements Queried", 125));
    auditGridColumns.add(getColumnConfig("rowsInserted",          "Rows Inserted",        85));
    auditGridColumns.add(getColumnConfig("rowsDeleted",           "Rows Deleted",         85));
    auditGridColumns.add(getColumnConfig("startTime",             "Query Low Time",       100));
    auditGridColumns.add(getColumnConfig("endTime",               "Query High Time",      100));
    auditGridColumns.add(getColoredColumnConfig("successFlag",    "Status",               60));
    
    return new ColumnModel(auditGridColumns);
  }
  
  private ColumnConfig getColumnConfig(String id, String headerName, Integer width) {
    ColumnConfig columnConfig = new ColumnConfig();
    
    columnConfig.setId(id);
    columnConfig.setHeader(headerName);
    columnConfig.setAlignment(HorizontalAlignment.CENTER);
    columnConfig.setWidth(width);
    
    return columnConfig;
  }

  private ColumnConfig getDateColumnConfig(String id, String headerName, Integer width) {
    ColumnConfig columnConfig = new ColumnConfig();
    
    columnConfig.setId(id);
    columnConfig.setHeader(headerName);
    columnConfig.setAlignment(HorizontalAlignment.CENTER);
    columnConfig.setDateTimeFormat(DateTimeFormat.getFormat("MM/dd/yyyy"));
    columnConfig.setWidth(width);
    
    return columnConfig;
  }
  
  private ColumnConfig getColoredColumnConfig(String id, String headerName, Integer width) {
    ColumnConfig columnConfig = new ColumnConfig();
    
    columnConfig.setId(id);
    columnConfig.setHeader(headerName);
    columnConfig.setAlignment(HorizontalAlignment.CENTER);
    columnConfig.setWidth(width);
    
    columnConfig.setRenderer(new GridCellRenderer<BeanModel>() {

      @Override
      public Object render(BeanModel model,      String property,   ColumnData           config,
                           int       rowIndex,   int    colIndex,   ListStore<BeanModel> store,
                           Grid<BeanModel> grid) {
        String value = model.get("successFlag").toString();
        
        String style = value == "Success" ? "green" : "red";
        
        return "<span style='color:" + style + "'>" + value + "</span>";
      }
    });
    
    return columnConfig;
  }
}
