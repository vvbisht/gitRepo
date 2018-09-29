package com.my.automation;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import org.testng.internal.annotations.ITest;

import com.my.automation.base.test.BaseAPITest;

import static org.testng.Assert.assertEquals;

import org.testng.ITestContext;
import org.testng.annotations.BeforeMethod;

public class Get_Users extends APIBaseTest {
  @Test
  public void Get_Users_Test() {
	  base.validateResponse();
	  SoftAssert softAssert = new SoftAssert();
	  softAssert.assertEquals(base.getResponseCode(), 200);
	  System.out.println(base.getResponseCode());
	  System.out.println(base.getResponseData());
	  softAssert.assertAll();
	  
  }
  @BeforeMethod
  public void beforeMethod(ITestContext context) {
	  System.out.println("4......");
	  
	  context.setAttribute("API_Key", "POST_PLAYER_NOTES_API");
		super.beforeMethod(context);
  }

}
