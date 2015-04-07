package test.java.admin.conferenceroom;

import static main.java.utils.AppConfigConstants.EXCEL_INPUT_DATA;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import jxl.read.biff.BiffException;

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
 * TC10: Verify that the changes on Displayed Name of rooms are reflected in tablet app 
 * @author Ruben Blanco
 * 
 */
public class DisplayNameIsReflectedInTablet {
	
	//read excel to create variables
	private ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	private List<Map<String, String>> roomList = excelReader.getMapValues("RoomInfo");
	private String displayName = roomList.get(0).get("DisplayName");	  	  
	private String newDisplayName = roomList.get(0).get("NewDisplayName");	  	  
	
	@Test(groups = "FUNCTIONAL")
	public void testChangesInDisplayNameAreReflectedIntablet() throws InterruptedException, IOException, BiffException {
		HomeAdminPage homeAdminPage = new HomeAdminPage();
		RoomsPage roomsPage = homeAdminPage.clickConferenceRoomsLink();
		RoomInfoPage roomInfoPage = roomsPage.doubleClickOverRoomName(displayName);
		roomInfoPage.setDisplayName(newDisplayName)
			.clickSaveBtn();		
		
		//get the modified room display name
		String savedDisplayName = roomsPage.getRoomDisplayName(newDisplayName);
		
		//navigate to tablet
		HomeTabletPage homeTabletPage = new HomeTabletPage();
		SettingsPage settingsPage = homeTabletPage.clickSettingsBtn();
		settingsPage.selectRoom(newDisplayName); 	   
		String tabletDisplayName = homeTabletPage.getRoomDisplayNameLbl();
		
		//Assertion for TC10
		Assert.assertEquals(savedDisplayName, tabletDisplayName);
	}
	
	@AfterClass(groups = "FUNCTIONAL")
	public void preconditions() throws InterruptedException, BiffException, IOException{
		HomeAdminPage homeAdminPage = new HomeAdminPage();
		RoomsPage roomsPage = homeAdminPage.clickConferenceRoomsLink();
		RoomInfoPage roomInfoPage = roomsPage.doubleClickOverRoomName(newDisplayName);
		
		//Clean room display name with default value
		roomInfoPage.setDisplayName(displayName)
			.clickSaveBtn();
	}
}
