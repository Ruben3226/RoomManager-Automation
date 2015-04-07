package tests.tablet.search;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import framework.pages.tablet.HomeTabletPage;
import framework.pages.tablet.SearchPage;

/**
 * TC13: Verify on search page when "Advanced" button is pressed 
 * filters criterias are displayed(Filter by resourse, Room name, Minimum capacity, location)
 * @author Jose Cabrera
 */
public class WhenPressAdvancedallCriteriasAreDisplayed {
	private SearchPage searchPage;
	
	@Test(groups = "UI")
	public void testPressAdvancedallCriteriasAreDisplayed () {
		HomeTabletPage homeTabletPage = new HomeTabletPage();
		searchPage = homeTabletPage
				.clickSearchBtn()
				.clickCollapseAdvancedBtn();
		Assert.assertTrue(searchPage.filtersArePresent());
	}

	@AfterMethod(groups = "UI")
	public void toHome() {
		searchPage.clickBackBtn();
	}
}
