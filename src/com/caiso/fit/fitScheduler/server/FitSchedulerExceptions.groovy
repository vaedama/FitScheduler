package com.caiso.fit.fitScheduler.server

import com.caiso.fit.fitScheduler.server.services.*

// ******************************************************
// Class: FitSchedulerExceptions
//
// ******************************************************
class FitSchedulerExceptions {
  // ******************************************************
  // Method: throwException
  //
  // ******************************************************
  private void throwException(Throwable t) {
    Log.error "Exception occured - $t.message"

    throw new Exception(t.getMessage())
  }
}
