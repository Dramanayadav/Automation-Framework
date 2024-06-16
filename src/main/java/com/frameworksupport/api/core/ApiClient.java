package com.frameworksupport.api.core;


import com.frameworksupport.util.general.ExtentReportLogger;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ApiClient {
  protected ExtentReportLogger logger = ApiBaseTest.getExtentReportLogger();
  
  public void printJson(Object json) {
    Gson gson = (new GsonBuilder()).setPrettyPrinting().create();
    this.logger.logInfo(gson.toJson(json));
  }
  
  public String getJsonString(Object json) {
    Gson gson = (new GsonBuilder()).setPrettyPrinting().create();
    return gson.toJson(json);
  }
}
