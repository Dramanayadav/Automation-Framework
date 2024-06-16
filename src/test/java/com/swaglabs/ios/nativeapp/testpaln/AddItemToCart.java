package com.swaglabs.ios.nativeapp.testpaln;

import static org.testng.Assert.assertEquals;
import java.net.MalformedURLException;
import org.openqa.selenium.By;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.frameworksupport.setup.AutomationBaseActionDriver;
import com.frameworksupport.setup.TestInfo;
import com.swaglabs.desktop.web.config.Author;
import com.swaglabs.desktop.web.util.TestRailId;

import io.appium.java_client.AppiumBy;

public class AddItemToCart extends AutomationBaseActionDriver{

	
	  @BeforeClass(alwaysRun = true)
		public void initialSetUp() throws Exception {
			initializeTest(getClassName());
		}

	  By sauceLabsBackPack=AppiumBy.accessibilityId("Sauce Labs Backpack");
	  By counterPlusButton=AppiumBy.accessibilityId("counter plus button");
	  By addToCart=AppiumBy.accessibilityId("Add To Cart button");
	  By openCart=AppiumBy.accessibilityId("tab bar option cart");
	  
	  @TestInfo(author=Author.SAMPATH,testRailId=TestRailId.C4)  
	   @Test
	    public void verifyAddItemToCart() throws MalformedURLException {
		   
		  getBaseActionDriver().waitForElementToBeVisible(sauceLabsBackPack);
		  String ActualItemName=getBaseActionDriver().getText(sauceLabsBackPack);
		  String ExpectedItemName="Sauce Labs Backpack";
		  System.out.println("actual item name is :"+ActualItemName);
		  System.out.println("expected item name is :"+ExpectedItemName);
		  assertEquals(ActualItemName, ExpectedItemName);
		  getMobileActionDriver().click(sauceLabsBackPack);
		  getMobileActionDriver().click(counterPlusButton);
		  getMobileActionDriver().click(addToCart);
		  getMobileActionDriver().click(openCart);
	   }
	  
	  
	@Override
	protected void cleanPageObjects() {
		// TODO Auto-generated method stub
		
	}

}
