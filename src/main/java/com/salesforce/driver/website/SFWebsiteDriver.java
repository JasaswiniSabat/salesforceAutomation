package com.salesforce.driver.website;

import java.io.File;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.saleforce.utils.PropertyFile;
import com.salesforce.driver.SFDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SFWebsiteDriver implements SFDriver, WebDriver {

	public static WebDriver driver; 
	private PropertyFile propertyFile;
	private static int DEFAULT_TIMEOUT = 60;

	public SFWebsiteDriver(PropertyFile propertyFile) {
		this.propertyFile = propertyFile;			
	}

	private static final Logger logger = LogManager
			.getLogger(SFWebsiteDriver.class.getName());


	public void loadApplication() throws MalformedURLException {

		if (propertyFile.getProperty("browser").equalsIgnoreCase("firefox"))
		{

			System.setProperty("webdriver.gecko.driver","E:\\Softwares\\geckodriver.exe");
			ProfilesIni profile2 = new ProfilesIni();
			FirefoxProfile profile3 = profile2.getProfile("AutoProfile");
			profile3.setPreference("browser.popups.showPopupBlocker", false);

			FirefoxOptions firefoxOptions = new FirefoxOptions();
			firefoxOptions.setProfile(profile3);

			driver = new FirefoxDriver(firefoxOptions);

		}
		else if (propertyFile.getProperty("browser").equalsIgnoreCase("chrome")){
			System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");

			String downloadFilepath = System.getProperty("user.dir") + "\\Downloads";


			HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
			chromePrefs.put("profile.default_content_settings.popups", 0);
			chromePrefs.put("download.default_directory", downloadFilepath);
			chromePrefs.put("pdfjs.disabled", true);
			ChromeOptions options = new ChromeOptions();
			HashMap<String, Object> chromeOptionsMap = new HashMap<String, Object>();
			options.setExperimentalOption("prefs", chromePrefs);
			options.addArguments("--test-type");
			options.addArguments("--disable-extensions"); //to disable browser extension popup

			DesiredCapabilities cap = DesiredCapabilities.chrome();
			cap.setCapability(ChromeOptions.CAPABILITY, chromeOptionsMap);
			cap.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
			cap.setCapability(ChromeOptions.CAPABILITY, options);
			driver = new ChromeDriver(cap);  			
		}

		else if(propertyFile.getProperty("browser").equalsIgnoreCase("ie")){
			String driverPath = System.getProperty("user.dir") + "\\src\\test\\resources\\";
			//static String driverPath = "IE driver path";
			System.setProperty("webdriver.ie.driver", "IEDriverServer.exe");
			System.out.println("*******************");
			System.out.println("launching IE browser");
			System.out.println("*******************");
			System.setProperty("webdriver.ie.driver", driverPath+"IEDriverServer.exe");

			DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
			capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
			capabilities.setCapability("requireWindowFocus", true);
			driver = new InternetExplorerDriver(capabilities);
		}

		driver.manage().timeouts().implicitlyWait(35, TimeUnit.SECONDS);
		//Disabling the chrome-is-being-controlled-by-automated-test-software infobar
		ChromeOptions options = new ChromeOptions(); 
		options.addArguments("disable-infobars"); 

		driver.manage().window().maximize();
		logger.info("Window is maximized");
		// for clearing cookies
		driver.manage().deleteAllCookies();
		logger.info("All cookies deleted");
		//	driver.get(propertyFile.getProperty("baseUrl"));

	}

	public void getURL() {
		driver.get(propertyFile.getProperty("baseUrl"));
	}

	public void waitForElementPresent(By locator, int timeout) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, timeout);
			logger.info("waiting for locator " + locator);
			wait.until(ExpectedConditions.presenceOfElementLocated(locator));
			logger.info("Element found");
		} catch (Exception e) {
			e.getStackTrace();
		}	
	}

	public boolean waitForElementToBeClickable(By locator, int timeOut) {
		//		scrollUpIntoView(locator);
		WebDriverWait wait = new WebDriverWait(SFWebsiteDriver.driver, timeOut);
		try {
			WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
			return true;
		} catch (Exception e) {
			logger.info("Timeout..element not clickable");
			return false;
		}
	}

	public void clickByJS(By locator, String message) {
		waitForElementPresent(locator, 20);
		JavascriptExecutor executor = (JavascriptExecutor) (SFWebsiteDriver.driver);
		WebElement e = SFWebsiteDriver.driver.findElement(locator);
		executor.executeScript("arguments[0].click();",e) ;
		logger.info("CLICKED by JS on " + message);
		System.out.println("CLICKED by JS on " + message);
	}

	public void click(By locator, String elementToBeClicked) {	
		try {
			waitForElementToBeClickable(locator, 25);
			findElement(locator).click();	
			logger.info("CLICKED on "+elementToBeClicked);
		}
		catch(Exception e){
			logger.info("Exception came while clicking "+elementToBeClicked+" trying through click By JS");
			clickByJS(locator, elementToBeClicked);
		}	
	}



	public void waitForElementToBeClickable(By locator) {
		WebDriverWait wait = new WebDriverWait(SFWebsiteDriver.driver, 20);
		wait.until(ExpectedConditions.elementToBeClickable(locator));
	}

	/**
	 * ThreadLocal variable which contains the  {@link WebDriver} instance which is used to perform browser interactions with.
	 */
	private static ThreadLocal<WebDriver> webDriver = new ThreadLocal<WebDriver>();

	public boolean isElementPresent(By locator,int time) {
		waitForElementPresent(locator,time);
		try{
			if (driver.findElements(locator).size() > 0) {
				return true;
			} else
				return false;
		}
		catch(Exception e){
			return false;
		}

		finally{
			turnOnImplicitWaits();
		}
	}

	public void pauseExecutionFor(long lTimeInMilliSeconds) {
		try {
			Thread.sleep(lTimeInMilliSeconds);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public boolean waitForPageLoad() {
		boolean isLoaded = false;
		try {
			logger.info("Waiting For Page load via JS");
			ExpectedCondition<Boolean> pageLoadCondition = new ExpectedCondition<Boolean>() {
				public Boolean apply(WebDriver driver) {
					return ((JavascriptExecutor) driver).executeScript(
							"return document.readyState").equals("complete");
				}
			};
			WebDriverWait wait = new WebDriverWait(driver, DEFAULT_TIMEOUT);
			wait.until(pageLoadCondition);
			isLoaded = true;
		} catch (Exception e) {
			logger.error("Error Occured waiting for Page Load "
					+ driver.getCurrentUrl());
		}
		return isLoaded;
	}

	public void click(By locator) {		
		waitForElementPresent(locator);
		quickWaitForElementPresent(locator);
		try{
			findElement(locator).click();			
		}catch(Exception e){
			retryingFindClick(locator);
		}		
	}

	public boolean retryingFindClick(By by) {
		boolean result = false;
		int attempts = 0;
		while (attempts < 5) {
			try {
				driver.findElement(by).click();
				result = true;
				break;
			} catch (StaleElementReferenceException e) {
			}
			attempts++;
			pauseExecutionFor(1000);
		}
		return result;
	}

	public void clickByJS(WebDriver driver, WebElement element) {
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].click();", element);
	}

	public void waitForElementPresent(By locator) {

		logger.info("wait started for "+locator);
		int timeout = 10;
		turnOffImplicitWaits();
		boolean isElementFound = false;
		for(int i=1;i<=timeout;i++){		
			try{
				if(driver.findElements(locator).size()==0){
					pauseExecutionFor(1000);
					logger.info("waiting...");
					continue;
				}else{
					logger.info("wait over,element found");
					isElementFound =true;
					turnOnImplicitWaits();
					pauseExecutionFor(1000);
					break;
				}			
			}catch(Exception e){
				continue;
			}
		}
		if(isElementFound ==false)
			logger.info("ELEMENT NOT FOUND");		
	}

	public static String takeSnapShotAndRetPath(WebDriver driver,String methodName) throws Exception {

		String FullSnapShotFilePath = "";

		try {			
			File scrFile = ((TakesScreenshot) driver)
					.getScreenshotAs(OutputType.FILE);
			String sFilename = null;
			sFilename = "Screenshot-" +methodName+getDateTime() + ".png";
			System.out.println("Screenshotfile name is "+sFilename);
			FullSnapShotFilePath = System.getProperty("user.dir")
					+ "\\output\\ScreenShots\\" + sFilename;
			System.out.println("FullSnapShotFilePath name is "+FullSnapShotFilePath);
			FileUtils.copyFile(scrFile, new File(FullSnapShotFilePath));
		} catch (Exception e) {

		}

		return FullSnapShotFilePath;
	}

	public static String getDateTime() {
		String sDateTime = "";
		try {
			SimpleDateFormat sdfDate = new SimpleDateFormat("dd-MM-yyyy");
			SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm:ss");
			Date now = new Date();
			String strDate = sdfDate.format(now);
			String strTime = sdfTime.format(now);
			strTime = strTime.replace(":", "-");
			sDateTime = "D" + strDate + "_T" + strTime;
		} catch (Exception e) {
			System.err.println(e);
		}
		return sDateTime;
	}

	public static String takeSnapShotAndRetPath(WebDriver driver) throws Exception {

		String FullSnapShotFilePath = "";
		try {
			logger.info("Taking Screenshot");
			File scrFile = ((TakesScreenshot) driver)
					.getScreenshotAs(OutputType.FILE);
			String sFilename = null;
			sFilename = "verificationFailure_Screenshot.png";
			FullSnapShotFilePath = System.getProperty("user.dir")
					+ "\\output\\ScreenShots\\" + sFilename;
			FileUtils.copyFile(scrFile, new File(FullSnapShotFilePath));
		} catch (Exception e) {

		}

		return FullSnapShotFilePath;
	}

	public void quit() {
		driver.quit();
	}

	public WebElement findElement(By by) {
		return driver.findElement(by);
	}

	public void clear(By by) {
		quickWaitForElementPresent(by);
		findElement(by).clear();
	}

	public void quickWaitForElementPresent(By locator){
		logger.info("quick wait started for "+locator);
		int timeout = 2;
		turnOffImplicitWaits();
		for(int i=1;i<=timeout;i++){
			try{
				if(driver.findElements(locator).size()==0){
					pauseExecutionFor(1000);
					logger.info("waiting...");
					continue;
				}else{
					logger.info("wait over,element found");
					turnOnImplicitWaits();
					break;
				}			
			}catch(Exception e){
				continue;
			}
		}
	}

	public void turnOffImplicitWaits() {
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
	}

	public void turnOnImplicitWaits() {
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
	}

	public void waitForElementToBeVisible(By locator, int timeOut) {

		try{			
			WebDriverWait wait = new WebDriverWait(SFWebsiteDriver.driver, 120);
			WebElement element = wait.until(ExpectedConditions
					.visibilityOfElementLocated(locator));
			logger.info("Element is visible now");

		}catch(Exception e){
			logger.info("Element is not visible after waiting for "+timeOut+" seconds");
		}
	}

	public void type(By locator, String input, String element) {
		waitForElementToBeVisible(locator,10);
		try {
			findElement(locator).clear();
		} catch (Exception e) {
		}
		findElement(locator).sendKeys(input);
		logger.info("TYPED text as = " + input + " in " + element + " text field");
	}



	public void type(By locator, String input) {
		waitForElementToBeVisible(locator, 5);
		type(locator, input, "");
	}


	public void get(String url) {
		// TODO Auto-generated method stub

	}



	public List<WebElement> findElements(By by) {
		// TODO Auto-generated method stub
		return null;
	}



	public String getPageSource() {
		// TODO Auto-generated method stub
		return null;
	}

	public void close() {
		// TODO Auto-generated method stub

	}


	public Set<String> getWindowHandles() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getWindowHandle() {
		// TODO Auto-generated method stub
		return null;
	}

	public TargetLocator switchTo() {
		// TODO Auto-generated method stub
		return null;
	}

	public Navigation navigate() {
		// TODO Auto-generated method stub
		return null;
	}

	public Options manage() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getCurrentUrl() {
		String currentURL = webDriver.get().getCurrentUrl();
		logger.info("current URL is " + currentURL);
		return currentURL;
	}

	public String getTitle() {
		// TODO Auto-generated method stub
		return null;
	}



}
