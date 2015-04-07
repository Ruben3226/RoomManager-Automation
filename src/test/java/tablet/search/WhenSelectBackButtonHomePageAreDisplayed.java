package test.java.tablet.search;

import org.testng.Assert;
import org.testng.annotations.Test;

import main.java.pages.tablet.HomeTabletPage;
import main.java.pages.tablet.SearchPage;

/**
 * TC17: Verify on search page when "BACK" (represented for an arrow) button 
 * is pressed the home page is displayed
 * @author Jose Cabrera
 */
public class WhenSelectBackButtonHomePageAreDisplayed {
	private SearchPage searchPage;
	
	@Test(groups = "UI")
	public void testSelectBackButtonHomePageAreDisplayed () {
		HomeTabletPage homeTabletPage = new HomeTabletPage();
		searchPage = homeTabletPage.clickSearchBtn();
		homeTabletPage = searchPage.clickBackBtn();
		Assert.assertTrue(homeTabletPage.isHomePageDisplayed());
	}
}
