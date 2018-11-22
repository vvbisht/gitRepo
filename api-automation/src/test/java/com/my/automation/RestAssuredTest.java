package com.my.automation;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class RestAssuredTest {

	public static void main(String[] args) {

		JsonObject master;
		JsonObject masterFile;
		JsonObject headers;
		JsonObject payload;
		JsonObject params;
		String url = "http://sovapp01.qa.rspc-ord.ea.com:8080/account/users/1000364103158/playernote";
		String keyName;
		String keyValue;
		String param;
		JsonParser parser = new JsonParser();

		try {
			masterFile = (JsonObject) parser.parse(new FileReader("D:\\Post_Player_Notes.json"));
			master = (JsonObject) masterFile.get("QAPC");
			headers = (JsonObject) master.get("headers");
			payload = (JsonObject) master.get("payload_param");
			RestAssured.baseURI = url;
			RequestSpecification request = RestAssured.given();
			if (headers.keySet().size() > 0) {
				for (Object key : headers.keySet()) {
					keyName = key.toString();
					keyValue = headers.get(keyName).toString().replaceAll("\"", "");
					request.header(keyName, keyValue);

				}

			}
			System.out.println(headers + "testing the headers");

			request.header("Authorization", "Bearer 4c0e893dac6345e37ffba0f516fb925b!23b5063e32858aa822a387925ae536a2");

			request.body(payload).toString();
			System.out.println(payload + "testing the payload");
			System.out.println(request.post() + "request is");
			Response response = request.post();
			System.out.println("Satus Code : " + response.getStatusCode());
			System.out.println("Status Line : " + response.getStatusLine());
			System.out.println("Body is : " + response.body().asString());

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
