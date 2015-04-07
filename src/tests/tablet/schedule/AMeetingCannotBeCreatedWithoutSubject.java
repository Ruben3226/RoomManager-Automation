package tests.tablet.schedule;

import static framework.common.AppConfigConstants.EXCEL_INPUT_DATA;

import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import framework.pages.tablet.HomeTabletPage;
import framework.pages.tablet.SchedulePage;
import framework.utils.readers.ExcelReader;

/**
 * TC09: Verify that a meeting cannot be created if the subject is not specified
 * @author Asael Calizaya
 *
 */
public class AMeetingCannotBeCreatedWithoutSubject {

	@Test(groups = "ACCEPTANCE")
	public void testAMeetingCannotBeCreatedWithoutSubject() {
		ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
		List<Map<String, String>> meetingData = excelReader.getMapValues("MeetingData");
		String organizer = meetingData.get(0).get("Organizer");
		String attendee = meetingData.get(0).get("Attendee");
		String body = meetingData.get(0).get("Body");

		HomeTabletPage homeTabletPage = new HomeTabletPage();
		SchedulePage schedulePage = homeTabletPage.clickScheduleBtn();
		schedulePage.setOrganizerTxtBox(organizer)
		.setAttendeeTxtBoxPressingEnter(attendee)
		.setBodyTxtBox(body)
		.clickCreateBtn();

		Assert.assertTrue(schedulePage.isErrorSubjectLblDisplayed());
	}
}
