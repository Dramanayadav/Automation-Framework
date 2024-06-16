package com.frameworksupport.util.zap.security;

import java.io.IOException;
import org.openqa.selenium.Proxy;
import org.zaproxy.clientapi.core.ApiResponse;
import org.zaproxy.clientapi.core.ApiResponseElement;
import org.zaproxy.clientapi.core.ClientApi;
import org.zaproxy.clientapi.core.ClientApiException;

public class ZapUtil 
{
	private static ClientApi clientApi;
	public static Proxy proxy;
	static String zapPort1;
	private static String zapAddress; 
	private static int zapPort;
	private static String apiKey;
	private static ApiResponse apireponse;
	
	public void zapConnection()
	  {
			zapAddress	= ZAPConfig.ZAP_PROXY_ADDRESS;
			zapPort1 	= ZAPConfig.ZAP_PROXY_PORT;
			zapPort 	= Integer.valueOf(zapPort1);
			apiKey		= ZAPConfig.ZAP_PROXY_APIKEY;
			proxy = new Proxy().setHttpProxy(zapAddress+":"+zapPort).setSslProxy(zapAddress+":"+zapPort);
			clientApi = new ClientApi(zapAddress, zapPort,apiKey);
	  }
	
	public void waitUnitlPassiveScanCompleted() throws IOException 
	{
		try {
			apireponse = clientApi.pscan.recordsToScan();
			String tempval = ((ApiResponseElement)apireponse).getValue();
			while(!tempval.equals("0"))
			{
				apireponse = clientApi.pscan.recordsToScan();
				tempval = ((ApiResponseElement)apireponse).getValue();
			}
		} catch (ClientApiException e) {
			e.printStackTrace();
		}
	}
	public void generateZapReport()
	{		
		String title = "ZAP Security Test";
        String template = "traditional-html";
        String theme = null;
        String description = "This is zap security test report";
        String contexts = null;
        String sites = null;
        String sections = null;
        String includedconfidences = null;
        String includedrisks = null;
        String reportfilename = "zap-report.html";
        String reportfilenamepattern = null;
        String reportdir = System.getProperty("user.dir");
        String display = null;
		
        try {
            clientApi.reports.generate(title, template, theme, description, contexts, sites, sections,
                    includedconfidences, includedrisks, reportfilename, reportfilenamepattern, reportdir, display);
        } catch (ClientApiException e) {
            e.printStackTrace();
        }
	}
}