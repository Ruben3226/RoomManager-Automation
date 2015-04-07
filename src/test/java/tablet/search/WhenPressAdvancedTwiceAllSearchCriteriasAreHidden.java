package test.java.tablet.search;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import main.java.pages.tablet.HomeTabletPage;
import main.java.pages.tablet.SearchPage;

/**
 * TC16: Verify when filters are displayed on search page and "Advanced" button 
 * is pressed all filters are hidden
 * @author Jose Cabrera
 */
public class WhenPressAdvancedTwiceAllSearchCriteriasAreHidden {
	private SearchPage searchPage;

	@Test(groups = "UI")
	public void testPressAdvancedTwiceAllSearchCriteriasAreHidden() {
		HomeTabletPage homeTabletPage = new HomeTabletPage();
		searchPage = homeTabletPage
				.clickSearchBtn()
				.clickCollapseAdvancedBtn()
				.clickHiddenAdvancedBtn();
		Assert.assertFalse(searchPage.filtersArePresent());
	}

	@AfterMethod(groups = "UI")
	public void toHome() {
		searchPage.clickBackBtn();
	}
}
