package com.frameworksupport.api.core;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.frameworksupport.util.general.CommonUtil;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class ApiListeners implements ITestListener {
  private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();
  
  private static ThreadLocal<com.frameworksupport.util.general.CommonUtil> common = new ThreadLocal<>();
  
  public void onFinish(ITestContext arg0) {
    test.remove();
    common.remove();
  }
  
  public void onStart(ITestContext arg0) {}
  
  public void onTestFailedButWithinSuccessPercentage(ITestResult arg0) {}
  
  public void onTestFailure(ITestResult arg0) {
    ((CommonUtil)common.get()).log(String.valueOf(arg0.getMethod().getMethodName()) + " Failed!");
    ((ExtentTest)test.get()).log(Status.FAIL, arg0.getThrowable());
    ((CommonUtil)common.get()).failApiTest(arg0.getThrowable(), 0);
    ApiBaseTest currentClass = (ApiBaseTest)arg0.getInstance();
    currentClass.assignTag();
    currentClass.assignAuthor();
  }
  
  public void onTestSkipped(ITestResult arg0) {
    ((CommonUtil)common.get()).log(String.valueOf(arg0.getMethod().getMethodName()) + " skipped!");
    ((CommonUtil)common.get()).log("Removing skipped test from Report....");
    CommonUtil.getExtentReports().removeTest(ApiBaseTest.getExtentTest());
  }
  
  public void onTestStart(ITestResult arg0) {
    common.set(ApiBaseTest.getCommonUtil());
    test.set(ApiBaseTest.getExtentTest());
    ((CommonUtil)common.get()).log("Execute test case " + ApiBaseTest.getTestCaseName());
  }
  
  public void onTestSuccess(ITestResult arg0) {
    ((ExtentTest)test.get()).log(Status.PASS, "Test passed");
    ApiBaseTest currentClass = (ApiBaseTest)arg0.getInstance();
    currentClass.assignTag();
    currentClass.assignAuthor();
  }
}
