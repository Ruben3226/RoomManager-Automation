package tests.admin.conferenceroomoutoforder;

import static framework.common.AppConfigConstants.EXCEL_INPUT_DATA;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import framework.pages.admin.HomeAdminPage;
import framework.pages.admin.conferencerooms.RoomInfoPage;
import framework.pages.admin.conferencerooms.RoomOutOfOrderPlanningPage;
import framework.pages.admin.conferencerooms.RoomsPage;
import framework.pages.tablet.HomeTabletPage;
import framework.pages.tablet.SettingsPage;
import framework.rest.RootRestMethods;
import framework.utils.readers.ExcelReader;

/**
 * TC11: Verify that an Out Of Order created in the current time is displayed in NOW tile 
 * in Tablet's HomePage
 * @author Yesica Acha
 *
 */
public class OutOfOrderCreatedInCurrentTimeIsDisplayedInHomeNowTile {
	
	//Getting Out Of Order data from an excel file
	private ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	private List<Map<String, String>> testData = excelReader.getMapValues("OutOfOrderPlanning");
	private String roomName = testData.get(3).get("Room Name");
	private String title = testData.get(3).get("Title");
		
	@Test(groups = "ACCEPTANCE")
	public void testOutOfOrderCreatedInCurrentTimeIsDisplayedInHomeNowTile() {
		String startDate = testData.get(3).get("Start date (days to add)");
		String endDate = testData.get(3).get("End date (days to add)");
		String startTime = testData.get(3).get("Start time (minutes to add)");
		String endTime = testData.get(3).get("End time (minutes to add)");
		String description = testData.get(3).get("Description");
		
		//Out Of Order Creation in Admin
		HomeAdminPage homeAdminPage = new HomeAdminPage(); 
		RoomsPage roomsPage = homeAdminPage.clickConferenceRoomsLink();
		RoomInfoPage roomInfoPage = roomsPage.doubleClickOverRoomName(roomName);
		RoomOutOfOrderPlanningPage outOfOrderPage = roomInfoPage.clickOutOfOrderPlanningLink();
		roomsPage = outOfOrderPage
				.setOutOfOrderPeriodInformation(startDate, endDate, startTime, endTime, title, 
						description)
				.activateOutOfOrder()
				.clickSaveOutOfOrderBtn();
		
		//Opening Tablet for assertions
		HomeTabletPage homeTabletPage = new HomeTabletPage();
		SettingsPage settingsPage = homeTabletPage.clickSettingsBtn();
		homeTabletPage = settingsPage.selectRoom(roomName);
				
		//Assertion for TC11
		Assert.assertEquals(homeTabletPage.getNowTileLbl(), title);
	}
	
	@AfterClass(groups = "ACCEPTANCE")
	public void deleteOutOfOrder() throws MalformedURLException, IOException{
		RootRestMethods.deleteOutOfOrder(roomName, title);
	}
}
