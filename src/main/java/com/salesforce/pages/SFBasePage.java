package com.salesforce.pages;

import org.openqa.selenium.WebDriver;

/**
 * @author PranavPasricha SFBasePage is the super class for all the page
 *         classes
 */

public class SFBasePage {

	public WebDriver webdriver;

	public SFBasePage(WebDriver driver){
		webdriver = driver;
	}

	public WebDriver getWebdriver(){
		return webdriver;
	}
}
