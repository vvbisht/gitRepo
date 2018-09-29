package com.my.automation.base.test;

import com.beust.jcommander.Parameter;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.my.automation.base.api.APIBase;
import com.my.automation.base.config.BaseDataConstants;
import com.my.automation.jsonhandlers.JsonProcessors;

import javax.naming.Context;

import org.testng.ITestContext;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

public class BaseAPITest {

	JsonObject configJson = new JsonObject();
	JsonProcessors processor = new JsonProcessors();
	protected APIBase base = new APIBase();
	protected static String environment;

	@BeforeSuite
	public void beforeSuite(ITestContext context) {
		System.setProperty("Home", BaseDataConstants.projectRootPath);
		System.out.println("2......");
		JsonProcessors jsonProcessor = new JsonProcessors();
		jsonProcessor.readJsonAsString(context.getAttribute("API_Master_Config").toString());
		context.setAttribute("Master_Config", jsonProcessor.getFileOutput());

	}

	@BeforeTest
	@Parameters({"testEnvironment", "testType"})
	public void beforeTest(@Optional("QAPC") String testEnvironment, @Optional("regression")String testType, ITestContext context) 
	{
		// 
		System.out.println("3......");
		BaseAPITest.environment = testEnvironment;
		context.setAttribute("environment", BaseAPITest.environment);
		context.setAttribute("testType", testType);

	}

	@BeforeMethod
	public void beforeMethod(ITestContext context) {
		System.out.println("6...........");
		System.out.println("this is test for jenkins chaness");
		JsonObject apiJson = new JsonObject();
		String api_Key;
		String url;
		String call_Type;
		String payload_Name;
		JsonObject masterConfig = new JsonObject();
		JsonParser parser = new JsonParser();
		String token = context.getAttribute("authtoken").toString();
		api_Key = context.getAttribute("API_Key").toString();
		masterConfig = (JsonObject) parser.parse(context.getAttribute("Master_Config").toString());
		apiJson = (JsonObject) masterConfig.get(api_Key);
		url = context.getAttribute("Base_Url") + apiJson.get("url").toString().replace("\"", "").trim();
		call_Type = apiJson.get("call_type").toString().replace("\"", "").trim();
		payload_Name = context.getAttribute("INPUT_API_PAYLOAD").toString()
				+ apiJson.get("payload_name").toString().replace("\"", "").trim();
		base = new APIBase(url, this.environment, call_Type, payload_Name, token);

	}
}
