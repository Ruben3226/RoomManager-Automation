package test.java.admin.conferenceroom;

import static main.java.utils.AppConfigConstants.EXCEL_INPUT_DATA;

import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import main.java.pages.admin.HomeAdminPage;
import main.java.pages.admin.conferencerooms.RoomsPage;
import main.java.utils.readers.ExcelReader;

/**
 * TC12: Verify that room availability can be disabled when enable/disable button is clicked in 
 * a conference room window 
 * TC13: Verify that room availability can be enabled when enable/disable button is clicked in 
 * a conference room window
 * @author Juan Carlos Guevara
 */
public class RoomAvailabilityCanBeEnabledOrDisabledWithEnableDisableButton {
	
	//Reading resource data from an .xls file
	private ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	private List<Map<String, String>> testData = excelReader.getMapValues("Resources");
	private String roomName = testData.get(2).get("Room Name");

	@Test(groups = "ACCEPTANCE")
	public void testRoomAvailabilityCanBeEnabledOrDisabledWithEnableDisableButton() {

		//Disable a room 
		HomeAdminPage homeAdminPage = new HomeAdminPage();
		RoomsPage roomsPage = homeAdminPage.clickConferenceRoomsLink();	
		roomsPage.enableDisableIcon(roomName);

		//Assertion for TC12 
		Assert.assertTrue(roomsPage.isRoomDisabled(roomName));
		roomsPage.enableDisableIcon(roomName);

		//Assertion for TC13 
		Assert.assertTrue(roomsPage.isRoomEnabled(roomName));
	}
}
