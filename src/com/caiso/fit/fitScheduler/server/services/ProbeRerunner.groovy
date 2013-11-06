package com.caiso.fit.fitScheduler.server.services

import com.caiso.fit.fitScheduler.shared.entities.JobDefinition

// ******************************************************
// Class: ProbeRerunner
//
// ******************************************************
public class ProbeRerunner {
  // **********
  // Constants
  // **********
  private static final String URL_CONTEXT = "http://fsapomt1:8080/QuartzService/Jobs/Rerun"
  
  // ******************************************************
  // Method: rerun
  //
  // ******************************************************
  public void rerun(Integer dataLoadID) {
    String urlQuery = "$URL_CONTEXT/$dataLoadID"
    
    new XmlSlurper().parseText(urlQuery.toURL().text).job.each {
      
       String jobName = it.jobName.text()
       
       Log.info("Successfully reran the job with name: $jobName");
    }
  }
}
