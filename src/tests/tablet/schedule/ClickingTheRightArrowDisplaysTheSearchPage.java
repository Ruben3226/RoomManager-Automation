package tests.tablet.schedule;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import framework.pages.tablet.HomeTabletPage;
import framework.pages.tablet.SearchPage;

/**
 * TC43: Verify that clicking the right arrow displays the search page
 * @author Jose Cabrera
 */
public class ClickingTheRightArrowDisplaysTheSearchPage {
	private SearchPage searchPage;
	
	@Test(groups = "UI")
	public void testClickingTheRightArrowDisplaysTheSearchPage () {
		HomeTabletPage homeTabletPage = new HomeTabletPage();
		searchPage = homeTabletPage.clickScheduleBtn()
				.clickSearchBtn();
		Assert.assertTrue(searchPage.dateLblIsDisplayed());
	}
	
	@AfterClass(groups = "UI")
	public void toHome() {
		searchPage.clickBackBtn();
	}
	
	
}
