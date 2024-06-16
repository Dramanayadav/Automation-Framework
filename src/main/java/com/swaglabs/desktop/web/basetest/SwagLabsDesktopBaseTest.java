package com.swaglabs.desktop.web.basetest;

import java.io.IOException;
import org.testng.annotations.BeforeMethod;
import com.frameworksupport.setup.AutomationBaseActionDriver;
import com.frameworksupport.util.general.CommonUtil;
import com.frameworksupport.util.json.JsonUtil;
import com.swaglabs.desktop.web.config.ConfigKeys;
import com.swaglabs.desktop.web.config.Resources;
import com.swaglabs.desktop.web.pages.InventoryPage;
import com.swaglabs.desktop.web.pages.SwagLabsLoginPage;


public class SwagLabsDesktopBaseTest extends AutomationBaseActionDriver {
	
	protected String locked_out_user;
	protected String problem_user;
	protected String standard_user;
	protected String performance_glitch_user;
	protected String defaultPasswordForAllUsers = "secret_sauce";
	private static boolean isSessionCleared = false;
	private static boolean isUserLoggedIn = false;
	protected String baseUrl;
	private ThreadLocal<JsonUtil> jsonUtilThread = new ThreadLocal<>();
	String loginJsonFile="\\data\\jsondata\\login.json";
	protected static SwagLabsLoginPage swaglabsloginpage;
	protected static InventoryPage inventoryPage;
	
	public void initializeCredentials() throws IOException {
		locked_out_user = CommonUtil.getConfigProperty(Resources.LOCKED_OUT_USER);
		jsonUtilThread.set(new JsonUtil());
		problem_user =jsonUtilThread.get().getJsonKeyValueFromJsonObject(loginJsonFile,Resources.USER, Resources.PROBLEM_USER); 
		standard_user=jsonUtilThread.get().getJsonKeyValueFromJsonObject(loginJsonFile,Resources.USER, Resources.STANDARD_USER);
	}
	
	@BeforeMethod(alwaysRun = true)
	public void launchUrl() throws IOException {
		commonUtil.log("SwagLabsDesktopBaseTest before method");
		baseUrl = CommonUtil.getConfigProperty(ConfigKeys.URL_SWAGLABS);
		swaglabsloginpage=new SwagLabsLoginPage();
		inventoryPage=new InventoryPage();
		if (Boolean.parseBoolean(CommonUtil.getConfigProperty(ConfigKeys.CLOSE_BROWSER_AFTER_TEST))) {
			getBaseActionDriver().get(baseUrl);
			getBaseActionDriver().deletecookies();
			if(!browserName.contains("mobile")) {
				getBaseActionDriver().manage().window().maximize();
			}
			
		} else {
			getBaseActionDriver().get(baseUrl);
			if(!browserName.contains("mobile")) {
				getBaseActionDriver().manage().window().maximize();
			}
			if (!isSessionCleared) {
				for (int i = 0; i < 9; i++) {
					getBaseActionDriver().deletecookies();
	
					if (getBaseActionDriver().isElementVisible(swaglabsloginpage.getSwagLabsLoginLogo(), 1)) {
						isSessionCleared = true;
						break;
					}
					commonUtil.log("Cookies not cleared yet...");
					commonUtil.waitInSeconds(1);
					getBaseActionDriver().get(baseUrl);
				}
			}
		}
		initializeCredentials();
	}


	public void login(String username, String password) {
		SwagLabsLoginPage swglogin=new SwagLabsLoginPage();
		if (getBaseActionDriver().isElementVisible(swglogin.getSwagLabsLoginLogo(), 1)) {
	
			swglogin.loginIntoApplication(username, password);
		}
	}

	protected void loginIfRequired(String userType)
			throws IOException {
		if (Boolean.parseBoolean(CommonUtil.getConfigProperty(ConfigKeys.CLOSE_BROWSER_AFTER_TEST))|| !isUserLoggedIn) {
			clearCookies();
			String userName = jsonUtilThread.get().getJsonKeyValueFromJsonObject(loginJsonFile, Resources.USER,userType);
			String userPassword=defaultPasswordForAllUsers;
			login(userName,userPassword);
			isUserLoggedIn = true;
		}
	}

	protected void clearCookies() {
		SwagLabsLoginPage swlogin=new SwagLabsLoginPage();
		for (int i = 0; i < 9; i++) {
			getBaseActionDriver().deletecookies();
			if (getBaseActionDriver().isElementVisible(swlogin.getSwagLabsLoginLogo(), 1)) {
				break;
			}
			commonUtil.log("Cookies not cleared yet...");
			commonUtil.waitInSeconds(1);
			getBaseActionDriver().get(baseUrl);
		}
	}
	
	
	
	@Override
	protected void cleanPageObjects() {
		// TODO Auto-generated method stub
		
	}

}
