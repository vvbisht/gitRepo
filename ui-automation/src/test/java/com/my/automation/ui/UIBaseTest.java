package com.my.automation.ui;

import org.testng.annotations.Test;

import com.my.automation.base.config.BaseDataConstants;
import com.my.automation.base.test.BaseTest;
import com.my.automation.baseutil.DataProvider;
import com.my.automation.config.UIDataConstants;
import com.my.automation.jsonhandlers.JsonProcessors;
import com.my.automation.pageobjects.SFLoginPage;
import com.my.automation.pageobjects.SelectJobRole;

import org.testng.annotations.BeforeClass;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;

public class UIBaseTest extends BaseTest {

	protected Map<String, String> testData = new HashMap<String, String>();
	protected DataProvider dataProvider = new DataProvider();
	JsonProcessors processor = new JsonProcessors();
	SFLoginPage loginPage;
	SelectJobRole jobRole;

	public void loadPageObjects(WebDriver driver) {
		loginPage = new SFLoginPage(driver);
		jobRole = new SelectJobRole(driver);
	}

	@BeforeClass
	public void beforeClass() {

		loadPageObjects(driver);

	}

	@AfterClass
	public void afterClass() {
		super.afterClass();
		driver.quit();
	}

	@BeforeSuite
	public void beforeSuite(ITestContext context) {

		System.out.println("1");

		String filePath = UIDataConstants.projectPath + UIDataConstants.testDataFile;

		context.setAttribute("testdata", filePath);
		processor.readJsonAsString(context.getAttribute("testdata").toString());
		dataProvider.getTestData(context.getAttribute("testdata").toString());

	}

}
