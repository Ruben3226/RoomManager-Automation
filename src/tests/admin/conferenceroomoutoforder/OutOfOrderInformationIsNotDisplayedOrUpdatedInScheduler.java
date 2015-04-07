package tests.admin.conferenceroomoutoforder;

import static framework.common.AppConfigConstants.EXCEL_INPUT_DATA;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import framework.pages.admin.HomeAdminPage;
import framework.pages.admin.conferencerooms.RoomInfoPage;
import framework.pages.admin.conferencerooms.RoomOutOfOrderPlanningPage;
import framework.pages.admin.conferencerooms.RoomsPage;
import framework.pages.tablet.HomeTabletPage;
import framework.pages.tablet.SchedulePage;
import framework.pages.tablet.SettingsPage;
import framework.rest.RootRestMethods;
import framework.utils.DataProviders;
import framework.utils.readers.ExcelReader;

/**
 * TC12: Verify a created Out Of Order's information is not displayed in the Scheduler by 
 * clicking on it in Scheduler page in the Tablet
 * TC13: Verify a created Out Of Order's information cannot be changed in the Scheduler Page 
 * in the Tablet
 * @author Yesica Acha
 *
 */
public class OutOfOrderInformationIsNotDisplayedOrUpdatedInScheduler {
	
	//Getting Out Of Order data from an excel file
	private ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	private List<Map<String, String>> testData = excelReader.getMapValues("OutOfOrderPlanning");
	private String roomName = testData.get(2).get("Room Name");
	private String title = testData.get(2).get("Title");
	
	@BeforeClass(groups = "ACCEPTANCE")
	public void selectRoomInTablet() {
		
		//Selecting a room in Tablet
		HomeTabletPage homeTabletPage = new HomeTabletPage();
		SettingsPage settingsPage = homeTabletPage.clickSettingsBtn();
		homeTabletPage = settingsPage.selectRoom(roomName);
	}
	
	@Test(dataProvider = "OutOfOrderData", dataProviderClass = DataProviders.class, 
			groups = "ACCEPTANCE")
	public void testOutOfOrderInformationIsNotDisplayedOrUpdatedInScheduler(String description, 
			String startDate, String endDate, String startTime, String endTime) {
		
		//Out Of Order Creation in Admin
		HomeAdminPage homeAdminPage = new HomeAdminPage(); 
		RoomsPage roomsPage = homeAdminPage.clickConferenceRoomsLink();
		RoomInfoPage roomInfoPage = roomsPage.doubleClickOverRoomName(roomName);
		RoomOutOfOrderPlanningPage outOfOrderPage = roomInfoPage.clickOutOfOrderPlanningLink();
		roomsPage = outOfOrderPage
				.setOutOfOrderPeriodInformation(startDate, endDate, startTime, endTime, title, description)
				.activateOutOfOrder()
				.clickSaveOutOfOrderBtn();
		
		//Openning Tablet for assertions
		HomeTabletPage homeTabletPage = new HomeTabletPage();
		SchedulePage schedulerPage = homeTabletPage
				.clickScheduleBtn()
				.clickOverOutOfOrder(title);
		
		
		
		//Assertion for TC12
		Assert.assertTrue(schedulerPage.getMeetingOrganizerValue().isEmpty());
		Assert.assertTrue(schedulerPage.getMeetingSubjectValue().isEmpty());
		
		//Assertion for TC13
		Assert.assertFalse(schedulerPage.isUpdateBtnPresent());
	}
	
	@AfterMethod(groups = "ACCEPTANCE")
	public void deleteOutOfOrder() throws MalformedURLException, IOException{
		RootRestMethods.deleteOutOfOrder(roomName, title);
	}
}
