package com.swaglabs.desktop.web.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import com.frameworksupport.setup.BaseActionDriver;
import com.frameworksupport.setup.AutomationBaseActionDriver;
import com.frameworksupport.util.general.CommonUtil;
import com.frameworksupport.util.general.ExcelReader;
import com.swaglabs.desktop.web.config.Resources;
import com.swaglabs.desktop.web.constants.ElementName;


public class SwagLabsLoginPage {
	BaseActionDriver actionDriver;
	CommonUtil commonUtil;
	ExcelReader testdata  = new ExcelReader(Resources.TESTDATA_EXCEL_FILE_PATH);;

	public SwagLabsLoginPage() {
		actionDriver = AutomationBaseActionDriver.getBaseActionDriver();
		commonUtil = AutomationBaseActionDriver.getCommonUtil();
	}
	
	private By swagLabLoginLogo=By.xpath("//div[@class='login_logo']");
	private By usernameTextbox = By.id("user-name");
	private By passwordTextbox = By.xpath("//input[@id='password']");
	private By loginButton = By.xpath("//input[@id='login-button']");
	private By appLogo=testdata.getXpathlocator(Resources.PAGELOCATORS_SHEET, Resources.LOCATORVALUE,ElementName.APP_LOGO.toString());
	private By errorButton=testdata.getXpathlocator(Resources.PAGELOCATORS_SHEET, Resources.LOCATORVALUE,ElementName.ERROR_BUTTON.toString());
	private By itemtitle= By.xpath("//div[text()='Sauce Labs Backpack']");
	private By allItemsList= By.xpath("//div[@class='inventory_list']/div");
	private By addcartButton=By.xpath("//button[@id='add-to-cart-sauce-labs-backpack']");
	private By cartLink= By.xpath("//a[@class='shopping_cart_link']");
	private By cartitemslist= By.xpath("//div[@class='cart_item']");
	private By continueShopping= By.xpath("//button[@id='continue-shopping']");
	private By checkoutButton= By.xpath("//button[@id='checkout']");
	private By checkoutpageTitle= By.xpath("//span[@class='title']");
	private By addcartBtns= By.xpath("//*[text()=\"Add to cart\"]");
	
	
	public static final String PAGE_TITLE = "Swag Labs";
	public static List<WebElement>allItems;
	
	public void loginIntoApplication(String username, String password) {

	    actionDriver.verifyPageTitle(PAGE_TITLE);
		actionDriver.click(usernameTextbox);
		commonUtil.waitInSeconds(2);
		actionDriver.enterTestData(usernameTextbox, username);
		actionDriver.type(passwordTextbox, password);
		actionDriver.click(loginButton);
		commonUtil.waitInSeconds(3);
	}
	
	
	public By getSwagLabsLoginLogo() {		
			return swagLabLoginLogo;
	}
	
	public void verifySwagLabsLoggedinLogo()
	{
		actionDriver.waitForElementToBeVisible(appLogo);
		String actualLoggedInLogoText=actionDriver.getText(appLogo);
		String expectedLoggedInLogoText=testdata.getValueByColumnNameAndRowName(Resources.STATIC_EXPECTEDTEXTS_SHEET, Resources.EXPECTEDTEXT, ElementName.SWAGLABSLOGO.toString());
		Assert.assertEquals(actualLoggedInLogoText, expectedLoggedInLogoText);
		commonUtil.log("verifed SwagLabs LoggedinLogo  Actual Text is  : " + actualLoggedInLogoText  + " And Expected Text is : " + expectedLoggedInLogoText);
	}
	
	public void verifyLockedOutUserLogin()
	{
		actionDriver.waitForElementToBeVisible(errorButton,200);
		String actualErrorText=actionDriver.getText(errorButton);
		String expectedErrorText=testdata.getValueByColumnNameAndRowName(Resources.STATIC_EXPECTEDTEXTS_SHEET, Resources.EXPECTEDTEXT, ElementName.ERROR_BUTTON.toString());
		Assert.assertEquals(actualErrorText,expectedErrorText);
		commonUtil.log("verifed SwagLabs LockedOutUserLogin and error  Actual Text is  : " + actualErrorText  + " And  Expected Text is : " + expectedErrorText);
	
	}
	
	//get list of items presented on site.
	public void getAllItems() {
		actionDriver.waitForElementToBeVisible(allItemsList, 100);
		allItems=actionDriver.getElements(allItemsList, 50);
		int size= allItems.size();
		System.out.println("number of items presented on site are: " +size);
		for(WebElement ele:allItems) {
			commonUtil.log("list of items presented on site are: " +ele.getText());
			}
	}
	//get title of item
	public void getItemTitle() {
		actionDriver.waitForElementToBeVisible(allItemsList, 100);
	String itemTitle=	actionDriver.getElement(itemtitle).getText();
		commonUtil.log("Item title is: " +itemTitle);
	}
	//item will add to cart
	public void AddTocart() {
		actionDriver.waitForElementToBeVisible(allItemsList, 100);
		actionDriver.click(addcartButton);
		commonUtil.log("Item succesfully added to cart");
	}
	
	//show cart items
	public void showCart() {
		actionDriver.waitForElementToBeVisible(cartLink, 100);
		actionDriver.click(cartLink);
		List<WebElement> cartItems= actionDriver.getElements(cartitemslist, 50);
		int size_items= cartItems.size();
		commonUtil.log("number of items added to cart are: " +size_items);
		for(WebElement ele: cartItems) {
			commonUtil.log("list of items presented on site are: " +ele.getText());			
		}
	}
	
	public void clickContinueShop() {
		actionDriver.waitForElementToBeVisible(continueShopping, 100);
		actionDriver.click(continueShopping);
	}
	public void clickCheckoutOnCart() {
		actionDriver.waitForElementToBeVisible(checkoutButton, 100);
		actionDriver.click(checkoutButton);
		String exptitle= actionDriver.getElement(checkoutpageTitle).getText();
		Assert.assertEquals("Checkout: Your Information",exptitle );
	}
	
}
