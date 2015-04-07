package test.java.tablet.schedule;

import static main.java.utils.AppConfigConstants.EXCEL_INPUT_DATA;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import main.java.pages.tablet.HomeTabletPage;
import main.java.pages.tablet.SchedulePage;
import main.java.rest.RoomManagerRestMethods;
import main.java.utils.readers.ExcelReader;

/**
 * TC07: Verify that a meeting can be created when the room is available
 * @author Asael Calizaya
 *
 */
public class CreateMeetingWhenRoomIsAvailable {
	private ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	private List<Map<String, String>> meetingData = excelReader.getMapValues("MeetingData");
	
	private String organizer = meetingData.get(0).get("Organizer");
	private String subject = meetingData.get(0).get("Subject");
	private String password = meetingData.get(0).get("Password");
	private String roomName = meetingData.get(1).get("Room");
	private String authentication = organizer + ":" + password;
	
	@Test(groups = "ACCEPTANCE")
	public void testCreateMeetingWhenRoomIsAvailable() {
		String startTime = meetingData.get(0).get("Start time");
		String endTime = meetingData.get(0).get("End time");
		String attendee = meetingData.get(0).get("Attendee");
		String body = meetingData.get(0).get("Body");
		
		HomeTabletPage homeTabletPage = new HomeTabletPage();
		SchedulePage schedulePage = homeTabletPage.clickScheduleBtn();
		schedulePage.createMeeting(organizer, subject, startTime, endTime, attendee, 
				body, password);

		Assert.assertTrue(schedulePage.isMessageMeetingCreatedDisplayed());
	}

	@AfterMethod(groups = "ACCEPTANCE")
	public void deleteMeetings() throws MalformedURLException, IOException {
		RoomManagerRestMethods.deleteMeeting(roomName, subject, authentication);
	}
}
