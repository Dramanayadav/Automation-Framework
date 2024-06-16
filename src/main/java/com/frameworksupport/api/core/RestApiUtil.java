package com.frameworksupport.api.core;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
//import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Cookie;
import io.restassured.matcher.RestAssuredMatchers;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import org.json.JSONObject;
import org.testng.Assert;

public class RestApiUtil extends ApiClient {
  public Response post(String url, DTO dto) {
    this.logger.logInfo("POST: " + url);
    printJson(dto);
    Response response = (Response)RestAssured.given().relaxedHTTPSValidation().contentType(ContentType.JSON).body(dto).post(url, new Object[0]);
    this.logger.logInfo("Response Code received: " + response.statusCode());
    this.logger.logInfo("Response Body: " + response.body().asString());
    return response;
  }
  
  public Response post(String url, RequestHeaders requestHeaders, Object jsonPayload, Cookie cookie) {
    Response response;
    this.logger.logInfo("POST: " + url);
    this.logger.logInfo(jsonPayload.toString());
    if (requestHeaders == null) {
      response = (Response)RestAssured.given().relaxedHTTPSValidation().cookie(cookie).contentType(ContentType.JSON).body(jsonPayload)
        .post(url, new Object[0]);
    } else {
      response = (Response)((RequestSpecification)RestAssured.given().relaxedHTTPSValidation().log().all()).headers(requestHeaders.getRequestHeaders())
        .cookie(cookie).contentType(ContentType.JSON).body(jsonPayload).post(url, new Object[0]);
    } 
    this.logger.logInfo("Response Code received: " + response.statusCode());
    this.logger.logInfo("Response Body: " + response.body().asString());
    return response;
  }
  
  public Response post(String url, RequestHeaders requestHeaders, String json) {
    Response response;
    this.logger.logInfo("POST: " + url);
    this.logger.logInfo(json);
    if (requestHeaders == null) {
      response = (Response)RestAssured.given().relaxedHTTPSValidation().contentType(ContentType.JSON).body(json).post(url, new Object[0]);
    } else {
      response = (Response)RestAssured.given().relaxedHTTPSValidation().headers(requestHeaders.getRequestHeaders())
        .contentType(ContentType.JSON).body(json).post(url, new Object[0]);
    } 
    this.logger.logInfo("Response Code received: " + response.statusCode());
    this.logger.logInfo("Response Body: " + response.body().asString());
    return response;
  }
  
  public Response post(String url, RequestHeaders requestHeaders, JSONObject jsonObject) {
    Response response;
    this.logger.logInfo("POST: " + url);
    this.logger.logInfo(jsonObject.toString());
    if (requestHeaders == null) {
      response = (Response)RestAssured.given().relaxedHTTPSValidation().contentType(ContentType.JSON).body(jsonObject.toString())
        .post(url, new Object[0]);
    } else {
      response = (Response)RestAssured.given().relaxedHTTPSValidation().headers(requestHeaders.getRequestHeaders())
        .contentType(ContentType.JSON).body(jsonObject.toString()).post(url, new Object[0]);
    } 
    this.logger.logInfo("Response Code received: " + response.statusCode());
    this.logger.logInfo("Response Body: " + response.body().asString());
    return response;
  }
  
  
  
  public Response post(String url, JSONObject jsonObject) {
    this.logger.logInfo("POST: " + url);
    this.logger.logInfo(jsonObject.toString());
    Response response = (Response)RestAssured.given().relaxedHTTPSValidation().contentType(ContentType.JSON).body(jsonObject.toString())
      .post(url, new Object[0]);
    this.logger.logInfo("Response Code received: " + response.statusCode());
    this.logger.logInfo("Response Body: " + response.body().asString());
    return response;
  }
  
  public Response post(String url, RequestHeaders requestHeaders, DTO dto) {
    this.logger.logInfo("POST: " + url);
    printJson(dto);
    Response response = (Response)RestAssured.given().relaxedHTTPSValidation().headers(requestHeaders.getRequestHeaders())
      .contentType(ContentType.JSON).body(dto).post(url, new Object[0]);
    this.logger.logInfo("Response Code received: " + response.statusCode());
    this.logger.logInfo("Response Body: " + response.body().asString());
    return response;
  }
  
