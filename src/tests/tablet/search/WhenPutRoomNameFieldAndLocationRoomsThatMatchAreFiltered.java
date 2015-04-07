package tests.tablet.search;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import jxl.read.biff.BiffException;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import framework.common.AppConfigConstants;
import framework.pages.tablet.HomeTabletPage;
import framework.pages.tablet.SearchPage;
import framework.rest.RootRestMethods;
import framework.utils.readers.ExcelReader;

/**
 * TC08: Verify on search page that filter by "location" and "Room Name", 
 * only the rooms that contains that especifications are displayed
 * @author Jose Cabrera
 */
public class WhenPutRoomNameFieldAndLocationRoomsThatMatchAreFiltered {
	private SearchPage searchPage;

	@Test(groups="FUNCTIONAL")
	public void testRoomNameFieldAndLocationRoomsThatMatchAreFiltered() 
			throws BiffException, IOException{
		ExcelReader excelReader = new ExcelReader(AppConfigConstants.EXCEL_INPUT_DATA);
		List<Map<String, String>> testData = excelReader.getMapValues("Search");
		String roomName = testData.get(1).get("Room Name");
		HomeTabletPage homeTabletPage = new HomeTabletPage();
		LinkedList<String> roomNameCond = RootRestMethods.getRoomsByName(roomName);
		String location = testData.get(1).get("Location");
		LinkedList<String> locationCond = RootRestMethods
				.getListByNumeric("rooms", "location", location, "displayName");
		searchPage = homeTabletPage
				.clickSearchBtn()
				.clickCollapseAdvancedBtn()
				.setName(roomName)
				.setLocation(location);
		Assert.assertTrue(searchPage.roomsInList(RootRestMethods.mergeLists(roomNameCond, locationCond)));
	}

	@AfterMethod(groups="FUNCTIONAL")
	public void toHome() {
		searchPage.clickBackBtn();
	}
}
