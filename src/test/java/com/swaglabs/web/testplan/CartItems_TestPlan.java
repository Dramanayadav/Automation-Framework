package com.swaglabs.web.testplan;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.frameworksupport.setup.TestInfo;
import com.swaglabs.desktop.web.basetest.SwagLabsDesktopBaseTest;
import com.swaglabs.desktop.web.config.Author;
import com.swaglabs.desktop.web.util.TestRailId;

public class CartItems_TestPlan extends SwagLabsDesktopBaseTest{
	
	@BeforeClass(alwaysRun = true)
	public void initialSetUp() throws Exception {
		initializeTest(getClassName());
	
	}
	
		 
	 @TestInfo(author=Author.NANDITHA,testRailId=TestRailId.C1)                         
	  @Test(priority=0)
	  public void getListOfItems_Test() throws Exception {
		  
	   login(standard_user, defaultPasswordForAllUsers);
	   swaglabsloginpage.getAllItems() ;
	   
	  }

	 @TestInfo(author=Author.RAMYA,testRailId=TestRailId.C2)                         
	  @Test(priority=1)
	  public void ItemTitleVerfication_Test() throws Exception {
		  
	   login(standard_user, defaultPasswordForAllUsers);
	   swaglabsloginpage. getItemTitle();
	   
	  }
	 
	 @TestInfo(author=Author.SRINIVAS,testRailId=TestRailId.C3)                         
	  @Test(priority=2)
	  public void clickAddTocart_Test() throws Exception {
		  
	   login(standard_user, defaultPasswordForAllUsers);
	   swaglabsloginpage.AddTocart();
	   
	  }
	 @TestInfo(author=Author.ASHOK,testRailId=TestRailId.C4)                         
	  @Test(priority=3)
	  public void showCartItems_Test() throws Exception {
		  
	   login(standard_user, defaultPasswordForAllUsers);
	   swaglabsloginpage.AddTocart();
	   swaglabsloginpage.showCart();
	  }
	 @TestInfo(author=Author.RAMANA,testRailId=TestRailId.C5)                         
	  @Test(priority=4)
	  public void continueShopping_Test() throws Exception {
		  
	   login(standard_user, defaultPasswordForAllUsers);
	   swaglabsloginpage.AddTocart();
	   swaglabsloginpage.showCart();
	   swaglabsloginpage.clickContinueShop();
	   swaglabsloginpage.verifySwagLabsLoggedinLogo();
	  }
	 
	 @TestInfo(author=Author.SAMPATH,testRailId=TestRailId.C6)                         
	  @Test(priority=5)
	  public void checkoutCart_Test() throws Exception {
		  
	   login(standard_user, defaultPasswordForAllUsers);
	   swaglabsloginpage.AddTocart();
	   swaglabsloginpage.showCart();
	   swaglabsloginpage.clickCheckoutOnCart();
	  }
}
