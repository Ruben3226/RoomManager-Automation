package test.java.admin.conferenceroomoutoforder;

import static main.java.utils.AppConfigConstants.EXCEL_INPUT_DATA;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import main.java.pages.admin.HomeAdminPage;
import main.java.pages.admin.conferencerooms.RoomInfoPage;
import main.java.pages.admin.conferencerooms.RoomOutOfOrderPage;
import main.java.pages.admin.conferencerooms.RoomsPage;
import main.java.rest.RoomManagerRestMethods;
import main.java.utils.readers.ExcelReader;

/**
 * TC21: Verify an Out Of Order can be created if description text box is empty
 * @author Yesica Acha
 *
 */
public class OutOfOrderIsCreatedIfDescriptionIsEmpty {
	
	//Getting Out Of Order data from an excel file
	private ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	private List<Map<String, String>> testData = excelReader.getMapValues("OutOfOrderPlanning");
	private String roomName = testData.get(8).get("Room Name");
	private String title = testData.get(8).get("Title");
		
	@Test(groups = "FUNCTIONAL")
	public void testOutOfOrderIsCreatedIfDescriptionIsEmpty() throws JSONException, MalformedURLException, IOException {
		String description = testData.get(8).get("Description");
		
		//Out Of Order creation
		HomeAdminPage homeAdminPage = new HomeAdminPage(); 
		RoomsPage roomsPage = homeAdminPage.clickConferenceRoomsLink();
		RoomInfoPage roomInfoPage = roomsPage.doubleClickOverRoomName(roomName);
		RoomOutOfOrderPage outOfOrderPage = roomInfoPage.clickOutOfOrderPlanningLink();
		roomsPage = outOfOrderPage
				.setTitleTxtBox(title)
				.setDescriptionTxtBox(description)
				.activateOutOfOrder()
				.clickSaveOutOfOrderBtn();
		
		//Assertion for TC21
		Assert.assertTrue(RoomManagerRestMethods.isOutOfOrderCreated(roomName, title));
		Assert.assertTrue(roomsPage.isMessagePresent());
		Assert.assertTrue(roomsPage.isOutOfOrderSuccessfullyCreatedMessageDisplayed());
		Assert.assertTrue(roomsPage.isOutOfOrderIconDisplayed(roomName));
	}
	
	@AfterMethod(groups = "FUNCTIONAL")
	public void deleteOutOfOrder() throws MalformedURLException, IOException {
		RoomManagerRestMethods.deleteOutOfOrder(roomName, title);
	}
}
