package com.UI.validations.testplan;


import com.applitools.eyes.BatchInfo;
import com.applitools.eyes.EyesRunner;
import com.applitools.eyes.RectangleSize;
import com.applitools.eyes.TestResultsSummary;
import com.applitools.eyes.config.Configuration;
import com.applitools.eyes.selenium.ClassicRunner;
import com.applitools.eyes.selenium.Eyes;
import com.frameworksupport.setup.AutomationBaseActionDriver;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


public class UIValidationsTestDemo extends AutomationBaseActionDriver{
	Eyes eys;
	static  BatchInfo myBatch;
	static Configuration suiteConfig;
	static EyesRunner testRunner;
	
	@BeforeClass(alwaysRun = true)
	public void initialSetUp() throws Exception {
		initializeTest(getClassName());
		myBatch=new BatchInfo(getClassName());
		suiteConfig=new Configuration();
		suiteConfig.setBatch(myBatch);
		suiteConfig.setApiKey("DAxetx103izgYSJsxuJL100c5UIXzIl1097r0KqIQ4dnAACK8110");
		testRunner=new ClassicRunner();
	
	}
	@BeforeMethod
	public void eyesSetup() {
	    eys=new Eyes(testRunner);
	    eys.setConfiguration(suiteConfig);
	}
    @Test
    public void HelloworldUItest() {
        
        eys.open(AutomationBaseActionDriver.getWebActionDriver().getWebDriver(), "hello world","hello UI Check test", new RectangleSize(800,600));
        getWebActionDriver().get("https://applitools.com/helloworld/");//https://applitools.com/helloworld/
        eys.checkWindow("Hello!");
      
    }
    @Test
    public void HelloworldUItest1() {
        eys.open(AutomationBaseActionDriver.getWebActionDriver().getWebDriver(), "hello world","Gmail UI login  page test", new RectangleSize(800,600));
      getWebActionDriver().get("https://accounts.google.com/signin/v2/usernamerecovery?continue=https%3A%2F%2Fmail.google.com%2Fmail%2Fu%2F0%2F&emr=1&followup=https%3A%2F%2Fmail.google.com%2Fmail%2Fu%2F0%2F&osid=1&passive=1209600&service=mail&ifkv=AVQVeyxd6LzToP_GoIf_yL0MRh5vcPzy01Jod80QWCCmuvfj4kG0sSN00CYaVdsJfrDvOpKo5OCx&theme=glif&flowName=GlifWebSignIn&flowEntry=ServiceLogin");
      eys.checkWindow("Gmail forgot web page");
      
    }
    @Test
    public void HelloworldUItest2() {
        eys.open(AutomationBaseActionDriver.getWebActionDriver().getWebDriver(), "hello world","hello world diff2 UI test", new RectangleSize(800,600));
      getWebActionDriver().get("https://applitools.com/helloworld/?diff2");
      eys.checkWindow("Hello!");
      
    }

@AfterMethod
    public void endUiTest() {
    	eys.close();
    }


@AfterClass
public void endTestClass() {
	TestResultsSummary trs=testRunner.getAllTestResults();
	System.out.println(trs);
}

	@Override
	protected void cleanPageObjects() {
		// TODO Auto-generated method stub
		
	}

  
}