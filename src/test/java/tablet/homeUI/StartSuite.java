package test.java.tablet.homeUI;

import static main.java.utils.AppConfigConstants.EXCEL_INPUT_DATA;
import static main.java.utils.AppConfigConstants.URL_TABLET;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.WebDriver;
import org.testng.ISuite;
import org.testng.ISuiteListener;

import main.java.pages.tablet.SettingsPage;
import main.java.selenium.SeleniumDriverManager;
import main.java.utils.readers.ExcelReader;

/**
 * This class implements ISuiteListener.
 * @author Eliana Navia
 *
 */
public class StartSuite implements ISuiteListener {
	ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	List<Map<String, String>> meetingData = excelReader.getMapValues("MeetingData");
	WebDriver driver = SeleniumDriverManager.getManager().getDriver();
	SettingsPage settingsPage = new SettingsPage();
	Logger log = Logger.getLogger(getClass());
	
    @Override
    public void onStart(ISuite arg0) {
    	driver.get(URL_TABLET + "/#/settings");
		PropertyConfigurator.configure("log4j.properties");
		log.info("Start suite" + arg0.getResults());
		settingsPage.selectRoom( meetingData.get(0).get("Room"));
    }
	
	@Override
    public void onFinish(ISuite arg0) {
		log.info("End suite" + arg0.getResults());
		driver.quit();
    }
}