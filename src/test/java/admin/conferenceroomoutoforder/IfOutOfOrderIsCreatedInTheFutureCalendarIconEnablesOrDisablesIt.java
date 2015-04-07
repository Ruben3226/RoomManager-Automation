package test.java.admin.conferenceroomoutoforder;

import static main.java.utils.AppConfigConstants.EXCEL_INPUT_DATA;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import main.java.pages.admin.HomeAdminPage;
import main.java.pages.admin.conferencerooms.RoomInfoPage;
import main.java.pages.admin.conferencerooms.RoomOutOfOrderPage;
import main.java.pages.admin.conferencerooms.RoomsPage;
import main.java.rest.RoomManagerRestMethods;
import main.java.utils.readers.ExcelReader;

/**
 * TC04: Verify that Calendar icon appears in out of order column when the schedule to be 
 * out of order is not in the time yet
 * TC16:Verify that clicking the icon clock in "Out of Order" column  disables out of order 
 * time of a Room
 * TC17:Verify that clicking the icon clock in "Out of Order" column  enables out of order 
 * time of a Room
 * @author Yesica Acha
 *
 */
public class IfOutOfOrderIsCreatedInTheFutureCalendarIconEnablesOrDisablesIt {
	
	//Getting Out Of Order data from an excel file
	private ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	private List<Map<String, String>> testData = excelReader.getMapValues("OutOfOrderPlanning");
	private String roomName1 = testData.get(2).get("Room Name");
	private String title = testData.get(2).get("Title");

	@Test(groups = {"ACCEPTANCE", "FUNCTIONAL"})
	public void testIfOutOfOrderIsCreatedInTheFutureCalendarIconEnablesOrDisablesIt() {
		String startDate = testData.get(2).get("Start date (days to add)");
		String endDate = testData.get(2).get("End date (days to add)");
		String startTime = testData.get(2).get("Start time (minutes to add)");
		String endTime = testData.get(2).get("End time (minutes to add)");
		String description = testData.get(2).get("Description");
		String expectedIcon = testData.get(2).get("Icon").toLowerCase();
		
		//Out Of Order creation
		HomeAdminPage homeAdminPage = new HomeAdminPage(); 
		RoomsPage roomsPage = homeAdminPage.clickConferenceRoomsLink();
		RoomInfoPage roomInfoPage = roomsPage.doubleClickOverRoomName(roomName1);
		RoomOutOfOrderPage outOfOrderPage = roomInfoPage.clickOutOfOrderPlanningLink();
		roomsPage = outOfOrderPage
				.setOutOfOrderPeriodInformation(startDate, endDate, startTime, endTime, title,
						description)
				.clickSaveOutOfOrderBtn();

		//Assertion for TC04
		Assert.assertTrue(roomsPage.getOutOfOrderIconClass(roomName1).contains(expectedIcon));	

		//Assertion for TC16
		roomsPage.clickOutOfOrderIcon(roomName1);
		Assert.assertTrue(RoomManagerRestMethods.isOutOfOrderEnable(roomName1, title));

		//Assertion for TC17
		roomsPage.clickOutOfOrderIcon(roomName1);
		Assert.assertFalse(RoomManagerRestMethods.isOutOfOrderEnable(roomName1, title));
	}

	@AfterMethod(groups = {"ACCEPTANCE", "FUNCTIONAL"})
	public void deleteOutOfOrder() throws MalformedURLException, IOException {
		RoomManagerRestMethods.deleteOutOfOrder(roomName1, title);
	}
}
