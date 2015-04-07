package test.java.admin.resources;

import static main.java.utils.AppConfigConstants.EXCEL_INPUT_DATA;

import java.util.List;
import java.util.Map;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import main.java.pages.admin.LoginPage;
import main.java.pages.tablet.HomeTabletPage;
import main.java.pages.tablet.SettingsPage;
import main.java.utils.readers.ExcelReader;

/**
 * @author Marco Llano
 *
 */
public class StartTestSuiteResources {
	LoginPage login = new LoginPage();

	@BeforeSuite(groups = {"FUNCTIONAL","NEGATIVE","ACCEPTANCE"})
	public void init() {
		
		//Go to tablet to select a room
		HomeTabletPage homeTabletPage =  new HomeTabletPage();
		SettingsPage settingsPage = homeTabletPage.clickSettingsBtn(); 

		//ExcelReader is used to read rooms data
		ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
		List<Map<String, String>> roomsDataList = excelReader.getMapValues("Resources");
		String roomDisplayName = roomsDataList.get(0).get("Room Name");
		
		//Set room01 in tablet
		homeTabletPage = settingsPage.selectRoom(roomDisplayName);	
	}

	@AfterSuite(groups = {"FUNCTIONAL","NEGATIVE","ACCEPTANCE"})
	public void afterSuite() {
		login.CloseWindow();
	}
}