package com.frameworksupport.util.enums;

public enum Browser {
	  FIREFOX("Firefox"),
	  ZAP_FIREFOX("zapFirefox"),
	  CHROME("Chrome"),
	  ZAP_CHROME("zapChrome"),
	  EDGE("Edge"),
	  ZAP_EDGE("zapEdge"),
	  SAFARI("Safari"),
	  IOS_SAFARI("iosSafari"),
	  ANDROID_CHROME("androidChrome"),
	  ANDROID_NATIVE_APP("androidApp"),
	  IOS_NATIVE_APP("iosApp");
	  
	  String browserName;
	  
	  Browser(String browserName) {
	    this.browserName = browserName;
	  }
	  
	  public String toString() {
	    return this.browserName;
	  }
	}