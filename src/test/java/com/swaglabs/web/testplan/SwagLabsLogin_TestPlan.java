package com.swaglabs.web.testplan;

import java.util.Map;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.frameworksupport.setup.TestInfo;
import com.frameworksupport.util.general.DataProviderUtil;
import com.swaglabs.desktop.web.basetest.SwagLabsDesktopBaseTest;
import com.swaglabs.desktop.web.config.Author;
import com.swaglabs.desktop.web.config.Resources;
import com.swaglabs.desktop.web.util.TestRailId;


public class SwagLabsLogin_TestPlan  extends SwagLabsDesktopBaseTest{
	
	@BeforeClass(alwaysRun = true)
	public void initialSetUp() throws Exception {
		initializeTest(getClassName());
	
	}

	@TestInfo(author=Author.SAMPATH,testRailId=TestRailId.C3)                         
	  @Test(dataProvider = "getLoginData")
	  public void DataDrivenloginTests(Map<String, String> getlogindata) throws Exception {
		 commonUtil.log("Test Name: " + getlogindata.get("TestName"));
		 login(getlogindata.get("UserName"), getlogindata.get("Password"));
		 swaglabsloginpage.verifySwagLabsLoggedinLogo();
	  }
	 
	 @DataProvider(parallel=true)
		public Object[][] getLoginData() {
			String file = Resources.TESTDATA_EXCEL_FILE_PATH;
			return new DataProviderUtil().getRowsAndColumnsFromExcelSheet(file,Resources.SWAGLOGIN_TESTS_SHEET);
		}

	 
	 
	  @TestInfo(author=Author.RAMYA,testRailId=TestRailId.C1)                         
	  @Test()
	  public void loginTest_lockeduesr() throws Exception {
		  
	   login(locked_out_user, defaultPasswordForAllUsers);
	  }
	  
	  
	 
	 @TestInfo(author=Author.SRINIVAS,testRailId=TestRailId.C2)
	  @Test()
	  public void loginTest_problemuser() throws Exception {
		 
	   login(problem_user, defaultPasswordForAllUsers);
	   swaglabsloginpage.verifySwagLabsLoggedinLogo();
	  }
	 
	   

} 
