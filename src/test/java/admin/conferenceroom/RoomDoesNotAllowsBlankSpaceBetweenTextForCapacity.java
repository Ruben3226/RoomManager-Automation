package test.java.admin.conferenceroom;

import static main.java.utils.AppConfigConstants.EXCEL_INPUT_DATA;

import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import main.java.pages.admin.HomeAdminPage;
import main.java.pages.admin.conferencerooms.RoomInfoPage;
import main.java.pages.admin.conferencerooms.RoomsPage;
import main.java.utils.readers.ExcelReader;

/**
 * TC20: Verify that the tool does not allows blank space between text in capacity of room
 * @author Marco Llano
 */
public class RoomDoesNotAllowsBlankSpaceBetweenTextForCapacity {
	private RoomsPage roomsPage;
	private RoomInfoPage roomInfoPage;
	
	//ExcelReader is used to read rooms data
	ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	List<Map<String, String>> roomsDataList = excelReader.getMapValues("Resources");
	String roomName = roomsDataList.get(2).get("Room Name");

	@Test(groups = "NEGATIVE")
	public void testRoomDoesNotAllowsBlankSpaceBetweenTextForCapacity() {
		HomeAdminPage homeAdminPage = new HomeAdminPage();
		roomsPage = homeAdminPage.clickConferenceRoomsLink();
		String roomCapacity = roomsDataList.get(2).get("Value");
		String blankSpaceBetweenText = roomCapacity + " " + roomCapacity;		

		//Insert blank space between text in room capacity
		roomsPage.clickConferenceRoomsLink();
		roomInfoPage = roomsPage.doubleClickOverRoomName(roomName);
		roomsPage = roomInfoPage.setRoomCapacity(blankSpaceBetweenText)
				.clickSaveBtn();

		//Verify if exist blank space between text for room display name
		roomInfoPage = roomsPage.doubleClickOverRoomName(roomName);
		Assert.assertNotSame(roomInfoPage.getRoomCode(), blankSpaceBetweenText);
	}

	@AfterMethod(groups = "NEGATIVE")
	public void afterMethod() {	
		String emptyValue="";
		roomsPage = roomInfoPage.setRoomCapacity(emptyValue)
				.clickSaveBtn();
	}
}