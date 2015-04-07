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
import main.java.utils.TimeMethods;
import main.java.utils.readers.ExcelReader;

/**
 * TC02: Verify Out of Order's information is displayed in Out Of Order planning if it is 
 * for current time or for future time
 * @author Yesica Acha
 *
 */
public class IfOfOutOfOrderIsCreatedItsInformationIsDisplayedInOutOfOrderPage {
	private RoomOutOfOrderPage outOfOrderPage;
	
	//Getting Out Of Order data from an excel file
	private ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	private List<Map<String, String>> testData = excelReader.getMapValues("OutOfOrderPlanning");
	private String roomName = testData.get(3).get("Room Name");
	private String title = testData.get(3).get("Title");
	
	@Test(groups = "ACCEPTANCE")
	public void testIfOfOutOfOrderIsCreatedItsInformationIsDisplayedInOutOfOrderPage() {
		String startDate = testData.get(3).get("Start date (days to add)");
		String endDate = testData.get(3).get("End date (days to add)");
		String startTime = testData.get(3).get("Start time (minutes to add)");
		String endTime = testData.get(3).get("End time (minutes to add)");
		String description = testData.get(3).get("Description");
		
		//Getting expected values for date and time
		String expectedStartDate = TimeMethods.getDate(Integer.parseInt(startDate), "MMM dd YYYY");
		String expectedEndDate = TimeMethods.getDate(Integer.parseInt(endDate), "MMM dd YYYY");
		String expectedStartHour = TimeMethods.getTime(Integer.parseInt(startTime), "hh");
		String expectedStartMinute = TimeMethods.getTime(Integer.parseInt(startTime), "mm");
		String expectedEndHour = TimeMethods.getTime(Integer.parseInt(endTime), "hh");
		String expectedEndMinute = TimeMethods.getTime(Integer.parseInt(endTime), "mm");
		
		//Out Of Order Creation
		HomeAdminPage homeAdminPage = new HomeAdminPage(); 
		RoomsPage roomsPage = homeAdminPage.clickConferenceRoomsLink();
		RoomInfoPage roomInfoPage = roomsPage.doubleClickOverRoomName(roomName);
		outOfOrderPage = roomInfoPage.clickOutOfOrderPlanningLink();
		roomsPage = outOfOrderPage
				.setOutOfOrderPeriodInformation(startDate, endDate, startTime, endTime, title, 
						description)
				.activateOutOfOrder()
				.clickSaveOutOfOrderBtn();
		roomInfoPage = roomsPage.doubleClickOverRoomName(roomName);
		outOfOrderPage = roomInfoPage.clickOutOfOrderPlanningLink();

		//Assertion for TC02
		Assert.assertEquals(outOfOrderPage.getStartDateValue(), expectedStartDate);
		Assert.assertEquals(outOfOrderPage.getEndDateValue(), expectedEndDate);
		Assert.assertEquals(outOfOrderPage.getStartHourValue(), expectedStartHour);
		Assert.assertEquals(Integer.parseInt(outOfOrderPage.getStartMinuteValue()), 
				Integer.parseInt(expectedStartMinute), 1);
		Assert.assertEquals(outOfOrderPage.getEndHourValue(), expectedEndHour);
		Assert.assertEquals(Integer.parseInt(outOfOrderPage.getEndMinuteValue()), 
				Integer.parseInt(expectedEndMinute), 1);
		Assert.assertEquals(outOfOrderPage.getTitleValue(), title);
		Assert.assertEquals(outOfOrderPage.getDescriptionValue(), description);
		Assert.assertTrue(outOfOrderPage.isOutOfOrderActivated());
	}

	@AfterMethod(groups = "ACCEPTANCE")
	public void deleteOutOfOrder() throws MalformedURLException, IOException {
		outOfOrderPage.clickCancelBtn();
		RoomManagerRestMethods.deleteOutOfOrder(roomName, title);
	}
}
