package com.frameworksupport.api.core;

import java.util.HashMap;
import java.util.Map;
import com.frameworksupport.api.resources.PropertyKeys;
import com.frameworksupport.api.resources.Resources;
import com.frameworksupport.api.resources.ServiceHeadersKeys;
import com.frameworksupport.util.general.CommonUtil;
import com.frameworksupport.util.propertryfiles.PropertyFiles;

public class RequestHeaders {
  protected static CommonUtil commonUtil;
  PropertyFiles serviceHeadersFile;
  public RequestHeaders(){
	  commonUtil = ApiBaseTest.getCommonUtil();
	  serviceHeadersFile=new PropertyFiles(Resources.SERVICE_HEADERS_FILEPATH);
  }
  
  
  private Map<String, String> requestHeaderMap = new HashMap<>();
  
  public void addRequestHeader(String key, String value) {
//    commonUtil.log("Adding Header: " + key + " : " + value);
    requestHeaderMap.put(key, value);
  }
  
  public void removeRequestHeader(String key) {
   commonUtil.log("Removing key from Header: " + key);
    requestHeaderMap.remove(key);
  }
  
  public Map<String, String> getRequestHeaders() {
    return this.requestHeaderMap;
  }
  
  public Map<String, String> korePostmanWebhookChannelHeadres() {
	    addRequestHeader(ServiceHeadersKeys.Content_Type,serviceHeadersFile.getProperty(PropertyKeys.CONTENTTYPE));
		addRequestHeader(ServiceHeadersKeys.Accept, serviceHeadersFile.getProperty(PropertyKeys.ACCEPT));
		addRequestHeader(ServiceHeadersKeys.Authorization,serviceHeadersFile.getProperty(PropertyKeys.KOREWEBHOOKAUTHORIZATION) );
		return requestHeaderMap;
  }
  
  public Map<String, String> goRestCreateApiHeadres() {
	    addRequestHeader(ServiceHeadersKeys.Content_Type,serviceHeadersFile.getProperty(PropertyKeys.CONTENTTYPE));
		addRequestHeader(ServiceHeadersKeys.Authorization,serviceHeadersFile.getProperty(PropertyKeys.GORESTAPIBEARERTOKEN));
		return requestHeaderMap;
}
}
