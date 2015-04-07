package test.java.tablet.schedule;

import static main.java.utils.AppConfigConstants.EXCEL_INPUT_DATA;

import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import main.java.pages.tablet.HomeTabletPage;
import main.java.pages.tablet.SchedulePage;
import main.java.selenium.MeetingMethods;
import main.java.utils.readers.ExcelReader;

/**
 * TC13: Verify that meetings can be removed by the organizer
 * @author Asael Calizaya
 *
 */
public class AMeetingCanBeDeleted {
	private ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	private List<Map<String, String>> meetingData = excelReader.getMapValues("MeetingData");
	private String password = meetingData.get(1).get("Password");
	private String subject;
	
	@BeforeMethod(groups = "ACCEPTANCE")
	public void createMeetingPreCondition() {
		MeetingMethods meeting = new MeetingMethods();
		subject = meetingData.get(1).get("Subject");
		String startTime = meetingData.get(1).get("Start time");
		String endTime = meetingData.get(1).get("End time");
		String attendee = meetingData.get(1).get("Attendee");
		String body = meetingData.get(1).get("Body");
		String organizer = meetingData.get(1).get("Organizer");	
		meeting.createMeetingFromHome(organizer, subject, startTime, endTime, 
				attendee, body, password);
	}

	@Test(groups = "ACCEPTANCE")
	public void testAMeetingCanBeDeleted() {
		HomeTabletPage homeTabletPage = new HomeTabletPage();
		SchedulePage schedulePage = homeTabletPage
				.clickScheduleBtn()
				.clickOverMeetingCreated(subject)
				.clickRemoveBtn()
				.confirmCredentials(password);

		Assert.assertTrue(schedulePage.isMessageMeetingDeletedDisplayed());
	}
}
