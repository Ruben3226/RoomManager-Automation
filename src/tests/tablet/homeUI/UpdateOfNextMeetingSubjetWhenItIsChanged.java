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
 * TC14: This test case verifies that next meeting subject is updated in {Next} Tile 
 * when it is updated.
 * @author Eliana Navia
 * 
 */
public class UpdateOfNextMeetingSubjetWhenItIsChanged {
	private Logger log = Logger.getLogger(getClass());
	private HomeTabletPage homeTabletPage = new HomeTabletPage();
	private SchedulePage schedulePage;

	private ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	private List<Map<String, String>> meetingData = excelReader.getMapValues("MeetingData");	
	private String organizer = meetingData.get(1).get("Organizer");
	private String subject = meetingData.get(1).get("Subject");
	private String password = meetingData.get(1).get("Password");
	private String room = meetingData.get(0).get("Room");
	private String authentication = organizer + ":" + password;

	private String expectedNewSubject = meetingData.get(2).get("Subject");

	@BeforeClass (groups = "ACCEPTANCE")
	public void init() {
		schedulePage = homeTabletPage.clickScheduleBtn();
		schedulePage.createMeeting(organizer, subject, 
				meetingData.get(1).get("Start time (minutes to add)"), 
				meetingData.get(1).get("End time (minutes to add)"),  
				meetingData.get(1).get("Attendee"), password);	
	}

	@Test (groups = "ACCEPTANCE")
	public void testUpdateOfNextMeetingSubjetWhenItIsChanged() {
		schedulePage
		.clickOverMeetingCreated(subject)
		.setSubjectTxtBox(expectedNewSubject)
		.clickUpdateBtn()
		.confirmCredentials(password)
		.isMessageMeetingUpdatedDisplayed();
		schedulePage.clickBackBtn();
		String actualMeetingNameUpdated = homeTabletPage.getNextTileLbl();

		Assert.assertEquals(actualMeetingNameUpdated, expectedNewSubject);
	}

	/**
	 * Delete meeting by rest API.
	 */
	@AfterClass (groups = "ACCEPTANCE")
	public void end() {
		PropertyConfigurator.configure("log4j.properties");
		try {
			RootRestMethods.deleteMeeting(room, expectedNewSubject, authentication);
			log.info("The meeting:" + expectedNewSubject + "has been deleted successfully.");
		} catch (IOException e) {
			log.error(e.getMessage());
		}
	}
}
