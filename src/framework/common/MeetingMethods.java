package framework.common;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import framework.pages.admin.HomeAdminPage;
import framework.pages.tablet.HomeTabletPage;
import framework.pages.tablet.SchedulePage;
import framework.selenium.SeleniumDriverManager;

/**
 * This class contains the methods to manage meetings, and to creates Out Of Order
 * @author Asael Calizaya
 *
 */
public class MeetingMethods {
	WebDriver driver;
	HomeTabletPage home;

	/**
	 * [AC] This method initialize the driver
	 */
	public MeetingMethods() {
		driver = SeleniumDriverManager.getManager().getDriver();
		PageFactory.initElements(driver, this);
	}	

	/**
	 * [AC] This method creates a meeting
	 * @param organizer
	 * @param subject
	 * @param startTime
	 * @param endTime
	 * @param attendee
	 * @param body
	 * @param password
	 */
	public void createMeetingFromHome(String organizer, String subject, String startTime, 
			String endTime, String attendee, String body, String password) {
		SchedulePage schedule = new SchedulePage();
		home = new HomeTabletPage();
		home.clickScheduleBtn()
		.createMeeting(organizer, subject, startTime, endTime, attendee, body, password);
		schedule.clickBackBtn();
	}

	/**
	 * [YA]This method creates a meeting 
	 * @param organizer: Organizer name
	 * @param subject: Meeting's subject
	 * @param starTimeMinutes: Minutes to add or subtract to current time to get startTime
	 * @param endTimeMinutes: Minutes to add or subtract to current time to get endTime
	 * @param attendee: Attendees for the meeting
	 * @param body: Meeting's body message
	 * @param password: Organizer's password
	 */
	public SchedulePage createMeeting(String organizer, String subject, String starTimeMinutes,
			String endTimeMinutes, String attendees, String body, String password) {
		home = new HomeTabletPage();
		home.clickScheduleBtn()
		.createMeeting(organizer, subject, starTimeMinutes, endTimeMinutes, 
				attendees, password);	
		return new SchedulePage();
	}

	/**
	 * [AC] This method creates an Out Of Order
	 * @param startDate
	 * @param endDate
	 * @param startTime
	 * @param endTime
	 * @param title
	 * @param description
	 * @param roomName
	 */
	public void createAnOutOfOrder(String startDate, String endDate, String startTime, 
			String endTime, String title, String description, String roomName) {
		HomeAdminPage homePage = new HomeAdminPage();
		homePage.clickConferenceRoomsLink()
		.doubleClickOverRoomName(roomName)
		.clickOutOfOrderPlanningLink()
		.setOutOfOrderPeriodInformation(startDate, endDate, startTime, 
				endTime, title, description)
				.activateOutOfOrder()
				.clickSaveBtn();
	}
}
