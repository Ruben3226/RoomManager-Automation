package tests.tablet.schedule;

import static framework.common.AppConfigConstants.EXCEL_INPUT_DATA;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import framework.common.MeetingMethods;
import framework.pages.tablet.HomeTabletPage;
import framework.pages.tablet.SchedulePage;
import framework.rest.RootRestMethods;
import framework.utils.readers.ExcelReader;

/**
 * TC15: Verify that a meeting cannot be created when it has conflicts with the 
 * schedule of another meeting the schedule of another meeting
 * @author Asael Calizaya
 *
 */
public class AMeetingCannotBeCreatedWhenItHasConflictsWithAnotherMeeting {
	private ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	private List<Map<String, String>> meetingData = excelReader.getMapValues("MeetingData");

	//Subject to create meeting as a precondition and to delete that
	private String subject = meetingData.get(1).get("Subject");

	private String organizer = meetingData.get(1).get("Organizer");
	private String roomName = meetingData.get(1).get("Room");
	private String password = meetingData.get(1).get("Password");
	private String authentication = organizer + ":" + password;

	@BeforeMethod(groups = "FUNCTIONAL")
	public void createMeetingPreCondition() {
		String startTime = meetingData.get(1).get("Start time");
		String endTime = meetingData.get(1).get("End time");
		String attendee = meetingData.get(1).get("Attendee");
		String body = meetingData.get(1).get("Body");
		MeetingMethods meeting = new MeetingMethods();
		meeting.createMeetingFromHome(organizer, subject, startTime, endTime, 
				attendee, body, password);
	}

	@Test(groups = "FUNCTIONAL")
	public void testAMeetingCannotBeCreatedWhenItHasConflictsWithAnotherMeeting() {
		String newOrganizer = meetingData.get(2).get("Organizer");
		String newSubject = meetingData.get(2).get("Subject");
		String newStartTime = meetingData.get(2).get("Start time");
		String newEndTime = meetingData.get(2).get("End time");
		String newAttendee = meetingData.get(2).get("Attendee");
		String newBody = meetingData.get(2).get("Body");
		String newPassword = meetingData.get(2).get("Password");
		HomeTabletPage homeTabletPage = new HomeTabletPage();
		SchedulePage schedulePage = homeTabletPage.clickScheduleBtn();
		schedulePage.createMeeting(newOrganizer, newSubject, newStartTime, 
				newEndTime, newAttendee, newBody, newPassword);

		//Fails because the error message is not related with the error
		Assert.assertTrue(schedulePage.isMessageErrorCreationMeetingPopUpDisplayed());
	}

	@AfterMethod(groups = "FUNCTIONAL")
	public void deleteMeetigs() throws MalformedURLException, IOException {
		RootRestMethods.deleteMeeting(roomName, subject, authentication);
	}
}
