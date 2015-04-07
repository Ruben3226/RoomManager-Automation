package test.java.tablet.search;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import main.java.pages.tablet.HomeTabletPage;
import main.java.pages.tablet.SearchPage;
import main.java.utils.TimeMethods;

/**
 * TC19: Verify that the red line in the timeline is in the current time 
 * @author Jose Cabrera
 */
public class TimeLineDisplayedIsInCurrentTime {
	private SearchPage searchPage;
	
	@Test(groups = "UI")
	public void testTimeDisplayedIsTheCurrent () {
		HomeTabletPage homeTabletPage = new HomeTabletPage();
		String currentDate = TimeMethods.getCurrentDate("MMMM d YYY, HH:mm");
		searchPage = homeTabletPage.clickSearchBtn();
		Assert.assertTrue(searchPage.getTimeLineDate().contains(currentDate));
	}
	
	@AfterMethod(groups = "UI")
	public void toHome() {
		searchPage.clickBackBtn();
	}
}
