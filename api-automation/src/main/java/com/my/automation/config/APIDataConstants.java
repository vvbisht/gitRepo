package com.my.automation.config;

/**
 * Hello world!
 *
 */
public class APIDataConstants 
{
   public static final String Input_File = "\\src\\test\\resources\\apiPayLoads\\";
   
   public static final String API_MASTER_CONFIG_FILE_PATH = "\\src\\test\\resources\\config\\api_config.json";
   
   public static final String API_Base_URL_QAPC = "http://sovapp01.qa.rspc-ord.ea.com:8080";
   
   public static final String API_Base_URL_UAT = "http://sovapp01.uat.rspc-ord.ea.com:8100";
   
   public static final String Token_Url = "/oauth/token";
   
   public static final String Token_File_Path = "\\src\\test\\resources\\apiPayLoads\\Token.json";
   
   public static int Token_Expiry_Window;
   
   public static int Token_Expiry_Time;
}
