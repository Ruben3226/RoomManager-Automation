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
 * TC13: Verify that is not allowed to insert long numbers in capacity field of rooms
 * @author Ruben Blanco
 *
 */
public class IsNotAllowedInsertLongNumbersInCapacityFieldOfRooms {
	
	//read excel to create variables
	private ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	private List<Map<String, String>> roomList = excelReader.getMapValues("RoomInfo");
	private String displayName = roomList.get(0).get("DisplayName");	  	  
	private String capacity = roomList.get(0).get("longValues");
	private String empty = "";
	
	@Test(groups = "NEGATIVE")
	public void testIsNotAllowedInsertLongNumbersInCapacityFieldOfRooms() {
		HomeAdminPage homeAdminPage = new HomeAdminPage();
		RoomsPage roomsPage = homeAdminPage.clickConferenceRoomsLink();
		RoomInfoPage roomInfoPage = roomsPage.doubleClickOverRoomName(displayName);
		roomInfoPage.setRoomCapacity(capacity)
			.clickSaveBtn();
		roomsPage.doubleClickOverRoomName(displayName);
		
		//get the modified room capacity
		String newCapacity = roomInfoPage.getRoomCapacity();
		
		//Assertion for TC13
		Assert.assertTrue(roomInfoPage.capacityIsLong(newCapacity));
	}
	
	@AfterClass(groups = "NEGATIVE")
	public void postcondition() {
		RoomInfoPage roomInf = new RoomInfoPage();
		
		//clean room capacity
		roomInf.setRoomCapacity(empty)
			.clickSaveBtn();
	}
	
}
