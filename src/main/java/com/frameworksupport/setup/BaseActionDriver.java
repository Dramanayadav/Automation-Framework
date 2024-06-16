package com.frameworksupport.setup;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.SessionId;
import com.frameworksupport.util.general.CommonUtil;

public abstract class BaseActionDriver {
	  protected CommonUtil commonUtil;
	  
	  private String browserName;
	  
	  private String testCaseName;
	  
	  public String getTestCaseName() {
	    return this.testCaseName;
	  }
	  
	  public void setTestCaseName(String testCaseName) {
	    this.testCaseName = testCaseName;
	  }
	  
	  public String getBrowserName() {
	    return this.browserName;
	  }
	  
	  public void setBrowserName(String browserName) {
	    this.browserName = browserName;
	  }
	  
	  public abstract Actions getActionsInstance();
	  
	  public abstract void verifyPageTitle(String extectedTitle);
	  
	  public abstract void enterTestData(By byLocator, Object enterExpectedText);
	  
	  public abstract SessionId getSessionId();
	  
	  public abstract void setImplicitWaitOnDriver(int maxWaitTime);
	  
	  public abstract void initializeLogging();
	  
	  public abstract void handleAlert();
	  
	  public abstract void takeScreenShot(String paramString);
	  
	  public abstract void click(By byLocator);
	  
	  public abstract void click(String stringText);
	  
	  public abstract void click(WebElement webElement);
	  
	  public abstract void clickUsingActionsInstance(By byLocator);
	  
	  public abstract void clickUsingJavaScript(By byLocator);
	  
	  public abstract void clickUsingJavaScript(WebElement webElement);
	  
	  public abstract void closeBrowser();
	  
	  public abstract WebElement getElement(By byLocator);
	  
	  public abstract List<WebElement> getElements(By byLocator, int timeout);
	  
	  public abstract <T> WebElement waitForElementToBePresent(T paramT);
	  
	  public abstract <T> WebElement waitForElementToBePresent(T paramT, int timeOut);
	  
	  public abstract <T> WebElement waitForElementToBeClickable(T paramT);
	  
	  public abstract void waitForElementNotPresent(By paramBy, int timeOut);
	  
	  public abstract void type(By byLocator, String enterExpectedText);
	  
	  public abstract void sendkeys(By byLocator, String enterExpectedText);
	  public abstract void typeUsingActionsInstance(By byLocator, String enterExpectedText);
	  
	  public abstract void clearTextBoxValue(By byLocator);
	  
	  public abstract <T> WebElement waitForElementToBeVisible(T paramT);
	  
	  public abstract <T> WebElement waitForElementToBeVisible(T paramT, int timeOut);
	  
	  public abstract void waitForElementToBeEnabled(By byLocator, int timeOut);
	  
	  public abstract boolean isElementPresent(By byLocator, int timeOut);
	  
	  public abstract void deletecookies();
	  
	  public abstract String getInputBoxValue(By byLocator);
	  
	  public abstract String getText(By byLocator);
	  
	  public abstract boolean isElementVisible(By byLocator, int timeOut);
	  
	  public abstract Object executeJavaScript(String string);
	  
	  public abstract String executeJavaScript(String string, WebElement webElement);
	  
	  public abstract void refreshBrowser();
	  
	  public abstract WebDriver.Options manage();
	  
	  public abstract void close();
	  
	  public abstract void quit();
	  
	  public abstract WebDriver.Navigation navigate();
	  
	  public abstract String getTitle();
	  
	  public abstract Capabilities getCapabilities();
	  
	  public abstract String getCurrentUrl();
	  
	  public abstract void get(String string);
	  
	  public abstract WebDriver.TargetLocator switchTo();
	  
	  public abstract void sendKeys(By byLocator, Object enterExpectedText);
	  
	  public abstract boolean isDriverSessionActive();
	  
	  public abstract void verifyUrl(String enterExpectedText, int timeOut);
	  
	  public abstract void mouseHover(By byLocator);
	  
	  public abstract WebElement scrollDownTo(By byLocator);
	  
	  public abstract Set<String> getWindowHandles();
	  
