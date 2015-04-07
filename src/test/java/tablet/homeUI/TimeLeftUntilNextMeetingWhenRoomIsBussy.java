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
import org.testng.annotations.Test;

import main.java.pages.tablet.HomeTabletPage;
import main.java.pages.tablet.SchedulePage;
import main.java.rest.RoomManagerRestMethods;
import main.java.utils.readers.ExcelReader;

/**
 * TC04: This test case verifies that time left displayed in {Now}Tape 
 * is from current time until next meeting when a meeting is in progress in the room.
 * @author Eliana Navia
 * 
 */
public class TimeLeftUntilNextMeetingWhenRoomIsBussy {
	private Logger log = Logger.getLogger(getClass());
	private HomeTabletPage homeTabletPage = new HomeTabletPage();
	private SchedulePage schedulePage;

	private ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	private List<Map<String, String>> meetingData = excelReader.getMapValues("MeetingData");
	private String room = meetingData.get(0).get("Room");

	//Current meeting values
	private String organizerCurrentMeeting = meetingData.get(0).get("Organizer");
	private String subjectCurrentMeeting = meetingData.get(0).get("Subject");
	private String passwordCurrentMeeting = meetingData.get(0).get("Password");
	private String authenticationCurrentMeeting = organizerCurrentMeeting + ":" + passwordCurrentMeeting;

	//Next meeting values
	private String organizerNextMeeting = meetingData.get(1).get("Organizer");
	private String subjectNextMeeting = meetingData.get(1).get("Subject");
	private String passwordNextMeeting = meetingData.get(1).get("Password");
	private String authenticationNextMeeting = organizerNextMeeting + ":" + passwordNextMeeting;

	@Test (groups = "ACCEPTANCE")
	public void testTimeLeftUntilNextMeetingWhenRoomIsBussy() {
		schedulePage = homeTabletPage.clickScheduleBtn();

		//Current meeting creation 
		schedulePage.createMeeting(organizerCurrentMeeting, subjectCurrentMeeting, 
				meetingData.get(0).get("Start time (minutes to add)"),
				meetingData.get(0).get("End time (minutes to add)"),
				meetingData.get(0).get("Attendee"), passwordCurrentMeeting);

		//Next meeting creation 
		schedulePage.createMeeting(organizerNextMeeting, subjectNextMeeting, 
				meetingData.get(1).get("Start time (minutes to add)"),
				meetingData.get(1).get("End time (minutes to add)"),
				meetingData.get(1).get("Attendee"), passwordCurrentMeeting);

		schedulePage.clickOverMeetingCreated(subjectNextMeeting);
		String startTime =  schedulePage.getStartTimeTxtBoxValue().substring(0, 5);
		String endTime =  schedulePage.getEndTimeTxtBoxValue().substring(0, 5);	
		schedulePage.clickBackBtn();
		String actualTimeLeft = homeTabletPage.getTimeLeftLbl();
		String expectedTimeLeft = differenceBetweenTimes(getCurrentDate("HH:mm"), startTime);

		Assert.assertEquals(actualTimeLeft, expectedTimeLeft);

		/**Assertions when current meeting and next meeting have a buffer of time.*/
		String actualMessageNextTile = homeTabletPage.getNextTileLbl();
		String expectedMessageNextTile = "Available"; 

		//Available message is displayed in next tile
		Assert.assertEquals(actualMessageNextTile, expectedMessageNextTile);
		String actualColorNextTile = homeTabletPage.getNextTileColor();
		String expectedColorNextTile = meetingData.get(0).get("Available color"); 

		//Available color is displayed in next tile
		Assert.assertEquals(actualColorNextTile, expectedColorNextTile);	
		String actualStartTime = homeTabletPage.getStartTimeNextMeetingLbl();	
		String actualEndTime = homeTabletPage.getEndTimeNextMeetingLbl();

		//Verify that the interval is correctly displayed in next tile.
		Assert.assertEquals(actualStartTime, startTime);
		Assert.assertEquals(actualEndTime, endTime);
	}

	/**
	 * deleteMeeting
	 */
	@AfterClass (groups = "ACCEPTANCE")
	public void end() {
		PropertyConfigurator.configure("log4j.properties");
		try {
			RoomManagerRestMethods.deleteMeeting(room, subjectNextMeeting, authenticationNextMeeting);
			log.info("The meeting:" + subjectNextMeeting + "has been deleted successfully.");	

			RoomManagerRestMethods.deleteMeeting(room, subjectCurrentMeeting, authenticationCurrentMeeting);
			log.info("The meeting:" + subjectCurrentMeeting + "has been deleted successfully.");
		} catch (IOException e) {
			log.error(e.getMessage());
		}
	}
}
