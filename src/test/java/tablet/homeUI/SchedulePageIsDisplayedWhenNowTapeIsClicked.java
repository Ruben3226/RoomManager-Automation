package test.java.tablet.homeUI;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import main.java.pages.tablet.HomeTabletPage;
import main.java.pages.tablet.SchedulePage;

/**
 * TC21: Verify that schedule page is displayed when Now Tape is clicked when no 
 * event is scheduled
 * @author Jose Cabrera
 */
public class SchedulePageIsDisplayedWhenNowTapeIsClicked {
	SchedulePage schedulePage;
	@Test(groups = "UI")
	public void testSchedulePageIsDisplayedWhenNowTapeIsClicked () {
		HomeTabletPage homeTabletPage = new HomeTabletPage();
		schedulePage = homeTabletPage.clickNowTileLbl();
		Assert.assertTrue(schedulePage.isSchedulerLblDisplayed());
	}

	@AfterClass(groups = "UI")
	public void toHome() {
		schedulePage.clickBackBtn();
	}
}
