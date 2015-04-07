package test.java.admin.conferenceroom;

import static main.java.utils.AppConfigConstants.EXCEL_INPUT_DATA;

import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import main.java.pages.admin.HomeAdminPage;
import main.java.pages.admin.conferencerooms.RoomsPage;
import main.java.pages.tablet.HomeTabletPage;
import main.java.pages.tablet.SearchPage;
import main.java.utils.readers.ExcelReader;

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
