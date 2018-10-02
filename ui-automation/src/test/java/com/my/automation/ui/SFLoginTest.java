package com.my.automation.ui;

import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.my.automation.baseutil.DataProvider;
import com.my.automation.config.UIDataConstants;

import junit.framework.Assert;

import org.testng.annotations.BeforeClass;

import static org.testng.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeSuite;

public class SFLoginTest extends UIBaseTest {

	/*@BeforeSuite
	public void beforeSuite(ITestContext context) {
		super.beforeSuite(context);
	}*/

	@BeforeClass
	public void beforeClass(ITestContext context) {
		System.out.println("5");
	}

	@Test(testName = "Verify the login to SF")
	public void loginToSF(ITestContext context) throws Exception {
		Map<String, String> data = new HashMap<String, String>();
		DataProvider testData = new DataProvider();
		data = testData.getTestData(context.getAttribute("testdata").toString());
		loginPage.logintoSF(data.get("username"), data.get("password"));
		loginPage.isPageLoaded();
		Thread.sleep(10000);
		jobRole.selectRole();
	}

}
