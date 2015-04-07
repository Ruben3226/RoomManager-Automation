package tests.admin.conferenceroomoutoforder;

import static framework.common.AppConfigConstants.EXCEL_INPUT_DATA;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import framework.pages.admin.HomeAdminPage;
import framework.pages.admin.conferencerooms.RoomInfoPage;
import framework.pages.admin.conferencerooms.RoomOutOfOrderPlanningPage;
import framework.pages.admin.conferencerooms.RoomsPage;
import framework.rest.RootRestMethods;
import framework.utils.readers.ExcelReader;

/**
 * TC05: Verify that Clock icon appears in out of order column when the schedule to be 
 * out of order is current time
 * TC18: Verify that clicking the icon calendar in "Out of Order" column  disables out 
 * of order time of a Room
 * TC19: Verify that clicking the icon calendar in "Out of Order" column  enables 
 * out of order time of a Room
 * @author Yesica Acha
 *
 */
public class IfOutOfOrderIsCreatedInThePresentClockIconEnablesOrDisablesIt {
	
	//Getting Out Of Order data from an excel file
	private ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	private List<Map<String, String>> testData = excelReader.getMapValues("OutOfOrderPlanning");
	private String roomName = testData.get(3).get("Room Name");
	private String title = testData.get(3).get("Title");

	@Test(groups = {"ACCEPTANCE", "FUNCTIONAL"})
	public void testIfOutOfOrderIsCreatedInThePresentClockIconEnablesOrDisablesIt() {
		String startDate = testData.get(3).get("Start date (days to add)");
		String endDate = testData.get(3).get("End date (days to add)");
		String startTime = testData.get(3).get("Start time (minutes to add)");
		String endTime = testData.get(3).get("End time (minutes to add)");
		String description = testData.get(3).get("Description");
		String expectedIcon = testData.get(3).get("Icon").toLowerCase().replaceAll(" ", "");
		
		//Out of Order creation
		HomeAdminPage homeAdminPage = new HomeAdminPage(); 
		RoomsPage roomsPage = homeAdminPage.clickConferenceRoomsLink();
		RoomInfoPage roomInfoPage = roomsPage.doubleClickOverRoomName(roomName);
		RoomOutOfOrderPlanningPage outOfOrderPage = roomInfoPage.clickOutOfOrderPlanningLink();
		roomsPage = outOfOrderPage
				.setOutOfOrderPeriodInformation(startDate, endDate, startTime, endTime, title, 
						description)
				.clickSaveOutOfOrderBtn();

		//Assertion for TC05
		Assert.assertTrue(roomsPage.getOutOfOrderIconClass(roomName).contains(expectedIcon));

		//Assertion for TC18
		roomsPage.clickOutOfOrderIcon(roomName);
		Assert.assertTrue(RootRestMethods.isOutOfOrderEnable(roomName, title));

		//Assertion for TC19
		roomsPage.clickOutOfOrderIcon(roomName);
		Assert.assertFalse(RootRestMethods.isOutOfOrderEnable(roomName, title));
	}

	@AfterMethod(groups = {"ACCEPTANCE", "FUNCTIONAL"})
	public void deleteOutOfOrder() throws MalformedURLException, IOException {
		RootRestMethods.deleteOutOfOrder(roomName, title);
	}
}
