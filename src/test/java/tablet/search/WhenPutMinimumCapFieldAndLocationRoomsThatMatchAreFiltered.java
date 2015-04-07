package test.java.tablet.search;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import jxl.read.biff.BiffException;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import main.java.pages.tablet.HomeTabletPage;
import main.java.pages.tablet.SearchPage;
import main.java.rest.RoomManagerRestMethods;
import main.java.utils.AppConfigConstants;
import main.java.utils.readers.ExcelReader;

/**
 * TC09: Verify on search page if filter by "location" and "Minimum capacity", 
 * only the rooms that contains that especifications are displayed
 * @author Jose Cabrera
 */
public class WhenPutMinimumCapFieldAndLocationRoomsThatMatchAreFiltered {
	private SearchPage searchPage;

	@Test(groups = "FUNCTIONAL")
	public void testPutMinimumCapFieldAndLocationRoomsAreFiltered () throws BiffException, IOException {
		ExcelReader excelReader = new ExcelReader(AppConfigConstants.EXCEL_INPUT_DATA);
		List<Map<String, String>> testData = excelReader.getMapValues("Search");
		String location = testData.get(1).get("Location");
		String capacity = testData.get(1).get("Capacity");
		HomeTabletPage homeTabletPage = new HomeTabletPage();
		LinkedList<String> locationCond = RoomManagerRestMethods
				.getListByNumeric("rooms", "location", location, "displayName");
		LinkedList<String> capacityCond = RoomManagerRestMethods
				.getListByNumeric("rooms", "capacity", capacity, "displayName");
		searchPage = homeTabletPage
				.clickSearchBtn()
				.clickCollapseAdvancedBtn()
				.setMinimumCap(capacity)
				.setLocation(location);
		Assert.assertTrue(searchPage.roomsInList(
				RoomManagerRestMethods.mergeLists(locationCond, capacityCond)));
	}
	@AfterMethod(groups = "FUNCTIONAL")
	public void toHome() {
		searchPage.clickBackBtn();
	}
}
