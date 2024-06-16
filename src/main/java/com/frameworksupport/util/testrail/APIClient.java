package com.frameworksupport.util.testrail;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Base64;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class APIClient {
  private String m_user;
  
  private String m_password;
  
  private String m_url;
  
  public APIClient(String base_url) {
    if (!base_url.endsWith("/"))
      base_url = String.valueOf(base_url) + "/"; 
    this.m_url = String.valueOf(base_url) + "index.php?/api/v2/";
  }
  
  public String getUser() {
    return this.m_user;
  }
  
  public void setUser(String user) {
    this.m_user = user;
  }
  
  public String getPassword() {
    return this.m_password;
  }
  
  public void setPassword(String password) {
    this.m_password = password;
  }
  
  public Object sendGet(String uri, String data) throws MalformedURLException, IOException, APIException {
    return sendRequest("GET", uri, data);
  }
  
  public Object sendGet(String uri) throws MalformedURLException, IOException, APIException {
    return sendRequest("GET", uri, null);
  }
  
  public Object sendPost(String uri, Object data) throws MalformedURLException, IOException, APIException {
    return sendRequest("POST", uri, data);
  }
  
  private Object sendRequest(String method, String uri, Object data) throws MalformedURLException, IOException, APIException {
    InputStream istream;
    Object result;
    URL url = new URL(String.valueOf(this.m_url) + uri);
    HttpURLConnection conn = (HttpURLConnection)url.openConnection();
    String auth = getAuthorization(this.m_user, this.m_password);
    conn.addRequestProperty("Authorization", "Basic " + auth);
    if (method.equals("POST")) {
      conn.setRequestMethod("POST");
      if (data != null)
        if (uri.startsWith("add_attachment")) {
          String boundary = "TestRailAPIAttachmentBoundary";
          File uploadFile = new File((String)data);
          conn.setDoOutput(true);
          conn.addRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
          OutputStream ostreamBody = conn.getOutputStream();
          BufferedWriter bodyWriter = new BufferedWriter(new OutputStreamWriter(ostreamBody));
          bodyWriter.write("\n\n--" + boundary + "\r\n");
          bodyWriter.write("Content-Disposition: form-data; name=\"attachment\"; filename=\"" + 
              uploadFile.getName() + "\"");
          bodyWriter.write("\r\n\r\n");
          bodyWriter.flush();
          InputStream istreamFile = new FileInputStream(uploadFile);
          byte[] dataBuffer = new byte[1024];
          int bytesRead;
          while ((bytesRead = istreamFile.read(dataBuffer)) != -1)
            ostreamBody.write(dataBuffer, 0, bytesRead); 
          ostreamBody.flush();
          bodyWriter.write("\r\n--" + boundary + "--\r\n");
          bodyWriter.flush();
          istreamFile.close();
          ostreamBody.close();
          bodyWriter.close();
        } else {
          conn.addRequestProperty("Content-Type", "application/json");
          byte[] block = JSONValue.toJSONString(data)
            .getBytes("UTF-8");
          conn.setDoOutput(true);
          OutputStream ostream = conn.getOutputStream();
          ostream.write(block);
          ostream.close();
        }  
    } else {
      conn.addRequestProperty("Content-Type", "application/json");
    } 
    int status = conn.getResponseCode();
    if (status != 200) {
      istream = conn.getErrorStream();
      if (istream == null)
        throw new APIException(
            "TestRail API return HTTP " + status + 
            " (No additional error message received)"); 
    } else {
      istream = conn.getInputStream();
    } 
    if (istream != null && 
      uri.startsWith("get_attachment/")) {
      FileOutputStream outputStream = new FileOutputStream((String)data);
      int bytesRead = 0;
      byte[] buffer = new byte[1024];
      while ((bytesRead = istream.read(buffer)) > 0)
        outputStream.write(buffer, 0, bytesRead); 
      outputStream.close();
      istream.close();
      return data;
    } 
    String text = "";
    if (istream != null) {
      BufferedReader reader = new BufferedReader(
          new InputStreamReader(
            istream, 
            "UTF-8"));
      String line;
      while ((line = reader.readLine()) != null) {
        text = String.valueOf(text) + line;
        text = String.valueOf(text) + System.getProperty("line.separator");
      } 
      reader.close();
    } 
    if (!text.equals("")) {
      result = JSONValue.parse(text);
    } else {
      result = new JSONObject();
    } 
    if (status != 200) {
      String line=null;
      String error = "No additional error message received";
      if (result != null && result instanceof JSONObject) {
        JSONObject obj = (JSONObject)result;
        if (obj.containsKey("error"))
          line = String.valueOf('"') + (String)obj.get("error") + '"'; 
      } 
      throw new APIException(
          "TestRail API returned HTTP " + status + "(" + line + ")");
    } 
    return result;
  }
  
  private static String getAuthorization(String user, String password) {
    try {
      return new String(Base64.getEncoder().encode((String.valueOf(user) + ":" + password).getBytes()));
    } catch (IllegalArgumentException illegalArgumentException) {
      return "";
    } 
  }
}
