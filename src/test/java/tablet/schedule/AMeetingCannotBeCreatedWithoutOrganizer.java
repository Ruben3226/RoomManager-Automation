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
 * TC08: Verify that a meeting cannot be created if the organizer is not specified
 * @author Asael Calizaya
 *
 */
public class AMeetingCannotBeCreatedWithoutOrganizer {

	@Test(groups = "ACCEPTANCE")
	public void testAMeetingCannotBeCreatedWithoutOrganizer() {
		ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
		List<Map<String, String>> meetingData = excelReader.getMapValues("MeetingData");
		String subject = meetingData.get(0).get("Subject");
		String attendee = meetingData.get(0).get("Attendee");
		String body = meetingData.get(0).get("Body");

		HomeTabletPage homeTabletPage = new HomeTabletPage();
		SchedulePage schedulePage = homeTabletPage.clickScheduleBtn();
		schedulePage.setSubjectTxtBox(subject)
		.setAttendeeTxtBoxPressingEnter(attendee)
		.setBodyTxtBox(body)
		.clickCreateBtn();

		Assert.assertTrue(schedulePage.isErrorOrganizerLblDisplayed());
	}
}
