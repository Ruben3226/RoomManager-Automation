package test.java.tablet.schedule;

import org.testng.Assert;
import org.testng.annotations.Test;

import main.java.pages.tablet.HomeTabletPage;
import main.java.pages.tablet.SchedulePage;

/**
 * This test cases verify the time text displayed below time line.
 * TC32: Time interval of time line is modified when it is moved with the mouse.
 * TC44: Time interval of time line is from 00:00 to 23:00 .
 * @author Eliana Navia
 *
 */
public class TimeTextDisplayedInTimeLine {

	/**
	 * TC32: This test case verifies that the time interval in the timeline 
	 * can be modified when it is moved with the mouse. 
	 */
	@Test (groups = "UI")
	public void testTimeIntervalOfTimelineIsModified() {
		HomeTabletPage homeTabletPage = new HomeTabletPage();
		SchedulePage schedulePage = homeTabletPage.clickScheduleBtn();
		schedulePage.moveTimeLine(-100000);
		
		//The page by default display time text from: 07:00 to 19:00
        Assert.assertTrue(schedulePage.isTextHourDisplayed("22:00"));
	}
	
	/**
	 * TC44: This test case verifies that timeline displays time text from 00:00 to 23:00.
	 */
	@Test (groups = "UI")
	public void testTimeTextDisplayedInTimeline() {
		HomeTabletPage homeTabletPage = new HomeTabletPage();
		SchedulePage schedulePage = homeTabletPage.clickScheduleBtn();
		schedulePage.moveTimeLine(-100000);	
        Assert.assertTrue(schedulePage.isTextHourDisplayed("23:00"));
        
        schedulePage.moveTimeLine(100000);
        Assert.assertTrue(schedulePage.isTextHourDisplayed("00:00"));
	}
}
