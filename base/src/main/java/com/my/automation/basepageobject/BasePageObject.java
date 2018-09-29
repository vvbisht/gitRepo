package com.my.automation.basepageobject;

import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BasePageObject {

	protected WebDriver driver;

	private static Logger logger = Logger.getLogger(BasePageObject.class);

	public BasePageObject(WebDriver driver) {
		this.driver = driver;

		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
	}

	public void loadPage(String url) {
		try {
			driver.navigate().to(url);
		} catch (WebDriverException e) {
			logger.warn("Unable to load page " + e.getMessage());
		}
	}

	public void clickElement(WebElement element) {
		try {
			element.click();
		} catch (NoSuchElementException e) {
			logger.info("Unable to find Element" + e.getMessage());
		} catch (Exception e) {
			logger.info("Unable to click element" + e.getMessage());
		}
	}

	public void sendKeys(WebElement element, String text) {
		try {
			element.sendKeys(text);
		} catch (NoSuchElementException e) {
			logger.info("Unable to find element" + e.getMessage());
		} catch (Exception e) {
			logger.info("Element not found" + e.getMessage());
		}
	}

	public void isPageLoaded() {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		int maxTime = 180;
		String state;
		try {
			if (driver instanceof JavascriptExecutor) {
				state = js.executeScript("return document.readyState").toString();
				for (int i = 0; i < maxTime; i++) {
					if (!state.equals("loaded") || state.equals("complete")) {
						continue;
					} else
						break;
				}

			}
		} catch (WebDriverException e) {
			logger.info("Failed to load page");

		}
	}

	public void selectOption(WebElement element, String selectBy) {
		Select select = new Select(element);
		if (selectBy.equals("index")) {
			select.selectByIndex(Integer.parseInt(selectBy));
		} else if (selectBy.equals("name")) {
			select.selectByVisibleText(selectBy);
		} else if (selectBy.equals("value")) {
			select.selectByValue(selectBy);
		}

	}
	
	public boolean isElementVisible(WebElement element, int time)
	{
		Boolean visible = false;
		try
		{
			WebDriverWait wait = new WebDriverWait(driver, time);
			wait.until(ExpectedConditions.presenceOfElementLocated((By) element));
			visible=true;
		}
		catch(ElementNotVisibleException e)
		{
			visible = false;
		}
		return visible;
	}
}
