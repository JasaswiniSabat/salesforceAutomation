package com.salesforce.test.website;

import static org.testng.Assert.assertTrue;

import java.util.Random;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.testng.annotations.Test;

import com.saleforce.utils.CommonUtils;
import com.salesforce.website.Pages.SFContactsPage;
import com.salesforce.website.Pages.SFHomePage;



public class CreateObjectTest extends SFWebsiteBaseTest {
	private String salutation = "Mr.";
	private String firstName = "First"+CommonUtils.getRandomNum(1, 100)+"Name";
	private String lastName = "Last"+CommonUtils.getRandomNum(1, 100)+"Name";
	private String phoneNumber = "(971) 880-"+CommonUtils.getRandomNum(1000, 9999);
	private String createdContactName = salutation+" "+firstName+" "+lastName;

	private SFHomePage sfHomePage;
	private SFContactsPage sFContactsPage;
	private static final Logger logger = LogManager
			.getLogger(CreateObjectTest.class.getName());

	@Test(priority=1)
	public void createAContactAndDeleteTest() throws Throwable {
		sfHomePage = new SFHomePage(driver);
		sFContactsPage = new SFContactsPage(driver);

		sfHomePage.loginUserToSalesforce();
		sfHomePage.switchToServiceAppFromHomePage();
		sfHomePage.clickOnContactsMenu();
		sFContactsPage.clickOnNewButton();
		assertTrue(sFContactsPage.isNewContactPopupDisplayed(), "New Contact Popup is not displayed");
		sFContactsPage.clickOnSaveButton();
		assertTrue(sFContactsPage.isErrorMessageDisplayedOnTop(), "Review Error on Page message is not displayed");
		assertTrue(sFContactsPage.isCompleteLastNameFieldMessageDisplayed("Complete this field"), "Complete Last Name Field Message is not displayed");
		sFContactsPage.createANewContact(salutation, firstName, lastName, phoneNumber);
		sFContactsPage.clickOnSaveButton();
		assertTrue(sFContactsPage.verifyActionMessage("created"), "Contact is not created");
		assertTrue(sFContactsPage.verifyCreatedContactDetails(createdContactName), "Contact Name Entered is not matching with the Saved Contact Name");
		assertTrue(sFContactsPage.verifySavedPhoneNumber(phoneNumber), "Phone Number Entered is not matching with Saved Phone Number");
		sFContactsPage.deleteCreatedContact();
		assertTrue(sFContactsPage.verifyDeleteContactPopupIsDisplayed(), "Delete Contact Popup is not displayed");
		sFContactsPage.clickDeleteButtonOnPopup();
		assertTrue(sFContactsPage.verifyActionMessage("deleted"), "Contact is not deleted");
	
	}
}
