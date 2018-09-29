package com.my.automation;

import org.apache.log4j.Logger;
import org.testng.ITestContext;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.my.automation.base.api.APIBase;

public class Post_Player_NotesTest extends APIBaseTest {
	public static Logger logger = Logger.getLogger(Post_Player_NotesTest.class);

	@BeforeMethod
	public void beforeMethod(ITestContext context)
	{
		System.out.println("4.....");
		context.setAttribute("API_Key", "POST_PLAYER_NOTES_API");
		super.beforeMethod(context);
	}
			
	@Test
	public void verifyPlayerNotes() {
		base.validateResponse();
		System.out.println(base.getResponseCode()+ "code");
		System.out.println(base.getResponseData()+ "data");
		
	}
}
