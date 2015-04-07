package tests.tablet.schedule;

import static framework.common.AppConfigConstants.EXCEL_INPUT_DATA;

import java.util.List;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import framework.pages.tablet.HomeTabletPage;
import framework.pages.tablet.SettingsPage;
import framework.selenium.SeleniumDriverManager;
import framework.utils.readers.ExcelReader;

/**
 * This class is to select some room to work on that
 * @author Asael Calizaya
 *
 */
public class StartTest {
	WebDriver driver = SeleniumDriverManager.getManager().getDriver();
	ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	List<Map<String, String>> meetingData = excelReader.getMapValues("MeetingData");
	String roomName = meetingData.get(1).get("Room");
	
	@BeforeSuite(groups = {"ACCEPTANCE", "FUNCTIONAL", "UI", "NEGATIVE"})
	public void start() {
		HomeTabletPage homePage = new HomeTabletPage();
		SettingsPage settingsPage = homePage.clickSettingsBtn();
		homePage = settingsPage.selectRoom(roomName);
	}
	
	@AfterSuite(groups = {"ACCEPTANCE", "FUNCTIONAL", "UI", "NEGATIVE"})
	public void end() {
		driver.quit();
	}
}
