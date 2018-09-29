package com.my.automation.base.api;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.Authenticator.RequestorType;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.log4j.Logger;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class APIBase {
	private static final Logger logger = Logger.getLogger(APIBase.class);
	protected String base_Url;
	protected String api_Url;
	protected String environment;
	protected String method_Type;
	protected String request_File_Location;
	protected String output_File_Location;
	protected JsonObject token;
	protected JsonObject headers;
	protected JsonObject uri_Param;
	protected JsonObject input_param;
	protected JsonObject payload_param;
	protected String authToken;
	RestParser restParser;
	boolean output = false;
	String responseData;
	int responseCode;

	public APIBase(String api_Url, String environment, String method_Type, String request_File_Location, String token) {
		this.method_Type = method_Type;
		this.api_Url = api_Url;
		this.environment = environment;
		this.method_Type = method_Type;
		this.authToken = token;
		this.request_File_Location = request_File_Location;
		readParamFromJson(this.request_File_Location, this.environment);
		restParser = new RestParser(this.headers, this.api_Url, this.request_File_Location, this.method_Type, this.authToken, this.payload_param);

	}

	public APIBase() {
	}

	public String getBase_Url() {
		return base_Url;
	}

	public void setBase_Url(String base_Url) {
		this.base_Url = base_Url;
	}

	public String getApi_Url() {
		return api_Url;
	}

	public void setApi_Url(String api_Url) {
		this.api_Url = api_Url;
	}

	public String getEnvironment() {
		return environment;
	}

	public void setEnvironment(String environment) {
		this.environment = environment;
	}

	public String getMethod_Type() {
		return method_Type;
	}

	public void setMethod_Type(String method_Type) {
		this.method_Type = method_Type;
	}

	public String getRequest_File_Location() {
		return request_File_Location;
	}

	public void setRequest_File_Location(String request_File_Location) {
		this.request_File_Location = request_File_Location;
	}

	public String getOutput_File_Location() {
		return output_File_Location;
	}

	public void setOutput_File_Location(String output_File_Location) {
		this.output_File_Location = output_File_Location;
	}

	public JsonObject getToken() {
		return token;
	}

	public void setToken(JsonObject token) {
		this.token = token;
	}

	public JsonObject getHeaders() {
		return headers;
	}

	public void setHeaders(JsonObject headers) {
		this.headers = headers;
	}

	public JsonObject getUri_Param() {
		return uri_Param;
	}

	public void setUri_Param(JsonObject uri_Param) {
		this.uri_Param = uri_Param;
	}

	public JsonObject getInput_param() {
		return input_param;
	}

	public void setInput_param(JsonObject input_param) {
		this.input_param = input_param;
	}

	public JsonObject getPayload_param() {
		return payload_param;
	}

	public void setPayload_param(JsonObject payload_param) {
		this.payload_param = payload_param;
	}

	public String getResponseData() {
		return responseData;
	}

	public void setResponseData(String responseData) {
		this.responseData = responseData;
	}

	public int getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}

	public void readParamFromJson(String filename, String environment) {
		logger.info("Reading headers, input, payload parameters");
		try {
			JsonArray array = new JsonArray();
			JsonObject master = new JsonObject();
			JsonParser parser = new JsonParser();
			JsonObject jsonObject = new JsonObject();
			File file = new File(filename);
			System.out.println(filename + " is file");
			if (file.exists()) {
				master = (JsonObject) parser.parse(new FileReader(filename));
				jsonObject = master.get(environment).getAsJsonObject();
				setUri_Param(jsonObject.get("uri_param").getAsJsonObject());
				setHeaders(jsonObject.get("headers").getAsJsonObject());
				setInput_param(jsonObject.get("input_param").getAsJsonObject());
				setPayload_param(jsonObject.get("payload_param").getAsJsonObject());
			}
		} catch (FileNotFoundException e) {
			logger.warn("Unable to process file" + e.getMessage());

		} catch (Exception e) {
			logger.warn("Error in processing please check logs" + e.getMessage());
		}
	}

	public static String get_Token(String url, String file_Location, String environment) {
		JsonObject masterObject = new JsonObject();
		JsonParser parser = new JsonParser();
		JsonObject headers = new JsonObject();
		JsonObject input_Param = new JsonObject();
		JsonObject tokenJson = new JsonObject();
		JsonObject postParamJSON = new JsonObject();
		String keyName = "";
		String keyValue = "";
		String params = "";
		String finalUrl;
		String accessToken = "";
		String expiry = "";

		File tokenFile = new File(file_Location);
		try {
			if (tokenFile.exists()) {
				masterObject = (JsonObject) parser.parse(new FileReader(tokenFile));
				tokenJson = (JsonObject) masterObject.get(environment);
				input_Param = tokenJson.get("input_param").getAsJsonObject();
				headers = tokenJson.get("headers").getAsJsonObject();
				postParamJSON = tokenJson.get("payload_param").getAsJsonObject();
				if (input_Param.keySet().size() > 0) {
					for (Object key : input_Param.keySet()) {
						keyName = key.toString();
						keyValue = input_Param.get(keyName).toString().replace("\"", "").trim();
						if (keyValue.length() > 0) {
							params = params + keyName + "=" + keyValue + "&";
						}
					}
					params = params.substring(0, params.length() - 1);
					finalUrl = url + "?" + params;
					
					HttpPost post = new HttpPost(finalUrl);
					HttpClient client = HttpClientBuilder.create().build();
					if (!headers.isJsonNull()) {
						for (Object key : headers.keySet()) {
							keyName = key.toString();
							keyValue = headers.get(keyName).toString().replace("\"", "").trim();
							post.setHeader(keyName, keyValue);
							System.out.println(keyName +"--"+ keyValue);
						}
					}
					String jsonString = postParamJSON.toString();    		    
				    StringEntity entity = new StringEntity(jsonString);
				    post.setEntity(entity);
					HttpResponse response = client.execute(post);
					System.out.println();
					ResponseHandler<String> handler = new BasicResponseHandler();
					String token = handler.handleResponse(response).toString();
					logger.info("--------------"+token);
					accessToken = parser.parse(token).getAsJsonObject().get("access_token").toString();
					logger.info("-----------"+accessToken);
					expiry = parser.parse(token).getAsJsonObject().get("expires_in").toString();

				}

			}
		} catch (Exception e) {
			
			logger.warn("Unable to generate aunthentication token");
			logger.info(e.getMessage());
			e.printStackTrace();

		}
		return accessToken + "," + expiry;
	}

	public boolean validateResponse() {
		if (method_Type.equalsIgnoreCase("POST"))

		{
			try {
				if (restParser.isResponseSuccessful()) {
					setResponseCode(restParser.getResponseCode());
					setResponseData(restParser.getResponseBody());
					output = true;
				} else {
					setResponseCode(restParser.getResponseCode());
					setResponseData(restParser.getResponseBody());
					output = false;
				}
			}

			catch (Exception e) {
				logger.error("Unable to process");
			}
		} else if (method_Type.equalsIgnoreCase("GET")) {
			try {
				if (restParser.isResponseSuccessful()) {
					setResponseCode(restParser.getResponseCode());
					setResponseData(restParser.getResponseBody());
					output = true;
				} else {
					setResponseCode(restParser.getResponseCode());
					setResponseData(restParser.getResponseBody());
					output = false;
				}
			}

			catch (Exception e) {

			}
		}

		return output;
	}

}