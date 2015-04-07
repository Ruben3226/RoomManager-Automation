package test.java.admin.conferenceroom;

import static main.java.utils.AppConfigConstants.EXCEL_INPUT_DATA;
import static main.java.utils.MessageConstants.ROOM_DISPLAY_NAME_EMPTY;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import main.java.pages.admin.HomeAdminPage;
import main.java.pages.admin.conferencerooms.RoomInfoPage;
import main.java.pages.admin.conferencerooms.RoomsPage;
import main.java.utils.readers.ExcelReader;

/**
 * TC12: Verify that the tool does not allow blank text for Display Name
 * @author Ruben Blanco
 *
 */
public class DoesNotAllowsBlankTextForDisplayName {

	//empty string to set room display name
	private String empty = "";
	
	@Test(groups = "NEGATIVE")
	public void testDoesNotAllowsBlankTextForDisplayName() {
		
		//read excel to create variables
		ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
		List<Map<String, String>> roomList = excelReader.getMapValues("RoomInfo");
		String displayName = roomList.get(0).get("DisplayName");

		//navigate to home admin page
		HomeAdminPage homeAdminPage = new HomeAdminPage();
		RoomsPage roomsPage = homeAdminPage.clickConferenceRoomsLink();
		RoomInfoPage roomInfoPage = roomsPage.doubleClickOverRoomName(displayName);
		roomInfoPage.setDisplayName(empty)
			.clickSaveWithErrorBtn();
		String errorMessage = roomInfoPage.getErrorMessageDisplayName();
		
		//Assertion for TC12
		Assert.assertEquals(errorMessage, ROOM_DISPLAY_NAME_EMPTY);
	}
	
	@AfterClass(groups = "NEGATIVE")
	public void postcondition() {
		RoomInfoPage roomInfoPage = new RoomInfoPage();
		roomInfoPage.clickCancelBtn();
	}
}
