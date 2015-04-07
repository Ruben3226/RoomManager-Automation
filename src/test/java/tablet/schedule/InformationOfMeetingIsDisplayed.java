package test.java.tablet.schedule;

import static main.java.utils.AppConfigConstants.EXCEL_INPUT_DATA;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import main.java.pages.tablet.HomeTabletPage;
import main.java.pages.tablet.SchedulePage;
import main.java.rest.RoomManagerRestMethods;
import main.java.selenium.MeetingMethods;
import main.java.utils.readers.ExcelReader;

/**
 * TC19: Verify by clicking the meeting in the timeLine the information of the meeting is displayed
 * TC20: Verify the body content is saved in the meeting information
 * @author Asael Calizaya
 *
 */
public class InformationOfMeetingIsDisplayed {
	private ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	private List<Map<String, String>> meetingData = excelReader.getMapValues("MeetingData");
	private String password = meetingData.get(1).get("Password");
	private String organizer = meetingData.get(1).get("Organizer");
	private String subject = meetingData.get(1).get("Subject");
	private String startTime = meetingData.get(1).get("Start time");
	private String endTime = meetingData.get(1).get("End time");
	private String attendee = meetingData.get(1).get("Attendee");
	private String body = meetingData.get(1).get("Body");

	private String roomName = meetingData.get(1).get("Room");
	private String authentication = organizer + ":" + password;

	@BeforeMethod(groups = "FUNCTIONAL")
	public void createMeetingPreCondition() {
		MeetingMethods meeting = new MeetingMethods();
		meeting.createMeetingFromHome(organizer, subject, startTime, endTime, 
				attendee, body, password);
	}

	@Test(groups = "FUNCTIONAL")
	public void testInformationOfMeetingIsDisplayed() {
		HomeTabletPage homeTabletPage = new HomeTabletPage();
		SchedulePage schedulePage = homeTabletPage
				.clickScheduleBtn()
				.clickOverMeetingCreated(subject);
		String actualOrganizer = schedulePage.getMeetingOrganizerValue();
		String actualSubject = schedulePage.getMeetingSubjectValue();
		String actualAttendee = schedulePage.getEmailAttendeeLblValue(attendee);
		String actualBody = schedulePage.getBodyTxtBoxValue();

		//Assertion TC19
		Assert.assertEquals(actualOrganizer, organizer);
		Assert.assertEquals(actualSubject, subject);
		Assert.assertEquals(actualAttendee, attendee);

		//Assertion TC20
		//Fails because the application does not save the body information 
		Assert.assertEquals(actualBody, body);
	}

	@AfterMethod(groups = "FUNCTIONAL")
	public void deleteMeetings() throws MalformedURLException, IOException {
		RoomManagerRestMethods.deleteMeeting(roomName, subject, authentication);
	}
}
