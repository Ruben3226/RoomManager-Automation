package tests.admin.conferenceRoom;

import static framework.common.AppConfigConstants.EXCEL_INPUT_DATA;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.testng.annotations.Test;

import framework.pages.admin.HomeAdminPage;
import framework.pages.admin.conferencerooms.RoomInfoPage;
import framework.pages.admin.conferencerooms.RoomsPage;
import framework.utils.readers.ExcelReader;

/**
 * TC14: Verify that is not allowed to insert non-number inputs into the 
 * capacity field of rooms
 * @author Ruben Blanco
 *
 */
public class IsNotAllowedInsertNonNumberInputsIntoCapacityFieldOfRooms {
	
	//read excel to create variables
	private ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	private List<Map<String, String>> roomList = excelReader.getMapValues("RoomInfo");
	private String displayName = roomList.get(0).get("DisplayName");	  	  
	private String capacity = roomList.get(0).get("invalidCapacity");
	
	@Test(groups = "NEGATIVE")
	public void testIsNotAllowedInsertNonNumberInputsIntoCapacityFieldOfRooms() {
		HomeAdminPage homeAdminPage = new HomeAdminPage();
		RoomsPage roomsPage = homeAdminPage.clickConferenceRoomsLink();
		RoomInfoPage roomInfoPage = roomsPage.doubleClickOverRoomName(displayName);
		roomInfoPage.setRoomCapacity(capacity);
		
		//get the new room capacity
		String newCapacity = roomInfoPage.getRoomCapacity();
		roomInfoPage.clickCancelBtn();
		
		//Assertion for TC14
		Assert.assertNotEquals(capacity, newCapacity);
	}
	
}
