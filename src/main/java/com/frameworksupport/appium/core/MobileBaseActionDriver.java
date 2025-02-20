package com.frameworksupport.appium.core;

import java.io.File;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.InputSource;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.remote.SessionId;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import com.aventstack.extentreports.Status;
import com.frameworksupport.setup.AutomationBaseActionDriver;
import com.frameworksupport.setup.BaseActionDriver;
import com.frameworksupport.util.enums.Browser;
import com.google.common.collect.ImmutableMap;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.PerformsTouchActions;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.SupportsContextSwitching;
import io.appium.java_client.touch.offset.PointOption;


public class MobileBaseActionDriver extends BaseActionDriver{
	

	  protected AppiumDriver appiumDriver;
	  
	  public void setIOSDriver(IOSDriver appiumDriver) {
	    this.appiumDriver = (AppiumDriver)appiumDriver;
	  }
	  
	  public AndroidDriver getAndroidDriver() {
	    return (AndroidDriver)this.appiumDriver;
	  }
	  
	  public IOSDriver getIOSDriver() {
	    return (IOSDriver)this.appiumDriver;
	  }
	  
	  public void setAndroidDriver(AndroidDriver appiumDriver) {
	    this.appiumDriver = (AppiumDriver)appiumDriver;
	  }
	  
	  public void setAppiumDriver(AppiumDriver appiumDriver) {
	    this.appiumDriver = appiumDriver;
	  }
	  
	  public AppiumDriver getAppiumDriver() {
	    return this.appiumDriver;
	  }
	  
	  private WebDriverWait getWebDriverWaitObject() {
		  WebDriverWait wait = new WebDriverWait((WebDriver)this.appiumDriver, Duration.ofSeconds(AutomationBaseActionDriver.WAIT_TIMEOUT));
		  return wait;
	  }
	  private WebDriverWait getWebDriverWaitObject(int setTimeout) {
		     WebDriverWait wait = new WebDriverWait((WebDriver)this.appiumDriver, Duration.ofSeconds(setTimeout));
		     return wait;
	  }
	  
	  public void initializeLogging() {
	    this.commonUtil = AutomationBaseActionDriver.getCommonUtil();
	  }
	  
	  public void get(String Url) {
	    this.commonUtil.log("Launching uRL: " + Url);
	    this.appiumDriver.get(Url);
	  }
	  
	  public void quit() {
	    this.appiumDriver.quit();
	  }
	  
	  public WebElement getElement(By locator) {
	    return (WebElement)getWebDriverWaitObject().until(ExpectedConditions.presenceOfElementLocated(locator));
	  }
	  
