package com.my.automation;

import java.util.Date;

import org.apache.log4j.Logger;
import org.testng.ITestContext;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import com.my.automation.base.api.APIBase;
import com.my.automation.base.test.BaseAPITest;
import com.my.automation.config.APIDataConstants;

public class APIBaseTest extends BaseAPITest{
	//private static Logger logger = Logger.getLogger(APIBaseTest.class);
	Logger log = Logger.getLogger(this.getClass());
  @BeforeMethod
  public void beforeMethod(ITestContext context) {
	  
	  System.out.println("5...................");
	  /*logger.debug("testing the debug message");
	  logger.info("testing the info message");*/
	  String test_Environment = context.getAttribute("environment").toString();
	  if(test_Environment.equalsIgnoreCase("QAPC"))
	  {
		  context.setAttribute("Base_Url", APIDataConstants.API_Base_URL_QAPC);
	  }
	  else if(test_Environment.equalsIgnoreCase("UATPC"))
	  {
		  context.setAttribute("Base_Url", APIDataConstants.API_Base_URL_UAT);
	  }
	  String token_Location = context.getAttribute("Token_File_Path").toString();
	  String tokenUrl = context.getAttribute("Base_Url")+APIDataConstants.Token_Url;
	  int currentTime = (int) (new Date().getTime()/1000);
	  if(APIDataConstants.Token_Expiry_Window==0||currentTime>APIDataConstants.Token_Expiry_Time)
	  {
	  String authTokenDetails=APIBase.get_Token(tokenUrl, token_Location, test_Environment);
	  log.info("-----------"+ authTokenDetails);
	  context.setAttribute("authtoken", authTokenDetails.split(",")[0]);
	  context.setAttribute("expiryWindow", Integer.parseInt(authTokenDetails.split(",")[1]));
	  APIDataConstants.Token_Expiry_Window = Integer.parseInt(context.getAttribute("expiryWindow").toString());
	  System.out.println(APIDataConstants.Token_Expiry_Window + "token expiry window");
	  APIDataConstants.Token_Expiry_Time = Integer.parseInt(context.getAttribute("SET_EXECUTION_START_TIME").toString())+
			  APIDataConstants.Token_Expiry_Window;
	  System.out.println(currentTime + "testing currentime");
	  System.out.println(APIDataConstants.Token_Expiry_Time + "token expiry time");
	  }
	  else
	  {
		  System.out.println("Existing token used");
		  log.info("Using the existing token here.");
	  }
	  super.beforeMethod(context);
  }

  @BeforeSuite
  public void beforeSuite(ITestContext context) {
	  System.out.println("1..");
	  //logger.info("Setting up Master Config and Input Payload Files");
	  context.setAttribute("API_Master_Config", System.getProperty("user.dir")+APIDataConstants.API_MASTER_CONFIG_FILE_PATH);
	  context.setAttribute("INPUT_API_PAYLOAD", System.getProperty("user.dir")+APIDataConstants.Input_File);
	  context.setAttribute("Token_File_Path", System.getProperty("user.dir")+APIDataConstants.Token_File_Path);
	  context.setAttribute("SET_EXECUTION_START_TIME", (int)(new Date().getTime()/1000));
	  super.beforeSuite(context);
  }

}
