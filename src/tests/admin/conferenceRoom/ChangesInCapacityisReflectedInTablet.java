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
 * TC08: Verify that the changes on Capacity of rooms are reflected tablet app 
 * @author Ruben Blanco
 *
 */
public class ChangesInCapacityisReflectedInTablet {

	//read excel to create variables
	private ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	private List<Map<String, String>> roomList = excelReader.getMapValues("RoomInfo");
	private String displayName = roomList.get(0).get("DisplayName");
	private String capacity = roomList.get(0).get("Capacity");
	private String empty = "";

	@Test(groups = "FUNCTIONAL")
	public void testChangesInCapacityisReflectedInTablet() {
		HomeAdminPage homeAdminPage = new HomeAdminPage();
		RoomsPage roomsPage = homeAdminPage.clickConferenceRoomsLink();
		RoomInfoPage roomInfoPage = roomsPage.doubleClickOverRoomName(displayName);
		roomInfoPage.setRoomCapacity(capacity)
			.clickSaveBtn();
		
		//navigate to tablet
		HomeTabletPage homeTabletPage = new HomeTabletPage();
		SettingsPage settingsPage = homeTabletPage.clickSettingsBtn();
		settingsPage.selectRoom(displayName);
        SearchPage searchPage = homeTabletPage.clickSearchBtn()
        	.clickCollapseAdvancedBtn()
        	.setMinimumCap(capacity);
      
        //Assertion for TC08
        Assert.assertTrue(searchPage.roomIsDiplayed(displayName));
	}

	@AfterClass(groups = "FUNCTIONAL")
	public void postcondition() {
		HomeAdminPage homeAdminPage = new HomeAdminPage();
		RoomsPage roomsPage = homeAdminPage.clickConferenceRoomsLink();
		RoomInfoPage roomInfoPage = roomsPage.doubleClickOverRoomName(displayName);
		
		//clean room capacity 
		roomInfoPage.setRoomCapacity(empty)
			.clickSaveBtn();
	}
}
