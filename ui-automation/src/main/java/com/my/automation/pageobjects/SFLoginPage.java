package com.my.automation.pageobjects;

import java.io.File;

import javax.swing.plaf.FileChooserUI;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.my.automation.config.UIDataConstants;

public class SFLoginPage extends UIBasePageObject {

	public SFLoginPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	private static Logger logger = Logger.getLogger(SFLoginPage.class);

	@FindBy(id = "username")
	WebElement userName;

	@FindBy(id = "password")
	WebElement password;

	@FindBy(id = "Login")
	WebElement loginButton;

	
	public void logintoSF(String user, String pass) {
		try {
			
			this.loadPage(UIDataConstants.appUrl);
			this.sendKeys(userName, user);
			File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(src, new File("C:\\selenium\\error.png"));
			this.sendKeys(password, pass);
			this.clickElement(loginButton);

		} catch (Exception e) {
			logger.info("Unable to process request" + e.getMessage());
		}

	}

}
