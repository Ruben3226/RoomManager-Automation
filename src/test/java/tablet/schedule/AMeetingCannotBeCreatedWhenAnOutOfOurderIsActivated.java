package test.java.tablet.schedule;

import static main.java.utils.AppConfigConstants.EXCEL_INPUT_DATA;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import main.java.pages.tablet.HomeTabletPage;
import main.java.pages.tablet.SchedulePage;
import main.java.rest.RoomManagerRestMethods;
import main.java.selenium.MeetingMethods;
import main.java.utils.readers.ExcelReader;

/**
 * TC23: Verify that a meeting cannot be created when an Out Of Order is activated
 * @author Asael Calizaya
 *
 */
public class AMeetingCannotBeCreatedWhenAnOutOfOurderIsActivated {
	private ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	private List<Map<String, String>> meetingData = excelReader.getMapValues("MeetingData");
	private List<Map<String, String>> outOfOrderData = excelReader.getMapValues("OutOfOrderPlanning");

	//data from excel to create an out of order and delete it
	private String roomName = outOfOrderData.get(11).get("Room Name");	
	private String title = outOfOrderData.get(11).get("Title");

	@BeforeMethod(groups = "FUNCTIONAL")
	public void createOutOfOrderPrecondition() {
		String startDate = outOfOrderData.get(11).get("Start date (days to add)");
		String endDate = outOfOrderData.get(11).get("End date (days to add)");
		String startTime = outOfOrderData.get(11).get("Start time (minutes to add)");
		String endTime = outOfOrderData.get(11).get("End time (minutes to add)");
		String description = outOfOrderData.get(11).get("Description");	
		MeetingMethods meeting = new MeetingMethods();
		meeting.createAnOutOfOrder(startDate, endDate, startTime, endTime, 
				title, description, roomName);
	}

	@Test(groups = "FUNCTIONAL")
	public void testAMeetingCannotBeCreatedWhenAnOutOfOurderIsActivated() {
		String meetingOrganizer = meetingData.get(10).get("Organizer");
		String meetingSubject = meetingData.get(10).get("Subject");
		String meetingStartTime = meetingData.get(10).get("Start time");
		String meetingEndTime = meetingData.get(10).get("End time");
		String meetingAttendee = meetingData.get(10).get("Attendee");
		String password = meetingData.get(10).get("Password");
		HomeTabletPage homeTabletPage = new HomeTabletPage();
		SchedulePage schedulePage = homeTabletPage
				.clickScheduleBtn()
				.createMeeting(meetingOrganizer, meetingSubject, meetingStartTime,
						meetingEndTime, meetingAttendee, password);

		//Assertion TC23
		Assert.assertTrue(schedulePage.isMessageErrorPopUpDisplayed());

		//Fails because the error message is not related with the error
		Assert.assertTrue(schedulePage.isMessageErrorCreationMeetingPopUpDisplayed());
	}

	@AfterMethod(groups = "FUNCTIONAL")
	public void deleteMeetings() throws MalformedURLException, IOException {
		RoomManagerRestMethods.deleteOutOfOrder(roomName, title);
	}
}
