package test.java.tablet.search;

import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import main.java.pages.tablet.HomeTabletPage;
import main.java.pages.tablet.SchedulePage;
import main.java.pages.tablet.SearchPage;
import main.java.utils.AppConfigConstants;
import main.java.utils.readers.ExcelReader;

/**
 * TC07: Verify on search page when you press over some room, 
 * the sheduler page is displayed of that room
 * @author Jose Cabrera
 */
public class WhenSelectRoomSchedulerofThisAreDisplayed {
	private SearchPage searchPage;
	private SchedulePage schedulePage;

	@Test(groups = "ACCEPTANCE")
	public void testSelectRoomSchedulerofThisAreDisplayed () throws InterruptedException {
		ExcelReader excelReader = new ExcelReader(AppConfigConstants.EXCEL_INPUT_DATA);
		List<Map<String, String>> testData = excelReader.getMapValues("Search");
		String roomName = testData.get(1).get("Room Name");
		HomeTabletPage homeTabletPage = new HomeTabletPage();
		searchPage = homeTabletPage
				.clickSearchBtn()
				.clickCollapseAdvancedBtn();
		schedulePage = searchPage.selectRoom(roomName);
		Assert.assertTrue(schedulePage.isSchedulerLblDisplayed());
	}

	@AfterMethod(groups = "ACCEPTANCE")
	public void toHome() {
		schedulePage.clickBackBtn();
	}


}
