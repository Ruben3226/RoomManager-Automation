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
		LinkedList<String> resCond = RoomManagerRestMethods.getRoomNamesByResource(resourceName);
		LinkedList<String> roomCond = RoomManagerRestMethods.getRoomsByName(roomName);
		searchPage = homeTabletPage
				.clickSearchBtn()
				.clickCollapseAdvancedBtn()
				.setName(roomName)
				.selectResource(resourceName);
		Assert.assertTrue(searchPage.roomsInList(RoomManagerRestMethods.mergeLists(resCond, roomCond)));
	}

	@AfterMethod(groups = "FUNCTIONAL")
	public void toHome() {
		searchPage.clickBackBtn();
	}
}
