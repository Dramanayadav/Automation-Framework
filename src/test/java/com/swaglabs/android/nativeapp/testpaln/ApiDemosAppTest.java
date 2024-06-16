package com.swaglabs.android.nativeapp.testpaln;

import org.openqa.selenium.By;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.frameworksupport.setup.AutomationBaseActionDriver;

import io.appium.java_client.AppiumBy;

public class ApiDemosAppTest extends AutomationBaseActionDriver{

	
	@BeforeClass(alwaysRun = true)
	public void initialSetUp() throws Exception {
		initializeTest(getClassName());
	}
	
	By views=AppiumBy.accessibilityId("Views");
	By listele=AppiumBy.id("android:id/list");
	By Animation=AppiumBy.accessibilityId("Animation");
	By Picker=AppiumBy.accessibilityId("Picker");
	By Grid=AppiumBy.accessibilityId("Grid");
	By Tabs=AppiumBy.accessibilityId("Tabs");
	By Gallery= AppiumBy.accessibilityId("Gallery");
	By photo1=AppiumBy.accessibilityId("1. Photos");
	By imagephoto1=AppiumBy.xpath("//*[@resource-id=\"io.appium.android.apis:id/gallery\"]/android.widget.ImageView[1]");
	
	@Test
	public void scrollTest()
	{
		
		getMobileActionDriver().waitForElementToBeVisible(views);
		getMobileActionDriver().click(views);
//		getMobileActionDriver().scrollToElementToBeVisibleByCordnates(Picker, "down");
		getMobileActionDriver().scrollToElement(Animation,"down");
		getMobileActionDriver().scrollToElement(listele,"down");
	}
	
	
	@Test
	public void swipeTest()
	{
		
		getMobileActionDriver().waitForElementToBeVisible(views);
		getMobileActionDriver().click(views);
//		getMobileActionDriver().swipeElement(listele, "up");
		getBaseActionDriver().click(Gallery);
		getBaseActionDriver().click(photo1);
		getMobileActionDriver().swipeElement(imagephoto1, "left");

	}
	
	
	
	
	@Override
	protected void cleanPageObjects() {
		// TODO Auto-generated method stub
		
	}

}
