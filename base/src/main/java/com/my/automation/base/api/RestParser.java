package com.my.automation.base.api;

import java.net.MalformedURLException;
import java.net.URL;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.testng.log4testng.Logger;

import com.google.gson.JsonObject;

public class RestParser {

	Logger logger = Logger.getLogger(RestParser.class);
	JsonObject headers;
	JsonObject payload;
	String endPointUrl;
	String fileLocation;
	String callType;
	HttpGet httpGet;
	HttpPost httpPost;
	String responseBody;
	HttpResponse response;
	URL url = null;
	String token;
	int responseCode;
	static String POST_CONTENT_TYPE = "application/json";

	public JsonObject getHeaders() {
		return headers;
	}

	public void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}

	public void setHeaders(JsonObject headers) {
		this.headers = headers;
	}

	public String getUrl() {
		return endPointUrl;
	}

	public void setUrl(String endPointUrl) {
		this.endPointUrl = endPointUrl;
	}

	public String getFileLocation() {
		return fileLocation;
	}

	public void setFileLocation(String fileLocation) {
		this.fileLocation = fileLocation;
	}

	public String getCallType() {
		return callType;
	}

	public void setCallType(String callType) {
		this.callType = callType;
	}

	public HttpGet getHttpGet() {
		return httpGet;
	}

	public void setHttpGet(HttpGet httpGet) {
		this.httpGet = httpGet;
	}

	public HttpPost getHttpPost() {
		return httpPost;
	}

	public void setHttpPost(HttpPost httpPost) {
		this.httpPost = httpPost;
	}

	public String getResponseBody() {
		return responseBody;
	}

	public void setResponseBody(String responseBody) {
		this.responseBody = responseBody;
	}

	public RestParser(JsonObject headers, String url, String fileLocation, String callType, String token, JsonObject payload) {
		this.headers = headers;
		this.endPointUrl = url;
		this.fileLocation = fileLocation;
		this.callType = callType;
		this.token = token;
		this.payload = payload;
		if (callType.equalsIgnoreCase("POST")) {
			this.httpPost = getHTTPPostObj(endPointUrl, headers);
		} else if (callType.equalsIgnoreCase("GET")) {
			this.httpGet = getHTTPGetObj(endPointUrl, headers);
		}
	}

	private HttpGet getHTTPGetObj(String endPointUrl, JsonObject headers) {
		HttpGet httpGetObj = null;
		String keyName = "";
		String keyValue = "";
		try {
			httpGetObj = new HttpGet(this.endPointUrl);
			if (!headers.isJsonNull()) {
				for (Object key : headers.keySet()) {
					keyName = key.toString().replace("\"", "").trim();
					if (keyName.equalsIgnoreCase("Authorization")) {
						keyValue = "Bearer " + this.token.toString().replace("\"", "").trim();
					}
					else
					keyValue = headers.get(keyName).toString().replace("\"", "");
					httpGetObj.setHeader(keyName, keyValue);
				}
			} else
				httpGetObj.setHeader("Content-Type", this.POST_CONTENT_TYPE);

		} catch (Exception e) {

		}

		return httpGetObj;
	}

	public RestParser() {
		super();
	}

	public HttpPost getHTTPPostObj(String urlValue, JsonObject headers) {
		HttpPost httpObj = null;
		String keyName = "";
		String keyValue = "";

		try {
			httpObj = new HttpPost(this.endPointUrl);
			if (!headers.isJsonNull()) {
				for (Object key : headers.keySet()) {
					keyName = key.toString().replace("\"", "");
					if (keyName.equalsIgnoreCase("Authorization")) {
						keyValue = "Bearer " + this.token.toString().replace("\"", "").trim();
					} else

						keyValue = headers.get(keyName).toString().replace("\"", "").trim();
					System.out.println("headers-------");
					System.out.println(keyName + ":" + keyValue);
					httpObj.setHeader(keyName, keyValue);
				}
			} else
				httpObj.setHeader("Content-Type", this.POST_CONTENT_TYPE);
		} catch (Exception e) {
			logger.error("Failed to create HTTP request" + e.getMessage());
		}
		return httpObj;
	}

	public int getResponseCode() {
		try {
			ResponseHandler<String> handler = new BasicResponseHandler();
			HttpClient client = HttpClientBuilder.create().build();
			if (callType.equalsIgnoreCase("POST")) {
				HttpPost post = this.httpPost;
				String payload = this.payload.toString();
				StringEntity entity = new StringEntity(payload);
				post.setEntity(entity);
				response = client.execute(post);
				responseCode = response.getStatusLine().getStatusCode();
				setResponseBody(handler.handleResponse(response).toString());
				setResponseCode(responseCode);
			} else if (callType.equalsIgnoreCase("GET")) {

				HttpGet get = this.httpGet;
				response = client.execute(get);
				responseCode = response.getStatusLine().getStatusCode();
				setResponseBody(handler.handleResponse(response).toString());
				setResponseCode(responseCode);
			}
		}

		catch (Exception e) {

		}
		return responseCode;
	}

	public boolean isResponseSuccessful() {
		if (getResponseCode() == 200) {
			return true;
		} else {
			return false;
		}
	}

}
