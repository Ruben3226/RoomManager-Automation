package test.java.tablet.schedule;

import static main.java.utils.AppConfigConstants.EXCEL_INPUT_DATA;

import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import main.java.pages.tablet.HomeTabletPage;
import main.java.pages.tablet.SchedulePage;
import main.java.utils.readers.ExcelReader;

/**
 * TC10: Verify that a meeting cannot be created if the attendees are not specified
 * @author Asael Calizaya
 *
 */
public class AMeetingCannotBeCreatedWithoutAttendees {

	@Test(groups = "ACCEPTANCE")
	public void testAMeetingCannotBeCreatedWithoutAttendees() {
		ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
		List<Map<String, String>> meetingData  = excelReader.getMapValues("MeetingData");
		String organizer = meetingData.get(0).get("Organizer");
		String subject = meetingData.get(0).get("Subject");
		String body = meetingData.get(0).get("Body");

		HomeTabletPage homeTabletPage = new HomeTabletPage();
		SchedulePage schedulePage = homeTabletPage.clickScheduleBtn();
		schedulePage
		.setOrganizerTxtBox(organizer)
		.setSubjectTxtBox(subject)
		.setBodyTxtBox(body)
		.clickCreateBtn()
		.clickCancelButton();

		//Fails because the tool allows create meeting without attendees
		Assert.assertTrue(schedulePage.isErrorAttendeeLblDisplayed());
	}
}