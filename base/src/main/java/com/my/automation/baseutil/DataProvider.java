package com.my.automation.baseutil;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FilterReader;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.my.automation.jsonhandlers.JsonProcessors;

public class DataProvider {
	JsonProcessors processor = new JsonProcessors();
	String output = "";
	JsonParser parser = new JsonParser();
	Map<String, String> testData = new HashMap<String, String>();
	JsonObject jsonObj;
	JsonObject data;

	public Map<String, String> getTestData(String filePath) {
		processor.readJsonAsString(filePath);
		output = processor.getFileOutput();
		jsonObj = (JsonObject) parser.parse(output);
		data=jsonObj.get("data").getAsJsonObject();
		for (Object key : data.keySet()) {
			String keyName = key.toString().replace("\"", "").trim();
			String keyValue = data.get(keyName).toString().replace("\"", "").trim();
			testData.put(keyName, keyValue);
		}

		return testData;

	}

}
