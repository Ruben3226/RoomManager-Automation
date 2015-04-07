package tests.tablet.schedule;

import org.junit.Assert;
import org.testng.annotations.Test;

import framework.pages.tablet.HomeTabletPage;
import framework.pages.tablet.SchedulePage;

/**
 * TC30: Verify that the default time of meeting is 30 minutes
 * @author Asael Calizaya
 *
 */
public class DefaultTimeOfMeetingIs30Minutes {

	@Test(groups = "UI")
	public void testDefaultTimeOfMeetingIs30Minutes() {	
		HomeTabletPage homeTabletPage = new HomeTabletPage();
		SchedulePage schedulePage = homeTabletPage.clickScheduleBtn();

		Assert.assertEquals(schedulePage.getDurationOfMeetingByDefault(), 30);
	}
}
