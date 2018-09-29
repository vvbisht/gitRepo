package com.my.automation.ui;

import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.my.automation.baseutil.DataProvider;
import com.my.automation.config.UIDataConstants;

import org.testng.annotations.BeforeClass;

import java.util.HashMap;
import java.util.Map;

import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeSuite;

public class SelectJobRole extends UIBaseTest {

	@BeforeSuite
	public void beforeSuite(ITestContext context) {
		super.beforeSuite(context);
	}

	@BeforeClass
	public void beforeClass(ITestContext context) {
		super.beforeClass();
	}

	@Test(testName = "Verify the login to SF")
	public void selectJobRole(ITestContext context) throws Exception {

		Map<String, String> data = new HashMap<String, String>();
		DataProvider testData = new DataProvider();
		data = testData.getTestData(context.getAttribute("testdata").toString());
		loginPage.logintoSF(data.get("username"), data.get("password"));
		jobRole.selectRole();

	}

	@AfterClass
	public void afterClass() {
		super.afterClass();
	}

}
