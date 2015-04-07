package tests.tablet.search;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.LinkedList;

import org.json.JSONException;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import framework.pages.tablet.HomeTabletPage;
import framework.pages.tablet.SearchPage;
import framework.rest.RootRestMethods;

/**
 * TC06:Verify on search page that all rooms are displayed when search 
 * page is loaded
 * @author Jose Cabrera
 */
public class AllRoomsareDisplayedWhenPageisLoaded {
	private SearchPage searchPage;

	@Test(groups = "UI")
	public void testAllRoomsareDisplayedPageLoaded () throws JSONException, MalformedURLException, 
	IOException {
		HomeTabletPage homeTabletPage = new HomeTabletPage();
		LinkedList<String> condition = RootRestMethods.getAllRooms();
		searchPage = homeTabletPage.clickSearchBtn();
		Assert.assertTrue(searchPage.roomsInList(condition));
	}

	@AfterMethod(groups = "UI")
	public void toHome() {
		searchPage.clickBackBtn();
	}

}
