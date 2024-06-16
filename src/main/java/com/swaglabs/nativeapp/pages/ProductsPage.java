package com.swaglabs.nativeapp.pages;

import org.openqa.selenium.By;
import org.testng.Assert;
import com.frameworksupport.setup.AutomationBaseActionDriver;
import com.frameworksupport.setup.BaseActionDriver;
import com.frameworksupport.util.general.CommonUtil;

import io.appium.java_client.AppiumBy;

public class ProductsPage {
	
	BaseActionDriver actionDriver;
	CommonUtil commonUtil;
	public ProductsPage() {
		
		actionDriver = AutomationBaseActionDriver.getBaseActionDriver();
		commonUtil = AutomationBaseActionDriver.getCommonUtil();
		
	}
By productName;
By productPrice;
By productAddToCartButton;

public void verifyProductDeatils(String expectedProductName ,String expectedProductPrice)
{
	productName=AppiumBy.xpath("//android.widget.TextView[@text='"+expectedProductName+"']");
	productPrice=AppiumBy.xpath("//android.widget.TextView[@text='"+expectedProductName+"']/following-sibling::android.widget.TextView[@content-desc='test-Price']");
//	AutomationBaseActionDriver.getMobileActionDriver().scrollByType(actionDriver.getElement(productPrice), "down");
	actionDriver.waitForElementToBeVisible(productName, 20);
	String actualProductName=actionDriver.getText(productName);
	String actualProductPrice=actionDriver.getText(productPrice);
	commonUtil.log("actualProductName is   :"+actualProductName);
	commonUtil.log("expectedProductName is   :"+expectedProductName);
	commonUtil.log("actualProductPrice is   :"+actualProductPrice);
	commonUtil.log("expectedProductPrice is   :"+expectedProductPrice);
	Assert.assertEquals(actualProductName, expectedProductName);
	Assert.assertEquals(actualProductPrice, expectedProductPrice);
}
public void addProductToCart(String expectedProductName) {
	productName=AppiumBy.xpath("//android.widget.TextView[@text='"+expectedProductName+"']");
	productAddToCartButton=AppiumBy.xpath("//android.widget.TextView[@text='"+expectedProductName+"']/following-sibling::android.view.ViewGroup[@content-desc='test-ADD TO CART']");
	actionDriver.waitForElementToBeVisible(productName, 20);
	actionDriver.scrollDownTo(productName);
	actionDriver.click(productAddToCartButton);
}
}
