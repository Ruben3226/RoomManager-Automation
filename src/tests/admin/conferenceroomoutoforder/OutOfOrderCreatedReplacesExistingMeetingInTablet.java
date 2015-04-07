package tests.admin.conferenceroomoutoforder;

import static framework.common.AppConfigConstants.EXCEL_INPUT_DATA;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import framework.pages.admin.HomeAdminPage;
import framework.pages.admin.conferencerooms.RoomInfoPage;
import framework.pages.admin.conferencerooms.RoomOutOfOrderPlanningPage;
import framework.pages.admin.conferencerooms.RoomsPage;
import framework.pages.tablet.HomeTabletPage;
import framework.pages.tablet.SchedulePage;
import framework.pages.tablet.SearchPage;
import framework.pages.tablet.SettingsPage;
import framework.rest.RootRestMethods;
import framework.utils.DataProviders;
import framework.utils.readers.ExcelReader;

/**
 * TC14: Verify that an Out Of Order created in the same time a scheduled meeting replaces 
 * the meeting in Tablet's Scheduler 
 * TC15: Verify that an Out Of Order created in the same time a scheduled meeting replaces 
 * the meeting in Search Page in Tablet
 * @author Yesica Acha
 *
 */
public class OutOfOrderCreatedReplacesExistingMeetingInTablet {
	
	//Getting Out Of Order data from an excel file
	private ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	private List<Map<String, String>> outOfOrderData = excelReader.getMapValues("OutOfOrderPlanning");
	private String roomName = outOfOrderData.get(10).get("Room Name");
	private String title = outOfOrderData.get(10).get("Title");
	
	//Getting Meeting data from an excel file
	private List<Map<String, String>> meetingData = excelReader.getMapValues("MeetingData");
	private String organizer = meetingData.get(7).get("Organizer");
	private String password = meetingData.get(7).get("Password");
	private String meetingSubject;

	@BeforeClass(groups = "ACCEPTANCE")
	public void selectRoomInTablet() {
		HomeTabletPage homeTabletPage = new HomeTabletPage();
		SettingsPage settingsPage = homeTabletPage.clickSettingsBtn();
		homeTabletPage = settingsPage.selectRoom(roomName);
	}

	@Test(dataProvider = "OutOfOrderAndMeetingData", dataProviderClass = DataProviders.class, 
			groups = "ACCEPTANCE")
	public void testOutOfOrderCreatedReplacesExistingMeetingInTablet(String subject, 
			String meetingStartTime, String meetingEndTime, String outStartTime, String outEndTime) {
		String attendees = meetingData.get(7).get("Attendee");
		meetingSubject = subject;

		//Meeting creation in tablet
		HomeTabletPage homeTabletPage = new HomeTabletPage();
		SchedulePage schedulePage = homeTabletPage
				.clickScheduleBtn()
				.createMeeting(organizer, meetingSubject, meetingStartTime, meetingEndTime, 
						attendees, password);

		//Out Of Order Creation in Admin
		HomeAdminPage homeAdminPage = new HomeAdminPage();
		RoomsPage roomsPage = homeAdminPage.clickConferenceRoomsLink();
		RoomInfoPage roomInfoPage = roomsPage.doubleClickOverRoomName(roomName);
		RoomOutOfOrderPlanningPage outOfOrderPage = roomInfoPage.clickOutOfOrderPlanningLink();
		roomsPage = outOfOrderPage
				.setStartTime(outStartTime)
				.setEndTime(outEndTime)
				.setTitleTxtBox(title)
				.activateOutOfOrder()
				.clickSaveBtn();

		//Assertion for TC14
		homeTabletPage = new HomeTabletPage();
		schedulePage = homeTabletPage.clickScheduleBtn();
		Assert.assertTrue(schedulePage.isOutOfOrderBoxDisplayed(title));
		Assert.assertFalse(schedulePage.isMeetingBoxDisplayed(subject));

		//Assertion for TC15
		SearchPage searchPage = schedulePage.clickSearchBtn();
		Assert.assertTrue(searchPage.isOutOfOrderBoxDisplayed(title));
		Assert.assertFalse(searchPage.isMeetingBoxDisplayed(subject));
	}

	@AfterMethod(groups = "ACCEPTANCE")
	public void deleteOutOfOrderAndMeeting() throws MalformedURLException, IOException {
		RootRestMethods.deleteOutOfOrder(roomName, title);
		String authentication = organizer + ":" + password;
		RootRestMethods.deleteMeeting(roomName, meetingSubject, authentication);
	}
}
