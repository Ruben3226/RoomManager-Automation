package test.java.admin.conferenceroom;

import static main.java.utils.AppConfigConstants.EXCEL_INPUT_DATA;

import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import main.java.pages.admin.HomeAdminPage;
import main.java.pages.tablet.HomeTabletPage;
import main.java.pages.tablet.SearchPage;
import main.java.pages.tablet.SettingsPage;
import main.java.utils.readers.ExcelReader;

/**
 * TC10:Verify that an enabled room is available in the tablet
 * @author Juan Carlos Guevara
 */
public class AnEnabledRoomIsAvailableInTheTablet {

	//Reading resource data from an .xls file
	private ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	private List<Map<String, String>> testData = excelReader.getMapValues("Resources");
	private String roomName = testData.get(0).get("Room Name");

	@Test(groups = "FUNCTIONAL")
	public void testAnEnabledRoomIsAvailableInTheTablet() {

		//Disabling room in Admin 
		HomeAdminPage homeAdminPage = new HomeAdminPage();
		homeAdminPage.clickConferenceRoomsLink();

		//Open tablet to see availability of an enable room
		HomeTabletPage homeTabletPage = new HomeTabletPage();
		SettingsPage settingsPage = homeTabletPage.clickSettingsBtn();
		homeTabletPage = settingsPage.selectRoom(roomName);	
		SearchPage searchPage = homeTabletPage.clickSearchBtn()
				.clickCollapseAdvancedBtn()
				.setName(roomName);

		//Assertion for TC10 and TC56
		Assert.assertTrue(searchPage.roomIsDiplayed(roomName));
	}
}
