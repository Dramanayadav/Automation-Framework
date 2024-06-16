package com.frameworksupport.api.resources;

public class Resources {

	
	public static final String JSON_PATH= "src/test/resources/data/jsondata/sampledata.json";
	public static final String CREDENTIAL_PATH= "src/test/resources/data/jsondata/credential.json";
	public static final String UPDATEDEATILS_PATH= "src/test/resources/data/jsondata/body.json";
	public static final String GETREQ_URL = "https://retoolapi.dev/n6FytM/userItems";
	public static final String GETREQ_URL_PARAM = "https://retoolapi.dev/n6FytM/userItems?ItemId=";
	public static final String POSTREQ_URL_AUTH = "https://restful-booker.herokuapp.com/auth";
	public static final String PUTREQ_URL_AUTH = "https://restful-booker.herokuapp.com/booking/554";
	
	public static final String HrAssitWebUrl="https://bots.kore.ai/chatbot/v2/webhook/st-f527aa51-c497-5b04-b882-64501d7203aa";
	public static final String googleweburl="https://www.google.com/";
	
	
	//postman body json file paths
	public static final String KOREWEBHOOKREQUESTBODY= "\\data\\jsondata\\apijsonpayloads\\korewebhookrequestbody.json";
	
	
	
	//postman Api expected responce json file paths
	public static final String KOREWEBHOOKRESPONSE= "\\data\\jsondata\\apijsonresponses\\korewebhookresponse.json";
	public static final String KOREWEBHOOKCHANNEL_NON_RESPONSE= "\\data\\jsondata\\apijsonresponses\\korewebhookchannelnonresponse.json";
	
	
	//properties file paths
	public static final String SERVICE_HEADERS_FILEPATH = System.getProperty("user.dir")+"/src/test/resources/config/service-headers.properties";
	
	
	
	//api json schema file  paths
	public static final String KOREWEBHOOKSERVICESCHEMA= "\\data\\jsondata\\apiservicesjsonschema\\korewebhookserviceschema.json";
	
}
