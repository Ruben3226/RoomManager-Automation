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
 * TC02: Verify that all resources are displayed on search page 
 * on "filter by resource" field 
 * @author Jose Cabrera
 */
public class AllResourcesAreDisplayed {
	private SearchPage searchPage;

	@Test(groups = "ACCEPTANCE")
	public void testAllResourcesAreDisplayed () throws JSONException, MalformedURLException, IOException {
		HomeTabletPage homeTabletPage = new HomeTabletPage();
		LinkedList<String> condition = RootRestMethods.getAllNameResources();
		searchPage = homeTabletPage
				.clickSearchBtn()
				.clickCollapseAdvancedBtn();
		Assert.assertTrue(searchPage.resourcesInList(condition));
	}

	@AfterMethod(groups = "ACCEPTANCE")
	public void toHome() {
		searchPage.clickBackBtn();
	}
}
