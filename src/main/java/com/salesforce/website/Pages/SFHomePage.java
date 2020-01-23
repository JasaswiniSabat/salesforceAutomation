package com.salesforce.website.Pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;


import com.salesforce.driver.website.SFWebsiteDriver;


public class SFHomePage extends SFWebsiteBasePage {
	private static final Logger logger = LogManager
			.getLogger(SFHomePage.class.getName());
	

	private static final By USERNAME_TXTFLD_LOC = By.id("username");
	private static final By PASSWORD_TXTFLD_LOC = By.id("password");
	private static final By LOGIN_BTN_LOC = By.id("Login");
	private static final By MENU_ICON = By.xpath("//div[@class='slds-icon-waffle']");
	private static final By APP_LAUNCHER_POPUP = By.xpath("//input[@class='slds-input input']");
	private static final By SERVICE_OPTION = By.xpath("(//a[contains(text(),'Service')])[1]");
	private static final By CONTACT_DROP_DOWN = By.xpath("//a[@title='Contacts']/span");
	private static final By NEW_CONTACT_BUTTON = By.xpath("//span[contains(text(),'New Contact')]");
	String userName = propertyFile.getProperty("userName");
	String password = propertyFile.getProperty("password");
	
	public SFHomePage(SFWebsiteDriver driver) {
		super(driver);
	}

	public void loginUserToSalesforce() {
		driver.waitForElementPresent(USERNAME_TXTFLD_LOC);
		driver.type(USERNAME_TXTFLD_LOC, userName);
		driver.type(PASSWORD_TXTFLD_LOC, password);
		logger.info("login username is: " + userName);
		logger.info("login password is: " + password);
		driver.click(LOGIN_BTN_LOC);
		logger.info("login button clicked");
		
	}
	
	public void switchToServiceAppFromHomePage() {
			driver.click(MENU_ICON, "Menu Icon");
			driver.waitForElementToBeVisible(APP_LAUNCHER_POPUP, 30);
			driver.click(SERVICE_OPTION, "Service Option");			
			driver.waitForPageLoad();		
		}
	
	public void clickOnContactsMenu() {
		driver.pauseExecutionFor(6000);
		driver.waitForElementToBeClickable(CONTACT_DROP_DOWN);
		driver.waitForElementPresent(CONTACT_DROP_DOWN, 30);
		driver.click(CONTACT_DROP_DOWN,"Contact Drop Down");
	}
	
	
	
}
