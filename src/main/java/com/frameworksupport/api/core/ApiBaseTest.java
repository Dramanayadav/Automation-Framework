package com.frameworksupport.api.core;

import com.aventstack.extentreports.ExtentTest;
import com.frameworksupport.api.resources.Resources;
import com.frameworksupport.setup.TestInfo;
import com.frameworksupport.util.general.CommonUtil;
import com.frameworksupport.util.general.ExtentReportLogger;
import com.frameworksupport.util.testrail.APIException;
import com.frameworksupport.util.testrail.TestRailUtil;
import com.google.gson.JsonObject;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;

@Listeners({ApiListeners.class})
public class ApiBaseTest {
  private String testRailCaseId;
  
  private String testFeatureName;
  
  private static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();
  
  private static ThreadLocal<CommonUtil> commonUtil = new ThreadLocal<>();
  
  private static ThreadLocal<RestApiUtil> restApiUtil = new ThreadLocal<>();
  
  private static ThreadLocal<Method> method = new ThreadLocal<>();
  
  private static ThreadLocal<ExtentReportLogger> extentReportLogger = new ThreadLocal<>();
  
  private static ThreadLocal<String> testCaseName = new ThreadLocal<>();
  
  public static ExtentReportLogger getExtentReportLogger() {
    return extentReportLogger.get();
  }
  
  public static RestApiUtil getRestApiUtil() {
    return restApiUtil.get();
  }
  
  public static String getTestCaseName() {
    return testCaseName.get();
  }
  
  public static ExtentTest getExtentTest() {
    return extentTest.get();
  }
  
  public static CommonUtil getCommonUtil() {
    return commonUtil.get();
  }
  
  public static Method getMethod() {
    return method.get();
  }
  public static JSONObject getJsonFileAsObject(String filePath) throws FileNotFoundException, IOException, ParseException {
	  JSONParser parser = new JSONParser();
		Object obj = parser.parse(new FileReader(filePath));
		JSONObject jsonObject = (JSONObject) obj;
		return jsonObject;
  }
  @BeforeMethod(alwaysRun = true)
  public synchronized void setUp(Method method, ITestContext ctx) throws Exception {
    testCaseName.set(method.getName());
    ApiBaseTest.method.set(method);
    extentTest.set(CommonUtil.getExtentReports().createTest(String.valueOf(this.testFeatureName) + "-" + getTestCaseName(), ""));
    commonUtil.set(new CommonUtil(this.testFeatureName, getExtentTest()));
    extentReportLogger.set(new ExtentReportLogger(getExtentTest()));
    restApiUtil.set(new RestApiUtil());
    if (method.isAnnotationPresent((Class)TestInfo.class)) {
      TestInfo testInfo = method.<TestInfo>getAnnotation(TestInfo.class);
      ctx.setAttribute("caseId", testInfo.testRailId());
      this.testRailCaseId = testInfo.testRailId();
    } 
  }
  
  @AfterMethod(alwaysRun = true)
  public synchronized void tearDown(ITestResult result) throws MalformedURLException, IOException, APIException {
    try {
      (new TestRailUtil(getExtentReportLogger())).updateStatusOnTestRail(result, this.testRailCaseId);
    } catch (Throwable t) {
      t.printStackTrace();
      getExtentReportLogger().logInfo(t.getMessage());
    } finally {
      CommonUtil.flushExtentReport();
      extentReportLogger.remove();
      restApiUtil.remove();
      commonUtil.remove();
      extentReportLogger.remove();
      extentTest.remove();
    } 
  }
  
  public void initializeTest(String featureName) throws Exception {
    this.testFeatureName = featureName;
    CommonUtil.setInitialConfigurations(CommonUtil.Platform.API);
  }
  
  public void assignAuthor() {
    if (getMethod().isAnnotationPresent((Class)TestInfo.class)) {
      TestInfo testInfo = getMethod().<TestInfo>getAnnotation(TestInfo.class);
      if (!testInfo.author().equalsIgnoreCase("none"))
        getExtentTest().assignAuthor(new String[] { testInfo.author() }); 
    } 
  }
  
  public void assignTag() {
    getExtentTest().assignCategory(new String[] { this.testFeatureName });
  }
}
