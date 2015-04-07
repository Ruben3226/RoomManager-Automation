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
		LinkedList<String> condition = RootRestMethods.getRoomsByName(roomName);
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
