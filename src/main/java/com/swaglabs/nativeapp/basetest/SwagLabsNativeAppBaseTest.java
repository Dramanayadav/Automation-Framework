package com.swaglabs.nativeapp.basetest;

import java.io.IOException;
import org.testng.annotations.BeforeMethod;
import com.frameworksupport.setup.AutomationBaseActionDriver;
import com.frameworksupport.util.general.CommonUtil;
import com.frameworksupport.util.json.JsonUtil;
import com.swaglabs.desktop.web.config.ConfigKeys;
import com.swaglabs.desktop.web.config.Resources;
import com.swaglabs.nativeapp.pages.NativeappLoginPage;
import com.swaglabs.nativeapp.pages.ProductsPage;


public class SwagLabsNativeAppBaseTest extends AutomationBaseActionDriver {
	
	protected static String locked_out_user;
	protected static String problem_user;
	protected static String standard_user;
	protected static String performance_glitch_user;
	protected static String defaultPasswordForAllUsers = "secret_sauce";
	private static boolean isSessionCleared = false;
	private static boolean isUserLoggedIn = false;
	static String loginJsonFile="\\data\\jsondata\\login.json";
	protected static NativeappLoginPage nativeAppLoginPage;
	protected static ProductsPage productsPage;
	static JsonUtil jsonUtil=new JsonUtil();

	public static void initializeCredentials() throws IOException {
		locked_out_user = CommonUtil.getConfigProperty(Resources.LOCKED_OUT_USER);
		problem_user =jsonUtil.getJsonKeyValueFromJsonObject(loginJsonFile,Resources.USER, Resources.PROBLEM_USER);
		standard_user=jsonUtil.getJsonKeyValueFromJsonObject(loginJsonFile,Resources.USER, Resources.STANDARD_USER);
		nativeAppLoginPage=new NativeappLoginPage();
		productsPage=new ProductsPage();
	}
	
   @BeforeMethod
	protected void loginApp() throws IOException {
		commonUtil.log("SwagLabsDesktopBaseTest before method");
		initializeCredentials();
	}


	public void login(String username, String password) {
		if (nativeAppLoginPage.getSwagLogoEle().isDisplayed()) {
			
			nativeAppLoginPage.loginToNativeApp(username, password);
		}
	}

	protected void loginIfRequired(String userType)throws IOException {
		if (Boolean.parseBoolean(CommonUtil.getConfigProperty(ConfigKeys.CLOSE_BROWSER_AFTER_TEST))|| !isUserLoggedIn) {
			clearCookies();
			String userName = jsonUtil.getJsonKeyValueFromJsonObject(loginJsonFile, Resources.USER,userType);
			String userPassword=defaultPasswordForAllUsers;
			login(userName,userPassword);
			isUserLoggedIn = true;
		}
	}

	protected void clearCookies() {
		for (int i = 0; i < 9; i++) {
			getBaseActionDriver().deletecookies();
			if (nativeAppLoginPage.getSwagLogoEle().isDisplayed()) {
				break;
			}
			commonUtil.log("Cookies not cleared yet...");
			commonUtil.waitInSeconds(1);
		}
	}
	
	
	
	@Override
	protected void cleanPageObjects() {
		// TODO Auto-generated method stub
		
	}

}
