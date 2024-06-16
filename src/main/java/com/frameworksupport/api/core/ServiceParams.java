package com.frameworksupport.api.core;

import java.util.HashMap;
import java.util.Map;

public class ServiceParams {
	 private Map<String, Object> pathParamsMap = new HashMap<>();
	 private Map<String, Object> queryParamsMap = new HashMap<>();
	  public void addPathParm(String key, Object object) {
//	    commonUtil.log("Adding Header: " + key + " : " + value);
		  pathParamsMap.put(key, object);
	  }
	  
	  
	  public void addQueryParm(String key, String value) {
//		    commonUtil.log("Adding Header: " + key + " : " + value);
		        queryParamsMap.put(key, value);
		  }
	  
	public Map<String, Object> getPathParams() {
		return pathParamsMap;
	}
	
	public Map<String, Object> getQueryParams() {
		return queryParamsMap;
	}
	

}
