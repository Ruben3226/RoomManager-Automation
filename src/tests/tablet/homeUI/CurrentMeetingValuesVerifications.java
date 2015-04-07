package tests.tablet.homeUI;

import static framework.common.AppConfigConstants.EXCEL_INPUT_DATA;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import framework.pages.tablet.HomeTabletPage;
import framework.pages.tablet.SchedulePage;
import framework.rest.RootRestMethods;
import framework.utils.readers.ExcelReader;

/**
 * This TCs verify that {now} tile is set whit correct information
 * when a meeting is created in the current time.
 * TC01: Meeting subject is correctly displayed in {Now} tile.
 * TC02: Meeting organizer  is correctly displayed in {Now} tile.
 * TC31: Now tile is red when the room has a meeting is in progress.
 * TC30: Next tile is green when any meeting is scheduled after meeting in progress.
 * TC18: Available message is displayed in {Next} tile when the room has a meeting is in progress.
 * TC07: Interval displayed in next tile is: end time current meeting - 0:00.
 * @author Eliana Navia
 * 
 */
public class CurrentMeetingValuesVerifications {
	private Logger log = Logger.getLogger(getClass());
	private HomeTabletPage homeTabletPage = new HomeTabletPage();
	private SchedulePage schedulePage;

	private ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	private List<Map<String, String>> meetingData = excelReader.getMapValues("MeetingData");
	private String organizer = meetingData.get(0).get("Organizer");
	private String expectedMeetingSubject = meetingData.get(0).get("Subject");
	private String password = meetingData.get(0).get("Password");
	private String authentication = organizer + ":" + password;

	private String expectedMeetingOrganizer = "";
	private String expectedEndTime = "";

	/**
	 * 1. Meeting in progress in the room.
	 * 2. Room free after meeting in progress.
	 */
	@BeforeClass (groups = "ACCEPTANCE")
	public void init() {
		schedulePage = homeTabletPage.clickScheduleBtn();
		schedulePage.createMeeting(organizer, expectedMeetingSubject, 
				meetingData.get(0).get("Start time (minutes to add)"),
				meetingData.get(0).get("End time (minutes to add)"),
				meetingData.get(0).get("Attendee"), password)
				.clickOverMeetingCreated(expectedMeetingSubject);

		expectedMeetingOrganizer = schedulePage.getMeetingOrganizerValue();
		expectedEndTime = schedulePage.getEndTimeTxtBoxValue().substring(0, 5);
		schedulePage.clickBackBtn();
	}

	/**
	 * TC01: Verify that meeting subject is displayed on Now Tile 
	 * when a meeting is in progress in the room.
	 * 
	 */
	@Test (groups = "ACCEPTANCE")
	public void testCurrentMeetingSubjectDisplayedOnNowTile() {
		String actualMeetingSubject = homeTabletPage.getNowTileLbl();

		Assert.assertEquals(actualMeetingSubject, expectedMeetingSubject);
	}

	/**
	 * TC02: This test case verifies that meeting organizer is displayed on Now Tile 
	 * when a meeting is in progress in the room.
	 * 
	 */
	@Test (groups = "ACCEPTANCE")
	public void testCurrentMeetingOrganizerIsDisplayedOnNowTile() {
		String actualMeetingOrganizer = homeTabletPage.getCurrentMeetingOrganizerLbl();

		Assert.assertEquals(actualMeetingOrganizer, expectedMeetingOrganizer);
	}

	/**
	 * TC31: Verify that now title changes the color to red 
	 * when a meeting is in progress in the room.
	 * 
	 */
	@Test (groups = "ACCEPTANCE")
	public void testBussyColorIsDisplayedOnNowTileWhenAMeetingIsInProgressInTheRoom() {
		String actualBusyColorValue = homeTabletPage.getNowTileColor();
		String expectedBusyColorValue = meetingData.get(0).get("Busy now color");

		Assert.assertEquals(actualBusyColorValue, expectedBusyColorValue);
	}

	/**
	 * TC30: This test case verifies that next title changes the color to green 
	 * when the room is free EOD after a meeting in progress in the room.
	 * 
	 */
	@Test (groups = "ACCEPTANCE")
	public void testAvailableColorIsDisplayedOnNextTileWhenTheToomIsFreeEODAfterAMeeting() {
		String actualAvailableColorValue = homeTabletPage.getNextTileColor();
		String expectedAvailableColorValue = meetingData.get(0).get("Available color"); 

		Assert.assertEquals(actualAvailableColorValue, expectedAvailableColorValue);
	}

	/**
	 * TC18: This test case verifies that {Available} message is displayed on {Next} tile 
	 * when a meeting is occurring and the room has no more meetings scheduled until EOD.
	 * 
	 */
	@Test (groups = "ACCEPTANCE")
	public void testAvailableMessageDisplayedInNextTileWhenRoomIsFreeAfterAMeeting() {
		String actualNextTileValue = homeTabletPage.getNextTileLbl();
		String expectedNextTileValue = "Available";

		Assert.assertEquals(actualNextTileValue, expectedNextTileValue );
	}

	/**
	 * TC07: This test case verifies that interval of hours displayed on {Next} tape are 
	 * from end meeting time to midnight when a meeting is in progress in the room and 
	 * the room has no more meetings scheduled until EOD.
	 * 
	 */
	@Test (groups = "ACCEPTANCE")
	public void testIntervalTimeDisplayedForNextMeetingInRoomIsFromEndTimeCurrentMeetingToMidnight() {
		String actualStartTime = homeTabletPage.getStartTimeNextMeetingLbl();

		//Verify that actual start time of interval is equal to end time of current meeting.
		Assert.assertEquals(actualStartTime, expectedEndTime);

		String actualEndTime = homeTabletPage.getEndTimeNextMeetingLbl();
		String expectedEndTime = "0:00";

		//Verify that actual end time of interval is equal to 0:00.
		Assert.assertEquals(actualEndTime, expectedEndTime);
	}	

	/**
	 * Delete meeting by rest API.
	 */
	@AfterClass (groups = "ACCEPTANCE")
	public void end() {
		PropertyConfigurator.configure("log4j.properties");
		try {
			RootRestMethods.deleteMeeting(meetingData.get(0).get("Room"), expectedMeetingSubject, 
					authentication);
			log.info("The meeting:" + expectedMeetingSubject + "has been deleted successfully.");
		} catch (IOException e) {
			log.error(e.getMessage());
		}
	}
}
