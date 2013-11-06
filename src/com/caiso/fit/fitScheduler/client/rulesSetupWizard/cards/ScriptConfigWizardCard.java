package com.caiso.fit.fitScheduler.client.rulesSetupWizard.cards;

import gwtupload.client.IFileInput.FileInputType;

import gwtupload.client.IUploadStatus.Status;

import gwtupload.client.IUploader;
import gwtupload.client.IUploader.OnFinishUploaderHandler;
import gwtupload.client.IUploader.Utils;

import gwtupload.client.SingleUploader;

import com.caiso.fit.fitScheduler.client.rulesSetupWizard.RulesSetupWizardCard;

import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.TextArea;
import com.extjs.gxt.ui.client.widget.form.FormPanel.LabelAlign;

import com.extjs.gxt.ui.client.widget.layout.FormData;
import com.extjs.gxt.ui.client.widget.layout.FormLayout;

import com.google.gwt.user.client.Window;

import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.XMLParser;

// ******************************************************
// Class: ScriptConfigWizardCard
//
// ******************************************************
public class ScriptConfigWizardCard extends RulesSetupWizardCard {
  private TextArea scriptTextArea = null;
  
  public ScriptConfigWizardCard(String cardtitle) {
    super(cardtitle);
    this.setFormPanel(getPanel());
  }

  // ******************************************************
  // Method: getPanel
  //
  // ******************************************************
  private FormPanel getPanel() {
    FormLayout formLayout = new FormLayout(LabelAlign.LEFT);
    formLayout.setLabelWidth(80);

    FormPanel formPanel = new FormPanel();
    formPanel.setHeight(350);
    formPanel.setLayout(formLayout);
    formPanel.setPadding(10);
    formPanel.setFrame(false);
    formPanel.setBorders(false);
    formPanel.setBodyBorder(false);
    formPanel.setHeaderVisible(false);

    FormData formData = new FormData("100%");
    
    formPanel.addText("You can write your script in the text area or upload.<br/><br/>"
        + "Clicking \"Finish\" will close this wizard.<br/><br/>");
    
    scriptTextArea = new TextArea();
    scriptTextArea.setFieldLabel("Script");
    scriptTextArea.setAllowBlank(true);
    formPanel.add(scriptTextArea, new FormData(400, 180));
    
    formPanel.add(getUploader(), formData);
    
    return formPanel;
  }
  
  
  //******************************************************
  // Method: getUploader
  //
  // ******************************************************
  private SingleUploader getUploader() {
    SingleUploader uploader = new SingleUploader(FileInputType.LABEL);
    uploader.setServletPath("uploader.scriptUpload");
   
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
   
    return uploader;
  }
}