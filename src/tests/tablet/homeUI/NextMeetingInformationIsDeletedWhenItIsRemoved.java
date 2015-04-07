package tests.tablet.homeUI;

import static framework.common.AppConfigConstants.EXCEL_INPUT_DATA;

import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import framework.pages.tablet.HomeTabletPage;
import framework.pages.tablet.SchedulePage;
import framework.utils.readers.ExcelReader;

/**
 * TC16: This test case verifies that the next meeting information is removed of Next Tile 
 * when it is deleted.
 * @author Eliana Navia
 * 
 */
public class NextMeetingInformationIsDeletedWhenItIsRemoved {
	private HomeTabletPage homeTabletPage = new HomeTabletPage();
	private SchedulePage schedulePage;

	private ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	private List<Map<String, String>> meetingData = excelReader.getMapValues("MeetingData");
	private String expectedMeetingSubject = meetingData.get(1).get("Subject");
	private String password = meetingData.get(1).get("Password");

	@Test (groups = "ACCEPTANCE")
	public void testNextMeetingInformationIsDeletedWhenItIsRemoved() {
		schedulePage = homeTabletPage.clickScheduleBtn();
		schedulePage.createMeeting(meetingData.get(1).get("Organizer"), expectedMeetingSubject, 
				meetingData.get(1).get("Start time (minutes to add)"), 
				meetingData.get(1).get("End time (minutes to add)"), 
				meetingData.get(1).get("Attendee"), password);

		schedulePage.clickBackBtn();

		String actualMeetingSubject = homeTabletPage.getNextTileLbl();

		//Verify that next meeting subject is displayed in {next} to be sure that was created.
		Assert.assertEquals(actualMeetingSubject, expectedMeetingSubject);

		homeTabletPage.clickScheduleBtn();

		schedulePage.deleteMeeting(expectedMeetingSubject, password);
		schedulePage.clickBackBtn();

		actualMeetingSubject = homeTabletPage.getNextTileLbl();

		//Verify that the next meeting subject has been deleted in {next} tile. 
		Assert.assertNotEquals(actualMeetingSubject, expectedMeetingSubject);	
	}
}
