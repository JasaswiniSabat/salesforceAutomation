package com.salesforce.website.Pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import com.salesforce.driver.website.SFWebsiteDriver;

public class SFContactsPage extends SFWebsiteBasePage{
	private static final Logger logger = LogManager
			.getLogger(SFHomePage.class.getName());

	public SFContactsPage(SFWebsiteDriver driver) {
		super(driver);
	}


	private static final By NEW_BUTTON = By.xpath("//div[contains(text(),'New')]");
	private static final By NEW_CONTACT_POPUP = By.xpath("//h2[contains(text(),'New Contact')]");
	private static final By SAVE_BUTTON  = By.xpath("(//span[contains(text(),'Save')])[last()]");
	private static final By ERROR_MESSAGE_HEADER = By.xpath("//span[contains(text(),'Review the errors on this page')]");
	private static final By LAST_NAME_ERROR = By.xpath("//span[contains(text(),'Last Name')]//following::li[1]");
	private static final By SALUTATION_DROPDOWN = By.xpath("//*[contains(text(),'Salutation')]//following::a[1]");
	private static final By FIRST_NAME_INPUT = By.xpath("//input[@placeholder='First Name']");
	private static final By LAST_NAME_INPUT = By.xpath("//input[@placeholder='Last Name']");
	private static final By PHONE_INPUT = By.xpath("(//span[contains(text(),'Phone')]//following::input[1])[2]");
	private static final By ACTION_MESSAGE = By.xpath("//span[contains(@class,'toastMessage slds-text-heading--small forceActionsText')]");
	private static final By CREATED_CONTACT_NAME = By.xpath("//span[contains(@class,'custom-truncate uiOutputText')]");
	private static final By SAVED_PHONE = By.xpath("//span[@class='uiOutputPhone']");
	private static final By ACTION_MENU = By.xpath("//a[@title='Show 5 more actions']");
	private static final By DELETE_BUTTON = By.xpath("//a[@title='Delete']");	
	private static final By DELETE_CONTACT_POPUP = By.xpath("//h2[contains(text(),'Delete Contact')]");
	private static final By DELETE_BTN_POPUP = By.xpath("//span[contains(text(),'Delete')]");



	public void clickOnNewButton() {
		driver.waitForElementPresent(NEW_BUTTON, 40);
		WebElement element = driver.findElement(NEW_BUTTON);
		Actions action = new Actions(SFWebsiteDriver.driver);
		action.moveToElement(element).build().perform();
		action.click(element).build().perform();
//		driver.click(NEW_BUTTON, "New Button");
	}

	public boolean isNewContactPopupDisplayed() {
		return driver.isElementPresent(NEW_CONTACT_POPUP, 60);
	}

	public void clickOnSaveButton() {
		driver.waitForElementPresent(SAVE_BUTTON);
		driver.click(SAVE_BUTTON, "Save Button");
	}

	public boolean isErrorMessageDisplayedOnTop() {
		return driver.isElementPresent(ERROR_MESSAGE_HEADER, 60);
	}

	public boolean isCompleteLastNameFieldMessageDisplayed(String message) {
		if(driver.findElement(LAST_NAME_ERROR).getText().contains(message)) {
			return true;
		}
		else {
			return false;
		}

	}


	public void createANewContact(String salutation, String firstName, String lastName, String phone) {
		driver.waitForElementPresent(SALUTATION_DROPDOWN, 60);
		driver.click(SALUTATION_DROPDOWN);
		driver.waitForElementPresent(By.xpath("//a[contains(text(),'"+salutation+"')]"));
		driver.click(By.xpath("//a[contains(text(),'"+salutation+"')]"));
		driver.type(FIRST_NAME_INPUT, firstName);
		driver.type(LAST_NAME_INPUT, lastName);
		driver.type(PHONE_INPUT, phone);

	}

	public boolean verifyActionMessage(String message) {
		if(driver.findElement(ACTION_MESSAGE).getText().contains(message)) {
			return true;
		}
		else {
			return false;
		}
	}

	public boolean verifyCreatedContactDetails(String contactName) {
		if(driver.findElement(CREATED_CONTACT_NAME).getText().contains(contactName)) {
			return true;
		}
		else {
			return false;
		}
	}

	public boolean verifySavedPhoneNumber(String phone) {
		if(driver.findElement(SAVED_PHONE).getText().contains(phone)) {
			return true;
		}
		else {
			return false;
		}
	}

	public void deleteCreatedContact() {
		driver.waitForElementPresent(ACTION_MENU);
		driver.click(ACTION_MENU, "Action Menu");
		driver.waitForElementToBeClickable(DELETE_BUTTON, 120);
		driver.click(DELETE_BUTTON, "Delete Button");
	}
	
	public boolean verifyDeleteContactPopupIsDisplayed() {
		return driver.isElementPresent(DELETE_CONTACT_POPUP, 60);
	}
	
	public void clickDeleteButtonOnPopup() {
		driver.waitForElementPresent(DELETE_BTN_POPUP, 60);
		driver.click(DELETE_BTN_POPUP);
	}
}