  public Response post(String url, RequestHeaders requestHeaders, Map<?, ?> payload) {
    this.logger.logInfo("POST: " + url);
    Response response = (Response)RestAssured.given().relaxedHTTPSValidation().headers(requestHeaders.getRequestHeaders())
      .contentType(ContentType.JSON).body(payload).post(url, new Object[0]);
    this.logger.logInfo("Response Code received: " + response.statusCode());
    this.logger.logInfo("Response Body: " + response.body().asString());
    return response;
  }
  
  public Response post(String url, Map<?, ?> payload) {
    this.logger.logInfo("POST: " + url);
    Response response = (Response)RestAssured.given().relaxedHTTPSValidation().contentType(ContentType.JSON).body(payload).post(url, new Object[0]);
    this.logger.logInfo("Response Code received: " + response.statusCode());
    return response;
  }
  
  public Response put(String url, RequestHeaders requestHeaders, String json, Cookie cookie) {
    Response response;
    this.logger.logInfo("PUT: " + url);
    this.logger.logInfo(json);
    if (requestHeaders == null) {
      response = (Response)RestAssured.given().relaxedHTTPSValidation().cookie(cookie).contentType(ContentType.JSON).body(json)
        .put(url, new Object[0]);
    } else {
      response = (Response)((RequestSpecification)RestAssured.given().relaxedHTTPSValidation().log().all()).headers(requestHeaders.getRequestHeaders())
        .cookie(cookie).contentType(ContentType.JSON).body(json).put(url, new Object[0]);
    } 
    this.logger.logInfo("Response Code received: " + response.statusCode());
    return response;
  }
  
  public Response put(String url, RequestHeaders requestHeaders, Map<?, ?> payload) {
	    this.logger.logInfo("PUT: " + url);
	    Response response = (Response)RestAssured.given().relaxedHTTPSValidation().headers(requestHeaders.getRequestHeaders())
	      .contentType(ContentType.JSON).body(payload).put(url, new Object[0]);
	    this.logger.logInfo("Response Code received: " + response.statusCode());
	    this.logger.logInfo("Response Body: " + response.body().asString());
	    return response;
	  }
  public Response put(String url, RequestHeaders requestHeaders,Map<String, ?> serviceParams, JSONObject jsonObject) {
	    Response response;
	    this.logger.logInfo("POST: " + url);
	    this.logger.logInfo(jsonObject.toString());
	    if (requestHeaders == null&&serviceParams==null) {
	      response = (Response)RestAssured.given().relaxedHTTPSValidation().contentType(ContentType.JSON).body(jsonObject.toString())
	        .put(url, new Object[0]);
	    } else {
	      response = (Response)RestAssured.given().relaxedHTTPSValidation().headers(requestHeaders.getRequestHeaders()).pathParams(serviceParams)
	        .contentType(ContentType.JSON).body(jsonObject.toString()).put(url, new Object[0]);
	    } 
	    this.logger.logInfo("Response Code received: " + response.statusCode());
	    this.logger.logInfo("Response Body: " + response.body().asString());
	    return response;
	  }
  public Response get(String url) {
    this.logger.logInfo("GET: " + url);
    Response response = (Response)RestAssured.given().relaxedHTTPSValidation().contentType(ContentType.JSON).get(url, new Object[0]);
    this.logger.logInfo("Response Code received: " + response.statusCode());
    this.logger.logInfo("Response Body: " + response.body().asString());
    return response;
  }
  
  public Response get(String url, Cookie cookie) {
    this.logger.logInfo("GET: " + url);
    Response response = (Response)RestAssured.given().relaxedHTTPSValidation().cookie(cookie).contentType(ContentType.JSON).get(url, new Object[0]);
    this.logger.logInfo("Response Code received: " + response.statusCode());
    this.logger.logInfo("Response Body: " + response.body().asString());
    return response;
  }
  
  public Response get(String url, RequestHeaders requestHeaders) {
    Response response;
    this.logger.logInfo("GET: " + url);
    if (requestHeaders == null) {
      response = (Response)((RequestSpecification)RestAssured.given().relaxedHTTPSValidation().log().all()).contentType(ContentType.JSON).get(url, new Object[0]);
    } else {
      response = (Response)((RequestSpecification)RestAssured.given().relaxedHTTPSValidation().log().all()).headers(requestHeaders.getRequestHeaders())
        .contentType(ContentType.JSON).get(url, new Object[0]);
    } 
    this.logger.logInfo("Response Code received: " + response.statusCode());
    this.logger.logInfo("Response Body: " + response.body().asString());
    return response;
  }
  
