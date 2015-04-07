package tests.admin.conferenceRoom;

import static framework.common.AppConfigConstants.EXCEL_INPUT_DATA;

import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import framework.pages.admin.HomeAdminPage;
import framework.pages.admin.conferencerooms.RoomsPage;
import framework.pages.tablet.HomeTabletPage;
import framework.pages.tablet.SearchPage;
import framework.utils.readers.ExcelReader;

/**
 * TC24: Verify that when a room status is disabled, the tablet does not schedule meetings
 * @author Juan Carlos Guevara
 */
public class ForARoomDisabledTheTabletDoesNotScheduleMeetings {
	
	//Reading resource data from an .xls file
	private ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	private List<Map<String, String>> testData = excelReader.getMapValues("Resources");
	private String roomName = testData.get(1).get("Room Name");

	@BeforeClass(groups = "FUNCTIONAL")
	public void disableARoom(){

		//Disabling room in Admin 
		HomeAdminPage homeAdminPage = new HomeAdminPage();
		RoomsPage roomsPage = homeAdminPage.clickConferenceRoomsLink();		
		roomsPage.enableDisableIcon(roomName);		
	}

	@Test(groups = "FUNCTIONAL")
	public void testForARoomDisabledTheTabletDoesNotScheduleMeetings() {

		//Opening tablet to see changes
		HomeTabletPage homeTabletPage = new HomeTabletPage();
		SearchPage searchPage = homeTabletPage.clickSearchBtn()
				.clickCollapseAdvancedBtn()
				.setName(roomName);

		//Assertion for TC24 
		Assert.assertFalse(searchPage.roomIsDiplayed(roomName));		
	}

	@AfterClass(groups = {"FUNCTIONAL"})
	public void EnablingTheRoom() {
		HomeAdminPage homeAdminPage = new HomeAdminPage();
		RoomsPage roomsPage = homeAdminPage.clickConferenceRoomsLink();	
		roomsPage.enableDisableIcon(roomName);
	}
}
