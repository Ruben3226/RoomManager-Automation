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
 * TC26: Verify an Out Of Order cannot be created when the start time and end time are in the past
 * TC35: Verify when the start time and end time are in the past the following message is displayed 
 * "Cannot establish out of order as a past event"
 * @author Yesica Acha
 *
 */
public class OutOfOrderIsNotCreatedIfStartTimeAndEndTimeAreInThePast {
	private RoomOutOfOrderPlanningPage outOfOrderPage;
	
	@Test(groups = {"ACCEPTANCE", "UI"})
	public void testOutOfOrderIsNotCreatedIfStartTimeAndEndTimeAreInThePast() throws JSONException, 
	MalformedURLException, IOException {
		
		//Getting Out Of Order data from an excel file
		ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
		List<Map<String, String>> testData = excelReader.getMapValues("OutOfOrderPlanning");
		String roomName = testData.get(5).get("Room Name");
		String title = testData.get(5).get("Title");
		String startTime = testData.get(5).get("Start time (minutes to add)");
		String endTime = testData.get(5).get("End time (minutes to add)");
		
		//Out Of Order creation
		HomeAdminPage homeAdminPage = new HomeAdminPage(); 
		RoomsPage roomsPage = homeAdminPage.clickConferenceRoomsLink();
		RoomInfoPage roomInfoPage = roomsPage.doubleClickOverRoomName(roomName);
		outOfOrderPage = roomInfoPage.clickOutOfOrderPlanningLink();
		outOfOrderPage = outOfOrderPage
				.setStartTime(startTime)
				.setEndTime(endTime)
				.clickSaveWithErrorBtn();

		//Assertion for TC26
		Assert.assertFalse(RootRestMethods.isOutOfOrderCreated(roomName, title));
		
		//Assertion for TC35
		Assert.assertTrue(outOfOrderPage.isErrorMessagePresent());
		Assert.assertTrue(outOfOrderPage.isOutOfOrderInThePastErrorDisplayed());
	}
	
	@AfterMethod(groups = {"ACCEPTANCE", "UI"})
	public void closeOutOfOrderPage() {
		outOfOrderPage.clickCancelBtn();
	}
}
