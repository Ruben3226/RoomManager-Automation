package tests.tablet.schedule;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import framework.pages.tablet.HomeTabletPage;
import framework.pages.tablet.SchedulePage;
import framework.utils.TimeManager;

/**
 * TC38: Verify that the red line in the timeline is in the current time 
 * @author Jose Cabrera
 */
public class TheRedLineInTheTimelineIsInTheCurrentTime {
	private SchedulePage schedulePage;
	
	@Test(groups = "UI")
	public void testRedLineInTheTimelineIsInTheCurrentTime (){
		String currentDate = TimeManager.getCurrentDate("MMMM d YYY, H:mm");
		HomeTabletPage homeTabletPage = new HomeTabletPage();
		schedulePage = homeTabletPage.clickScheduleBtn();
		Assert.assertTrue(schedulePage.getTimeLineDate().contains(currentDate));
	}

	@AfterClass(groups = "UI")
	public void toHome() {
		schedulePage.clickBackBtn();
	}
}
