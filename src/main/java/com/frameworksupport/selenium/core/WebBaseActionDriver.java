package com.frameworksupport.selenium.core;


import java.io.File;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
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
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.SessionId;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import com.frameworksupport.setup.BaseActionDriver;
import com.frameworksupport.setup.AutomationBaseActionDriver;
import com.frameworksupport.util.enums.Browser;
import com.frameworksupport.util.json.JsonUtil;


public class WebBaseActionDriver extends BaseActionDriver {
  protected RemoteWebDriver webdriver;
  
  public void setWebDriver(RemoteWebDriver driver) {
    this.webdriver = driver;
  }
  
  public  RemoteWebDriver getWebDriver() {
    return this.webdriver;
    
  }
  
  public void initializeLogging() {
    this.commonUtil = AutomationBaseActionDriver.getCommonUtil();
  }
  
  public void get(String Url) {
    this.commonUtil.log("Launching uRL: " + Url);
    this.webdriver.get(Url);
  }
  
  public void quit() {
    this.webdriver.quit();
  }
  
  public WebElement getElement(By locator) {
    return waitForElementToBePresent(locator);
  }
  private ThreadLocal<WebDriverWait> webdriverwaitThread = new ThreadLocal<>();

  private synchronized WebDriverWait getWebDriverWaitObject() {
	  WebDriverWait wait = new WebDriverWait((WebDriver)this.webdriver, Duration.ofSeconds(AutomationBaseActionDriver.WAIT_TIMEOUT));
	   webdriverwaitThread.set(wait);
	  return webdriverwaitThread.get();
  }
  private synchronized WebDriverWait getWebDriverWaitObject(int setTimeout) {
	    WebDriverWait  wait = new WebDriverWait((WebDriver)this.webdriver, Duration.ofSeconds(setTimeout));
	     webdriverwaitThread.set(wait);
		  return webdriverwaitThread.get();
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
  
  public <T> WebElement waitForElementToBePresent(T locator, int timeOut) {
    this.commonUtil.log("Wait for element to be present in DOM : " + locator);
    try {
    
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
      return null;
    } 
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
      return null;
    } 
  }
  
  public void waitForElementNotPresent(By locator, int timeOut) {
    this.commonUtil.log("waitForElementNotPresent: " + locator);
    try {
      getWebDriverWaitObject(timeOut).until(ExpectedConditions.invisibilityOfElementLocated(locator));
    } catch (StaleElementReferenceException s) {
      this.commonUtil.log("StaleElementReferenceException occurred : " + s.getMessage());
      this.commonUtil.waitInSeconds(2L);
      waitForElementToBeVisible(locator);
    } 
  }
  
  public boolean isElementVisible(By locator, int waitTime) {
    try {
     getWebDriverWaitObject(waitTime).until(ExpectedConditions.visibilityOfElementLocated(locator));
      return true;
    } catch (Exception e) {
      return false;
    } 
  }
  
  public boolean isElementPresent(By locator, int waitTime) {
    try {
     getWebDriverWaitObject(waitTime).until(ExpectedConditions.presenceOfElementLocated(locator));
      return true;
    } catch (Throwable e) {
      return false;
    } 
  }
  
  public void closeBrowser() {
    try {
      if (this.webdriver != null) {
        this.commonUtil.log("Terminating Driver Session... ");
        this.webdriver.quit();
        this.webdriver = null;
      } else {
        this.commonUtil.log("Session is already closed...");
      } 
    } catch (Exception e) {
      e.printStackTrace();
      this.commonUtil.log("Error handled while closing browser ");
      this.webdriver = null;
    } 
  }
  
