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
 * TC05: Verify on search page that can filter by location, 
 * 		   just the rooms that be part of that location are displayed
 * @author Jose Cabrera
 */
public class WhenSelectLocationRoomsThatMatchAreFiltered {
	private SearchPage searchPage;

	@Test(groups = "UI")
	public void testSelectLocationRoomsThatMatchAreFiltered () throws BiffException, IOException {
		ExcelReader excelReader = new ExcelReader(AppConfigConstants.EXCEL_INPUT_DATA);
		List<Map<String, String>> testData = excelReader.getMapValues("Search");
		String location = testData.get(1).get("Location");
		HomeTabletPage homeTabletPage = new HomeTabletPage();
		LinkedList<String> locationCond = RootRestMethods
				.getListByNumeric("rooms", "location", location, "displayName");
		searchPage = homeTabletPage
				.clickSearchBtn()
				.clickCollapseAdvancedBtn()
				.setLocation(location);
		Assert.assertTrue(searchPage.roomsInList(locationCond));
	}

	@AfterMethod(groups = "UI")
	public void toHome() {
		searchPage.clickBackBtn();
	}
}
