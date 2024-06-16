package com.swaglabs.nativeapp.pages;

import static org.testng.Assert.assertEquals;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.frameworksupport.setup.AutomationBaseActionDriver;
import com.frameworksupport.setup.BaseActionDriver;
import com.frameworksupport.util.general.CommonUtil;

import io.appium.java_client.AppiumBy;

public class NativeappLoginPage {
	
	BaseActionDriver actionDriver;
	CommonUtil commonUtil;
	public NativeappLoginPage() {
		
		actionDriver = AutomationBaseActionDriver.getBaseActionDriver();
		commonUtil = AutomationBaseActionDriver.getCommonUtil();
		
	}
	By swagLabsLogo=AppiumBy.xpath("//android.widget.ScrollView[@content-desc=\"test-Login\"]/android.view.ViewGroup/android.widget.ImageView[1]");
	By loginUserName=AppiumBy.accessibilityId("test-Username");
	By loginUserPassword=AppiumBy.accessibilityId("test-Password");
	By loginButton=AppiumBy.accessibilityId("test-LOGIN");
	By products=AppiumBy.xpath("//android.view.ViewGroup[@content-desc=\"test-Cart drop zone\"]/android.view.ViewGroup/android.widget.TextView");
	By lockedOutLoginError=AppiumBy.xpath("//android.view.ViewGroup[@content-desc=\"test-Error message\"]/android.widget.TextView");
	public void loginToNativeApp(String username ,String password) {
		actionDriver.waitForElementToBeVisible(loginUserName);
		actionDriver.sendKeys(loginUserName, username);
		actionDriver.sendKeys(loginUserPassword, password);
		actionDriver.click(loginButton);
		
	}
	 
	public void verifyLoginSuccessfull() {
		actionDriver.waitForElementToBeVisible(products);
		String actualProductText=actionDriver.getText(products);
		String expectedProductText="PRODUCTS";
		assertEquals(actualProductText, expectedProductText);
		commonUtil.log("actualProductText   is   : "+actualProductText);
		commonUtil.log("expectedProductText   is   : "+expectedProductText);
		
		
	}
	
	public WebElement getSwagLogoEle() {
		actionDriver.waitForElementToBeVisible(swagLabsLogo);
		return actionDriver.getElement(swagLabsLogo);
	}
	
	public void verifyLockedUserLogin() {
		actionDriver.waitForElementToBeVisible(lockedOutLoginError);
		String actualErrorMsg=actionDriver.getText(lockedOutLoginError);
		String expectedErrorMsg="Sorry, this user has been locked ou.";
		Assert.assertEquals(actualErrorMsg, expectedErrorMsg);
		commonUtil.log("actualErrorMsg  :"+actualErrorMsg);
		commonUtil.log("expectedErrorMsg : "+ expectedErrorMsg);
	}

}
