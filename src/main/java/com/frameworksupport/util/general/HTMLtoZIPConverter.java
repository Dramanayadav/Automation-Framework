package com.frameworksupport.util.general;


import java.io.*;
import java.util.zip.*;

import com.frameworksupport.setup.DIRECTORY;

public class HTMLtoZIPConverter {
	

	    public static void reportConvertAsZip(String inputHtmlPath) {
	    	
	    	inputHtmlPath=inputHtmlPath+"index.html";
	        String zipFilePath = DIRECTORY.HTML_REPORT_PATH+"/htmlreportoutput.zip";

	        try {
	            FileOutputStream fos = new FileOutputStream(zipFilePath);
	            ZipOutputStream zipOut = new ZipOutputStream(fos);

	            addToZipFile(inputHtmlPath, zipOut);

	            zipOut.close();
	            fos.close();
	            
	            System.out.println("HTML file successfully converted to ZIP.");
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }

	    private static void addToZipFile(String filePath, ZipOutputStream zipOut) throws IOException {
	        File file = new File(filePath);
	        FileInputStream fis = new FileInputStream(file);
	        ZipEntry zipEntry = new ZipEntry(file.getName());
	        zipOut.putNextEntry(zipEntry);

	        byte[] bytes = new byte[1024];
	        int length;
	        while ((length = fis.read(bytes)) >= 0) {
	            zipOut.write(bytes, 0, length);
	        }

	        fis.close();
	    }

}
