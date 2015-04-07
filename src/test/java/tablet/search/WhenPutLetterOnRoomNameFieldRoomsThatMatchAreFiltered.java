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
 * TC03: Verify when put a letter on "Room Name" field, 
 * the rooms that match with that criteria is filtered
 * @author Jose Cabrera
 */
public class WhenPutLetterOnRoomNameFieldRoomsThatMatchAreFiltered {
	private SearchPage searchPage;

	@Test(groups = "ACCEPTANCE")
	public void testPutLetterOnRoomNameFieldRoomsAreFiltered ()throws BiffException, IOException {
		ExcelReader excelReader = new ExcelReader(AppConfigConstants.EXCEL_INPUT_DATA);
		List<Map<String, String>> testData = excelReader.getMapValues("Search");
		String roomName = testData.get(1).get("Room Name");
		HomeTabletPage homeTabletPage = new HomeTabletPage();
		LinkedList<String> condition = RoomManagerRestMethods.getRoomsByName(roomName);
		searchPage = homeTabletPage
				.clickSearchBtn()
				.clickCollapseAdvancedBtn()
				.setName(roomName);
		Assert.assertTrue(searchPage.roomsInList(condition));
	}

	@AfterMethod(groups = "ACCEPTANCE")
	public void toHome() {
		searchPage.clickBackBtn();
	}
}
