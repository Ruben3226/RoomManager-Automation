package test.java.admin.conferenceroomoutoforder;

import static main.java.utils.AppConfigConstants.EXCEL_INPUT_DATA;

import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import main.java.pages.admin.HomeAdminPage;
import main.java.pages.admin.conferencerooms.RoomInfoPage;
import main.java.pages.admin.conferencerooms.RoomOutOfOrderPage;
import main.java.pages.admin.conferencerooms.RoomsPage;
import main.java.utils.TimeMethods;
import main.java.utils.readers.ExcelReader;

/**
 * TC38: Verify that start date is set in current time when the room has no 
 * Out Of Order Period established
 * TC39: Verify that end date are set in the current time when the room has no 
 * Out Of Order Period established
 * @author Yesica Acha
 *
 */
public class StartAndEndDateAreSetForCurrentTimeByDefault {
	private RoomOutOfOrderPage outOfOrderPage;
		
	@Test(groups = "UI")
	public void testStartAndEndDateIsSetForCurrentTimeByDefault() {
		
		//Getting Out Of Order data from an excel file
		ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
		List<Map<String, String>> testData = excelReader.getMapValues("OutOfOrderPlanning");
		String roomName = testData.get(5).get("Room Name");
		String currentDate = TimeMethods.getCurrentDate("MMM dd YYYY");
		
		//Opening Out Of Order Planning
		HomeAdminPage homeAdminPage = new HomeAdminPage(); 
		RoomsPage roomsPage = homeAdminPage.clickConferenceRoomsLink();
		RoomInfoPage roomInfoPage = roomsPage.doubleClickOverRoomName(roomName);
		outOfOrderPage = roomInfoPage.clickOutOfOrderPlanningLink();
		
		//Assertion for TC38
		Assert.assertEquals(outOfOrderPage.getStartDateValue(), currentDate);
		
		//Assertion for TC39
		Assert.assertEquals(outOfOrderPage.getEndDateValue(), currentDate);
	}
	
	@AfterMethod(groups = "UI")
	public void closeOutOfOrderPage() {
		outOfOrderPage.clickCancelBtn();
	}
}
