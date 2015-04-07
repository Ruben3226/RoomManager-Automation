package test.java.tablet.homeUI;

import static main.java.utils.AppConfigConstants.EXCEL_INPUT_DATA;
import static main.java.utils.TimeMethods.differenceBetweenTimes;
import static main.java.utils.TimeMethods.getCurrentDate;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import main.java.pages.tablet.HomeTabletPage;
import main.java.pages.tablet.SchedulePage;
import main.java.rest.RoomManagerRestMethods;
import main.java.utils.readers.ExcelReader;

/**
 * This TCs verify that {next} tile is set whit correct information
 * when a meeting is scheduled in the future in the room.
 * TC06: Interval hours of next meeting is the time set in "From" and "To" text boxes.
 * TC03: Time left displayed in now tape is current time - start time next meeting.
 * TC32: Verify the color of next tile.
 * @author Eliana Navia
 */
public class NextMeetingValuesVerifications {
	private Logger log = Logger.getLogger(getClass());
	private HomeTabletPage homeTabletPage = new HomeTabletPage();
	private SchedulePage schedulePage;

	private ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	private List<Map<String, String>> meetingData = excelReader.getMapValues("MeetingData");
	private String organizer = meetingData.get(1).get("Organizer");
	private String expectedMeetingSubject = meetingData.get(1).get("Subject");
	private String password = meetingData.get(1).get("Password");
	private String authentication = organizer + ":" + password;
	private String room = meetingData.get(0).get("Room");
	private String expectedStartTime = "";
	private String expectedEndTime = "";

	/**
	 * Meeting scheduled in the room.
	 */
	@BeforeClass (groups = "ACCEPTANCE")
	public void init() {
		schedulePage = homeTabletPage.clickScheduleBtn();
		schedulePage.createMeeting(organizer, expectedMeetingSubject, 
				meetingData.get(1).get("Start time (minutes to add)"), 
				meetingData.get(1).get("End time (minutes to add)"), 
				meetingData.get(1).get("Attendee"), password)
				.clickOverMeetingCreated(expectedMeetingSubject);
		expectedStartTime =  schedulePage.getStartTimeTxtBoxValue().substring(0, 5);
		expectedEndTime =  schedulePage.getEndTimeTxtBoxValue().substring(0, 5);
		schedulePage.clickBackBtn();	
	}

	/**
	 * TC06: This test case verifies that interval of beginning-end time 
	 * of next meeting scheduled for the room is displayed in the {Next} Tape.
	 * 
	 */
	@Test (groups = "ACCEPTANCE")
	public void testIntervalTimeDisplayedForNextMeetingInRoom() {
		String actualStartTime = homeTabletPage.getStartTimeNextMeetingLbl();	
		String actualEndTime = homeTabletPage.getEndTimeNextMeetingLbl();

		Assert.assertEquals(actualStartTime, expectedStartTime );
		Assert.assertEquals(actualEndTime, expectedEndTime );
	}

	/**
	 * TC03: This test case verifies that time left displayed in {Now}Tape 
	 * is from current time until next meeting when any meeting is in progress.
	 * 
	 */
	@Test (groups = "ACCEPTANCE")
	public void testTimeLeftDisplayTimeUntilNextMeetingWhenRoomIsFreeBeforeInNowTape() {
		String expectedTimeLeft = differenceBetweenTimes(getCurrentDate("HH:mm"),
				expectedStartTime);
		String actualTimeLeft = homeTabletPage.getTimeLeftLbl();

		//Current time - start time of next meeting
		//Fails the rest is less a minute.
		Assert.assertEquals(actualTimeLeft, expectedTimeLeft );
	}

	/**
	 * TC32: Verify that next title changes the color 
	 * when a meeting is set to occur after some minutes of current time in the room.
	 * 
	 */
	@Test (groups = "ACCEPTANCE")
	public void testBussyColorIsDisplayedOnNextTileWhenAMeetingIsSetInTheRoom() {
		String actualBusyColorValue = homeTabletPage.getNextTileColor();
		String expectedBusyColorValue =  meetingData.get(0).get("Busy next color");

		Assert.assertEquals(actualBusyColorValue, expectedBusyColorValue);
	}

	/**
	 * Delete meeting by rest API.
	 */
	@AfterClass (groups = "ACCEPTANCE")
	public void end() {
		PropertyConfigurator.configure("log4j.properties");
		try {
			RoomManagerRestMethods.deleteMeeting(room, expectedMeetingSubject, authentication);
			log.info("The meeting:" + expectedMeetingSubject + "has been deleted successfully.");
		} catch (IOException e) {
			log.error(e.getMessage());
		}
	}
}
