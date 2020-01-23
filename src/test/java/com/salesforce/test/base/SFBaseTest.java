package com.salesforce.test.base;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import com.saleforce.utils.PropertyFile;



@Listeners({ com.salesforce.listners.TestListner.class })
public class SFBaseTest {
	public static WebDriver driver;
	public String defaultProps = "defaultenv.properties";

	protected PropertyFile propertyFile = new PropertyFile();
	private static final Logger logger = LogManager
			.getLogger(SFBaseTest.class.getName());

	/**
	 * @param envproperties
	 *            envproperties is the file that
	 *            contains the environment details that to be loaded
	 */
	
	@BeforeSuite(alwaysRun = true)
	@Parameters({"envproperties"})
	public void beforeSuite(@Optional String envproperties) {
		System.out.println("Started execution with " + " " + envproperties);
		logger.info("Started execution with " + " " + envproperties);
		if (!StringUtils.isEmpty(envproperties)) {
			propertyFile.loadProps(envproperties);
			logger.debug("Environment properties recieved and preparing the environment for "
					+ envproperties); 
			logger.info("EXECUTION ENVIRONMENT ------ "+propertyFile.getProperty("browser"));

		} else {
			propertyFile.loadProps(defaultProps);
			logger.info("Environment properties are not provided by the user ... loading the default properties");
			logger.info("Default Browser is  ------ "+propertyFile.getProperty("browser"));
			logger.info("Default URL is  ------ "+propertyFile.getProperty("baseUrl"));
			logger.info("Default username is  ------ "+propertyFile.getProperty("username"));
			logger.info("Default user password is  ------ "+propertyFile.getProperty("password"));
				
		}
		// clear screenshots folder
		try {
			FileUtils.cleanDirectory(new File(System.getProperty("user.dir")
					+ "\\output\\ScreenShots"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// clear Downloads folder
		try {
			FileUtils.cleanDirectory(new File(System.getProperty("user.dir")
					+ "\\Downloads"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@AfterSuite(alwaysRun=true)
	public void afterSuite(){
		//create a time stamp to be added to new logs,output and test-output folders
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss") ;
		String timeStamp = String.valueOf(dateFormat.format(date));

		// set the location of the source directories of logs,output and test-output folder
		String srcLogsDirectory = System.getProperty("user.dir")+"\\logs";
		String srcOutputDirectory = System.getProperty("user.dir")+"\\output";
		String srcTestOutputDirectory = System.getProperty("user.dir")+"\\test-output";

		// set the location of the destination directories of logs,output and test-output folder under buildHistory folder
		String destinationLogsDirectory = System.getProperty("user.dir")+"\\buildHistory\\logs\\logs-"+timeStamp;
		String destinationOutputDirectory = System.getProperty("user.dir")+"\\buildHistory\\output\\output-"+timeStamp;
		String destinationTestOutputDirectory = System.getProperty("user.dir")+"\\buildHistory\\test-output\\test-output-"+timeStamp;

		// create new folders for logs,output and test-output directories
		try {
			FileUtils.forceMkdir(new File(destinationLogsDirectory));
			FileUtils.forceMkdir(new File(destinationOutputDirectory));
			FileUtils.forceMkdir(new File(destinationTestOutputDirectory));
		} catch (IOException e) {
			e.printStackTrace();
		}

		// copy the latest data to logs,output and test-output directories
		try {
			FileUtils.copyDirectory(new File(srcLogsDirectory), new File(destinationLogsDirectory));
			FileUtils.copyDirectory(new File(srcOutputDirectory), new File(destinationOutputDirectory));
			FileUtils.copyDirectory(new File(srcTestOutputDirectory), new File(destinationTestOutputDirectory));

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
