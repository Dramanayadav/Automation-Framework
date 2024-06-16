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

public class Inventory_TestPaln extends SwagLabsDesktopBaseTest{
	@BeforeClass(alwaysRun = true)
	public void initialSetUp() throws Exception {
		initializeTest(getClassName());
	
	}
	@TestInfo(author=Author.SAMPATH,testRailId=TestRailId.C4)                         
	  @Test(dataProvider = "getInventoryDetails")
	  public void DataDrivenInventoryTests(Map<String, String> getInventoryData) throws Exception {
		 commonUtil.log("Test Name: " + getInventoryData.get("TestName"));
		 login(problem_user, defaultPasswordForAllUsers);
		 inventoryPage.verifyListItemDeatils(getInventoryData.get("InventoryItemName"), getInventoryData.get("InventoryItemDesc"), getInventoryData.get("InventoryItemPrice"), getInventoryData.get("AddToCartItemID"));
		 
	  }
	 
	 @DataProvider(parallel=false)
		public Object[][] getInventoryDetails() {
			String file = Resources.TESTDATA_EXCEL_FILE_PATH;
			return new DataProviderUtil().getRowsAndColumnsFromExcelSheet(file,Resources.INVENTORYPAGE_TESTS_SHEET);
		}

}
