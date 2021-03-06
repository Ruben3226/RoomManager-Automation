package test.java.admin.conferenceroomoutoforder;

import static main.java.utils.AppConfigConstants.EXCEL_INPUT_DATA;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import main.java.pages.admin.HomeAdminPage;
import main.java.pages.admin.conferencerooms.RoomInfoPage;
import main.java.pages.admin.conferencerooms.RoomOutOfOrderPage;
import main.java.pages.admin.conferencerooms.RoomsPage;
import main.java.pages.tablet.HomeTabletPage;
import main.java.pages.tablet.SettingsPage;
import main.java.rest.RoomManagerRestMethods;
import main.java.utils.readers.ExcelReader;

/**
 * TC10: Verify that an Out Of Order created in the future is displayed NEXT tile in Tablet's 
 * HomePage if the room has no meetings scheduled
 * @author Yesica Acha
 *
 */
public class OutOfOrderCreatedInFutureTimeIsDisplayedInHomeNextTile {
	
	//Getting Out Of Order data from an excel file
	private ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	private List<Map<String, String>> testData = excelReader.getMapValues("OutOfOrderPlanning");
	private String roomName = testData.get(2).get("Room Name");
	private String title = testData.get(2).get("Title");
	
	@Test(groups = "ACCEPTANCE")
	public void testOutOfOrderCreatedInFutureTimeIsDisplayedInHomeNextTile() {
		String startDate = testData.get(2).get("Start date (days to add)");
		String endDate = testData.get(2).get("End date (days to add)");
		String startTime = testData.get(2).get("Start time (minutes to add)");
		String endTime = testData.get(2).get("End time (minutes to add)");
		String description = testData.get(2).get("Description");
		
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
		
		//Opening Tablet for assertions
		HomeTabletPage homeTabletPage = new HomeTabletPage();
		SettingsPage settingsPage = homeTabletPage.clickSettingsBtn();
		homeTabletPage = settingsPage.selectRoom(roomName);
				
		//Assert for TC10
		Assert.assertEquals(homeTabletPage.getNextTileLbl(), title);
	}
	
	@AfterClass(groups = "ACCEPTANCE")
	public void deleteOutOfOrder() throws MalformedURLException, IOException{
		RoomManagerRestMethods.deleteOutOfOrder(roomName, title);
	}
}
