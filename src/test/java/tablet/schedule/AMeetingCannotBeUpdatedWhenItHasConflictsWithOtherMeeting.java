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
 * TC12: Verify that an existing meeting cannot be updated if it has conflicts with another meeting
 * @author Asael Calizaya
 *
 */
public class AMeetingCannotBeUpdatedWhenItHasConflictsWithOtherMeeting {
	private ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	private List<Map<String, String>> meetingData = excelReader.getMapValues("MeetingData");
	private String nameMeeting1 = meetingData.get(0).get("Subject");
	private String organizer1 = meetingData.get(0).get("Organizer");
	private String password1 = meetingData.get(0).get("Password");
	
	private String nameMeeting2 = meetingData.get(1).get("Subject");
	private String organizer2 = meetingData.get(1).get("Organizer");	
	private String password2 = meetingData.get(1).get("Password");
	
	private String roomName = meetingData.get(1).get("Room");
	private String authentication1 = organizer1 + ":" + password1;
	private String authentication2 = organizer2 + ":" + password2;

	@BeforeMethod(groups = "ACCEPTANCE")
	public void createMeetingsPreCondition() {
		MeetingMethods preCondition = new MeetingMethods();

		//Meeting 01
		preCondition.createMeetingFromHome(meetingData.get(0).get("Organizer"),
				meetingData.get(0).get("Subject"), meetingData.get(0).get("Start time"),
				meetingData.get(0).get("End time"), meetingData.get(0).get("Attendee"),
				meetingData.get(0).get("Body"), meetingData.get(0).get("Password"));

		//Meeting 02
		preCondition.createMeetingFromHome(meetingData.get(1).get("Organizer"),
				meetingData.get(1).get("Subject"), meetingData.get(1).get("Start time"),
				meetingData.get(1).get("End time"), meetingData.get(1).get("Attendee"),
				meetingData.get(1).get("Body"), meetingData.get(1).get("Password"));
	}

	@Test(groups = "ACCEPTANCE")
	public void testAMeetingCannotBeUpdatedWhenItHasConflictsWithOtherMeeting() {
		String subject = meetingData.get(2).get("Subject");
		String startTime = meetingData.get(2).get("Start time");
		String endTime = meetingData.get(2).get("End time");
		String attendee = meetingData.get(2).get("Attendee");
		String body = meetingData.get(2).get("Body");
		String password = meetingData.get(2).get("Password");
		
		HomeTabletPage homeTabletPage = new HomeTabletPage();
		SchedulePage schedulePage = homeTabletPage.clickScheduleBtn();
		schedulePage
		.clickOverMeetingCreated(nameMeeting1)
		.setSubjectTxtBox(subject)
		.setStartTimeDate(startTime)
		.setEndTimeDate(endTime)
		.setAttendeeTxtBoxPressingEnter(attendee)
		.setBodyTxtBox(body)
		.clickUpdateBtn()
		.confirmCredentials(password);

		//Fails because the error message is not related with the error
		Assert.assertTrue(schedulePage.isMessageErrorUpdateMeetingPopUpDisplayed());
	}

	@AfterMethod(groups = "ACCEPTANCE")
	public void deleteMeetings() throws MalformedURLException, IOException {
		RoomManagerRestMethods.deleteMeeting(roomName, nameMeeting2, authentication2);
		RoomManagerRestMethods.deleteMeeting(roomName, nameMeeting1, authentication1);
	}
}
