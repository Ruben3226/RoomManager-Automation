package tests.tablet.search;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import framework.pages.tablet.HomeTabletPage;
import framework.pages.tablet.SearchPage;
import framework.utils.TimeManager;

/**
 * TC19: Verify that the red line in the timeline is in the current time 
 * @author Jose Cabrera
 */
public class TimeLineDisplayedIsInCurrentTime {
	private SearchPage searchPage;
	
	@Test(groups = "UI")
	public void testTimeDisplayedIsTheCurrent () {
		HomeTabletPage homeTabletPage = new HomeTabletPage();
		String currentDate = TimeManager.getCurrentDate("MMMM d YYY, HH:mm");
		searchPage = homeTabletPage.clickSearchBtn();
		Assert.assertTrue(searchPage.getTimeLineDate().contains(currentDate));
	}
	
	@AfterMethod(groups = "UI")
	public void toHome() {
		searchPage.clickBackBtn();
	}
}
