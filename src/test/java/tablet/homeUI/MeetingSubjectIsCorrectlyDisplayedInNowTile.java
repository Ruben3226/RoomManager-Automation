package test.java.tablet.homeUI;

import static main.java.utils.AppConfigConstants.EXCEL_INPUT_DATA;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import main.java.pages.tablet.HomeTabletPage;
import main.java.pages.tablet.SchedulePage;
import main.java.rest.RoomManagerRestMethods;
import main.java.utils.DataProviders;
import main.java.utils.readers.ExcelReader;

/**
 * Data provider to verify that {now} tile displays correctly the meeting subject.
 * @author Eliana Navia
 *
 */
public class MeetingSubjectIsCorrectlyDisplayedInNowTile {	
	private HomeTabletPage homeTabletPage = new HomeTabletPage();
	private SchedulePage schedulePage;

	private ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	private List<Map<String, String>> meetingData = excelReader.getMapValues("MeetingData");
	private String password = meetingData.get(0).get("Password");
	private String organizer = meetingData.get(0).get("Organizer");
	private String room = meetingData.get(0).get("Room");
	private String authentication = organizer + ":" + password;

	/**
	 * @param organizer
	 * @param subject 1: Letters and numbers less than 10 characters, 2: 100 characters, 3: XML syntax 
	 * @param minutesFrom
	 * @param minutesTo
	 * @param attendee
	 * @param password
	 * @throws IOException 
	 * @throws MalformedURLException 
	 */
	@Test(dataProvider="CurrentMeetingDataProvider", dataProviderClass = DataProviders.class, 
			groups = "Acceptance")
	public void testMeetingSubjectIsDisplayedCorrectlyOnNowTile(String organizer, String subject, 
			String minutesFrom, String minutesTo, String attendee, String password) 
					throws MalformedURLException, IOException {

		schedulePage = homeTabletPage.clickScheduleBtn();

		schedulePage.createMeeting(organizer, subject, minutesFrom, minutesTo, attendee, password)
		.clickBackBtn();

		Assert.assertEquals(homeTabletPage.getNowTileLbl(), subject);
		RoomManagerRestMethods.deleteMeeting(room, subject, authentication);
	}
}
