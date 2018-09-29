package com.my.automation.base.test;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.internal.annotations.ITest;

import com.my.automation.baseutil.Driver;

public class BaseTest {

	protected WebDriver driver;
	Driver driverInstance = new Driver();

	@BeforeSuite
	public void beforeSuite(ITestContext context) {
		System.out.println("this is test for main base");
	}

	@BeforeClass
	@Parameters({"browser"})
	public void beforeClass(@Optional("Chrome")String browserName) {
		System.out.println("3");
		driver = driverInstance.getDriver(browserName);

	}

	@AfterClass
	public void afterClass() {
		driver.close();
	}

}
