package test.java.tablet.schedule;

import static main.java.utils.AppConfigConstants.EXCEL_INPUT_DATA;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import main.java.pages.tablet.HomeTabletPage;
import main.java.pages.tablet.SchedulePage;
import main.java.rest.RoomManagerRestMethods;
import main.java.utils.readers.ExcelReader;

/**
 * TC18: Verify that a meeting can be created when the body is empty
 * TC28: Verify that pressing enter confirms the name of an attendee
 * TC29: Verify that pressing semicolon confirms the name of an attendee
 * @author Asael Calizaya
 *
 */
public class CreateMeetingWhenNotHasBodyAndConfirmsAttendeePressingEnterOrSemicolon {
	private ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	private List<Map<String, String>> meetingData = excelReader.getMapValues("MeetingData");

	private String subject = meetingData.get(0).get("Subject");
	private String organizer = meetingData.get(0).get("Organizer");	
	private String password = meetingData.get(0).get("Password");
	private String roomName = meetingData.get(1).get("Room");
	private String authentication = organizer + ":" + password;

	@Test(groups = {"FUNCTIONAL", "UI"})
	public void testCreateMeetingWhenNotHasBodyAndConfirmsAttendeePressingEnterOrSemicolon() {
		String startTime = meetingData.get(0).get("Start time");
		String endTime = meetingData.get(0).get("End time");
		String attendee1 = meetingData.get(0).get("Attendee");
		String attendee2 = meetingData.get(0).get("Attendee");
		HomeTabletPage homeTabletPage = new HomeTabletPage();
		SchedulePage schedulePage = homeTabletPage.clickScheduleBtn();
		schedulePage
		.setOrganizerTxtBox(organizer)
		.setSubjectTxtBox(subject)
		.setStartTimeDate(startTime)
		.setEndTimeDate(endTime);

		//Assertion TC28
		schedulePage.setAttendeeTxtBoxPressingEnter(attendee1);
		Assert.assertEquals(schedulePage.getEmailAttendeeLblValue(attendee1), attendee1);

		//Assertion TC29
		schedulePage.setAttendeeTxtBoxPressingSemicolon(attendee2);
		Assert.assertEquals(schedulePage.getEmailAttendeeLblValue(attendee2), attendee2);		

		schedulePage.clickCreateBtn()
		.setPasswordTxtBox(password)
		.clickOkButton();

		//Assertion TC18
		Assert.assertTrue(schedulePage.isMessageMeetingCreatedDisplayed());
	}

	@AfterMethod(groups = {"FUNCTIONAL", "UI"})
	public void deleteMeetings() throws MalformedURLException, IOException {
		RoomManagerRestMethods.deleteMeeting(roomName, subject, authentication);
	}
}
