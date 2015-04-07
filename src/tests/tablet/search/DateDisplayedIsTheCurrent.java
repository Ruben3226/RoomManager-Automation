package tests.tablet.search;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import framework.pages.tablet.HomeTabletPage;
import framework.pages.tablet.SearchPage;

/**
 * TC18: Verify that the date displayed on search page is 
 * the current date
 * @author Jose Cabrera
 */
public class DateDisplayedIsTheCurrent {
	private SearchPage searchPage;
	
	@Test(groups = "UI")
	public void testDateDisplayedIsTheCurrent (){
		HomeTabletPage homeTabletPage = new HomeTabletPage();
		searchPage = homeTabletPage.clickSearchBtn();
		Assert.assertTrue(searchPage.dateIsPresent());
	}
	
	@AfterMethod(groups = "UI")
	public void toHome() {
		searchPage.clickBackBtn();
	}	
}
