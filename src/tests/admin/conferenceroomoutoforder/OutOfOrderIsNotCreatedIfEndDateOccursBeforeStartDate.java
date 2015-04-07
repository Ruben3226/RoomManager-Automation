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
 * TC28: Verify an Out Of Order cannot be created when end date (day, month, year) occurs 
 * before start date
 * TC37: Verify when end date (day, month, year) occurs before start date an error message 
 * that says "'To' field must be greater than 'From' field" is displayed.
 * @author Yesica Acha
 *
 */
public class OutOfOrderIsNotCreatedIfEndDateOccursBeforeStartDate {
	private RoomOutOfOrderPlanningPage outOfOrderPage;

	@Test(groups = {"FUNCTIONAL", "UI"})
	public void testOutOfOrderIsNotCreatedIfEndDateOccursBeforeStartDate() throws JSONException, 
	MalformedURLException, IOException {
		
		//Getting Out Of Order data from an excel file
		ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
		List<Map<String, String>> testData = excelReader.getMapValues("OutOfOrderPlanning");
		String title = testData.get(0).get("Title");
		String startDate = testData.get(0).get("Start date (days to add)");
		String endDate = testData.get(0).get("End date (days to add)");
		String roomName = testData.get(0).get("Room Name");

		//Out Of Order creation
		HomeAdminPage homeAdminPage = new HomeAdminPage(); 
		RoomsPage roomsPage = homeAdminPage.clickConferenceRoomsLink();
		RoomInfoPage roomInfoPage = roomsPage.doubleClickOverRoomName(roomName);
		outOfOrderPage = roomInfoPage.clickOutOfOrderPlanningLink();
		outOfOrderPage = outOfOrderPage
				.setStartDateWithCalendar(startDate)
				.setEndDateWithCalendar(endDate)
				.clickSaveWithErrorBtn();

		//Assertion for TC28
		Assert.assertFalse(RootRestMethods.isOutOfOrderCreated(roomName, title));

		//Assertion for TC37
		Assert.assertTrue(outOfOrderPage.isErrorMessagePresent());
		Assert.assertTrue(outOfOrderPage.isToGreaterThanFromErrorDisplayed());
	}

	@AfterMethod(groups = {"FUNCTIONAL", "UI"})
	public void closeOutOfOrderPage() {
		outOfOrderPage.clickCancelBtn();
	}
}
