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
 * TC04: Verify on search page if put a number on "Minimum Capacity" field, 
 * 		   the rooms are filtered by the capacity of the room
 * @author Jose Cabrera
 */
public class WhenPutMinimumCapFieldRoomsThatMatchAreFiltered {
	private SearchPage searchPage;

	@Test(groups = "ACCEPTANCE")
	public void testPutMinimumCapFieldRoomsAreFiltered ()throws BiffException, IOException {
		ExcelReader excelReader = new ExcelReader(AppConfigConstants.EXCEL_INPUT_DATA);
		List<Map<String, String>> testData = excelReader.getMapValues("Search");
		String capacity = testData.get(1).get("Capacity");
		HomeTabletPage homeTabletPage = new HomeTabletPage();
		LinkedList<String> capacityCond = RoomManagerRestMethods
				.getListByNumeric("rooms", "capacity", capacity, "displayName");
		searchPage = homeTabletPage
				.clickSearchBtn()
				.clickCollapseAdvancedBtn()
				.setMinimumCap(capacity);
		Assert.assertTrue(searchPage.roomsInList(capacityCond));
	}

	@AfterMethod(groups = "ACCEPTANCE")
	public void toHome() {
		searchPage.clickBackBtn();
	}
}