	  public <T> WebElement waitForElementToBeVisible(T locator) {
	    this.commonUtil.log("waitForElementToBeVisible: " + locator);
	    try {
	      WebElement element = null;
	      if (locator.getClass().getName().contains("By")) {
	        element = (WebElement)getWebDriverWaitObject().until(ExpectedConditions.visibilityOfElementLocated((By)locator));
	      } else if (locator.getClass().getName().contains("String")) {
	        element = (WebElement)getWebDriverWaitObject().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(),\"" + (String)locator + "\"" + ")]")));
	      } 
	      return element;
	    } catch (StaleElementReferenceException s) {
	      this.commonUtil.log("StaleElementReferenceException occurred : " + s.getMessage());
	      this.commonUtil.waitInSeconds(2L);
	      waitForElementToBeVisible(locator);
	      return null;
	    } 
	  }
	  
	  public <T> WebElement waitForElementToBePresent(T locator, int timeOut) {
	    this.commonUtil.log("Wait for element to be present in DOM : " + locator);
	    try {
	      setImplicitWaitOnDriver(0);
	      WebElement element = null;
	      if (locator.getClass().getName().contains("By")) {
	        element = (WebElement)getWebDriverWaitObject(timeOut).until(ExpectedConditions.presenceOfElementLocated((By)locator));
	      } else if (locator.getClass().getName().contains("String")) {
	        element = (WebElement)getWebDriverWaitObject(timeOut).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(text(),\"" + (String)locator + "\"" + ")]")));
	      } 
	      return element;
	    } catch (StaleElementReferenceException s) {
	      this.commonUtil.log("StaleElementReferenceException occurred : " + s.getMessage());
	      this.commonUtil.waitInSeconds(2L);
	      waitForElementToBePresent(locator, timeOut);
	    } finally {
	      setImplicitWaitOnDriver(AutomationBaseActionDriver.WAIT_TIMEOUT);
	    } 
	    return null;
	  }
	  
	  public <T> WebElement waitForElementToBePresent(T locator) {
	    this.commonUtil.log("Wait for element to be present in DOM : " + locator);
	    try {
	      WebElement element = null;
	      if (locator.getClass().getName().contains("By")) {
	        element = (WebElement)getWebDriverWaitObject().until(ExpectedConditions.presenceOfElementLocated((By)locator));
	      } else if (locator.getClass().getName().contains("String")) {
	        element = (WebElement)getWebDriverWaitObject().until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(text(),\"" + (String)locator + "\"" + ")]")));
	        this.commonUtil.log(String.format("Text : %s Found !!!", new Object[] { (String)locator }));
	      } 
	      return element;
	    } catch (StaleElementReferenceException s) {
	      this.commonUtil.log("StaleElementReferenceException occurred : " + s.getMessage());
	      this.commonUtil.waitInSeconds(2L);
	      waitForElementToBePresent(locator);
	      return null;
	    } 
	  }
	  
	  public <T> WebElement waitForElementToBeVisible(T locator, int timeOut) {
	    this.commonUtil.log("Wait for element to be visible on web page: " + locator);
	    try {
	      setImplicitWaitOnDriver(0);
	      
	      WebElement element = null;
	      if (locator.getClass().getName().contains("By")) {
	        element = (WebElement)getWebDriverWaitObject(timeOut).until(ExpectedConditions.visibilityOfElementLocated((By)locator));
	      } else if (locator.getClass().getName().contains("String")) {
	        element = waitForElementToBeVisible(By.xpath("//*[contains(text(),\"" + (String)locator + "\"" + ")]"), 
	            timeOut);
	      } else {
	        element = (WebElement)getWebDriverWaitObject(timeOut).until(ExpectedConditions.visibilityOf((WebElement)locator));
	      } 
	      return element;
	    } catch (StaleElementReferenceException s) {
	      this.commonUtil.log("StaleElementReferenceException occurred : " + s.getMessage());
	      this.commonUtil.waitInSeconds(2L);
	      waitForElementToBeVisible(locator, timeOut);
	    } finally {
	      setImplicitWaitOnDriver(AutomationBaseActionDriver.WAIT_TIMEOUT);
	    } 
	    return null;
	  }
	  
	  public void waitForElementNotPresent(By locator, int timeOut) {
	    this.commonUtil.log("waitForElementNotPresent: " + locator);
	    try {
	      setImplicitWaitOnDriver(0);
	      getWebDriverWaitObject(timeOut).until(ExpectedConditions.invisibilityOfElementLocated(locator));
	    } catch (StaleElementReferenceException s) {
	      this.commonUtil.log("StaleElementReferenceException occurred : " + s.getMessage());
	      this.commonUtil.waitInSeconds(2L);
	      waitForElementToBeVisible(locator);
	    } finally {
	      setImplicitWaitOnDriver(AutomationBaseActionDriver.WAIT_TIMEOUT);
	    } 
	  }
	  
	  public <T> WebElement waitForElementToBeClickable(T locator) {
	    this.commonUtil.log("waitForElementToBeClickable: " + locator);
	    try {
	      WebElement element = null;
	      if (locator.getClass().getName().contains("By")) {
	        element = (WebElement)getWebDriverWaitObject().until(ExpectedConditions.elementToBeClickable((By)locator));
	      } else if (locator.getClass().getName().contains("String")) {
	        element = (WebElement)getWebDriverWaitObject().until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(text(),\"" + (String)locator + "\"" + ")]")));
	      } 
	      return element;
	    } catch (StaleElementReferenceException s) {
	      this.commonUtil.log("StaleElementReferenceException occurred : " + s.getMessage());
	      this.commonUtil.waitInSeconds(2L);
	      waitForElementToBeVisible(locator);
	      return null;
	    } 
	  }
	  
	  public boolean isElementVisible(By locator, int waitTime) {
	    try {
	      setImplicitWaitOnDriver(0);
	      
	      getWebDriverWaitObject(waitTime).until(ExpectedConditions.visibilityOfElementLocated(locator));
	      return true;
	    } catch (Exception e) {
	      return false;
	    } finally {
	      setImplicitWaitOnDriver(AutomationBaseActionDriver.WAIT_TIMEOUT);
	    } 
	  }
	  
	  public boolean isElementPresent(By locator, int waitTime) {
	    try {
	      setImplicitWaitOnDriver(0);
	      getWebDriverWaitObject(waitTime).until(ExpectedConditions.presenceOfElementLocated(locator));
	      return true;
	    } catch (Throwable e) {
	      return false;
	    } finally {
	      setImplicitWaitOnDriver(AutomationBaseActionDriver.WAIT_TIMEOUT);
	    } 
	  }
	  
	  public void closeBrowser() {
	    try {
	      if (this.appiumDriver != null) {
	        this.commonUtil.log("Terminating Driver Session... ");
	        this.appiumDriver.quit();
	        this.appiumDriver = null;
	      } else {
	        this.commonUtil.log("Session is already closed...");
	      } 
	    } catch (Exception e) {
	      e.printStackTrace();
	      this.commonUtil.log("Error handled while closing browser ");
	      this.appiumDriver = null;
	    } 
	  }
	  
	  public void clearTextBoxValue(By locator) {
	    this.commonUtil.log("Cleaning textbox value");
	    boolean isTextBoxCleared = false;
	    for (int i = 0; i < 10; i++) {
	      if (getBrowserName().toLowerCase().contains(Browser.ANDROID_NATIVE_APP.toString().toLowerCase()) || 
	        getBrowserName().toLowerCase().contains(Browser.IOS_NATIVE_APP.toString().toLowerCase())) {
	        getElement(locator).clear();
	        isTextBoxCleared = true;
	        break;
	      } 
	      if (getElement(locator).getAttribute("value").trim().length() == 0) {
	        isTextBoxCleared = true;
	        break;
	      } 
	      try {
	        click(locator);
	      } catch (Exception e) {
	        e.printStackTrace();
	        this.commonUtil.log("Ignoring error : " + e.getMessage());
	      } 
	      try {
	        getElement(locator).clear();
	      } catch (Exception e) {
	        e.printStackTrace();
	        this.commonUtil.log("Ignoring error : " + e.getMessage());
	      } 
	      if (getElement(locator).getAttribute("value").trim().length() != 0)
	        try {
	          executeJavaScript("arguments[0].scrollIntoView(true);", getElement(locator));
	          getElement(locator).sendKeys(new CharSequence[] { Keys.CONTROL + "a" });
	          getElement(locator).sendKeys(new CharSequence[] { (CharSequence)Keys.DELETE });
	        } catch (Exception e) {
	          e.printStackTrace();
	          this.commonUtil.log("Ignoring error : " + e.getMessage());
	        }  
	      this.commonUtil.log("Text value not cleared. Trying again");
	      this.commonUtil.waitInSeconds(2L);
	    } 
	    if (!isTextBoxCleared)
	      throw new WebDriverException("Not able to clear text box value"); 
	  }
	  
	  public void type(By locator, String testData) {
	    this.commonUtil.log("Typing value : " + testData);
	    clearTextBoxValue(locator);
	    if (getBrowserName().toLowerCase().contains(Browser.ANDROID_NATIVE_APP.toString().toLowerCase()) || 
	      getBrowserName().toLowerCase().contains(Browser.IOS_NATIVE_APP.toString().toLowerCase())) {
	      tap(locator);
	    } else {
	      try {
	        ((WebElement)getWebDriverWaitObject().until(ExpectedConditions.elementToBeClickable(locator))).click();
	      } catch (Exception e) {
	        this.commonUtil.log("Ignoring error : " + e.getMessage());
	      } 
	    } 
	    ((WebElement)getWebDriverWaitObject().until(ExpectedConditions.elementToBeClickable(locator))).sendKeys(new CharSequence[] { testData });
	  }
	 
	  public void sendKeys(By locator, Object testData) {
	    testData = testData.toString().trim();
	    this.commonUtil.log("Typing value : " + testData);
	    getElement(locator).clear();
	    getElement(locator).sendKeys(new CharSequence[] { testData.toString() });
	  }
	  
	  public void click(By locator) {
	    this.commonUtil.log("Click : " + locator);
	    try {
	      ((WebElement)getWebDriverWaitObject().until(ExpectedConditions.elementToBeClickable(locator))).click();
	    } catch (WebDriverException e) {
	      this.commonUtil.log("Error message received " + e.getMessage());
	      waitForElementToBePresent(locator, 5);
	      this.commonUtil.waitInSeconds(2L);
	      if (getBrowserName().toLowerCase().contains(Browser.ANDROID_NATIVE_APP.toString().toLowerCase()) || 
	        getBrowserName().toLowerCase().contains(Browser.IOS_NATIVE_APP.toString().toLowerCase())) {
	        clickUsingActionApi(locator);
	      } else {
	        clickUsingJavaScript(locator);
	      } 
	    } 
	  }
	  
	  public void setImplicitWaitOnDriver(int maxWaitTime) {
	   
	  }
	  
	  public void clickUsingJavaScript(By locator) {
	    try {
	      AppiumDriver appiumDriver = this.appiumDriver;
	      appiumDriver.executeScript("arguments[0].click();", new Object[] { waitForElementToBePresent(locator) });
	    } catch (StaleElementReferenceException e) {
	      this.commonUtil.waitInSeconds(2L);
	      if (isElementPresent(locator, 7))
	        clickUsingJavaScript(locator); 
	    } 
	  }
	  
	  public void clickUsingJavaScript(WebElement element) {
	    AppiumDriver appiumDriver = this.appiumDriver;
	    appiumDriver.executeScript("arguments[0].click();", new Object[] { element });
	  }
	  
	  public void clickUsingActionApi(By locator) {
	    Actions actions = new Actions((WebDriver)this.appiumDriver);
	    actions.moveToElement(waitForElementToBePresent(locator)).click().build().perform();
	  }
	  
	  public void click(String text) {
	    By locator = By.xpath("//*[contains(text(),\"" + text + "\"" + ")]");
	    click(locator);
	  }
	  
	  public void click(WebElement element) {
	    ((WebElement)getWebDriverWaitObject().until(ExpectedConditions.elementToBeClickable(element))).click();
	  }
	  
	  public String getText(By locator) {
	    try {
	      return waitForElementToBePresent(locator).getText().trim();
	    } catch (StaleElementReferenceException s) {
	      this.commonUtil.log("StaleElementReferenceException occurred : " + s.getMessage());
	      this.commonUtil.waitInSeconds(2L);
	      getText(locator);
	      return null;
	    } 
	  }
	  
	  public String getInputBoxValue(By locator) {
	    try {
	      return getElement(locator).getAttribute("value").trim();
	    } catch (StaleElementReferenceException s) {
	      this.commonUtil.log("StaleElementReferenceException occurred : " + s.getMessage());
	      this.commonUtil.waitInSeconds(2L);
	      getInputBoxValue(locator);
	      return null;
	    } 
	  }
	  
	  public void refreshBrowser() {
	    this.commonUtil.log("Refreshing Browser !!");
	    this.appiumDriver.navigate().refresh();
	  }
	  
	  public void handleAlert() {
	    for (int i = 0; i < 10; i++) {
	      try {
	        Alert alert = this.appiumDriver.switchTo().alert();
	        alert.accept();
	        this.commonUtil.log("Alert handled");
	        break;
	      } catch (Throwable t) {
	        this.commonUtil.log("Alert is not there");
	        this.commonUtil.waitInSeconds(2L);
	      } 
	    } 
	  }
	  
	  public void close() {
	    this.appiumDriver.close();
	  }
	  
	  public WebDriver.TargetLocator switchTo() {
	    return this.appiumDriver.switchTo();
	  }
	  
	  public Object executeJavaScript(String param) {
	    AppiumDriver appiumDriver = this.appiumDriver;
	    return appiumDriver.executeScript(param, new Object[0]);
	  }
	  
	  public String executeJavaScript(String param, WebElement element) {
	    AppiumDriver appiumDriver = this.appiumDriver;
	    return (String)appiumDriver.executeScript(param, new Object[] { element });
	  }
	  
	  public WebDriver.Options manage() {
	    return this.appiumDriver.manage();
	  }
	  
	  public void deletecookies() {
	    this.appiumDriver.manage().deleteAllCookies();
	  }
	  
	  public void takeScreenShot(String filePath) {
	    try {
	      Augmenter augmenter = new Augmenter();
	      TakesScreenshot ts = (TakesScreenshot)augmenter.augment((WebDriver)this.appiumDriver);
	      File scrFile = (File)ts.getScreenshotAs(OutputType.FILE);
	      FileUtils.copyFile(scrFile, new File(filePath));
	    } catch (Throwable e) {
	      this.commonUtil.log("Screen shot failure " + e.getMessage());
	    } 
	  }
	  
	  public void enterTestData(By locator, Object testData) {
	    String value = null;
	    boolean isValueVerified = false;
	    for (int i = 0; i <= 10; i++) {
	      try {
	        testData = testData.toString().trim();
	        type(locator, testData.toString());
	        if (getElement(locator).getAttribute("value") != null) {
	          value = getElement(locator).getAttribute("value").trim();
	          this.commonUtil.log("value in input box: " + value);
	          Assert.assertEquals(value.toLowerCase().trim(), testData.toString().toLowerCase().trim());
	          isValueVerified = true;
	          break;
	        } 
	      } catch (AssertionError e) {
	        this.commonUtil.log("Value doesn't match. Trying again!! ");
	        this.commonUtil.waitInSeconds(2L);
	      } 
	    } 
	    if (!isValueVerified)
	      throw new AssertionError("Expected: " + testData + " but found: " + value); 
	  }
	  
	  public void verifyPageTitle(String title) {
	    verifyPageTitle(title, 10);
	  }
	  
	  public void verifyPageTitle(String title, int timeout) {
	    this.commonUtil.log("Verifying Page title: " + title);
	    boolean isTitleVerified = false;
	    this.commonUtil.log("Actual: " + this.appiumDriver.getTitle());
	    for (int i = 1; i <= 10; i++) {
	      String actual = this.appiumDriver.getTitle();
	      if (actual.trim().toLowerCase().contains(title.toLowerCase())) {
	        isTitleVerified = true;
	        this.commonUtil.log("Page Title verified | ");
	        break;
	      } 
	      this.commonUtil.waitInSeconds(1L);
	    } 
	    if (!isTitleVerified)
	      Assert.assertTrue(false, "Page Title is incorrect"); 
	  }
	  
	  public WebDriver.Navigation navigate() {
	    return this.appiumDriver.navigate();
	  }
	  
	  public String getTitle() {
	    return this.appiumDriver.getTitle();
	  }
	  
	  public Capabilities getCapabilities() {
	    return this.appiumDriver.getCapabilities();
	  }
	  
	  public SessionId getSessionId() {
	    return this.appiumDriver.getSessionId();
	  }
	  
	  public String getCurrentUrl() {
	    return this.appiumDriver.getCurrentUrl();
	  }
	  
	  public List<WebElement> getElements(By locator, int timeout) {
	    try { 
	      setImplicitWaitOnDriver(0);
	      return (List<WebElement>)getWebDriverWaitObject(timeout).until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
	    } catch (WebDriverException e) {
	      throw new WebDriverException(e);
	    } finally {
	      setImplicitWaitOnDriver(AutomationBaseActionDriver.WAIT_TIMEOUT);
	    } 
	  }
	  
	  public boolean isDriverSessionActive() {
	    this.commonUtil.log("Checking if driver session is closed!!");
	    boolean isActive = true;
	    try {
	      Assert.assertNotNull(this.appiumDriver.getPageSource());
	      Assert.assertNotNull(this.appiumDriver.getSessionId());
	      this.commonUtil.log("Driver session is Active..");
	    } catch (Throwable e) {
	      this.commonUtil.log("Driver session is closed..");
	      isActive = false;
	    } 
	    return isActive;
	  }
	  
	  public void verifyUrl(String textInUrl, int timeOut) {
	    this.commonUtil.log("Verify following text in URL : " + textInUrl);
	    boolean check = true;
	    for (int i = 1; i <= timeOut; i++) {
	      String url = this.appiumDriver.getCurrentUrl();
	      this.commonUtil.log("actual URL : " + url);
	      if (url.trim().contains(textInUrl)) {
	        check = false;
	        this.commonUtil.log("URL verified | ");
	        break;
	      } 
	      this.commonUtil.waitInSeconds(1L);
	    } 
	    if (check)
	      throw new WebDriverException("URL seems to be incorrect. Need to investigate"); 
	  }
	  
	  public void waitForElementToBeEnabled(By locator, int timeOut) {
	    this.commonUtil.log("waitForElementToBeEnabled..");
	    getWebDriverWaitObject(timeOut).until(ExpectedConditions.presenceOfElementLocated(locator));
	    for (int i = 0; i < timeOut && (
	      !isElementPresent(locator, 1) || 
	      
	      !((WebElement)getWebDriverWaitObject((int) 1L).until(ExpectedConditions.presenceOfElementLocated(locator))).isEnabled()); i++) {
	      this.commonUtil.log("waiting for element to be enabled..");
	      this.commonUtil.waitInSeconds(1L);
	    } 
	  }
	  
	  public void mouseHover(By locator) {
	    try {
	      if (getBrowserName().toLowerCase().contains(Browser.ANDROID_CHROME.toString().toLowerCase())) {
	        Actions actions = new Actions((WebDriver)this.appiumDriver);
	        actions.moveToElement(waitForElementToBePresent(locator)).build().perform();
	      } else if (getBrowserName().toLowerCase().contains(Browser.IOS_SAFARI.toString().toLowerCase())) {
	        this.appiumDriver.executeScript("arguments[0].scrollIntoView(true);", new Object[] { waitForElementToBePresent(locator) });
	      } else {
	        String wrn = "Not implemented for selected Platform: " + getBrowserName();
	        AutomationBaseActionDriver.getTest().log(Status.WARNING, wrn);
	        System.err.println(wrn);
	      } 
	    } catch (TimeoutException t) {
	      throw new TimeoutException(t);
	    } catch (Exception e) {
	      this.commonUtil.log("Ignoring issue occurred due to mouse hover: " + e);
	    } 
	  }
	  
	  public Actions getActionsInstance() {
	    Actions actions = new Actions((WebDriver)this.appiumDriver);
	    return actions;
	  }
	  
	  public Set<String> getWindowHandles() {
	    return this.appiumDriver.getWindowHandles();
	  }
	  
	  public String getWindowHandle() {
	    return this.appiumDriver.getWindowHandle();
	  }
	  
	  public void selectByText(By locator, String testData) {
	    mouseHover(locator);
	    WebElement element = waitForElementToBePresent(locator);
	    selectByText(element, testData);
	  }
	  
	  
	  public void refreshBrowserUntilElementPresent(By locator, int maxAttempt) {
	    this.commonUtil.log("Refresh browser until element present in DOM : " + locator);
	    boolean isElementFound = false;
	    for (int i = 0; i < maxAttempt; i++) {
	      if (isElementPresent(locator, 5)) {
	        isElementFound = true;
	        break;
	      } 
	      refreshBrowser();
	    } 
	    Assert.assertTrue(isElementFound, "Element not found.");
	  }
	  
	 
	  public void typeWithDelay(By locator, String testData, long delayInMilliSeconds) {
	    this.commonUtil.log("Typing value : " + testData);
	    clearTextBoxValue(locator);
	    try {
	      int length = testData.length();
	      long actualDelayMillis = 0L;
	      if (delayInMilliSeconds > 3000L) {
	        delayInMilliSeconds = 3000L;
	      } else if (delayInMilliSeconds > 0L) {
	        actualDelayMillis = delayInMilliSeconds;
	      } 
	      for (int i = 0; i < length; i++) {
	        ((WebElement)getWebDriverWaitObject().until(ExpectedConditions.presenceOfElementLocated(locator)))
	          .sendKeys(new CharSequence[] { Character.toString(testData.charAt(i)) });
	        try {
	          Thread.sleep(actualDelayMillis);
	        } catch (InterruptedException interruptedException) {}
	      } 
	    } catch (StaleElementReferenceException e) {
	      this.commonUtil.log("Ignoring error : " + e.getMessage());
	      this.commonUtil.waitInSeconds(5L);
	      typeWithDelay(locator, testData, delayInMilliSeconds);
	    } 
	  }

	

	
	public void sendkeys(By byLocator, String enterExpectedText) {
		this.commonUtil.log("Typing value : " + enterExpectedText);
	    clearTextBoxValue(byLocator);
	    try {
	        ((WebElement)getWebDriverWaitObject().until(ExpectedConditions.elementToBeClickable(byLocator))).sendKeys(new CharSequence[] { enterExpectedText });
	      } catch (Exception e) {
	        this.commonUtil.log("Ignoring error : " + e.getMessage());
	      } 
		
	}


	public void typeUsingActionsInstance(By byLocator, String enterExpectedText) {
		 String value = null;
		    for (int i = 0; i <= 10; i++) {
		      try {
		        Actions actions = new Actions((WebDriver)this.appiumDriver);
		        actions.sendKeys(new CharSequence[] { enterExpectedText }).build().perform();
		        if (getBrowserName().toLowerCase().contains(Browser.ANDROID_NATIVE_APP.toString().toLowerCase()) || 
		          getBrowserName().toLowerCase().contains(Browser.IOS_NATIVE_APP.toString().toLowerCase())) {
		          if (getElement(byLocator).getText() != null) {
		            value = getElement(byLocator).getText().trim();
		            this.commonUtil.log("value in input box :" + value);
		            Assert.assertEquals(value.toLowerCase().trim(), enterExpectedText.toString().toLowerCase().trim());
		            break;
		          } 
		        } else if (getElement(byLocator).getAttribute("value") != null) {
		          value = getElement(byLocator).getAttribute("value").trim();
		          this.commonUtil.log("value in input box :" + value);
		          Assert.assertEquals(value.toLowerCase().trim(), enterExpectedText.toString().toLowerCase().trim());
		          break;
		        } 
		      } catch (AssertionError e) {
		        getElement(byLocator).clear();
		      } catch (Throwable e) {
		        e.printStackTrace();
		        this.commonUtil.log(e.getMessage());
		      } 
		    } 
		
	}


	public void clickUsingActionsInstance(By byLocator) {
		 Actions actions = new Actions((WebDriver)this.appiumDriver);
		    actions.moveToElement(waitForElementToBePresent(byLocator)).click().build().perform();
		
	}
	
	  public WebElement scrollDownTo(By locator) {
		    this.commonUtil.log("Scrolling down to: " + locator);
		    boolean isElementFound = false;
		    try {
		      setImplicitWaitOnDriver(0);
		      for (int i = 0; i < 20; i++) {
		        if (this.appiumDriver.findElements(locator).size() == 0) {
		          scrollDown();
		          this.commonUtil.waitInSeconds(1L);
		        } else {
		          isElementFound = true;
		          break;
		        } 
		        System.out.println("searching...");
		      } 
		      if (!isElementFound)
		        throw new WebDriverException("Element no found: " + locator); 
		      return this.appiumDriver.findElement(locator);
		    } catch (WebDriverException e) {
		      throw new WebDriverException(e);
		    } finally {
		      setImplicitWaitOnDriver(AutomationBaseActionDriver.WAIT_TIMEOUT);
		    } 
		  }
		  

	 private void scrollDown() {
//		    Dimension dimension = this.appiumDriver.manage().window().getSize();
//		    int scrollStart = (int)(dimension.getHeight() * 0.5D);
//		    int scrollEnd = (int)(dimension.getHeight() * 0.1D);
//		    (new TouchAction((PerformsTouchActions)this.appiumDriver)).press(PointOption.point(0, scrollStart))
//		      .waitAction(WaitOptions.waitOptions(Duration.ofSeconds(1L))).moveTo(PointOption.point(0, scrollEnd))
//		      .release().perform();
		    
		  
		    Dimension dimension = this.appiumDriver.manage().window().getSize();
		    int scrollStart = (int)(dimension.getHeight() * 0.5D);
		    int scrollEnd = (int)(dimension.getHeight() * 0.2D);
		    int x = dimension.getWidth() / 2;
		    PointerInput FINGER = new PointerInput(PointerInput.Kind.TOUCH, "finger");
		    Sequence swipe = (new Sequence((InputSource)FINGER, 1))
		      .addAction(
		        FINGER.createPointerMove(Duration.ofMillis(0L), PointerInput.Origin.viewport(), x, scrollStart))
		      .addAction(FINGER.createPointerDown(PointerInput.MouseButton.LEFT.asArg()))
		      .addAction(
		        FINGER.createPointerMove(Duration.ofMillis(700L), PointerInput.Origin.viewport(), x, scrollEnd))
		      .addAction(FINGER.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
		    this.appiumDriver.perform(Arrays.asList(new Sequence[] { swipe }));
		  }
		  
	 
		  public void tap(By locator) {
		    Point point = getElement(locator).getLocation();
		    (new TouchAction((PerformsTouchActions)this.appiumDriver)).tap(PointOption.point(point.getX(), point.getY()))
		      .perform();
		  }
		  
		  public void switchToWebView(String packageName) {
			    boolean isWebViewFound = false;
			    Set<String> contextNames = ((SupportsContextSwitching) this.appiumDriver).getContextHandles();
			    System.out.println(((SupportsContextSwitching) this.appiumDriver).getContext());
			    for (int i = 0; i < 10; i++) {
			      contextNames = ((SupportsContextSwitching) this.appiumDriver).getContextHandles();
			      for (String contextName : contextNames) {
			        if (contextName.toLowerCase().contains("webview_" + packageName.toLowerCase())) {
			          ((SupportsContextSwitching) this.appiumDriver).context(contextName);
			          isWebViewFound = true;
			          break;
			        } 
			      } 
			      if (isWebViewFound)
			        break; 
			      this.commonUtil.waitInSeconds(1L);
			      System.out.println("waiting..");
			    } 
			    System.out.println(((SupportsContextSwitching) this.appiumDriver).getContext());
			  }
			  
			  public void switchToNativeApp() {
			    ((SupportsContextSwitching) this.appiumDriver).context("NATIVE_APP");
			  }
			  public void selectByText(WebElement element, String testData) {
				    boolean isSelected = false;
				    try {
				      Select select = new Select(element);
				      List<WebElement> selectedOptions = select.getAllSelectedOptions();
				      for (WebElement selectedOption : selectedOptions) {
				        if (selectedOption.getText().trim().toLowerCase().contains(testData.toLowerCase())) {
				          isSelected = true;
				          this.commonUtil.log("Option already selected");
				          break;
				        } 
				      } 
				      if (!isSelected)
				        select.selectByVisibleText(testData); 
				    } catch (WebDriverException t) {
				      throw new WebDriverException(t);
				    } 
				  }		
			  
					
			  
				public  void longClickOnElement(By by, int timeInMilliSeconds) {
					this.appiumDriver.executeScript("mobile: longClickGesture", ImmutableMap.of("elementId",
							((RemoteWebElement) getElement(by)).getId(), "duration", timeInMilliSeconds));
				}

				public void longClickOnElementByCoordinates(int xCord, int yCord, int timeInMilliSeconds) {
					this.appiumDriver.executeScript("mobile: longClickGesture",
							ImmutableMap.of("x", xCord, "y", yCord, "duration", timeInMilliSeconds));
				}
				
				 public  void clickGesture(By by){
					 this.appiumDriver.executeScript("mobile: clickGesture", ImmutableMap.of(
			                "elementId", ((RemoteWebElement) this.appiumDriver.findElement(by)).getId()
			        ));
			    }

				public void dragAndDropElement(By by, int dropXcord, int dropYcord) {

					this.appiumDriver.executeScript("mobile: dragGesture", ImmutableMap.of("elementId",
							((RemoteWebElement) getElement(by)).getId(), "endX", dropXcord, "endY", dropYcord));
				}
				
				 private  void zoomInByCordnates(int leftXCord,int topYcord,int width,int height) throws InterruptedException {
			            
					 this.appiumDriver.executeScript("mobile: pinchOpenGesture", ImmutableMap.of(
			                "left", leftXCord,
			                "top", topYcord,
			                "width", width,
			                "height", height,
			                "percent", 0.75
			            ));
			    }
				 //swipeType is up, down, left and right 
					public void swipeElement(By by, String swipeType) {

						this.appiumDriver.executeScript("mobile: swipeGesture", ImmutableMap.of("elementId",
								((RemoteWebElement) getElement(by)).getId(), "direction", swipeType, "percent", 0.75));

					}
					 //swipeType is up, down, left and right
					public void swipeElementByCordnates(String swipeType) {
						this.appiumDriver.executeScript("mobile: swipeGesture", ImmutableMap.of("left", 100, "top", 100,
								"width", 200, "height", 200, "direction", swipeType, "percent", 0.75));

					}

					public void scrollToElement(By by, String scrollType) {
						this.appiumDriver.executeScript("mobile: scrollGesture",
								ImmutableMap.of("elementId",
										((RemoteWebElement) this.appiumDriver.findElement(by)).getId(), "direction",
										scrollType, "percent", 1.0));
					}
					//scrolltype down
					public void scrollToElementToBeVisibleByCordnates(By by,String scrollType) {
					while(!isElementVisible(by, 10)) {
					             this.appiumDriver.executeScript("mobile: scrollGesture", ImmutableMap.of(
					                    "left", 100, "top", 100, "width",600, "height", 1000,
					                    "direction", scrollType,
					                    "percent", 1.0
					            ));
					        }
					}
	

}
