package com.caiso.fit.fitScheduler.server

import gwtupload.server.UploadAction

import gwtupload.server.exceptions.UploadActionException

import javax.servlet.http.HttpServletRequest

import org.apache.commons.fileupload.FileItem

// ******************************************************
// Class: FileUploadServlet
//
// ******************************************************
public class FileUploadServlet extends UploadAction {

 private static final long serialVersionUID = 1L;

 // ******************************************************
 // Method: executeAction
 //
 // ******************************************************
 public String executeAction(HttpServletRequest request,
                             List<FileItem>     sessionFiles) throws UploadActionException {
   String response = "Received file:";

   for (FileItem item : sessionFiles) {
     
     if (!item.isFormField()) {
       try {
         File file = new File("/app/isomon/jboss-5.1.0.GA/server/isomon/excel_stage/$item.name")
         item.write(file);
         response += " " + file.path + item.name;
       }
       catch (Exception e) {
         throw new UploadActionException(e.message);
       }
     }
   }

   try {
     removeSessionFileItems(request);
   }
   catch (Exception e) {
     throw new UploadActionException(e.message);
   }
   return response;
 }
}