package com.api.testplan;


import org.apache.http.HttpStatus;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.frameworksupport.api.core.ApiBaseTest;
import com.frameworksupport.api.core.RequestHeaders;
import com.frameworksupport.api.core.RestApiUtil;
import com.frameworksupport.api.resources.Resources;
import com.frameworksupport.setup.TestInfo;
import com.frameworksupport.util.general.ExtentReportLogger;
import com.frameworksupport.util.json.JsonUtil;
import com.swaglabs.desktop.web.config.Author;
import com.swaglabs.desktop.web.config.FeatureName;
import io.restassured.response.Response;

public class KorePostmanBotApi extends ApiBaseTest {
	ExtentReportLogger logger= ApiBaseTest.getExtentReportLogger();
	RequestHeaders headers = new RequestHeaders();
	RestApiUtil restApiUtil;
	 JsonUtil jsonUtil=new JsonUtil();
	@BeforeClass(alwaysRun = true)
	public void initialSetUp() throws Exception {
		initializeTest(FeatureName.API.toString());
	}

	@TestInfo(author = Author.SAMPATH)
	@Test
	public void postmanWebHookBotService() throws Exception {
		restApiUtil= new RestApiUtil();
        headers.korePostmanWebhookChannelHeadres();
		String endPoint = Resources.HrAssitWebUrl;
		Response response = restApiUtil.post(endPoint, headers, jsonUtil.getJsonFileObject(Resources.KOREWEBHOOKREQUESTBODY));
		Assert.assertEquals(response.statusCode(), HttpStatus.SC_OK);
		JSONObject korewebhookchannel_non_responseJO=  jsonUtil.getJsonFileObject(Resources.KOREWEBHOOKCHANNEL_NON_RESPONSE);
		
		
		String expectedval=(String) jsonUtil.getJsonKeyValueFromJsonArrayObject(korewebhookchannel_non_responseJO,"data", 0, "val");
		String actualval=(String) restApiUtil.getResponseJsonKeyValue(response, "data[0].val");
        System.out.println("extected :"+expectedval);
        System.out.println("actual :"+actualval);
        Assert.assertEquals(expectedval, actualval);
        
	}
	
	
	@TestInfo(author = Author.SAMPATH)
	@Test
	public void postmanWebHookBotServiceJsonSchemaValidation() throws Exception {
		restApiUtil= new RestApiUtil();
        headers.korePostmanWebhookChannelHeadres();
		String endPoint = Resources.HrAssitWebUrl;
		Response response = restApiUtil.post(endPoint, headers, jsonUtil.getJsonFileObject(Resources.KOREWEBHOOKREQUESTBODY));
		restApiUtil.jsonSchemaValidation(response, Resources.KOREWEBHOOKSERVICESCHEMA);
	}
	
	@TestInfo(author = Author.SAMPATH)
	@Test(enabled = false)
	public void googleGet() {
		restApiUtil= new RestApiUtil();
		String endPoint = Resources.googleweburl;
		Response response =restApiUtil.get(endPoint);
		System.out.println(response.getCookies());
	}
		
}
