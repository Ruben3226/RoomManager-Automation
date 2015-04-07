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
 * TC15: Verify that the tool allows blank text for Room Capacity
 * TC16: Verify that the tool allows blank text for Room Code
 * @author Marco Llano
 */
public class RoomAllowsBlankTextForRoomCodeAndCapacity {
	private RoomsPage roomsPage;
	private RoomInfoPage roomInfoPage;

	@Test(groups = "NEGATIVE")
	public void testRoomAllowsBlankTextForRoomCodeAndCapacity() {
		HomeAdminPage homeAdminPage = new HomeAdminPage();
		roomsPage = homeAdminPage.clickConferenceRoomsLink();
		String emptyValue = "";

		//ExcelReader is used to read rooms data (roomName) from Excel file and save it into a List
		ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
		List<Map<String, String>> roomsDataList = excelReader.getMapValues("Resources");
		String roomName = roomsDataList.get(0).get("Room Name");

		//Set room capacity to blank text
		roomsPage.clickConferenceRoomsLink();
		roomInfoPage = roomsPage.doubleClickOverRoomName(roomName);
		roomsPage = roomInfoPage.setRoomCapacity(emptyValue)
				.setRoomCode(emptyValue)
				.clickSaveBtn();
		roomInfoPage = roomsPage.doubleClickOverRoomName(roomName);

		//Assertion for TC15
		Assert.assertEquals(roomInfoPage.getRoomCapacity(), emptyValue);

		//Assertion for TC16
		Assert.assertEquals(roomInfoPage.getRoomCode(), emptyValue);		
	}
}
