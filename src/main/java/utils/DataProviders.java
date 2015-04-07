package main.java.utils;

import static main.java.utils.AppConfigConstants.EXCEL_DATA_PROVIDER;

import org.testng.annotations.DataProvider;

import main.java.utils.readers.ExcelReader;

/**
 * This class contains data providers for tests
 * @author Yesica Acha
 *
 */
public class DataProviders {
	
	/**
	 * [YA] Data provider for Out Of Order and Meeting Creation
	 * @return Object[][] with the following values: Meeting's Subject, Meeting's StartTime,
	 * Meeting's EndTime, Out Of Order's StartTime, Out Of Order's EndTime
	 */
	@DataProvider(name = "OutOfOrderAndMeetingData")
	public static Object[][] getOutOfOrderAndMeetingData() {
		ExcelReader excelFile = new ExcelReader(EXCEL_DATA_PROVIDER);
		return excelFile.getObjectValues("OutOfOrderAndMeetingData");		
	}
	
	/**
	 * [YA] Data provider for Out Of Order Creation
	 * @return Object[][] with the following values: StartDate, EndDate, StartTime, EndTime,
	 * Description
	 */
	@DataProvider(name = "OutOfOrderData")
	public static Object[][] getOutOfOrderData() {
		ExcelReader excelFile = new ExcelReader(EXCEL_DATA_PROVIDER);
		return excelFile.getObjectValues("OutOfOrderData");		
	}
	
	/**
	 * [EN] Reads the data of a excel file.
	 * @return Object[][] where the column contain the following values: 
	 * Organizer, Subject, minutesFrom, minutesTo, Attendee, Password
	 */
	@DataProvider(name = "CurrentMeetingDataProvider")
	public static Object[][] getCurrentMeetingDataProvider() {
		ExcelReader excelFile = new ExcelReader(EXCEL_DATA_PROVIDER);
		return excelFile.getObjectValues("CurrentMeetingProvider");
	}
	
	/**
	 * Reads the data of a excel file.
	 * @return Object[][] Object[][] where the column contain the following values: 
	 * Organizer, Subject, minutesFrom, minutesTo, Attendee, Password
	 */
	@DataProvider(name = "NextMeetingDataProvider")
	public static Object[][] getNextMeetingDataProvider() {
		ExcelReader excelFile = new ExcelReader(EXCEL_DATA_PROVIDER);
		return excelFile.getObjectValues("NextMeetingProvider");
	}
}
