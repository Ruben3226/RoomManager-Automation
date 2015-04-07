package tests.tablet.schedule;

import static framework.common.AppConfigConstants.EXCEL_INPUT_DATA;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import framework.pages.tablet.HomeTabletPage;
import framework.pages.tablet.SchedulePage;
import framework.rest.RootRestMethods;
import framework.utils.readers.ExcelReader;

/**
 * TC22: Verify that the schedule of a meeting can be changed by moving it inside the timeline
 * and click update
 * @author Jose Cabrera
 */
public class ScheduleOfAMeetingIsChangedByMovingItInsideTheTimeLineIfUpdateButtonIsClicked {
	private HomeTabletPage homeTabletPage = new HomeTabletPage();
	private SchedulePage schedulePage;
	private ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	private List<Map<String, String>> meetingData = excelReader.getMapValues("MeetingData");
	private String organizer = meetingData.get(1).get("Organizer");
	private String subject = meetingData.get(1).get("Subject");
	private String attendee = meetingData.get(1).get("Attendee");
	private String password = meetingData.get(1).get("Password");
	private String minStartTime = meetingData.get(1).get("Start time (minutes to add)");
	private String minEndTime = meetingData.get(1).get("End time (minutes to add)");
	
	@BeforeClass(groups = "UI")
	public void createNextMeeting() {
		schedulePage = homeTabletPage.clickScheduleBtn();
		schedulePage.createMeeting(organizer, subject, minStartTime, minEndTime, 
				attendee, password)
				.clickBackBtn();
	}
	
	@Test(groups = "UI")
	public void testMeetingIsChangedByMovingItInsideTheTimeLineWhenUpdateButtonIsClicked () {
		homeTabletPage.clickScheduleBtn();
		schedulePage.clickOverMeetingCreated(subject);
		String start = schedulePage.getStartTimeTxtBoxValue();
		schedulePage.moveMeeting(4000)
		.clickUpdateBtn().confirmCredentials(password);
		schedulePage.isMessageMeetingUpdatedDisplayed();
		schedulePage.clickOverMeetingCreated(subject);
		Assert.assertFalse(schedulePage.getStartTimeTxtBoxValue().equals(start));
	}

	@AfterClass(groups = "UI")
	public void toHome() throws MalformedURLException, IOException {
		String roomName = meetingData.get(1).get("Room");
		RootRestMethods.deleteMeeting(roomName, subject, organizer + ":" + password);
		schedulePage.clickBackBtn();
	}
}
