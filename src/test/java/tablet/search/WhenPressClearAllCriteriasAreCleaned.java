package test.java.tablet.search;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import main.java.pages.tablet.HomeTabletPage;
import main.java.pages.tablet.SearchPage;
import main.java.rest.RoomManagerRestMethods;
import main.java.utils.AppConfigConstants;
import main.java.utils.readers.ExcelReader;

/**
 * TC15: Verify on search page that when "Clear" button  is pressed 
 * all fields are cleaned
 * @author Jose Cabrera
 */
public class WhenPressClearAllCriteriasAreCleaned {
	private SearchPage searchPage;

	@Test(groups = "UI")
	public void testPressClearAllCriteriasAreCleaned () throws JSONException, 
	MalformedURLException, IOException, InterruptedException {
		ExcelReader excelReader = new ExcelReader(AppConfigConstants.EXCEL_INPUT_DATA);
		List<Map<String, String>> testData = excelReader.getMapValues("Search");
		String roomName = testData.get(1).get("Room Name");
		String location = testData.get(1).get("Location");
		String capacity = testData.get(1).get("Capacity");
		String resourceName = testData.get(1).get("Resource");
		HomeTabletPage homeTabletPage = new HomeTabletPage();
		LinkedList<String> condition = RoomManagerRestMethods.getAllNameResources();
		searchPage = homeTabletPage
				.clickSearchBtn()
				.clickCollapseAdvancedBtn()
				.selectResource(resourceName)
				.setName(roomName)
				.setMinimumCap(capacity)
				.setLocation(location)
				.clickClearBtn();
		Assert.assertFalse(searchPage.resourcesAreSelected(condition)&&
				searchPage.allFiltersAreCleaned());
	}

	@AfterMethod(groups = "UI")
	public void toHome() {
		searchPage.clickBackBtn();
	}
}

