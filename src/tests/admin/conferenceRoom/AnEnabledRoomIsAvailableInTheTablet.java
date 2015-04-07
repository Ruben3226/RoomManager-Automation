package tests.admin.conferenceRoom;

import static framework.common.AppConfigConstants.EXCEL_INPUT_DATA;

import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import framework.pages.admin.HomeAdminPage;
import framework.pages.tablet.HomeTabletPage;
import framework.pages.tablet.SearchPage;
import framework.pages.tablet.SettingsPage;
import framework.utils.readers.ExcelReader;

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
