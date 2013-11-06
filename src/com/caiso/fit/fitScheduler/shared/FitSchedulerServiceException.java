package com.caiso.fit.fitScheduler.shared;

// ******************************************************
// Interface: FitSchedulerServiceException
//
// ******************************************************
public class FitSchedulerServiceException extends Exception {

  private static final long serialVersionUID = 1L;

  public FitSchedulerServiceException() {
  }
  
  public FitSchedulerServiceException(String exceptionText) {
    super(exceptionText);
  }
  
  public FitSchedulerServiceException(Throwable t) {
    super(t);
  }

  public FitSchedulerServiceException(String exceptionText, Throwable t) {
    super(exceptionText, t);
  }
}