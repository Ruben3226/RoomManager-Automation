package test.java.admin.conferenceroom;

import static main.java.utils.AppConfigConstants.EXCEL_INPUT_DATA;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import main.java.pages.admin.HomeAdminPage;
import main.java.pages.admin.conferencerooms.RoomInfoPage;
import main.java.pages.admin.conferencerooms.RoomsPage;
import main.java.pages.tablet.HomeTabletPage;
import main.java.pages.tablet.SettingsPage;
import main.java.utils.readers.ExcelReader;

/**
 * TC09: Verify that the changes on Code of rooms are reflected in tablet app 
 * @author Ruben Blanco
 *
 */
public class CoderoomsIsReflectedInTablet {
	//read excel to create variables
	private ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	private List<Map<String, String>> roomList = excelReader.getMapValues("RoomInfo");
	private String displayName = roomList.get(0).get("DisplayName");	  	  
	private String roomCode = roomList.get(0).get("Code");
	private String empty = "";
	
	@Test(groups = "FUNCTIONAL")
	public void testCoderoomsIsReflectedInTablet() {
		HomeAdminPage homeAdminPage = new HomeAdminPage();
		RoomsPage roomsPage = homeAdminPage.clickConferenceRoomsLink();
		RoomInfoPage roomInfoPage = roomsPage.doubleClickOverRoomName(displayName);
		roomInfoPage.setRoomCode(roomCode)
			.clickCancelBtn();
		
		//get room saved room code
		String savedCode = roomsPage.doubleClickOverRoomName(displayName)
			.getRoomCode();  
		roomInfoPage.clickCancelBtn();
		
		//navigate to tablet
		HomeTabletPage homeTabletPage = new HomeTabletPage();
		SettingsPage settingsPage = homeTabletPage.clickSettingsBtn();
		settingsPage.selectRoom(displayName);   
		String tabletRoomCode = homeTabletPage.getRoomCodeLbl();
		
		//Assertion for TC09
		Assert.assertEquals(savedCode, tabletRoomCode);
	}
	
	@AfterClass(groups = "FUNCTIONAL")
	public void postcondition() {
		HomeAdminPage homeAdminPage = new HomeAdminPage();
		RoomsPage roomsPage = homeAdminPage.clickConferenceRoomsLink();
		RoomInfoPage roomInfoPage = roomsPage.doubleClickOverRoomName(displayName);
		roomInfoPage.setRoomCode(empty)
			.clickSaveBtn();
	}
}
