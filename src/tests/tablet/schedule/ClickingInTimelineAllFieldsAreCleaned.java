package tests.tablet.schedule;

import static framework.common.AppConfigConstants.EXCEL_INPUT_DATA;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import framework.common.MeetingMethods;
import framework.pages.tablet.HomeTabletPage;
import framework.pages.tablet.SchedulePage;
import framework.rest.RootRestMethods;
import framework.utils.readers.ExcelReader;

/**
 * TC35: Verify that clicking in the timeline erases all the content in 
 * Organizer, Subject, Attendees and body textboxes
 * @author Asael Calizaya
 *
 */
public class ClickingInTimelineAllFieldsAreCleaned {
	private ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	private List<Map<String, String>> meetingData = excelReader.getMapValues("MeetingData"); 

	//Subject to create meeting as a precondition and to delete
	private String subject = meetingData.get(1).get("Subject");

	private String roomName = meetingData.get(1).get("Room");
	private String organizer = meetingData.get(1).get("Organizer");
	private String password = meetingData.get(1).get("Password");	
	private String authentication = organizer + ":" + password;

	@BeforeMethod(groups = "UI")
	public void createMeetingPreCondition() {
		String startTime = meetingData.get(1).get("Start time");
		String endTime = meetingData.get(1).get("End time");
		String attendee = meetingData.get(1).get("Attendee");
		String body = meetingData.get(1).get("Body");
		MeetingMethods meeting = new MeetingMethods();
		meeting.createMeetingFromHome(organizer, subject, startTime, endTime, 
				attendee, body, password);
	}

	@Test(groups = "UI")
	public void testClickingInTimeLineAllFiledsAreCleaned() {
		HomeTabletPage homeTabletPage = new HomeTabletPage();
		SchedulePage schedulePage = homeTabletPage.clickScheduleBtn();
		schedulePage
		.clickOverMeetingCreated(subject)
		.clickOverTimeline();

		String actualOrganizer = schedulePage.getMeetingOrganizerValue();
		String actualSubject = schedulePage.getMeetingSubjectValue();
		String actualAttendee = schedulePage.getEmailAttendeeTxtBoxValue();
		String actualBody = schedulePage.getBodyTxtBoxValue();

		Assert.assertEquals(actualOrganizer, "");
		Assert.assertEquals(actualSubject, "");
		Assert.assertEquals(actualAttendee, "");
		Assert.assertEquals(actualBody, "");
	}

	@AfterMethod(groups = "UI")
	public void deleteMeetings() throws MalformedURLException, IOException {
		RootRestMethods.deleteMeeting(roomName, subject, authentication);
	}
}
