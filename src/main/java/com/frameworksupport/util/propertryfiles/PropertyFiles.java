package com.frameworksupport.util.propertryfiles;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import org.testng.Assert;

public class PropertyFiles {

	private static Properties prop = new Properties();
	public static String currentURL;

//	static String path = System.getProperty("user.dir")+ "/src/test/resources/propertyFiles/propertycontent.properties";

	
	public PropertyFiles(String filePath){
		try {
			FileReader fr = new FileReader(filePath);
			prop.load(fr);
		} catch (IOException e) {
			e.printStackTrace();
			Assert.assertTrue(true, "File not intialized ");
		} catch (NullPointerException e1) {
			e1.printStackTrace();
		}

	}
	public synchronized static void initialize(String path) {
		try {
			FileReader fr = new FileReader(path);
			prop.load(fr);
		} catch (IOException e) {
			e.printStackTrace();
			Assert.assertTrue(true, "File not intialized ");
		} catch (NullPointerException e1) {
			e1.printStackTrace();
		}

	}

	public synchronized  String getProperty(String Key) {
		return prop.getProperty(Key);
	}

//	public  String getConfigProperty(String value) {
//		initialize(path);
//		String propvalue = getProperty(value);
//		return propvalue;
//	}

	public String getConfigProperty(String path, String value) {
		initialize(path);
		String propvalue = getProperty(value);
		return propvalue;
	}

	public static void SetConfigProperty(String path, String key, String value) {
		initialize(path);
		prop.setProperty(key, value);

	}

}
