package com.saleforce.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Inet4Address;
import java.net.URL;
import java.net.UnknownHostException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.TimeZone;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;

import com.salesforce.driver.website.SFWebsiteDriver;



/**
 * Utility class for common functions.
 * 
 * @author Pranav Pasricha
 * 
 */
public class CommonUtils {
	protected static SFWebsiteDriver driver;

	
	/**
	 * Generates random integer from a range
	 * 
	 * @param min
	 * @param max
	 * @return - random integer
	 */
	public static int getRandomNum(int min, int max) {
		return new Random().nextInt((max - min) + 1) + min;
	} 

	
}