package test.java.admin.conferenceroom;

import static main.java.utils.AppConfigConstants.EXCEL_INPUT_DATA;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import main.java.pages.admin.HomeAdminPage;
import main.java.pages.admin.conferencerooms.RoomInfoPage;
import main.java.pages.admin.conferencerooms.RoomsPage;
import main.java.pages.tablet.HomeTabletPage;
import main.java.pages.tablet.SearchPage;
import main.java.pages.tablet.SettingsPage;
import main.java.utils.readers.ExcelReader;

/**
 * TC02: Verify that a room availability can be enable from room info window
 * @author Ruben Blanco
 *
 */
public class RoomAvailabilityCanBeEnableFromRoomInfoPage {

	//read excel to create variables
	private ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	private List<Map<String, String>> roomList = excelReader.getMapValues("RoomInfo");
	private String displayName = roomList.get(0).get("DisplayName");
	private String auxRoom = roomList.get(1).get("DisplayName");

	@BeforeClass(groups = "FUNCTIONAL")
	public void precondition() {
		HomeAdminPage homeAdminPage = new HomeAdminPage();
		RoomsPage roomsPage = homeAdminPage.clickConferenceRoomsLink();
		
		//click to disable the selected room
		roomsPage.enableDisableIcon(displayName);
	}
	
	@Test(groups = "FUNCTIONAL")
	public void testRoomAvailabilityCanBeEnableFromRoomInfoPage() {
		HomeAdminPage homeAdminPage = new HomeAdminPage();
		RoomsPage roomsPage = homeAdminPage.clickConferenceRoomsLink();
		RoomInfoPage roomInfoPage = roomsPage.doubleClickOverRoomName(displayName);
		roomInfoPage.clickEnableIcon()
			.clickSaveBtn();
		
		//navigate to tablet
		HomeTabletPage homeTabletPage = new HomeTabletPage();
		SettingsPage settingsPage = homeTabletPage.clickSettingsBtn();
		settingsPage.selectRoom(auxRoom);
		SearchPage searchPage = homeTabletPage.clickSearchBtn();
		
		//Assertion for TC 02
		Assert.assertTrue(searchPage.roomIsDiplayed(displayName));
	}
	
}
