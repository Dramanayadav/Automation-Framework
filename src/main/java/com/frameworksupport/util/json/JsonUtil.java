package com.frameworksupport.util.json;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.json.JsonException;

public class JsonUtil {

	private JSONObject jsonobject;
	
	

	private JSONObject jsonDataObject(String filepath) throws IOException {
		InputStream datais = null;
		try {
			String dataFileName = filepath;
			datais = getClass().getClassLoader().getResourceAsStream(dataFileName);
			JSONTokener tokener = new JSONTokener(datais);
			jsonobject = new JSONObject(tokener);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (datais != null) {
				datais.close();
			}
		}

		return jsonobject;
	}
	
	public JSONObject getJsonFileObject(String jsonPath) throws IOException{
		
		return jsonDataObject(jsonPath);
	}

	public String getJsonKeyValueFromJsonObject(String jsonPath, String jsonObjectName, String jsonStringKeyName)
			throws IOException {
		JSONObject jsonobject = jsonDataObject(jsonPath);
		String jsonKeyValue = jsonobject.getJSONObject(jsonObjectName).getString(jsonStringKeyName);
		return jsonKeyValue;
	}
	public String getJsonKeyValueFromJsonObject(String jsonObjectName, String jsonStringKeyName)
			throws IOException {
		String jsonKeyValue = jsonobject.getJSONObject(jsonObjectName).getString(jsonStringKeyName);
		return jsonKeyValue;
	}

	public Object getJsonKeyValueFromJsonArrayObject(String jsonPath, String jsonArrayName, int IndexNo,
			String jsonStringKeyName) throws IOException {
		JSONObject jsonobject = jsonDataObject(jsonPath);
		Object jsonKeyValue = jsonobject.getJSONArray(jsonArrayName).getJSONObject(IndexNo).get(jsonStringKeyName);
		return jsonKeyValue;
	}
	public Object getJsonKeyValueFromJsonArrayObject(String jsonArrayName, int IndexNo,
			String jsonStringKeyName) throws IOException {
		Object jsonKeyValue = jsonobject.getJSONArray(jsonArrayName).getJSONObject(IndexNo).get(jsonStringKeyName);
		return jsonKeyValue;
	}

	public Object getJsonKeyValueFromJsonArrayObject(JSONObject extractedJsonObject, String jsonArrayName, int IndexNo,
			String jsonStringKeyName) throws IOException {
		Object jsonKeyValue = extractedJsonObject.getJSONArray(jsonArrayName).getJSONObject(IndexNo).get(jsonStringKeyName);
		return jsonKeyValue;
	}

	public String getJsonKeyValueFromJsonObject(String jsonPath, String SiteName, String user, String userType)
			throws IOException {
		JSONObject jsonobject = jsonDataObject(jsonPath);
		String jsonKeyValue = jsonobject.getJSONObject(SiteName).getJSONObject(user).getString(userType);
		return jsonKeyValue;
	}

	// Read Json file
	static String jsonPath = System.getProperty("user.dir") + "/src/test/resources/jsonReader/testdata.json";

	public static String readJson(String path, int index, String key) throws FileNotFoundException {
		FileReader fr = new FileReader(path);
		Object obj;
		String keyvalue = null;
		try {
			JSONParser jsonparser = new JSONParser();
			obj = jsonparser.parse(fr);
			JSONObject usersobj = (JSONObject) obj;
			JSONArray usersList = (JSONArray) usersobj.get("users");
			JSONObject user = (JSONObject) usersList.get(index);
			keyvalue = (String) user.get(key);
			// String password = (String) user.get("password");
		} catch (JsonException | IOException | ParseException e) {
			e.printStackTrace();
		}
		return keyvalue;
	}

	public static String getJsonData(String key) throws FileNotFoundException {
		String loginData = readJson(jsonPath, 0, key);
		// String[] values=loginData.toString().split(",");
		return loginData;

	}
}
