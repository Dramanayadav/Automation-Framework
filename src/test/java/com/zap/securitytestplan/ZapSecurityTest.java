package com.zap.securitytestplan;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.frameworksupport.setup.AutomationBaseActionDriver;
import com.frameworksupport.setup.TestInfo;
import com.frameworksupport.util.general.CommonUtil;
import com.swaglabs.desktop.web.config.Author;

public class ZapSecurityTest extends AutomationBaseActionDriver
{
	@BeforeClass(alwaysRun = true)
	public void initialSetup()
	{
    	initializeWebTest("ZAP Security Testing...");
	}
	@TestInfo(author=Author.SRINIVAS)
	@Test
	public void test() throws Exception
	{
		getBaseActionDriver().get(CommonUtil.getConfigProperty("url_swaglabs"));
	}
	@TestInfo(author=Author.SRINIVAS)
	@Test
	public void test2()
	{
		getBaseActionDriver().get(CommonUtil.getConfigProperty("qafoxurl"));
	}
	@TestInfo(author=Author.SRINIVAS)
	@Test
	public void test3()
	{
		getBaseActionDriver().get(CommonUtil.getConfigProperty("openhrm_url"));
	}
	
	@Override
	protected void cleanPageObjects() {
		// TODO Auto-generated method stub
		
	}
}