  public Response get(String url, RequestHeaders requestHeaders,Map<String, ?> serviceParams) {
	    Response response;
	    this.logger.logInfo("GET: " + url);
	    if (requestHeaders == null&&serviceParams==null) {
	      response = (Response)((RequestSpecification)RestAssured.given().relaxedHTTPSValidation().log().all()).contentType(ContentType.JSON).get(url, new Object[0]);
	    } else {
	      response = (Response)((RequestSpecification)RestAssured.given().relaxedHTTPSValidation().log().all()).headers(requestHeaders.getRequestHeaders()).pathParams( serviceParams).get(url, new Object[0]);
	    } 
	    this.logger.logInfo("Response Code received: " + response.statusCode());
	    this.logger.logInfo("Response Body: " + response.body().asString());
	    return response;
	  }
  
  public void verifyResponse(Response response, String textToVerify) {
    String jsonResponse = response.body().asString();
    this.logger.logInfo(jsonResponse);
    this.logger.logInfo("Expected : " + textToVerify);
    Assert.assertTrue(jsonResponse.toLowerCase().contains(textToVerify.toLowerCase()));
  }
  
  public <T> T deserializeJson(Response response, Class<T> dataClass) {
    Gson gson = new Gson();
    T responseData = (T)gson.fromJson(response.body().asString(), dataClass);
    return responseData;
  }
  
  public static String get(DTO dto) {
    Gson gson = (new GsonBuilder()).setPrettyPrinting().create();
    return gson.toJson(dto);
  }
  
  public JsonElement parseJsonFile(String jsonFilePath) throws IOException {
    Path path = Paths.get(jsonFilePath, new String[0]);
    Reader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8);
    JsonParser parser = new JsonParser();
    JsonElement tree = parser.parse(reader);
    return tree;
  }
  
  public <T> T parseJson(String json, Class<T> dataClass) {
    Gson gson = new Gson();
    T responseData = (T)gson.fromJson(json, dataClass);
    return responseData;
  }
  
  public JsonElement getJsonElement(DTO dto) {
    Gson gson = (new GsonBuilder()).setPrettyPrinting().create();
    return (new JsonParser()).parse(gson.toJson(dto));
  }
  
  public Response delete(String url, DTO dto, Cookie cookie) {
    this.logger.logInfo("DELETE: " + url);
    printJson(dto);
    Response response = (Response)RestAssured.given().relaxedHTTPSValidation().cookie(cookie).contentType(ContentType.JSON).body(dto).delete(url, new Object[0]);
    this.logger.logInfo("Response Code received: " + response.statusCode());
    return response;
  }
  
  public Response delete(String url, RequestHeaders requestHeaders,Map<String, ?> serviceParams) {
	    Response response;
	    this.logger.logInfo("GET: " + url);
	    if (requestHeaders == null&&serviceParams==null) {
	      response = (Response)((RequestSpecification)RestAssured.given().relaxedHTTPSValidation().log().all()).contentType(ContentType.JSON).delete(url, new Object[0]);
	    } else {
	      response = (Response)((RequestSpecification)RestAssured.given().relaxedHTTPSValidation().log().all()).headers(requestHeaders.getRequestHeaders()).pathParams((Map<String, ?>) serviceParams).delete(url, new Object[0]);
	    } 
	    this.logger.logInfo("Response Code received: " + response.statusCode());
	    this.logger.logInfo("Response Body: " + response.body().asString());
	    return response;
	  }
  
  public void jsonSchemaValidation(Response response ,String expectedJsonSchema) {
	  response.then().assertThat().body( JsonSchemaValidator.matchesJsonSchemaInClasspath(expectedJsonSchema));
  }
  
  public void xmlSchemaValidation(Response response ,String expectedXmlSchema) {
	  response.then().assertThat().body(RestAssuredMatchers.matchesXsdInClasspath(expectedXmlSchema));
  }
  
  public  Object getResponseJsonKeyValue(Response response ,String expectedJsonKeyPath) {
	 return response.jsonPath().get(expectedJsonKeyPath);
  }
  
  
}
