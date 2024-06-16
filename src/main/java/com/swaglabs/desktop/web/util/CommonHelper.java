package com.swaglabs.desktop.web.util;

import com.frameworksupport.setup.AutomationBaseActionDriver;
import com.frameworksupport.util.general.CommonUtil;


public class CommonHelper {
	
	
	public static void getNavigateUrl(Object urltype) {
		
		String url = CommonUtil.getConfigProperty((String) urltype);
		AutomationBaseActionDriver.getBaseActionDriver().get(url);
	
}
	
}
