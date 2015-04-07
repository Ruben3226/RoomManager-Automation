package test.java.tablet.schedule;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import main.java.pages.tablet.HomeTabletPage;
import main.java.pages.tablet.SchedulePage;
import main.java.utils.TimeMethods;

/**
 * TC38: Verify that the red line in the timeline is in the current time 
 * @author Jose Cabrera
 */
public class TheRedLineInTheTimelineIsInTheCurrentTime {
	private SchedulePage schedulePage;
	
	@Test(groups = "UI")
	public void testRedLineInTheTimelineIsInTheCurrentTime (){
		String currentDate = TimeMethods.getCurrentDate("MMMM d YYY, H:mm");
		HomeTabletPage homeTabletPage = new HomeTabletPage();
		schedulePage = homeTabletPage.clickScheduleBtn();
		Assert.assertTrue(schedulePage.getTimeLineDate().contains(currentDate));
	}

	@AfterClass(groups = "UI")
	public void toHome() {
		schedulePage.clickBackBtn();
	}
}
