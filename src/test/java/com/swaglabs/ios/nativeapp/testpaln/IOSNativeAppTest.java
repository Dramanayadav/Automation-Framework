package com.swaglabs.ios.nativeapp.testpaln;

import com.frameworksupport.setup.AutomationBaseActionDriver;
import org.openqa.selenium.By;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.net.MalformedURLException;



public class IOSNativeAppTest extends AutomationBaseActionDriver{

    By productsScreenLocator = By.id("products screen");
    By sortButtonLocator = By.id("sort button");
    By sortModalLocator = By.id("active option");

    
    @BeforeClass(alwaysRun = true)
	public void initialSetUp() throws Exception {
		initializeTest(getClassName());
	}

    @Test
    public void verifyInProductsPage() throws MalformedURLException {

       
        getMobileActionDriver().waitForElementToBeVisible(productsScreenLocator, 50);
        getMobileActionDriver().click(sortButtonLocator);
        getBaseActionDriver().waitForElementToBeVisible(sortModalLocator,50);

    }

	@Override
	protected void cleanPageObjects() {
		// TODO Auto-generated method stub
		
	}

}
