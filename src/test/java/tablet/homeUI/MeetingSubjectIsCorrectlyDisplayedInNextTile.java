package test.java.tablet.homeUI;

import static main.java.utils.AppConfigConstants.EXCEL_INPUT_DATA;

import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import main.java.pages.tablet.HomeTabletPage;
import main.java.pages.tablet.SchedulePage;
import main.java.utils.DataProviders;
import main.java.utils.readers.ExcelReader;

/**
 * Data provider to verify that {next} tile displays correctly the meeting subject
 * and they are stored correctly in the data base.
 * @author Eliana Navia
 *
 */
public class MeetingSubjectIsCorrectlyDisplayedInNextTile {
	private HomeTabletPage homeTabletPage = new HomeTabletPage();
	private SchedulePage schedulePage;

	private ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	private List<Map<String, String>> meetingData = excelReader.getMapValues("MeetingData");
	private String password = meetingData.get(0).get("Password");

	/**
	 * All cases the TCs fails because they are stored incorrectly in the data base.
	 * @param organizer
	 * @param subject 1.special characters, 2. ASCII characters, 3. HTML tags, 
	 * 4. space in the beginning, 5. Angular format, 6. JavaScript alert,  
	 * @param minutesFrom
	 * @param minutesTo
	 * @param attendee
	 * @param password
	 */
	@Test(dataProvider="NextMeetingDataProvider", dataProviderClass = DataProviders.class, 
			groups = "Acceptance")
	public void testMeetingSubjectIsDisplayedCorrectlyInNextTile(String organizer, String subject, 
			String minutesFrom, String minutesTo, String attendee, String password) {
		schedulePage = homeTabletPage.clickScheduleBtn();
		schedulePage.createMeeting(organizer, subject, minutesFrom, minutesTo, attendee, password)
		.clickBackBtn();

		Assert.assertEquals(homeTabletPage.getNextTileLbl(), subject);
	}	

	/**
	 * Delete the meeting by UI.
	 */
	@AfterMethod (groups = "ACCEPTANCE")
	public void end() {
		homeTabletPage.clickScheduleBtn();
		schedulePage.clickItemRangeMeeting()
		.clickRemoveBtn()
		.confirmCredentials(password)
		.isMessageMeetingDeletedDisplayed();
		homeTabletPage = schedulePage.clickBackBtn();
	}
}
