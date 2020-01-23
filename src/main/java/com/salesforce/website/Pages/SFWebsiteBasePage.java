package com.salesforce.website.Pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.saleforce.utils.PropertyFile;
import com.salesforce.driver.website.SFWebsiteDriver;
import com.salesforce.pages.SFBasePage;


//This Class will contain all the common methods of all the Pages

public class SFWebsiteBasePage extends SFBasePage{
	
	private static final Logger logger = LogManager
			.getLogger(SFWebsiteBasePage.class.getName());
	

	protected SFWebsiteDriver driver;
	public SFWebsiteBasePage(SFWebsiteDriver driver){		
		super(driver);
		this.driver = driver;
	}

	protected PropertyFile propertyFile = new PropertyFile();

}
