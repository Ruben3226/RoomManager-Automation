package tests.admin.conferenceRoom;

import static framework.common.AppConfigConstants.EXCEL_INPUT_DATA;
import static framework.common.AppConfigConstants.ROOM1_PATH;

import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import framework.pages.admin.HomeAdminPage;
import framework.pages.admin.conferencerooms.RoomInfoPage;
import framework.pages.admin.conferencerooms.RoomsPage;
import framework.utils.readers.ExcelReader;
import framework.utils.readers.JsonReader;

/**
 * TC17: Verify that is allowed to change the display name of rooms for special characters. 
 * @author Marco Llano
 */
public class RoomAllowsSpecialCharactersForDisplayName {
	private RoomsPage roomsPage;
	private RoomInfoPage roomInfoPage;
	
	//ExcelReader is used to read rooms data (roomName) from Excel file and save it into a List
	private ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	private List<Map<String, String>> roomsDataList = excelReader.getMapValues("Resources");
	private String roomName = roomsDataList.get(0).get("Room Name");

	@Test(groups = "NEGATIVE")
	public void testRoomDoesNotAllowsBlankTextForDisplayName() {
		HomeAdminPage homeAdminPage = new HomeAdminPage();
		roomsPage = homeAdminPage.clickConferenceRoomsLink();
		
		//JsonReader is used to read data (specialCharacters) from .json file (Room1.json)
		JsonReader jsonReader = new JsonReader();	
		String specialCharacters = jsonReader.readJsonFile("customName" , ROOM1_PATH);

		//Set room capacity to blank text
		roomsPage.clickConferenceRoomsLink();
		roomInfoPage = roomsPage.doubleClickOverRoomName(roomName);
		roomsPage = roomInfoPage.setDisplayName(specialCharacters)
				.clickSaveBtn();
		roomInfoPage = roomsPage.doubleClickOverRoomName(specialCharacters);

		//Assertion for TC17
		Assert.assertTrue(roomsPage.getRoomDisplayName(specialCharacters).contains(specialCharacters));		
	}

	@AfterMethod(groups = "NEGATIVE")
	public void afterMethod() {	
		roomsPage = roomInfoPage.setDisplayName(roomName)
				.clickSaveBtn();
	}
}