package main.java.utils;

import main.java.utils.readers.JsonReader;

/**
 * 
 * @author Asael Calizaya
 *
 */
public final class AppConfigConstants {
	private static JsonReader value = new JsonReader();
	private static String appconfigPath = "\\appconfig.json";
	public static final String BROWSER = value.readJsonFile("browser", "navigator", appconfigPath);
	public static final String URL_TABLET = value.readJsonFile("browser", "urlTablet", appconfigPath);
	public static final String URL_ADMIN = value.readJsonFile("browser", "urlAdmin", appconfigPath);
	public static final String URL_REST = value.readJsonFile("browser", "urlRest", appconfigPath);
	public static final String CHROMEDRIVER_PATH = value.readJsonFile("confDriver", "chrome", appconfigPath);
	public static final String IEDRIVER_PATH = value.readJsonFile("confDriver", "ie", appconfigPath);
	public static final int IMPLICIT_WAIT = Integer.parseInt(value.readJsonFile("confDriver", 
			"implicitWait", appconfigPath));
	public static final int EXPLICIT_WAIT = Integer.parseInt(value.readJsonFile("confDriver", 
			"explicitWait", appconfigPath));
	public static final int WEBDRIVERWAIT_SLEEP = Integer.parseInt(value.readJsonFile("confDriver",
			"webDriverWait_Sleep", appconfigPath));
	public static final String EXCEL_PATH = value.readJsonFile("ExternalFile", "excelPath", appconfigPath);
	public static final String EXCEL_INPUT_DATA = value.readJsonFile("ExternalFile", "excelInputData", appconfigPath);
	public static final String EXCEL_DATA_PROVIDER = value.readJsonFile("ExternalFile", "dataProviderData", appconfigPath);
	public static final String RESOURCE1_PATH = "\\src\\test\\resources\\Resource1.json";
	public static final String ROOM1_PATH = "\\src\\test\\resources\\Room1.json";
}
