package com.api.testplan;

import org.json.JSONObject;
import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.frameworksupport.api.core.ApiBaseTest;
import com.frameworksupport.api.core.RequestHeaders;
import com.frameworksupport.api.core.RestApiUtil;
import com.frameworksupport.api.core.ServiceParams;
import com.frameworksupport.util.general.ExtentReportLogger;
import com.frameworksupport.util.general.RandomUtil;
import com.frameworksupport.util.json.JsonUtil;
import com.swaglabs.desktop.web.config.FeatureName;

public class CreateUser extends ApiBaseTest{

	ExtentReportLogger logger= ApiBaseTest.getExtentReportLogger();
	RequestHeaders headers = new RequestHeaders();
	RestApiUtil restApiUtil;
	ServiceParams serviceParams=new ServiceParams();
	 JsonUtil jsonUtil=new JsonUtil();
	@BeforeClass(alwaysRun = true)
	public void initialSetUp() throws Exception {
		initializeTest(FeatureName.API.toString());
	}

	
	@Test(priority = 0)
	public void createUser(ITestContext context) {
		
		JSONObject jo=new JSONObject();
		restApiUtil=new RestApiUtil();
		jo.put("name", RandomUtil.getRandomName());
		jo.put("gender", "male");
		jo.put("email", RandomUtil.getRandomEmail());
		jo.put("status", "active");
		headers.goRestCreateApiHeadres();
		String createApi="https://gorest.co.in/public/v2/users";
        int id=(int) restApiUtil.getResponseJsonKeyValue(restApiUtil.post(createApi, headers, jo), "id");
        System.out.println(id);
        context.setAttribute("userId", id);
	}
	@Test(priority = 1)
	public void getUser(ITestContext context) {
		int id=(int) context.getAttribute("userId");
		restApiUtil=new RestApiUtil();
		serviceParams.addPathParm("id",id );
		headers.goRestCreateApiHeadres();
		String getApi="https://gorest.co.in/public/v2/{id}";
       restApiUtil.get(getApi, headers, serviceParams.getPathParams());
	}
	
	@Test(priority = 2)
	public void updateUser(ITestContext context) {
		int id=(int) context.getAttribute("userId");
		JSONObject jo=new JSONObject();
		restApiUtil=new RestApiUtil();
		jo.put("name", RandomUtil.getRandomName());
		jo.put("gender", "male");
		jo.put("email", RandomUtil.getRandomEmail());
		jo.put("status", "inactive");
		serviceParams.addPathParm("id",id );
		headers.goRestCreateApiHeadres();
		String updateApi="https://gorest.co.in/public/v2/{id}";
     restApiUtil.put(updateApi, headers, serviceParams.getPathParams(), jo);
       
	}
	@Test(priority = 3)
	public void deleteUser(ITestContext context) {
		int id=(int) context.getAttribute("userId");
		restApiUtil=new RestApiUtil();
		serviceParams.addPathParm("id",id );
		headers.goRestCreateApiHeadres();
		String getApi="https://gorest.co.in/public/v2/{id}";
       restApiUtil.delete(getApi, headers, serviceParams.getPathParams());
	}
}
