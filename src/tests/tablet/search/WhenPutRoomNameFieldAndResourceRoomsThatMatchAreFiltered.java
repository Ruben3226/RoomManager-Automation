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
 * TC10: Verify on search page that filter by "room name" and "resource", 
 * only the rooms that contains that especifications are displayed
 * @author Jose Cabrera
 */
public class WhenPutRoomNameFieldAndResourceRoomsThatMatchAreFiltered {
	private SearchPage searchPage;


	@Test(groups = "FUNCTIONAL")
	public void testRoomNameFieldAndResourceRoomsThatMatchAreFiltered() 
			throws BiffException, IOException {
		ExcelReader excelReader = new ExcelReader(AppConfigConstants.EXCEL_INPUT_DATA);
		List<Map<String, String>> testData = excelReader.getMapValues("Search");
		String roomName = testData.get(2).get("Room Name");
		String resourceName = testData.get(1).get("Resource");
		HomeTabletPage homeTabletPage = new HomeTabletPage();
		LinkedList<String> resCond = RootRestMethods.getRoomNamesByResource(resourceName);
		LinkedList<String> roomCond = RootRestMethods.getRoomsByName(roomName);
		searchPage = homeTabletPage
				.clickSearchBtn()
				.clickCollapseAdvancedBtn()
				.setName(roomName)
				.selectResource(resourceName);
		Assert.assertTrue(searchPage.roomsInList(RootRestMethods.mergeLists(resCond, roomCond)));
	}

	@AfterMethod(groups = "FUNCTIONAL")
	public void toHome() {
		searchPage.clickBackBtn();
	}
}
