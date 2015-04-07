package test.java.tablet.schedule;

import static main.java.utils.AppConfigConstants.EXCEL_INPUT_DATA;

import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import main.java.pages.tablet.HomeTabletPage;
import main.java.utils.readers.ExcelReader;

/**
 * TC41: Verify that clicking the left arrow displays the home page
 * @author Asael Calizaya
 *
 */
public class HomePageIsDisplayedWhenBackButtonIsClicked {

	@Test(groups = "UI")
	public void testHomePageIsDisplayedWhenBackButtonIsClicked() {
		ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
		List<Map<String, String>> meetingData = excelReader.getMapValues("MeetingData");
		String roomName = meetingData.get(1).get("Room");
		
		HomeTabletPage homeTabletPage = new HomeTabletPage();
		String actual = homeTabletPage
				.clickScheduleBtn()
				.clickBackBtn()
				.getRoomDisplayNameLbl();

		Assert.assertEquals(actual, roomName);
	}
}
