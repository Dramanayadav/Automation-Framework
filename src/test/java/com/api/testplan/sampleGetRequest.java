package com.api.testplan;

import java.io.FileReader;

import org.apache.http.HttpStatus;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.frameworksupport.api.core.ApiBaseTest;
import com.frameworksupport.api.core.RequestHeaders;
import com.frameworksupport.api.core.RestApiUtil;
import com.frameworksupport.api.resources.Resources;
import com.frameworksupport.setup.TestInfo;
import com.frameworksupport.util.general.ExtentReportLogger;
import com.swaglabs.desktop.web.config.Author;
import com.swaglabs.desktop.web.config.FeatureName;

import io.restassured.response.Response;

public class sampleGetRequest extends ApiBaseTest {

	@BeforeClass(alwaysRun = true)
	public void initialSetUp() throws Exception {
		initializeTest(FeatureName.API.toString());
	}

	ExtentReportLogger logger;
	RestApiUtil restApiUtil;

	@TestInfo(author = Author.NANDITHA)
	@Test
	public void test_getRequest() throws Exception {
		this.logger = ApiBaseTest.getExtentReportLogger();
		this.restApiUtil = new RestApiUtil();

		// Get URL
		Response response = restApiUtil.get(Resources.GETREQ_URL);
		// Http status validation
		Assert.assertEquals(response.statusCode(), HttpStatus.SC_OK);

	}

	@TestInfo(author = Author.NANDITHA)
	@Test
	public void test_getReq_withParameters() throws Exception {
		this.logger = ApiBaseTest.getExtentReportLogger();
		this.restApiUtil = new RestApiUtil();

		JSONParser parser = new JSONParser();
		Object obj = parser.parse(new FileReader(Resources.JSON_PATH));
		JSONObject jsonObject = (JSONObject) obj;
		String id = (String) jsonObject.get("id");

		// Get URL with parameters
		String endPoint = Resources.GETREQ_URL_PARAM + id;
		Response response = restApiUtil.get(endPoint);

		// Http status validation
		Assert.assertEquals(response.statusCode(), HttpStatus.SC_OK);

	}

	@TestInfo(author = Author.ASHOK)
	@Test
	public void test_Postreq_withparameters() throws Exception {
		this.logger = ApiBaseTest.getExtentReportLogger();
		this.restApiUtil = new RestApiUtil();

		JSONParser parser = new JSONParser();
		Object obj = parser.parse(new FileReader(Resources.CREDENTIAL_PATH));
		JSONObject jsonObject = (JSONObject) obj;

		// Post URL
		String endPoint = Resources.POSTREQ_URL_AUTH;
		Response response1 = restApiUtil.post(endPoint, jsonObject);

		Assert.assertEquals(response1.statusCode(), HttpStatus.SC_OK);

		logger.logInfo("Token for Post call :" + response1.jsonPath().getString("token"));

	}

	@TestInfo(author = Author.ASHOK)
	@Test
	public void test_putReq_withParameters() throws Exception {
		this.logger = ApiBaseTest.getExtentReportLogger();
		this.restApiUtil = new RestApiUtil();

		// Generating Token
		JSONParser parser = new JSONParser();
		Object tokenCredentail = parser.parse(new FileReader(Resources.CREDENTIAL_PATH));
		JSONObject credentailObject = (JSONObject) tokenCredentail;

		String endPoint = Resources.POSTREQ_URL_AUTH;
		Response response = restApiUtil.post(endPoint, credentailObject);
		Assert.assertEquals(response.statusCode(), HttpStatus.SC_OK);

		String token = response.jsonPath().getString("token");
		
		// put request
		Object obj = parser.parse(new FileReader(Resources.UPDATEDEATILS_PATH));
		JSONObject jsonObject = (JSONObject) obj;

		// Headers
		RequestHeaders headers = new RequestHeaders();
		headers.addRequestHeader("Content-Type", "application/json");
		headers.addRequestHeader("Accept", "application/json");
		headers.addRequestHeader("Cookie", "token=" + token);

		// URL
		String endPoint1 = Resources.PUTREQ_URL_AUTH;

		Response response1 = restApiUtil.put(endPoint1, headers, jsonObject);
		Assert.assertEquals(response1.statusCode(), HttpStatus.SC_OK);

	}

}