	  public abstract String getWindowHandle();
	  
	  public abstract void selectByText(By byLocator, String enterExpectedText);
	  
	  public abstract void refreshBrowserUntilElementPresent(By byLocator, int maxAttempt);
	  
	  public abstract void typeWithDelay(By byLocator, String enterExpectedText, long delayInMilliSeconds);
	  
	  public List<String> switchToTabWindow() {
	    boolean flag = true;
	    Set<String> handles = null;
	    for (int i = 0; i < 7; i++) {
	      handles = getWindowHandles();
	      if (handles.size() == 2) {
	        flag = false;
	        break;
	      } 
	      this.commonUtil.log("Waiting for new tab to open..");
	      this.commonUtil.waitInSeconds(5L);
	    } 
	    if (flag)
	      throw new WebDriverException("Tab window is not opened"); 
	    this.commonUtil.log("Moving ahead on new tab");
	    String mainWindow = getWindowHandle();
	    Object[] hndlArr = handles.toArray();
	    String valComp = (String)hndlArr[0];
	    String tabWindow = null;
	    if (valComp.equalsIgnoreCase(mainWindow)) {
	      tabWindow = (String)hndlArr[1];
	    } else {
	      tabWindow = valComp;
	    } 
	    close();
	    switchTo().window(tabWindow);
	    List<String> windowsId = new ArrayList<>();
	    windowsId.add(mainWindow);
	    windowsId.add(tabWindow);
	    return windowsId;
	  }
	  
	  public List<String> switchToTabWithoutClosingMainWindow() {
	    boolean flag = true;
	    Set<String> handles = null;
	    for (int i = 0; i <= 3; i++) {
	      handles = getWindowHandles();
	      if (handles.size() == 2) {
	        flag = false;
	        break;
	      } 
	      this.commonUtil.log("Waiting for new tab to open..");
	      this.commonUtil.waitInSeconds(3L);
	    } 
	    if (flag)
	      throw new WebDriverException("Tab window is not opened"); 
	    this.commonUtil.log("Moving ahead on new tab");
	    String mainWindow = getWindowHandle();
	    Object[] hndlArr = handles.toArray();
	    String valComp = (String)hndlArr[0];
	    String tabWindow = null;
	    if (valComp.equalsIgnoreCase(mainWindow)) {
	      tabWindow = (String)hndlArr[1];
	    } else {
	      tabWindow = valComp;
	    } 
	    switchTo().window(tabWindow);
	    List<String> windowsId = new ArrayList<>();
	    windowsId.add(mainWindow);
	    windowsId.add(tabWindow);
	    return windowsId;
	  }
	  
	  public String getAbsoluteXPath(WebElement element) {
	    return executeJavaScript("function absoluteXPath(element) {var comp, comps = [];var parent = null;var xpath = '';var getPos = function(element) {var position = 1, curNode;if (element.nodeType == Node.ATTRIBUTE_NODE) {return null;}for (curNode = element.previousSibling; curNode; curNode = curNode.previousSibling){if (curNode.nodeName == element.nodeName) {++position;}}return position;};if (element instanceof Document) {return '/';}for (; element && !(element instanceof Document); element = element.nodeType ==Node.ATTRIBUTE_NODE ? element.ownerElement : element.parentNode) {comp = comps[comps.length] = {};switch (element.nodeType) {case Node.TEXT_NODE:comp.name = 'text()';break;case Node.ATTRIBUTE_NODE:comp.name = '@' + element.nodeName;break;case Node.PROCESSING_INSTRUCTION_NODE:comp.name = 'processing-instruction()';break;case Node.COMMENT_NODE:comp.name = 'comment()';break;case Node.ELEMENT_NODE:comp.name = element.nodeName;break;}comp.position = getPos(element);}for (var i = comps.length - 1; i >= 0; i--) {comp = comps[i];xpath += '/' + comp.name.toLowerCase();if (comp.position !== null) {xpath += '[' + comp.position + ']';}}return xpath;} return absoluteXPath(arguments[0]);", 
	        
	        element);
	  }
	}
