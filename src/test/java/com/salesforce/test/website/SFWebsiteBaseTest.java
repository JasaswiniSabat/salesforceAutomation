package com.salesforce.test.website;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.salesforce.driver.website.SFWebsiteDriver;
import com.salesforce.test.base.SFBaseTest;
import com.salesforce.website.Pages.SFHomePage;


public class SFWebsiteBaseTest extends SFBaseTest{
	
	private SFHomePage sfHomePage;
	
	protected SFWebsiteDriver driver = new SFWebsiteDriver(propertyFile);
	private static final Logger logger = LogManager
			.getLogger(SFWebsiteDriver.class.getName());

	/**
	 * @throws Exception
	 *             setup function loads the driver and environment and baseUrl
	 *             for the tests
	 */
	
	@BeforeMethod(alwaysRun=true)
	public void setUp() throws Exception {
		driver.loadApplication();           
		driver.getURL();
		
		logger.info("Application loaded"); 
	}
	
	@AfterMethod
	public void tearDownSession() {
		driver.quit();
	}
}
