package test.java.tablet.schedule;

import org.testng.Assert;
import org.testng.annotations.Test;

import main.java.pages.tablet.HomeTabletPage;
import main.java.pages.tablet.SchedulePage;

/**
 * TC24: This test case verifies that double clicking a period in timeline 
 * creates a meeting box and sets the time for a new meeting with default duration.
 * @author Eliana Navia
 *
 */
public class DoubleClickingATimelinePeriodCreatesAMeetingBoxAndSetNewMeetingTime {

	@Test (groups = "UI")
	public void testDoubleClickingATimelinePeriodCreatesAMeetingBoxAndSetNewMeetingTime() {
		HomeTabletPage homeTabletPage = new HomeTabletPage();
		SchedulePage schedulePage = homeTabletPage.clickScheduleBtn();
		schedulePage.doubleClickTimeLineGroup();
		
		/**Verify that the default duration between the times of "From" and "to" text boxes is 30
		of meeting box created.*/
        Assert.assertEquals(schedulePage.getDurationOfMeetingByDefault(), 30);
	}
}
