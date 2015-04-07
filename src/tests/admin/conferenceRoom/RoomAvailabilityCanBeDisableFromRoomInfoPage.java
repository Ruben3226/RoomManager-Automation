package tests.admin.conferenceRoom;

import static framework.common.AppConfigConstants.EXCEL_INPUT_DATA;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import framework.pages.admin.HomeAdminPage;
import framework.pages.admin.conferencerooms.RoomInfoPage;
import framework.pages.admin.conferencerooms.RoomsPage;
import framework.pages.tablet.HomeTabletPage;
import framework.pages.tablet.SearchPage;
import framework.pages.tablet.SettingsPage;
import framework.utils.readers.ExcelReader;

/**
 * TC01: Verify that a room availability can be disable from room info window
 * @author Ruben Blanco
 *
 */
public class RoomAvailabilityCanBeDisableFromRoomInfoPage {
	
	//read excel to create variables
	private ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	private List<Map<String, String>> roomList = excelReader.getMapValues("RoomInfo");
	private String displayName = roomList.get(0).get("DisplayName");
	private String auxRoom = roomList.get(1).get("DisplayName");
	
	@Test(groups = "FUNCTIONAL")
	public void testRoomAvailabilityCanBeDisableFromRoomInfoPage() {
		HomeAdminPage homeAdminPage = new HomeAdminPage();
		RoomsPage roomsPage = homeAdminPage.clickConferenceRoomsLink();
		RoomInfoPage roomInfoPage = roomsPage.doubleClickOverRoomName(displayName);
		roomInfoPage.clickDisableIcon()
			.clickSaveBtn();
		
		//navigate to tablet
		HomeTabletPage homeTabletPage = new HomeTabletPage();
		SettingsPage settingsPage = homeTabletPage.clickSettingsBtn();
		settingsPage.selectRoom(auxRoom);
		SearchPage searchPage = homeTabletPage.clickSearchBtn();
		
		//Assertion for TC01
		Assert.assertFalse(searchPage.roomIsDiplayed(displayName));
	}
	
	@AfterClass(groups = "FUNCTIONAL")
	public void cleanRoom() {
		HomeAdminPage homeAdminPage = new HomeAdminPage();
		RoomsPage roomsPage = homeAdminPage.clickConferenceRoomsLink();
		roomsPage.enableDisableIcon(displayName);
	}
}
