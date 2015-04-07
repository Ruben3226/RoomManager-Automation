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
import framework.utils.readers.ExcelReader;

/**
 * TC04: Verify that is allowed to change the display name of rooms
 * TC05: Verify that is allowed to insert a code for a room
 * TC06: Verify that is allowed to insert the capacity of rooms 
 * TC07: Verify that is allowed to insert the location of a room
 * 
 * @author Ruben Blanco
 * 
 */
public class RoomDisplayNameCanBeChanged {

	//read excel to create variables
	private ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	private List<Map<String, String>> roomList = excelReader.getMapValues("RoomInfo");
	private String displayName = roomList.get(0).get("DisplayName");  	  
	private String capacity = roomList.get(0).get("Capacity");
	private String roomCode = roomList.get(0).get("Code");
	private String location = roomList.get(0).get("Location");
	private String empty = "";

	@Test(groups = "FUNCTIONAL")
	public void testDisplayNameRoomsCanBeChanged() {
		HomeAdminPage homeAdminPage = new HomeAdminPage();
		RoomsPage roomsPage = homeAdminPage.clickConferenceRoomsLink();
		RoomInfoPage roomInfoPage = roomsPage.doubleClickOverRoomName(
		roomsPage.getRoomDisplayName(displayName));
		roomInfoPage.setDisplayName(displayName)
			.setRoomCode(roomCode)
			.setRoomCapacity(capacity)
			.setLocation(location)
			.clickSaveBtn();
		roomsPage.doubleClickOverRoomName(roomsPage.getRoomDisplayName(displayName));
		
		//Assertion for TC04
		Assert.assertEquals(roomInfoPage.getRoomDisplayName(), displayName);

		//Assertion for TC05
		Assert.assertEquals(roomInfoPage.getRoomCode(), roomCode);

		//Assertion for TC06
		Assert.assertEquals(roomInfoPage.getRoomCapacity(), capacity);

		//Assertion for TC04
		Assert.assertEquals(roomInfoPage.getRoomLocation(), location);
	}

	@AfterClass(groups = "FUNCTIONAL")
	public void postcondition() {
		
		//clean the modified room with a empty value
		RoomInfoPage roomInfoPage = new RoomInfoPage();
		roomInfoPage.setDisplayName(displayName)
			.setRoomCode(empty)
			.setRoomCapacity(empty)
			.clickSaveBtn();
	}
}
