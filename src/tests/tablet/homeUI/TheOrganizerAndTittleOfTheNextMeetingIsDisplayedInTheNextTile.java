package tests.tablet.homeUI;

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
 * TC12: Verify that the name of the next meeting in the room is displayed in Next Tile
 * TC13: Verify that the organizer of the next meeting in the room is displayed in the Next Tile
 * @author Jose Cabrera
 */
public class TheOrganizerAndTittleOfTheNextMeetingIsDisplayedInTheNextTile {
	private ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	private List<Map<String, String>> meetingData = excelReader.getMapValues("MeetingData");
	private String organizer = meetingData.get(1).get("Organizer");
	private String subject = meetingData.get(1).get("Subject");
	private String attendee = meetingData.get(1).get("Attendee");
	private String password = meetingData.get(1).get("Password");
	HomeTabletPage homeTabletPage = new HomeTabletPage();
	@BeforeClass(groups = "UI")
	public void createNextMeeting() {
		String minStartTime = meetingData.get(1).get("Start time (minutes to add)");
		String minEndTime = meetingData.get(1).get("End time (minutes to add)");
		SchedulePage schedule = homeTabletPage.clickScheduleBtn();
		schedule.createMeeting(organizer, subject, minStartTime, minEndTime, 
				attendee, password).clickBackBtn();
	}
	
	@Test(groups = "UI")
	public void testOrganizerOfTheNextMeetingIsDisplayedInTheNextTile () {
		Assert.assertTrue(homeTabletPage.getNextMeetingOrganizerNameLbl().equals(organizer)
				&&homeTabletPage.getNextTileLbl().equals(subject));
	}

	@AfterClass(groups = "UI")
	public void toHome() throws MalformedURLException, IOException {
		String roomName = meetingData.get(0).get("Room");
		RootRestMethods.deleteMeeting(roomName, subject, organizer+":"+password);
	}
}
