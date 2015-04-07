package test.java.admin.conferenceroom;

import static main.java.utils.AppConfigConstants.EXCEL_INPUT_DATA;

import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import main.java.pages.admin.HomeAdminPage;
import main.java.pages.admin.conferencerooms.RoomInfoPage;
import main.java.pages.admin.conferencerooms.RoomsPage;
import main.java.utils.readers.ExcelReader;

/**
 * TC24: Verify if Room Location not Allows an inexistent location
 * @author Ruben Blanco
 *
 */
public class RoomLocationNotAllowsInexistentLocation {

	//invalid location for room location field
	private String invalidLoacation = "invalid location";

	@Test(groups = "NEGATIVE")
	public void testRoomLocationNotAllowInexistentLocation() {
		
		//read excel to create variables
		ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
		List<Map<String, String>> roomList = excelReader.getMapValues("RoomInfo");
		String displayName = roomList.get(0).get("DisplayName");
		
		//navigate to room admin page
		HomeAdminPage homeAdminPage = new HomeAdminPage();
		RoomsPage roomsPage = homeAdminPage.clickConferenceRoomsLink();
		RoomInfoPage roomInfoPage = roomsPage.doubleClickOverRoomName(displayName);
		roomInfoPage.setLocation(invalidLoacation);
		
		//Assertion for TC24
		Assert.assertTrue(roomInfoPage.isNoMatchFoundMessageDisplayed());
	}
}
