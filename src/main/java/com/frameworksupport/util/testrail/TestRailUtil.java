package com.frameworksupport.util.testrail;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;
import org.testng.ITestResult;
import com.frameworksupport.util.general.CommonUtil;
import com.frameworksupport.util.general.ExtentReportLogger;

public class TestRailUtil {
  private ExtentReportLogger logger;
  
  public TestRailUtil(ExtentReportLogger logger) {
    this.logger = logger;
  }
  
  public void updateStatusOnTestRail(ITestResult result, String testRailCaseId) throws MalformedURLException, IOException, APIException {
    String testRunId = CommonUtil.getConfigProperty("testrail_run_id");
    if (testRailCaseId != null && testRunId != null && testRunId.length() > 0 && testRailCaseId.length() > 0) {
      if (testRailCaseId.contains(",")) {
        String[] arr = testRailCaseId.split(",");
        for (int i = 0; i < arr.length; i++)
          postTestRail(result, arr[i]); 
      } else {
        postTestRail(result, testRailCaseId);
      } 
    } else {
      this.logger.logInfo("TestCaseId/TestRunId is either missing or null. Hence not updating status on TestRail");
    } 
  }
  
  private void postTestRail(ITestResult result, String testRailCaseId) {
    String testRunId = CommonUtil.getConfigProperty("testrail_run_id");
    APIClient client = new APIClient(CommonUtil.getConfigProperty("testrail_base_url"));
    client.setUser(CommonUtil.getConfigProperty("testrail_username"));
    client.setPassword(CommonUtil.getConfigProperty("testrail_password"));
    Map<Object, Object> data = new HashMap<>();
    if (result.getStatus() == 1) {
      data.put("status_id", Integer.valueOf(1));
    } else if (result.getStatus() == 2 || result.getStatus() == 3) {
      data.put("status_id", Integer.valueOf(5));
      data.put("comment", result.getThrowable().getMessage());
    } 
    try {
      client.sendPost("add_result_for_case/" + testRunId + "/" + testRailCaseId, data);
      System.out.println("update status");
    } catch (Throwable t) {
      this.logger.logInfo("Got error while upating status in Test Rail: " + t.getMessage());
    } 
  }
}
