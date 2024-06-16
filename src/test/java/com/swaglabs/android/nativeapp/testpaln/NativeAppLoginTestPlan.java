package com.swaglabs.android.nativeapp.testpaln;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.frameworksupport.setup.TestInfo;
import com.swaglabs.desktop.web.config.Author;
import com.swaglabs.desktop.web.util.TestRailId;
import com.swaglabs.nativeapp.basetest.SwagLabsNativeAppBaseTest;


public class NativeAppLoginTestPlan extends SwagLabsNativeAppBaseTest{
	
	@BeforeClass(alwaysRun = true)
	public void initialSetUp() throws Exception {
		initializeTest(getClassName());
	}
	
	@TestInfo(author=Author.SAMPATH,testRailId=TestRailId.C3)   
	@Test
	public void verify_standardUserLoginTest() {
		login(standard_user, defaultPasswordForAllUsers);
		nativeAppLoginPage.verifyLoginSuccessfull();
		
	}
	@TestInfo(author=Author.SAMPATH,testRailId=TestRailId.C2)   
	@Test
	public void verify_problemUserLoginTest() {
		login(problem_user, defaultPasswordForAllUsers);
		nativeAppLoginPage.verifyLoginSuccessfull();
		
	}
	@TestInfo(author=Author.SAMPATH,testRailId=TestRailId.C1)   
	@Test
	public void verify_lockedOutUserLoginTest() {
		login(locked_out_user, defaultPasswordForAllUsers);
		nativeAppLoginPage.verifyLockedUserLogin();
		
	}

}
