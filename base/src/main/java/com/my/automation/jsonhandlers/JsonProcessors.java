package com.my.automation.jsonhandlers;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.testng.log4testng.Logger;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class JsonProcessors {
	
	String fileOutput ="";
	
	public String getFileOutput() {
		return fileOutput;
	}

	public void setFileOutput(String fileOutput) {
		this.fileOutput = fileOutput;
	}

	public JsonProcessors(String fileOutput) {
		this.fileOutput = fileOutput;
	}

	public JsonProcessors() {
		
	}

	Logger logger = Logger.getLogger(JsonProcessors.class);
	
	public void readJsonAsString(String filePath)
	{
		StringBuilder builder = new StringBuilder();
		try
		{
			BufferedReader reader = new BufferedReader(new FileReader(filePath));
			String line = reader.readLine();
			while(line !=null)
			{
				builder.append(line);
				line=reader.readLine();
			}
			
			reader.close();
			setFileOutput(builder.toString());
		}
		catch(FileNotFoundException e)
		{
			
		} catch (IOException e) {
			logger.error("Unable to process file");
		}
	}
	
	 public JsonObject getContentAsJsonObject(String jsonContent) {
		    JsonObject result = new JsonObject();
		    JsonParser parser = new JsonParser();
		    if (!(parser.parse(jsonContent) instanceof JsonArray))
		      result = parser.parse(jsonContent).getAsJsonObject();
		    return result;
		  }

}
