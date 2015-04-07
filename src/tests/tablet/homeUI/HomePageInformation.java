package tests.tablet.homeUI;

import static framework.common.AppConfigConstants.EXCEL_INPUT_DATA;
import static framework.utils.TimeManager.getCurrentDate;
import static framework.utils.TimeManager.differenceBetweenTimes;

import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import framework.pages.tablet.HomeTabletPage;
import framework.utils.readers.ExcelReader;

/**
 * This TCs verify that the information displayed is correct in the home page when it is loaded.
 * TC27: Room display name is displayed in the main window.
 * TC28: Room code is displayed in the main window.
 * TC33: Current time is displayed in the main window.
 * TC19: Available message in {Now} tile, default value of {Now} tile.
 * TC29: Default color of {Now} tile.
 * TC17: Default End of day message in {Next} tile.
 * TC05: Time left display the time remaining from current time to midnight.
 * @author Eliana Navia
 * 
 */
public class HomePageInformation {
	private ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	private List<Map<String, String>> meetingData = excelReader.getMapValues("MeetingData");
	
	/**
	 * TC27: This test case verifies that room display name is displayed 
	 * in the main container window.
	 * 
	 */
	@Test (groups = "UI")
	public void testRoomNameDisplayedInMainWindow() {
		HomeTabletPage homeTabletPage = new HomeTabletPage();
		String actualRoomDisplayName = homeTabletPage.getRoomDisplayNameLbl();
		String expectedRoomDisplayName = meetingData.get(0).get("Room");

		//Verify that actual room display name is equal to expected.
		Assert.assertEquals(actualRoomDisplayName, expectedRoomDisplayName );
	}	

	/**
	 * TC28: This test case verifies that the code of a room is displayed in the main window.
	 * 
	 */
	@Test (groups = "UI")
	public void testRoomCodeDisplayedInMainWindow() {
		HomeTabletPage homeTabletPage = new HomeTabletPage();
		String actualRoomCode = homeTabletPage.getRoomCodeLbl();
		String expectedRoomCode = meetingData.get(0).get("Room code");

		//Verify that actual room code is equal to expected.
		Assert.assertEquals(actualRoomCode, expectedRoomCode);
	}	

	/**
	 * TC33: This test case verifies that current time is  displayed 
	 * in the main container window.
	 * 
	 */
	@Test (groups = "UI")
	public void testCurrentTimeIsDisplayedInMainWindow() {
		HomeTabletPage homeTabletPage = new HomeTabletPage();
		String actualCurrentTime = homeTabletPage.getCurrentTimeLbl();
		String expectedCurentTime = getCurrentDate("HH:mm");

		//Verify that current time is displayed.
		Assert.assertEquals(actualCurrentTime, expectedCurentTime );
	}

	/**
	 * TC19: This test case verifies that "Available" message is displayed 
	 * in {Now} tile when any meeting is in progress in the room.
	 * 
	 */
	@Test (groups = "UI")
	public void testAvailableMessageDisplayedWhenRoomIsFreeEOD() {
		HomeTabletPage homeTabletPage = new HomeTabletPage();
		String actualNowTileValue = homeTabletPage.getNowTileLbl();
		String expectedNowTileValue = "Available";

		Assert.assertEquals(actualNowTileValue, expectedNowTileValue );
	}

	/**
	 * TC29: This test case verifies that now title is green color 
	 * when any room is in progress in the room.
	 * 
	 */
	@Test (groups = "UI")
	public void testAvailableColorIsDisplayedInNowTileWhenAnyMeetingIsInProgress() {
		HomeTabletPage homeTabletPage = new HomeTabletPage();
		String actualNowTileColor = homeTabletPage.getNowTileColor();
		String expectedNowTileColor = meetingData.get(0).get("Available color");

		Assert.assertEquals(actualNowTileColor, expectedNowTileColor );
	}

	/**
	 * TC17: This test case verifies that "End of day" message is displayed 
	 * in {Next} tile when any meeting is in progress and the room is free EOD.
	 * 
	 */
	@Test (groups = "UI")
	public void testEndOfDayMessageIsDisplayedWhenRoomIsFreeEOD() {
		HomeTabletPage homeTabletPage = new HomeTabletPage();
		String actualNextTileValue = homeTabletPage.getNextTileLbl();
		String expectedNextTileValue = "End of day";

		Assert.assertEquals(actualNextTileValue, expectedNextTileValue );
	}

	/**
	 * TC05: This test case verifies that time left displayed in {Now} Tape 
	 * is the amount of hours reaming until 2359 when any meeting is in 
	 * progress and the room is free EOD.
	 * 
	 */
	@Test (groups = "UI")
	public void testTimeLeftUntilMidnightWhenRoomIsFreeAfter() {
		HomeTabletPage homeTabletPage = new HomeTabletPage();
		String actualTimeLeft = homeTabletPage.getTimeLeftLbl();
		String expectedTimeLeft = differenceBetweenTimes(getCurrentDate("HH:mm"), "23:59");

		//Verify that the difference of current time - midnight(23:59) is correct.
		Assert.assertEquals(actualTimeLeft, expectedTimeLeft );
	}
}
