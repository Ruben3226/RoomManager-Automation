package tests.admin.conferenceroomoutoforder;

import static framework.common.AppConfigConstants.EXCEL_INPUT_DATA;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import framework.pages.admin.HomeAdminPage;
import framework.pages.admin.conferencerooms.RoomInfoPage;
import framework.pages.admin.conferencerooms.RoomOutOfOrderPlanningPage;
import framework.pages.admin.conferencerooms.RoomsPage;
import framework.rest.RootRestMethods;
import framework.utils.readers.ExcelReader;

/**
 * TC27: Verify an Out Of Order cannot be created when end time (hours and minutes) occurs before 
 * start time
 * TC36: Verify when end time (hours and minutes) occurs before start time an error message that says 
 * 'To' field must be greater than 'From' field is displayed
 * @author Yesica Acha
 *
 */
public class OutOfOrderIsNotCreatedIfEndTimeOccursBeforeStartTime {
	private RoomOutOfOrderPlanningPage outOfOrderPage;

	@Test(groups = {"ACCEPTANCE", "UI"})
	public void testOutOfOrderIsNotCreatedIfEndTimeOccursBeforeStartTime() throws JSONException, 
	MalformedURLException, IOException {
		
		//Getting Out Of Order data from an excel file
		ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
		List<Map<String, String>> testData = excelReader.getMapValues("OutOfOrderPlanning");
		String roomName = testData.get(1).get("Room Name");
		String title = testData.get(1).get("Title");
		String startDate = testData.get(1).get("Start date (days to add)");
		String endDate = testData.get(1).get("End date (days to add)");
		String startTime = testData.get(1).get("Start time (minutes to add)");
		String endTime = testData.get(1).get("End time (minutes to add)");

		//Out Or Order creation
		HomeAdminPage homeAdminPage = new HomeAdminPage(); 
		RoomsPage roomsPage = homeAdminPage.clickConferenceRoomsLink();
		RoomInfoPage roomInfoPage = roomsPage.doubleClickOverRoomName(roomName);
		outOfOrderPage = roomInfoPage.clickOutOfOrderPlanningLink();
		outOfOrderPage = outOfOrderPage
				.setStartDateWithCalendar(startDate)
				.setEndDateWithCalendar(endDate)
				.setStartTime(startTime)
				.setEndTime(endTime)
				.clickSaveWithErrorBtn();

		//Assertion for TC27
		Assert.assertFalse(RootRestMethods.isOutOfOrderCreated(roomName, title));

		//Assertion for TC36
		Assert.assertTrue(outOfOrderPage.isErrorMessagePresent());
		Assert.assertTrue(outOfOrderPage.isToGreaterThanFromErrorDisplayed());
	}

	@AfterMethod(groups = {"ACCEPTANCE", "UI"})
	public void closeOutOfOrderPage() {
		outOfOrderPage.clickCancelBtn();
	}
}
