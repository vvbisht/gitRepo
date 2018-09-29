package com.my.automation.baseutil;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.my.automation.base.config.BaseDataConstants;

public class Driver {

	WebDriver driver;

	/*public Driver() {
		driver.manage().deleteAllCookies();
		driver.manage().window().maximize();
	}
*/
	public WebDriver getDriver(String browser) {
		if (browser.equalsIgnoreCase("Chrome")) {
			String path = "D:\\My_Framework_Api\\test-suite";
			System.setProperty("webdriver.chrome.driver",path+ BaseDataConstants.chromeDriverPath);
			driver = new ChromeDriver();
			driver.manage().deleteAllCookies();
			driver.manage().window().maximize();
			System.out.println("driver is chrome here.");
		}

		else if (browser.equalsIgnoreCase("IE")) {
			System.setProperty("webdriver.ie.driver", BaseDataConstants.ieDriverPath);
			driver = new InternetExplorerDriver();
		}

		else if (browser.equalsIgnoreCase("FireFox")) {
			System.setProperty("webdriver.gecko.driver", BaseDataConstants.firefoxDriverPath);
			driver = new FirefoxDriver();
		}
		return driver;
	}

}
