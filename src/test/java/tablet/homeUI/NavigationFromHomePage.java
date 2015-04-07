package test.java.tablet.homeUI;

import org.testng.Assert;
import org.testng.annotations.Test;

import main.java.pages.tablet.HomeTabletPage;
import main.java.pages.tablet.SchedulePage;
import main.java.pages.tablet.SearchPage;

/**
 * This TCs verify that the navigation to other pages from Home page works correctly. 
 * TC20, TC22, TC23, TC24
 * @author Eliana Navia
 * 
 */
public class NavigationFromHomePage {

	/**
	 * TC20: This test case verifies that schedule page is displayed 
	 * when Now Tile is clicked when any meeting is in progress in the room.
	 * 
	 */
	@Test (groups = "UI")
	public void testSchedulePageIsDisplayedWhenNowTileIsClickedAndTheRoomIsFreeEOD() {
		HomeTabletPage homeTabletPage = new HomeTabletPage();
		SchedulePage schedulePage = homeTabletPage.clickNowTileLbl();	

		Assert.assertTrue(schedulePage.isSchedulerLblDisplayed());
	}

	/**
	 * TC22: This test case verifies that Schedule page is displayed when Time Line is clicked.
	 * 
	 */
	@Test (groups = "UI")
	public void testSchedulePageIsDisplayedWhenTimeLineIsClicked() {
		HomeTabletPage homeTabletPage = new HomeTabletPage();
		SchedulePage schedulePage = homeTabletPage.clickTimelineContainer();

		Assert.assertTrue(schedulePage.isSchedulerLblDisplayed());
	}

	/**
	 * TC23: This test case verifies that Schedule page is displayed 
	 * when schedule button is clicked.
	 * 
	 */
	@Test (groups = "UI")
	public void testSchedulePageIsDisplayedWhenScheduleButtonIsClicked() {
		HomeTabletPage homeTabletPage = new HomeTabletPage();
		SchedulePage schedulePage = homeTabletPage.clickScheduleBtn();

		Assert.assertTrue(schedulePage.isSchedulerLblDisplayed());
	}

	/**
	 * TC24: This test case verifies that Search page is displayed when search button is clicked.
	 * 
	 */
	@Test (groups = "UI")
	public void testSearchPageIsDisplayedWhenSearchButtonIsClicked() {
		HomeTabletPage homeTabletPage = new HomeTabletPage();
		SearchPage searchPage = homeTabletPage.clickSearchBtn();

		Assert.assertTrue(searchPage.isSearchLblDisplayed());
	}	
}
