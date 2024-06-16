package com.frameworksupport.util.general;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelTestdata {

	public static FileInputStream inputstream;
	public static FileOutputStream outputstream;
	public static XSSFWorkbook workbook;
	public static Workbook workBookFactory;
	public static String excelValue;	
	
	public static void getFileInputStream(String path) throws Exception {
		inputstream = new FileInputStream(path);
	}

	public void getFileOutputStream(String path) throws Exception {
		outputstream = new FileOutputStream(path);
	}

	public static String readExcelData(String path, String sheetName, int rowNo, int cellNo) throws Throwable {
		getFileInputStream(path);
		workbook = new XSSFWorkbook(inputstream);
		//for(int cellNo=0; cellNo<6; cellNo++) {
		excelValue = workbook.getSheet(sheetName).getRow(rowNo).getCell(cellNo).toString();
		//}
		workbook.close();
		return excelValue;
	}
	
	public void writeExcelData(String path, String sheetName, int rowNo, int cellNo, String data) throws Throwable {

		getFileInputStream(path);
		workBookFactory = WorkbookFactory.create(inputstream);
		workBookFactory.getSheet(sheetName).getRow(rowNo).createCell(cellNo).setCellValue(data);

		getFileOutputStream(path);
		workBookFactory.write(outputstream);
		workBookFactory.close();

	}

	public static int getRowCount(String path, String sheetName) throws Throwable {

		getFileInputStream(path);
		workBookFactory = WorkbookFactory.create(inputstream);
		int rowCount = workBookFactory.getSheet(sheetName).getLastRowNum();
		workBookFactory.close();
		return rowCount;

	}
	/*
	public String readPropertyData(String filePath,String key) throws Throwable {

		getFileInputStream(filePath);
		Properties prop = new Properties();
		prop.load(fileInputStream);
		String propValue = prop.getProperty(key, "Incorrect key");
		return propValue;
	}
	
	
	 * public String getPropertyData(String key) throws Throwable { return new
	 * FileLib().readPropertyData(CONFIG_PROP_PATH, key); }
	 */

	
	public void deleteOldFies() {
		File file = new File(System.getProperty("user.dir") + "\\HtmlReport\\");
		try {
			if (file != null) {
				FileUtils.deleteDirectory(file);
				//extentManager.logInfo("Old Reports are Deleted......");
			}
		} catch (Exception e) {
			//extentManager.logInfo("Old Reports are not Deleted......");
	}
	}
	
	
	
}
