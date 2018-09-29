package com.my.automation.pageobjects;

import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SelectJobRole extends UIBasePageObject {

	public SelectJobRole(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath="//*[@class='slds-form-element']//span//input[@type='radio']/following-sibling::label//span[contains(text(),'Tos Tier1')]")
	WebElement jobRole;
	
	public void selectRole()
	{
		try
		{
		this.clickElement(jobRole);
		}
		catch(ElementNotVisibleException e)
		{
			e.printStackTrace();
		}
	}
	

}
