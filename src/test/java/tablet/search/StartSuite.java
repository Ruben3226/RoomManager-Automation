package test.java.tablet.search;

import static main.java.utils.AppConfigConstants.EXCEL_INPUT_DATA;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import main.java.pages.tablet.HomeTabletPage;
import main.java.pages.tablet.SettingsPage;
import main.java.rest.RoomManagerRestMethods;
import main.java.selenium.SeleniumDriverManager;
import main.java.utils.readers.ExcelReader;

/**
 * This class is to select a some room to work on this
 * and create a new resource
 * @author Jose Cabrera
 *
 */
public class StartSuite {
	private WebDriver driver = SeleniumDriverManager.getManager().getDriver();
	private ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	private List<Map<String, String>> meetingData = excelReader.getMapValues("MeetingData");
	private String roomName = meetingData.get(2).get("Room");
	
	@BeforeSuite(groups = {"ACCEPTANCE", "UI", "NEGATIVE", "FUNCTIONAL"})
	public void start() throws InterruptedException, MalformedURLException, IOException {
        String resourceFileJSON = "\\src\\test\\resources\\Resource1.json";
        String filePath = System.getProperty("user.dir") + resourceFileJSON;
		RoomManagerRestMethods.createResource(filePath,"");
		HomeTabletPage homePage = new HomeTabletPage();
		SettingsPage settingsPage = homePage.clickSettingsBtn();
		settingsPage.selectRoom(roomName);
	}
	
	@AfterSuite(groups = {"ACCEPTANCE", "UI", "NEGATIVE", "FUNCTIONAL"})
	public void cleanEnvironment() throws MalformedURLException, IOException {
		List<Map<String, String>> testData = excelReader.getMapValues("Search");
		String resourceName = testData.get(1).get("Resource");
		RoomManagerRestMethods.deleteResource(resourceName);
		driver.quit();
	}
}
