package com.frameworksupport.util.general;


import java.util.HashMap;
import java.util.Map;
import org.testng.Assert;

public class DataProviderUtil {
  public synchronized Object[][] getRowsAndColumnsFromExcelSheet(String filePath, String sheetName) {
    Assert.assertNotNull(sheetName, "Sheet Name is Null.");
    ExcelReader xlsReader = new ExcelReader(filePath);
    int colStartRowNum = 1;
    int dataStartRowNum = colStartRowNum + 1;
    int rows = 0;
    while (!xlsReader.getCellData(sheetName, 0, dataStartRowNum + rows).equals(""))
      rows++; 
    int cols = 0;
    while (!xlsReader.getCellData(sheetName, cols, colStartRowNum).equals(""))
      cols++; 
    System.out.println("total rows -- " + rows);
    System.out.println("total cols -- " + cols);
    Object[][] data = new Object[rows][1];
    int dataRow = 0;
    Map<String, String> map = null;
    for (int rNum = dataStartRowNum; rNum < dataStartRowNum + rows; rNum++) {
      map = new HashMap<>();
      for (int cNum = 0; cNum < cols; cNum++) {
        String key = xlsReader.getCellData(sheetName, cNum, colStartRowNum);
        String value = xlsReader.getCellData(sheetName, cNum, rNum);
        map.put(key, value);
      } 
      data[dataRow][0] = map;
      dataRow++;
    } 
    return data;
  }
  
  public Object[][] getData(String filePath, String sheetName, String testName) {
	  ExcelReader xls = new ExcelReader(filePath);
    int testStartRowNum = 1;
    while (!xls.getCellData(sheetName, 0, testStartRowNum).equals(testName))
      testStartRowNum++; 
    int colStartRowNum = testStartRowNum + 1;
    int dataStartRowNum = testStartRowNum + 2;
    int rows = 0;
    while (!xls.getCellData(sheetName, 0, dataStartRowNum + rows).equals(""))
      rows++; 
    int cols = 0;
    while (!xls.getCellData(sheetName, cols, colStartRowNum).equals(""))
      cols++; 
    Object[][] data = new Object[rows][1];
    int dataRow = 0;
    HashMap<String, String> map = null;
    for (int rNum = dataStartRowNum; rNum < dataStartRowNum + rows; rNum++) {
      map = new HashMap<>();
      for (int cNum = 0; cNum < cols; cNum++) {
        String key = xls.getCellData(sheetName, cNum, colStartRowNum);
        String value = xls.getCellData(sheetName, cNum, rNum);
        map.put(key, value);
      } 
      data[dataRow][0] = map;
      dataRow++;
    } 
    return data;
  }
}

