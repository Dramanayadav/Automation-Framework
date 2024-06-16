package com.frameworksupport.util.general;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import org.apache.poi.common.usermodel.HyperlinkType;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Hyperlink;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFCreationHelper;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFHyperlink;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;

public class ExcelReader {
  public String path;
  
  public InputStream fis = null;
  
  public FileOutputStream fileOut = null;
  
  private XSSFWorkbook workbook = null;
  
  private XSSFSheet sheet = null;
  
  private XSSFRow row = null;
  
  private XSSFCell cell = null;
  
  HSSFWorkbook workbook_xls = null;
  
  HSSFSheet sheet_xls = null;
  
  HSSFRow row_xls = null;
  
  HSSFCell cell_xls = null;
  
  public ExcelReader(String xlsxpath) {
    this.path = xlsxpath;
    try {
      this.fis = new FileInputStream(path);
      this.workbook = new XSSFWorkbook(this.fis);
      this.sheet = this.workbook.getSheetAt(0);
      this.fis.close();
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  private ExcelReader(String hssfxlspath, String temp) {
    this.path = path;
    try {
      this.fis = new FileInputStream(path);
      this.workbook_xls = new HSSFWorkbook(this.fis);
      this.sheet_xls = this.workbook_xls.getSheetAt(0);
      this.fis.close();
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  public int getRowCount(String sheetName) {
    int index = this.workbook.getSheetIndex(sheetName);
    if (index == -1)
      return 0; 
    this.sheet = this.workbook.getSheetAt(index);
    int number = this.sheet.getLastRowNum() + 1;
    return number;
  }
  
  public String getCellData(String sheetName, String colName, int rowNum) {
    try {
      if (rowNum <= 0)
        return ""; 
      int index = this.workbook.getSheetIndex(sheetName);
      int col_Num = -1;
      if (index == -1)
        return ""; 
      this.sheet = this.workbook.getSheetAt(index);
      this.row = this.sheet.getRow(0);
      for (int i = 0; i < this.row.getLastCellNum(); i++) {
        if (this.row.getCell(i).getStringCellValue().trim().equals(colName.trim()))
          col_Num = i; 
      } 
      if (col_Num == -1)
        return ""; 
      this.sheet = this.workbook.getSheetAt(index);
      this.row = this.sheet.getRow(rowNum - 1);
      if (this.row == null)
        return ""; 
      this.cell = this.row.getCell(col_Num);
      if (this.cell == null)
        return ""; 
      CellType celltype=this.cell.getCellTypeEnum();
      if (this.cell.getCellType() == 1)
        return this.cell.getStringCellValue(); 
      if (this.cell.getCellType() == 0 || this.cell.getCellType() == 2) {
        String cellText = String.valueOf(this.cell.getNumericCellValue());
        if (DateUtil.isCellDateFormatted((Cell)this.cell)) {
          double d = this.cell.getNumericCellValue();
          Calendar cal = Calendar.getInstance();
          cal.setTime(DateUtil.getJavaDate(d));
          cellText = String.valueOf(cal.get(1)).substring(2);
          cellText = String.valueOf(cal.get(5)) + "/" + cal.get(2) + '\001' + "/" + cellText;
        } 
        return cellText;
      } 
      if (this.cell.getCellType() == 3)
        return ""; 
      return String.valueOf(this.cell.getBooleanCellValue());
    } catch (Exception e) {
      e.printStackTrace();
      return "row " + rowNum + " or column " + colName + " does not exist in xls";
    } 
  }
  
  public String getCellData(String sheetName, int colNum, int rowNum) {
    try {
      if (rowNum <= 0)
        return ""; 
      int index = this.workbook.getSheetIndex(sheetName);
      if (index == -1)
        return ""; 
      this.sheet = this.workbook.getSheetAt(index);
      this.row = this.sheet.getRow(rowNum - 1);
      if (this.row == null)
        return ""; 
      this.cell = this.row.getCell(colNum);
      if (this.cell == null)
        return ""; 
      if (this.cell.getCellType() == 1)
        return this.cell.getStringCellValue(); 
      if (this.cell.getCellType() == 0 || this.cell.getCellType() == 2) {
        String cellText = String.valueOf(this.cell.getNumericCellValue());
        if (DateUtil.isCellDateFormatted((Cell)this.cell)) {
          double d = this.cell.getNumericCellValue();
          Calendar cal = Calendar.getInstance();
          cal.setTime(DateUtil.getJavaDate(d));
          cellText = String.valueOf(cal.get(1)).substring(2);
          cellText = String.valueOf(cal.get(2) + 1) + "/" + cal.get(5) + "/" + cellText;
        } 
        return cellText;
      } 
      if (this.cell.getCellType() == 3)
        return ""; 
      return String.valueOf(this.cell.getBooleanCellValue());
    } catch (Exception e) {
      e.printStackTrace();
      return "row " + rowNum + " or column " + colNum + " does not exist  in xls";
    } 
  }
  
  private String getCellData_xls(String sheetName, int colNum, int rowNum) throws IOException {
    try {
      if (rowNum <= 0)
        return ""; 
      int index = this.workbook_xls.getSheetIndex(sheetName);
      if (index == -1)
        return ""; 
      this.sheet_xls = this.workbook_xls.getSheetAt(index);
      this.row_xls = this.sheet_xls.getRow(rowNum - 1);
      if (this.row_xls == null)
        return ""; 
      this.cell_xls = this.row_xls.getCell(colNum);
      if (this.cell_xls == null)
        return ""; 
      if (this.cell_xls.getCellType() == 1)
        return this.cell_xls.getStringCellValue(); 
      if (this.cell_xls.getCellType() == 0 || 
        this.cell_xls.getCellType() == 2) {
        String cellText = String.valueOf(this.cell_xls.getNumericCellValue());
        if (DateUtil.isCellDateFormatted((Cell)this.cell_xls)) {
          double d = this.cell_xls.getNumericCellValue();
          Calendar cal = Calendar.getInstance();
          cal.setTime(DateUtil.getJavaDate(d));
          cellText = String.valueOf(cal.get(1)).substring(2);
          cellText = String.valueOf(cal.get(2) + 1) + "/" + cal.get(5) + "/" + cellText;
        } 
        return cellText;
      } 
      if (this.cell_xls.getCellType() == 3)
        return ""; 
      return String.valueOf(this.cell_xls.getBooleanCellValue());
    } catch (Exception e) {
      e.printStackTrace();
      return "row " + rowNum + " or column " + colNum + " does not exist  in xls";
    } 
  }
  
  public boolean setCellData(String sheetName, String colName, int rowNum, String data) {
    try {
      this.fis = new FileInputStream(this.path);
      this.workbook = new XSSFWorkbook(this.fis);
      if (rowNum <= 0)
        return false; 
      int index = this.workbook.getSheetIndex(sheetName);
      int colNum = -1;
      if (index == -1)
        return false; 
      this.sheet = this.workbook.getSheetAt(index);
      this.row = this.sheet.getRow(0);
      for (int i = 0; i < this.row.getLastCellNum(); i++) {
        if (this.row.getCell(i).getStringCellValue().trim().equals(colName))
          colNum = i; 
      } 
      if (colNum == -1)
        return false; 
      this.sheet.autoSizeColumn(colNum);
      this.row = this.sheet.getRow(rowNum - 1);
      if (this.row == null)
        this.row = this.sheet.createRow(rowNum - 1); 
      this.cell = this.row.getCell(colNum);
      if (this.cell == null)
        this.cell = this.row.createCell(colNum); 
      this.cell.setCellValue(data);
      this.fileOut = new FileOutputStream(this.path);
      this.workbook.write(this.fileOut);
      this.fileOut.close();
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    } 
    return true;
  }
  
  public boolean setCellData(String sheetName, String colName, int rowNum, String data, String url) {
    try {
      this.fis = new FileInputStream(this.path);
      this.workbook = new XSSFWorkbook(this.fis);
      if (rowNum <= 0)
        return false; 
      int index = this.workbook.getSheetIndex(sheetName);
      int colNum = -1;
      if (index == -1)
        return false; 
      this.sheet = this.workbook.getSheetAt(index);
      this.row = this.sheet.getRow(0);
      for (int i = 0; i < this.row.getLastCellNum(); i++) {
        if (this.row.getCell(i).getStringCellValue().trim().equalsIgnoreCase(colName))
          colNum = i; 
      } 
      if (colNum == -1)
        return false; 
      this.sheet.autoSizeColumn(colNum);
      this.row = this.sheet.getRow(rowNum - 1);
      if (this.row == null)
        this.row = this.sheet.createRow(rowNum - 1); 
      this.cell = this.row.getCell(colNum);
      if (this.cell == null)
        this.cell = this.row.createCell(colNum); 
      this.cell.setCellValue(data);
      XSSFCreationHelper createHelper = this.workbook.getCreationHelper();
      XSSFCellStyle xSSFCellStyle = this.workbook.createCellStyle();
      XSSFFont hlink_font = this.workbook.createFont();
      hlink_font.setUnderline((byte)1);
      hlink_font.setColor(IndexedColors.BLUE.getIndex());
      xSSFCellStyle.setFont((Font)hlink_font);
      XSSFHyperlink link = createHelper.createHyperlink(HyperlinkType.FILE);
      link.setAddress(url);
      this.cell.setHyperlink((Hyperlink)link);
      this.cell.setCellStyle((CellStyle)xSSFCellStyle);
      this.fileOut = new FileOutputStream(this.path);
      this.workbook.write(this.fileOut);
      this.fileOut.close();
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    } 
    return true;
  }
  
  public boolean addSheet(String sheetname) {
    try {
      this.workbook.createSheet(sheetname);
      FileOutputStream fileOut = new FileOutputStream(this.path);
      this.workbook.write(fileOut);
      fileOut.close();
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    } 
    return true;
  }
  
  public boolean removeSheet(String sheetName) {
    int index = this.workbook.getSheetIndex(sheetName);
    if (index == -1)
      return false; 
    try {
      this.workbook.removeSheetAt(index);
      FileOutputStream fileOut = new FileOutputStream(this.path);
      this.workbook.write(fileOut);
      fileOut.close();
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    } 
    return true;
  }
  
  public boolean addColumn(String sheetName, String colName) {
    try {
      this.fis = new FileInputStream(this.path);
      this.workbook = new XSSFWorkbook(this.fis);
      int index = this.workbook.getSheetIndex(sheetName);
      if (index == -1)
        return false; 
      XSSFCellStyle style = this.workbook.createCellStyle();
      style.setFillForegroundColor((short)55);
      style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
      this.sheet = this.workbook.getSheetAt(index);
      this.row = this.sheet.getRow(0);
      if (this.row == null)
        this.row = this.sheet.createRow(0); 
      if (this.row.getLastCellNum() == -1) {
        this.cell = this.row.createCell(0);
      } else {
        this.cell = this.row.createCell(this.row.getLastCellNum());
      } 
      this.cell.setCellValue(colName);
      this.cell.setCellStyle((CellStyle)style);
      this.fileOut = new FileOutputStream(this.path);
      this.workbook.write(this.fileOut);
      this.fileOut.close();
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    } 
    return true;
  }
  
  public boolean removeColumn(String sheetName, int colNum) {
    try {
      if (!isSheetExist(sheetName))
        return false; 
      this.fis = new FileInputStream(this.path);
      this.workbook = new XSSFWorkbook(this.fis);
      this.sheet = this.workbook.getSheet(sheetName);
      XSSFCellStyle style = this.workbook.createCellStyle();
      style.setFillForegroundColor((short)55);
      style.setFillPattern(FillPatternType.NO_FILL);
      for (int i = 0; i < getRowCount(sheetName); i++) {
        this.row = this.sheet.getRow(i);
        if (this.row != null) {
          this.cell = this.row.getCell(colNum);
          if (this.cell != null) {
            this.cell.setCellStyle((CellStyle)style);
            this.row.removeCell((Cell)this.cell);
          } 
        } 
      } 
      this.fileOut = new FileOutputStream(this.path);
      this.workbook.write(this.fileOut);
      this.fileOut.close();
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    } 
    return true;
  }
  
  public boolean isSheetExist(String sheetName) {
    int index = this.workbook.getSheetIndex(sheetName);
    if (index == -1) {
      index = this.workbook.getSheetIndex(sheetName.toUpperCase());
      if (index == -1)
        return false; 
      return true;
    } 
    return true;
  }
  
  public int getColumnCount(String sheetName) {
    if (!isSheetExist(sheetName))
      return -1; 
    this.sheet = this.workbook.getSheet(sheetName);
    this.row = this.sheet.getRow(0);
    if (this.row == null)
      return -1; 
    return this.row.getLastCellNum();
  }
  
  private boolean addHyperLink(String sheetName, String screenShotColName, String testCaseName, int index, String url, String message) {
    url = url.replace('\\', '/');
    if (!isSheetExist(sheetName))
      return false; 
    this.sheet = this.workbook.getSheet(sheetName);
    for (int i = 2; i <= getRowCount(sheetName); i++) {
      if (getCellData(sheetName, 0, i).equalsIgnoreCase(testCaseName)) {
        setCellData(sheetName, screenShotColName, i + index, message, url);
        break;
      } 
    } 
    return true;
  }
  
  public int getCellRowNum(String sheetName, String colName, String cellValue) {
    for (int i = 2; i <= getRowCount(sheetName); i++) {
      if (getCellData(sheetName, colName, i).equalsIgnoreCase(cellValue))
        return i; 
    } 
    return -1;
  }
  public  String getValueByColumnNameAndRowName( String sheetName, String columnName, String rowName)  {
 
      this.sheet = this.workbook.getSheet(sheetName);
      int columnIndex = -1;
      int rowIndex = -1;
      Row headerRow = this.sheet.getRow(0);
      for (Cell cell : headerRow) {
          if (cell.getStringCellValue().equals(columnName)) {
              columnIndex = cell.getColumnIndex();
              break;
          }
      }
      for (Row row : this.sheet) {
          Cell firstCell = row.getCell(0); 
          if (firstCell.getStringCellValue().equals(rowName)) {
              rowIndex = row.getRowNum();
              break;
          }
      }

      if (columnIndex != -1 && rowIndex != -1) {
          Row dataRow = this.sheet.getRow(rowIndex);
          Cell cell = dataRow.getCell(columnIndex);
          if (cell != null) {
              String cellValue = cell.toString();
              try {
				workbook.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
              return cellValue;
          }
      }

      try {
		workbook.close();
	} catch (IOException e) {
		e.printStackTrace();
	}
      return null;
  }
  
  public By getXpathlocator( String locataorssheetname, String locatorsColName, String locatorEleName) {
		String str = getValueByColumnNameAndRowName(locataorssheetname, locatorsColName, locatorEleName);
		By by = By.xpath(str);
		return by;
	}
}
