package test.java.admin.conferenceroomoutoforder;

import static main.java.utils.AppConfigConstants.EXCEL_INPUT_DATA;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import main.java.pages.admin.HomeAdminPage;
import main.java.pages.admin.conferencerooms.RoomInfoPage;
import main.java.pages.admin.conferencerooms.RoomOutOfOrderPage;
import main.java.pages.admin.conferencerooms.RoomsPage;
import main.java.pages.tablet.HomeTabletPage;
import main.java.pages.tablet.SchedulePage;
import main.java.pages.tablet.SearchPage;
import main.java.pages.tablet.SettingsPage;
import main.java.rest.RoomManagerRestMethods;
import main.java.utils.DataProviders;
import main.java.utils.readers.ExcelReader;

/**
 * TC07: Verify that an Out Of Order created is displayed in Scheduler page in Tablet
 * TC08: Verify that an Out Of Order created is displayed in Search page in Tablet
 * @author Yesica Acha
 *
 */
public class OutOfOrderCreatedIsDispayedInTabletTimeline {
	
	//Getting Out Of Order data from an excel file
	private ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	private List<Map<String, String>> testData = excelReader.getMapValues("OutOfOrderPlanning");
	private String roomName = testData.get(10).get("Room Name");
	private String title = testData.get(10).get("Title");

	@BeforeClass(groups = "ACCEPTANCE")
	public void selectRoomInTablet() {
		HomeTabletPage homeTabletPage = new HomeTabletPage();
		SettingsPage settingsPage = homeTabletPage.clickSettingsBtn();
		homeTabletPage = settingsPage.selectRoom(roomName);
	}
	
	@Test(dataProvider = "OutOfOrderData", dataProviderClass = DataProviders.class, 
			groups = "ACCEPTANCE")
	public void testOutOfOrderCreatedIsDispayedInTabletTimeline(String description, 
			String startDate, String endDate, String startTime, String endTime) {
		
		//Out Of Order Creation in Admin
		HomeAdminPage homeAdminPage = new HomeAdminPage(); 
		RoomsPage roomsPage = homeAdminPage.clickConferenceRoomsLink();
		RoomInfoPage roomInfoPage = roomsPage.doubleClickOverRoomName(roomName);
		RoomOutOfOrderPage outOfOrderPage = roomInfoPage.clickOutOfOrderPlanningLink();
		roomsPage = outOfOrderPage
				.setOutOfOrderPeriodInformation(startDate, endDate, startTime, endTime, title, 
						description)
				.activateOutOfOrder()
				.clickSaveOutOfOrderBtn();

		//Openning Tablet for assertions
		HomeTabletPage homeTabletPage = new HomeTabletPage();
		SchedulePage schedulePage = homeTabletPage.clickScheduleBtn();

		//Assertion for TC07
		Assert.assertTrue(schedulePage.isOutOfOrderBoxDisplayed(title));

		//Assertion for TC08
		SearchPage search = schedulePage.clickSearchBtn();
		Assert.assertTrue(search.isOutOfOrderBoxDisplayed(title));
	}

	@AfterMethod(groups = "ACCEPTANCE")
	public void deleteOutOfOrder() throws MalformedURLException, IOException{
		RoomManagerRestMethods.deleteOutOfOrder(roomName, title);
	}
}
