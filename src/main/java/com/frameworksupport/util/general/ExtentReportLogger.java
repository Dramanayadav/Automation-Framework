package com.frameworksupport.util.general;


import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.testng.Reporter;

public class ExtentReportLogger {
  ExtentTest test;
  
  public ExtentReportLogger(ExtentTest test) {
    this.test = test;
  }
  
  public void logInfo(Object log) {
    if (log != null) {
      Reporter.log(log.toString(), true);
      if (this.test != null)
        this.test.log(Status.INFO, log.toString()); 
    } else {
      Reporter.log("null", true);
      this.test.log(Status.INFO, "null");
    } 
  }
  
  public void logPass(Object log) {
    if (log != null) {
      Reporter.log(log.toString(), true);
      if (this.test != null)
        this.test.log(Status.PASS, log.toString()); 
    } 
  }
  
  public void logFail(Object log) {
    if (log != null) {
      Reporter.log(log.toString(), true);
      if (this.test != null)
        this.test.log(Status.FAIL, log.toString()); 
    } else {
      Reporter.log("null", true);
      this.test.log(Status.FAIL, "null");
    } 
  }
  
  public void logSkip(Object log) {
    if (log != null) {
      Reporter.log(log.toString(), true);
      if (this.test != null)
        this.test.log(Status.SKIP, log.toString()); 
    } else {
      Reporter.log("null", true);
      this.test.log(Status.SKIP, "null");
    } 
  }
}

