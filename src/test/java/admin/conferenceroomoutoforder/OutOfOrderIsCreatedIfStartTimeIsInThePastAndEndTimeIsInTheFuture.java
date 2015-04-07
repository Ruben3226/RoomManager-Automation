package test.java.admin.conferenceroomoutoforder;

import static main.java.utils.AppConfigConstants.EXCEL_INPUT_DATA;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import main.java.pages.admin.HomeAdminPage;
import main.java.pages.admin.conferencerooms.RoomInfoPage;
import main.java.pages.admin.conferencerooms.RoomOutOfOrderPage;
import main.java.pages.admin.conferencerooms.RoomsPage;
import main.java.rest.RoomManagerRestMethods;
import main.java.utils.readers.ExcelReader;

/**
 * TC20: Verify an Out Of Order can be created when the start date is in the past but 
 * the end date is in the future
 * @author Yesica Acha
 *
 */
public class OutOfOrderIsCreatedIfStartTimeIsInThePastAndEndTimeIsInTheFuture {
	
	//Getting Out Of Order data from an excel file
	private ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	private List<Map<String, String>> testData = excelReader.getMapValues("OutOfOrderPlanning");
	private String roomName = testData.get(9).get("Room Name");
	private String title = testData.get(9).get("Title");
	
	@Test(groups = "FUNCTIONAL")
	public void testOutOfOrderIsCreatedIfStartTimeIsInThePastAndEndTimeIsInTheFuture() throws JSONException, MalformedURLException, IOException {
		String startDate = testData.get(9).get("Start date (days to add)");
		String endDate = testData.get(9).get("End date (days to add)");
		String startTime = testData.get(9).get("Start time (minutes to add)");
		String endTime = testData.get(9).get("End time (minutes to add)");
		String description = testData.get(9).get("Description");

		//Out Of Oder creation
		HomeAdminPage homeAdminPage = new HomeAdminPage(); 
		RoomsPage roomsPage = homeAdminPage.clickConferenceRoomsLink();
		RoomInfoPage roomInfoPage = roomsPage.doubleClickOverRoomName(roomName);
		RoomOutOfOrderPage outOfOrderPage = roomInfoPage.clickOutOfOrderPlanningLink();
		roomsPage = outOfOrderPage
				.setOutOfOrderPeriodInformation(startDate, endDate, startTime, endTime, title, 
						description)
				.activateOutOfOrder()
				.clickSaveOutOfOrderBtn();

		//Assertion for TC20
		Assert.assertTrue(RoomManagerRestMethods.isOutOfOrderCreated(roomName, title));
		Assert.assertTrue(roomsPage.isMessagePresent());
		Assert.assertTrue(roomsPage.isOutOfOrderSuccessfullyCreatedMessageDisplayed());
		Assert.assertTrue(roomsPage.isOutOfOrderIconDisplayed(roomName));
	}

	@AfterClass(groups = "FUNCTIONAL")
	public void deleteOutOfOrder() throws MalformedURLException, IOException{
		RoomManagerRestMethods.deleteOutOfOrder(roomName, title);
	}
}