  public void clearTextBoxValue(By locator) {
    this.commonUtil.log("Cleaning textbox value");
    boolean isTextBoxCleared = false;
    for (int i = 0; i < 10; i++) {
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
    try {
      ((WebElement)getWebDriverWaitObject().until(ExpectedConditions.elementToBeClickable(locator))).click();
    } catch (Exception e) {
      this.commonUtil.log("Ignoring error : " + e.getMessage());
    } 
    ((WebElement)getWebDriverWaitObject().until(ExpectedConditions.elementToBeClickable(locator))).sendKeys(new CharSequence[] { testData });
  }
  
  public void sendkeys(By locator, String data) {
	  this.commonUtil.log("Typing value : " + data);
	    clearTextBoxValue(locator);
	    try {
	        ((WebElement)getWebDriverWaitObject().until(ExpectedConditions.elementToBeClickable(locator))).sendKeys(new CharSequence[] { data });
	      } catch (Exception e) {
	        this.commonUtil.log("Ignoring error : " + e.getMessage());
	      } 
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
        ((WebElement)getWebDriverWaitObject().until(ExpectedConditions.presenceOfElementLocated(locator))).sendKeys(new CharSequence[] { Character.toString(testData.charAt(i)) });
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
  
  public void typeUsingActionsInstance(By locator, String testData) {
    String value = null;
    Actions actions = new Actions((WebDriver)this.webdriver);
    for (int i = 0; i <= 10; i++) {
      try {
        actions.sendKeys(new CharSequence[] { testData }).build().perform();
        if (getElement(locator).getAttribute("value") != null) {
          value = getElement(locator).getAttribute("value").trim();
          this.commonUtil.log("value in input box :" + value);
          Assert.assertEquals(value.toLowerCase().trim(), testData.toString().toLowerCase().trim());
          break;
        } 
      } catch (AssertionError e) {
        getElement(locator).clear();
        actions.click(getElement(locator));
      } catch (Throwable e) {
        e.printStackTrace();
        this.commonUtil.log(e.getMessage());
      } 
      this.commonUtil.waitInSeconds(1L);
    } 
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
      clickUsingJavaScript(locator);
    } 
  }
  
  public void setImplicitWaitOnDriver(int maxWaitTime) {}
  
  public void clickUsingJavaScript(By locator) {
    try {
      RemoteWebDriver remoteWebDriver = this.webdriver;
      remoteWebDriver.executeScript("arguments[0].click();", new Object[] { waitForElementToBePresent(locator) });
    } catch (StaleElementReferenceException e) {
      this.commonUtil.waitInSeconds(2L);
      if (isElementPresent(locator, 7))
        clickUsingJavaScript(locator); 
    } 
  }
  
  public void clickUsingJavaScript(WebElement element) {
    RemoteWebDriver remoteWebDriver = this.webdriver;
    remoteWebDriver.executeScript("arguments[0].click();", new Object[] { element });
  }
  
  public void clickUsingActionsInstance(By locator) {
    Actions actions = new Actions((WebDriver)this.webdriver);
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
    this.webdriver.navigate().refresh();
  }
  
  public void handleAlert() {
    for (int i = 0; i < 10; i++) {
      try {
        Alert alert = this.webdriver.switchTo().alert();
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
    this.webdriver.close();
  }
  
  public WebDriver.TargetLocator switchTo() {
    return this.webdriver.switchTo();
  }
  
  public Object executeJavaScript(String param) {
    RemoteWebDriver remoteWebDriver = this.webdriver;
    return remoteWebDriver.executeScript(param, new Object[0]);
  }
  
  public String executeJavaScript(String param, WebElement element) {
    RemoteWebDriver remoteWebDriver = this.webdriver;
    return (String)remoteWebDriver.executeScript(param, new Object[] { element });
  }
  
  public WebDriver.Options manage() {
    return this.webdriver.manage();
  }
  
  public void deletecookies() {
    this.webdriver.manage().deleteAllCookies();
  }
  
  public void takeScreenShot(String filePath) {
    try {
      Augmenter augmenter = new Augmenter();
      TakesScreenshot ts = (TakesScreenshot)augmenter.augment((WebDriver)this.webdriver);
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
    this.commonUtil.log("Actual: " + this.webdriver.getTitle());
    for (int i = 1; i <= 10; i++) {
      String actual = this.webdriver.getTitle();
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
    return this.webdriver.navigate();
  }
  
  public String getTitle() {
    return this.webdriver.getTitle();
  }
  
  public Capabilities getCapabilities() {
    return this.webdriver.getCapabilities();
  }
  
  public SessionId getSessionId() {
    return this.webdriver.getSessionId();
  }
  
  public String getCurrentUrl() {
    return this.webdriver.getCurrentUrl();
  }
  
  public List<WebElement> getElements(By locator, int timeout) {
    return (List<WebElement>)getWebDriverWaitObject(timeout).until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
  }
  
  public boolean isDriverSessionActive() {
    this.commonUtil.log("Checking if driver session is closed!!");
    boolean isActive = true;
    try {
      Assert.assertNotNull(this.webdriver.getPageSource());
      Assert.assertNotNull(this.webdriver.getSessionId());
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
      String url = this.webdriver.getCurrentUrl();
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
    for (int i = 0; i < timeOut && (!isElementPresent(locator, 1) ||  !((WebElement)getWebDriverWaitObject(timeOut).until(ExpectedConditions.presenceOfElementLocated(locator))).isEnabled()); i++) {
      this.commonUtil.log("waiting for element to be enabled..");
      this.commonUtil.waitInSeconds(1L);
    } 
  }
  
  public void mouseHover(By locator) {
    try {
      if (getBrowserName().toLowerCase().contains(Browser.FIREFOX.toString().toLowerCase())) {
        scrollDownTo(locator);
      } else {
        Actions actions = new Actions((WebDriver)this.webdriver);
        actions.moveToElement(waitForElementToBePresent(locator)).build().perform();
      } 
    } catch (TimeoutException t) {
      throw new TimeoutException(t);
    } catch (Exception e) {
      this.commonUtil.log("Ignoring issue occurred due to mouse hover: " + e);
    } 
  }
  
  public WebElement scrollDownTo(By locator) {
    Point point = getElement(locator).getLocation();
    int x_coordinate = point.getX();
    int y_coordinate = point.getY();
    RemoteWebDriver remoteWebDriver = this.webdriver;
    remoteWebDriver.executeScript("window.scrollBy(" + x_coordinate + ", " + y_coordinate + ");", new Object[0]);
    return getElement(locator);
  }
  
  public Actions getActionsInstance() {
    Actions actions = new Actions((WebDriver)this.webdriver);
    return actions;
  }
  
  public Set<String> getWindowHandles() {
    return this.webdriver.getWindowHandles();
  }
  
  public String getWindowHandle() {
    return this.webdriver.getWindowHandle();
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
  
  public List<String> getWindowsId() {
    Set<String> handles = getWindowHandles();
    Iterator<String> iterator = handles.iterator();
    List<String> windowsId = new ArrayList<>();
    while (iterator.hasNext())
      windowsId.add(iterator.next()); 
    return windowsId;
  }
  
  public void selectByText(By locator, String testData) {
    mouseHover(locator);
    WebElement element = waitForElementToBePresent(locator);
    selectByText(element, testData);
  }
  
  public void selectByText(WebElement element, String testData) {
    boolean isSelected = false;
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
  }
}