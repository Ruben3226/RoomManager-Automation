package test.java.tablet.schedule;

import org.testng.Assert;
import org.testng.annotations.Test;

import main.java.pages.tablet.HomeTabletPage;
import main.java.pages.tablet.SchedulePage;

/**
 * TC44: Verify that clicking back button goes to the previous page
 * @author Jose Cabrera
 */
public class ClickingBackButtonGoesToThePreviousPage { 

	@Test(groups = "UI")
	public void testClickingBackButtonGoesToThePreviousPage () {
		HomeTabletPage homeTabletPage = new HomeTabletPage();
		SchedulePage schedulePage = homeTabletPage.clickScheduleBtn();
		homeTabletPage = schedulePage.clickBackBtn();
		Assert.assertTrue(homeTabletPage.isHomePageDisplayed());
	}
}
