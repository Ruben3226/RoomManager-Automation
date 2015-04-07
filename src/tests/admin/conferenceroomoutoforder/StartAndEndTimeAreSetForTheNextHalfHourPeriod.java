package tests.admin.conferenceroomoutoforder;

import static framework.common.AppConfigConstants.EXCEL_INPUT_DATA;

import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import framework.pages.admin.HomeAdminPage;
import framework.pages.admin.conferencerooms.RoomInfoPage;
import framework.pages.admin.conferencerooms.RoomOutOfOrderPlanningPage;
import framework.pages.admin.conferencerooms.RoomsPage;
import framework.utils.readers.ExcelReader;

/**
 * TC40: Verify that start time (hours and minutes) is set for the beginning of the next 
 * half hour period to current time when the room has no Out Of Order established
 * TC41: Verify that end time (hours and minutes) is set for the end of the next half 
 * hour period to current time when the room has no Out Of Order Period established
 * @author Yesica Acha
 *
 */
public class StartAndEndTimeAreSetForTheNextHalfHourPeriod {
	private RoomOutOfOrderPlanningPage outOfOrderPage;	
	
	@Test(groups = "UI")
	public void testStartAndEndTimeAreSetForTheNextHalfHourPeriod() {
		
		//Getting Out Of Order data from an excel file
		ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
		List<Map<String, String>> testData = excelReader.getMapValues("OutOfOrderPlanning");
		String roomName = testData.get(5).get("Room Name");
		
		//Opening Out Of Order Planning
		HomeAdminPage homeAdminPage = new HomeAdminPage(); 
		RoomsPage roomsPage = homeAdminPage.clickConferenceRoomsLink();
		RoomInfoPage roomInfoPage = roomsPage.doubleClickOverRoomName(roomName);
		outOfOrderPage = roomInfoPage.clickOutOfOrderPlanningLink();
		
		//Assertion for TC40:
		Assert.assertEquals(outOfOrderPage.getStartTimeValue(), 
				outOfOrderPage.getDefaultStartTimeValue());
		
		//Assertion for TC41:
		Assert.assertEquals(outOfOrderPage.getEndTimeValue(), 
				outOfOrderPage.getDefaultEndTimeValue());
	}
	
	@AfterMethod(groups = "UI")
	public void closeOutOfOrderPage() {
		outOfOrderPage.clickCancelBtn();
	}
}
