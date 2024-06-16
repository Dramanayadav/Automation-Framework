package com.swaglabs.desktop.web.pages;

import org.openqa.selenium.By;
import org.testng.Assert;
import com.frameworksupport.setup.AutomationBaseActionDriver;
import com.frameworksupport.setup.BaseActionDriver;
import com.frameworksupport.util.general.CommonUtil;
import com.frameworksupport.util.general.ExcelReader;
import com.swaglabs.desktop.web.config.Resources;


public class InventoryPage {
	BaseActionDriver actionDriver;
	CommonUtil commonUtil;
	ExcelReader testdata  = new ExcelReader(Resources.TESTDATA_EXCEL_FILE_PATH);;

	public InventoryPage() {
		actionDriver = AutomationBaseActionDriver.getBaseActionDriver();
		commonUtil = AutomationBaseActionDriver.getCommonUtil();
	}
	
    
	private By inventory_item_name;
	private By inventory_item_desc;
	private By inventory_item_price;
	private By inventory_item_addtocart;
	
	public void verifyListItemDeatils(String expectedInventoryItemName,String expectedInventoryItemDesc,String expetedInventoryItemPrice,String addItemToCartId )
	{
		inventory_item_name=By.xpath("//button[@id='"+addItemToCartId+"']/preceding::div[text()='"+expectedInventoryItemName+"']");
		inventory_item_desc=By.xpath("//button[@id='"+addItemToCartId+"']/preceding::div[text()='"+expectedInventoryItemName+"']/following::div[@class='inventory_item_desc']");
		inventory_item_price=By.xpath("//button[@id='"+addItemToCartId+"']/preceding::div[text()='"+expectedInventoryItemName+"']/following::div[@class='inventory_item_price']");
	 
//		AutomationBaseActionDriver.getMobileActionDriver().scrollByType(actionDriver.getElement(inventory_item_price), "down");
		actionDriver.waitForElementToBeVisible(inventory_item_name, 20);
		String actualInventoryItemName=actionDriver.getText(inventory_item_name);
		commonUtil.log("Actual Inventory ItemName is :" +actualInventoryItemName);
		commonUtil.log("Expected Inventory ItemName is :" +expectedInventoryItemName);
		Assert.assertEquals(actualInventoryItemName, expectedInventoryItemName);
		String actualInventoryItemDesc=actionDriver.getText(inventory_item_desc);
		commonUtil.log("Actual Inventory Item Description is :" +actualInventoryItemDesc);
		commonUtil.log("Expected Inventory Item Description is :" +expectedInventoryItemDesc);
		Assert.assertEquals(actualInventoryItemDesc, expectedInventoryItemDesc);
		String actualInventoryItemPrice=actionDriver.getText(inventory_item_price);
		commonUtil.log("Actual Inventory Item Price is :" +actualInventoryItemPrice);
		commonUtil.log("Expected Inventory Item Price is :" +expetedInventoryItemPrice);
		Assert.assertEquals(actualInventoryItemPrice, expetedInventoryItemPrice);
		
	}
	
	public void addItemToCart(String expectedInventoryItemName,String addItemToCartId) {
		inventory_item_name=By.xpath("//button[@id='"+addItemToCartId+"']/preceding::div[text()='"+expectedInventoryItemName+"']");
		inventory_item_addtocart=By.xpath("//button[@id='"+addItemToCartId+"']");
		actionDriver.waitForElementToBeVisible(inventory_item_name,20);
		commonUtil.log("Adding the  :" +expectedInventoryItemName+" to cart ....");
		actionDriver.click(inventory_item_addtocart);
	}
	

}
