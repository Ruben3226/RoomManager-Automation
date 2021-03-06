package tests.tablet.schedule;

import static framework.common.AppConfigConstants.EXCEL_INPUT_DATA;

import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import framework.pages.tablet.HomeTabletPage;
import framework.pages.tablet.SchedulePage;
import framework.utils.readers.ExcelReader;

/**
 * TC26: Verify that the quantity of characters in the organizer text box is limited
 * @author Asael Calizaya
 *
 */
public class QuantityOfCharactersOfOrganizerIsLimited {

	@Test(groups = "NEGATIVE")
	public void testQuantityOfCharactersToOrganazerIsLimited() {
		ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
		List<Map<String, String>> meetingData = excelReader.getMapValues("MeetingData"); 
		String organizer = meetingData.get(5).get("Organizer");

		HomeTabletPage homeTabletPage = new HomeTabletPage();
		SchedulePage schedulePage = homeTabletPage.clickScheduleBtn();
		schedulePage
		.setOrganizerTxtBox(organizer)
		.clickCreateBtn();

		/*Fails, an error message should be displayed when more than expected 
		quantity of characters is inserted in Organizer text box*/
		Assert.assertTrue(schedulePage.isMessageErrorPopUpDisplayed());
	}
}