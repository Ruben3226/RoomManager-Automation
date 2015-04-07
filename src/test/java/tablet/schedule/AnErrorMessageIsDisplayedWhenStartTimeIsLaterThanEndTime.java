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
 * TC48: Verify that a message is displayed if the start time is later than end time
 * @author Asael Calizaya
 *
 */
public class AnErrorMessageIsDisplayedWhenStartTimeIsLaterThanEndTime {

	@Test(groups = "FUNCTIONAL")
	public void testAnErrorMessageIsDisplayedWhenStartTimeIsLaterThanEndTime() {
		ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
		List<Map<String, String>> meetingData = excelReader.getMapValues("MeetingData");
		String organizer = meetingData.get(8).get("Organizer");
		String subject = meetingData.get(8).get("Subject");
		String startTime = meetingData.get(8).get("Start time");
		String endTime = meetingData.get(8).get("End time");

		HomeTabletPage homeTabletPage = new HomeTabletPage();
		SchedulePage schedulePage = homeTabletPage.clickScheduleBtn();
		schedulePage
		.setOrganizerTxtBox(organizer)
		.setSubjectTxtBox(subject)
		.setStartTimeDate(startTime)
		.setEndTimeDate(endTime)
		.clickCreateBtn();

		Assert.assertTrue(schedulePage.isMessageOfTimeErrorDisplayed());
	}
}
