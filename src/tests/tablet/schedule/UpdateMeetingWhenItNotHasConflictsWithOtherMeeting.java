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
 * TC11: Verify that an existing meeting can be updated if it doesn't have a 
 * conflict with another meeting
 * @author Asael Calizaya
 *
 */
public class UpdateMeetingWhenItNotHasConflictsWithOtherMeeting {	
	private ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	private List<Map<String, String>> meetingData = excelReader.getMapValues("MeetingData");
	private String password = meetingData.get(1).get("Password");

	private String organizer = meetingData.get(1).get("Organizer");
	private String roomName = meetingData.get(1).get("Room");
	private String authentication = organizer + ":" + password;
	private String subject;
	private String newSubject;

	@BeforeMethod(groups = "ACCEPTANCE")
	public void createMeetingsPrecondition() {
		subject = meetingData.get(1).get("Subject");
		String startTime = meetingData.get(1).get("Start time");
		String endTime = meetingData.get(1).get("End time");
		String attendee = meetingData.get(1).get("Attendee");
		String body = meetingData.get(1).get("Body");

		MeetingMethods meeting = new MeetingMethods();
		meeting.createMeetingFromHome(organizer, subject, startTime, endTime, 
				attendee, body, password);
	}

	@Test(groups = "ACCEPTANCE")
	public void testUpdateMeetingWhenItNotHasConflictsWithOtherMeeting() {
		newSubject = meetingData.get(4).get("Subject");
		String newStartTime = meetingData.get(4).get("Start time");
		String newEndTime = meetingData.get(4).get("End time");
		String newAttendee = meetingData.get(4).get("Attendee");
		String newBody = meetingData.get(4).get("Body");
		HomeTabletPage homeTabletPage = new HomeTabletPage();
		SchedulePage schedulePage = homeTabletPage.clickScheduleBtn();
		schedulePage
		.clickOverMeetingCreated(subject)
		.setSubjectTxtBox(newSubject)
		.setStartTimeDate(newStartTime)
		.setEndTimeDate(newEndTime)
		.setAttendeeTxtBoxPressingEnter(newAttendee)
		.setBodyTxtBox(newBody)
		.clickUpdateBtn()
		.setPasswordTxtBox(password)
		.clickOkButton();

		Assert.assertTrue(schedulePage.isMessageMeetingUpdatedDisplayed());
	}

	@AfterMethod(groups = "ACCEPTANCE")
	public void deleteMeetings() throws MalformedURLException, IOException {
		RootRestMethods.deleteMeeting(roomName, newSubject, authentication);
	}
}
