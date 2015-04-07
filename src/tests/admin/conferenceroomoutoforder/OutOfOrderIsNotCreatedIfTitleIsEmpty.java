package tests.admin.conferenceroomoutoforder;

import static framework.common.AppConfigConstants.EXCEL_INPUT_DATA;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
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
 * TC22: Verify an Out Of Order cannot be created if title is empty
 * TC33: Verify that when title is empty the message "Out of order should have a title" is displayed
 * @author Yesica Acha
 *
 */
public class OutOfOrderIsNotCreatedIfTitleIsEmpty {
	private RoomOutOfOrderPlanningPage outOfOrderPage;

	@Test(groups = {"FUNCTIONAL", "UI"})
	public void testOutOfOrderIsNotCreatedIfTitleIsEmpty() throws JSONException, 
	MalformedURLException, IOException {
		
		//Getting Out Of Order data from an excel file
		ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
		List<Map<String, String>> testData = excelReader.getMapValues("OutOfOrderPlanning");
		String roomName = testData.get(6).get("Room Name");
		String title = testData.get(6).get("Title");
		String description = testData.get(6).get("Description");

		//Out Of Order Creation
		HomeAdminPage homeAdminPage = new HomeAdminPage(); 
		RoomsPage roomsPage = homeAdminPage.clickConferenceRoomsLink();
		RoomInfoPage roomInfoPage = roomsPage.doubleClickOverRoomName(roomName);
		outOfOrderPage = roomInfoPage.clickOutOfOrderPlanningLink();
		outOfOrderPage = outOfOrderPage
				.setDescriptionTxtBox(description)
				.setTitleTxtBox(title)
				.clickSaveWithErrorBtn();

		//Assertion for TC22
		Assert.assertFalse(RootRestMethods.isOutOfOrderCreated(roomName, title));

		//Assertion for TC33
		Assert.assertTrue(outOfOrderPage.isErrorMessagePresent());
		Assert.assertTrue(outOfOrderPage.isOutOfOrderShouldHaveTitleErrorDisplayed());
	}

	@AfterMethod(groups = {"FUNCTIONAL", "UI"})
	public void closeOutOfOrderPage() {
		outOfOrderPage.clickCancelBtn();
	}
}
