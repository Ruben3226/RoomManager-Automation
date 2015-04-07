package tests.tablet.homeUI;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.Test;

import framework.pages.tablet.HomeTabletPage;
import framework.utils.TimeManager;

/**
 * TC34: Verify the red line in the timeline is in the current time 
 * @author Jose Cabrera
 */
public class TheRedLineInTheTimelineIsInTheCurrentTime {
	
	@Test(groups = "UI")
	public void testRedLineInTheTimelineIsInTheCurrentTime () throws IOException {
		HomeTabletPage homeTabletPage = new HomeTabletPage();
		String currentDate = TimeManager.getCurrentDate("MMMM d YYY, HH:mm");
		Assert.assertTrue(homeTabletPage.getTimeLineDate().contains(currentDate));
	}

}
