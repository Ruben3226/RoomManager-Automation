package test.java.admin.conferenceroomresourceassociations;

import static main.java.utils.AppConfigConstants.EXCEL_INPUT_DATA;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import jxl.read.biff.BiffException;
import main.java.pages.admin.HomeAdminPage;
import main.java.pages.admin.conferencerooms.RoomInfoPage;
import main.java.pages.admin.conferencerooms.RoomResourceAssociationsPage;
import main.java.pages.admin.conferencerooms.RoomsPage;
import main.java.pages.admin.resources.ResourceCreatePage;
import main.java.pages.admin.resources.ResourcesPage;
import main.java.pages.tablet.HomeTabletPage;
import main.java.pages.tablet.SettingsPage;
import main.java.rest.RoomManagerRestMethods;
import main.java.utils.readers.ExcelReader;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


/**
 * TC08: Verify that a resource associated to a room that is deleted is removed on home page.
 * @author Ruben Blanco
 * 
 */
public class ResourceAssociatedToRoomThatIsDeletedIsRemovedOnHomePage {
	
	//read excel to create variables to resource creation
	private ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	private List<Map<String, String>> roomList = excelReader.getMapValues("Resources");
	private String roomName = roomList.get(0).get("Room Name");
	private String resourceName = roomList.get(0).get("ResourceName");
	private String resourceDisplayName = roomList.get(0).get("ResourceDisplayName");
	private String resourceDescription = roomList.get(0).get("Description");
	private String iconTitle = roomList.get(0).get("Icon");	
	private String quantity = roomList.get(0).get("Value");
	
	@BeforeClass(groups = {"FUNCTIONAL"})
	public void precondition() {
		HomeAdminPage homeAdminPage = new HomeAdminPage();
		ResourcesPage ResourcesPage = homeAdminPage.clickResourcesLink();	
		ResourceCreatePage resourceCreatePage = ResourcesPage.clickAddResourceBtn();
		
		//create a resource
		ResourcesPage = resourceCreatePage.clickResourceIcon()
			.selectResourceIcon(iconTitle)
			.setResourceName(resourceName)
			.setResourceDisplayName(resourceDisplayName)
			.setResourceDescription(resourceDescription)
			.clickSaveResourceBtn();
		RoomsPage roomsPage = ResourcesPage.clickConferenceRoomsLink();
		RoomInfoPage roomInfoPage = roomsPage.doubleClickOverRoomName(roomName);
		RoomResourceAssociationsPage resourceAssociation = roomInfoPage.clickResourceAssociationsLink();
		resourceAssociation.clickAddResourceToARoom(resourceDisplayName)
			.changeValueForResourceFromAssociatedList(resourceDisplayName,quantity)
			.clickSaveBtn();
	}

	@Test(groups = {"FUNCTIONAL"})
	public void testResourcesAssociatedToRoomthatIsDeletedIsRemovedOnHomePage() {
		
		//reading to excel to create variables of room
		ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
		List<Map<String, String>> testData2 = excelReader.getMapValues("RoomInfo");
		String resourceName = testData2.get(0).get("AssociatedResource");
		String amount = testData2.get(0).get("Quantity");
		String displayName = testData2.get(0).get("DisplayName");
        
		//navigate to home admin page
		HomeAdminPage homeAdminPage = new HomeAdminPage();
		RoomsPage roomsPage = homeAdminPage.clickConferenceRoomsLink();
		RoomInfoPage roomInfoPage = roomsPage.doubleClickOverRoomName(displayName);
		RoomResourceAssociationsPage resourceAssociation = roomInfoPage
			.clickResourceAssociationsLink();
		
		//remove resource of a room
		resourceAssociation.removeResourceFromAssociatedList(resourceName)
			.clickSaveBtn();

		//navigate to tablet
		HomeTabletPage homeTabletPage = new HomeTabletPage();
		SettingsPage settingsPage = homeTabletPage.clickSettingsBtn();
		settingsPage.selectRoom(displayName);
		
		//Assertion for TC08
		Assert.assertFalse(homeTabletPage.VerifyResourceIsAsociated(resourceName, amount));
	}
	
	@AfterClass(groups = "FUNCTIONAL")
	public void cleanRoom() throws InterruptedException, BiffException, IOException {
		
		//Delete resource
	    RoomManagerRestMethods.deleteResource(resourceName);
	}
}
