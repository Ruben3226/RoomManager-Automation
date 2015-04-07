package test.java.tablet.schedule;

import static main.java.utils.AppConfigConstants.EXCEL_INPUT_DATA;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.testng.annotations.Test;

import main.java.pages.tablet.HomeTabletPage;
import main.java.pages.tablet.SchedulePage;
import main.java.utils.readers.ExcelReader;

/**
 * TC34: Verify that attendees text box doesn't allow a different format than email
 * @author Asael Calizaya
 *
 */
public class AttenddesTxtBoxDoesNotAllowInvalidAttendees {

	@Test(groups = "UI")
	public void testAttenddesTxtBoxDoesNotAllowInvalidAttendees() {
		ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
		List<Map<String, String>> meetingData = excelReader.getMapValues("MeetingData");
		String organizer = meetingData.get(9).get("Organizer");
		String subject = meetingData.get(9).get("Subject");
		String startTime = meetingData.get(9).get("Start time");
		String endTime = meetingData.get(9).get("End time");
		String attendee = meetingData.get(9).get("Attendee");
		String body = meetingData.get(9).get("Body");

		HomeTabletPage homeTabletPage = new HomeTabletPage();
		SchedulePage schedulePage = homeTabletPage.clickScheduleBtn();
		schedulePage
		.setMeetingInformation(organizer, subject, startTime, endTime, attendee)
		.setBodyTxtBox(body)
		.clickCreateBtn();

		Assert.assertTrue(schedulePage.isErrorAttendeeInvalidLblDisplayed());
	}
}
