package tests.admin.conferenceRoom;

import static framework.common.AppConfigConstants.EXCEL_INPUT_DATA;

import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import framework.pages.admin.HomeAdminPage;
import framework.pages.admin.conferencerooms.RoomInfoPage;
import framework.pages.admin.conferencerooms.RoomsPage;
import framework.utils.readers.ExcelReader;

/**
 * TC18: Verify that the tool allows blank space between text in displayed name of room
 * TC19: Verify that the tool allows blank space between text in code of room
 * @author Marco Llano
 */
public class RoomAllowsBlankSpaceBetweenTextForDisplayNameAndCode {
	private RoomsPage roomsPage;
	private RoomInfoPage roomInfoPage;
	
	//ExcelReader is used to read rooms data (roomName) from Excel file and save it into a List
	private ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	private List<Map<String, String>> roomsDataList = excelReader.getMapValues("Resources");
	private String roomName = roomsDataList.get(0).get("Room Name");

	@Test(groups = "NEGATIVE")
	public void testRoomAllowsBlankSpaceBetweenTextForDisplayNameAndCode() {
		HomeAdminPage homeAdminPage = new HomeAdminPage();
		roomsPage = homeAdminPage.clickConferenceRoomsLink();
		String blankSpaceBetweenText = roomName + " " + roomName;

		//Insert blank space between text in room display name and room code
		roomsPage.clickConferenceRoomsLink();
		roomInfoPage = roomsPage.doubleClickOverRoomName(roomName);
		roomsPage = roomInfoPage.setDisplayName(blankSpaceBetweenText)
				.setRoomCode(blankSpaceBetweenText)
				.clickSaveBtn();
		roomInfoPage = roomsPage.doubleClickOverRoomName(blankSpaceBetweenText);

		//Assertion for TC18		
		Assert.assertTrue(roomInfoPage.getRoomDisplayName().contains(blankSpaceBetweenText));

		//Assertion for TC19
		Assert.assertEquals(roomInfoPage.getRoomCode(), blankSpaceBetweenText);
	}

	@AfterMethod(groups = "NEGATIVE")
	public void afterMethod() {	
		String emptyValue = "";
		roomsPage = roomInfoPage.setDisplayName(roomName)
				.setRoomCode(emptyValue)
				.clickSaveBtn();
	}
}