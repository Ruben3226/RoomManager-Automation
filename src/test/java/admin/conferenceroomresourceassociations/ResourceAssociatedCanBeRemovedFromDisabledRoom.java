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
import main.java.rest.RoomManagerRestMethods;
import main.java.utils.readers.ExcelReader;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * TC10: Verify that resources associated can be removed from a room disabled.
 * @author Ruben Blanco
 *
 */
public class ResourceAssociatedCanBeRemovedFromDisabledRoom {

	//read to excel to create variables for resource creation
	private ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	private List<Map<String, String>> roomList = excelReader.getMapValues("Resources");
	private String roomName = roomList.get(0).get("Room Name");
	private String resourceName = roomList.get(0).get("ResourceName");
	private String resourceDisplayName = roomList.get(0).get("ResourceDisplayName");
	private String resourceDescription = roomList.get(0).get("Description");
	private String iconTitle = roomList.get(0).get("Icon");	

	@BeforeClass(groups = "FUNCTIONAL")
	public void precondition() {
		HomeAdminPage homeAdminPage = new HomeAdminPage();
		ResourcesPage resourcesPage = homeAdminPage.clickResourcesLink();	
		ResourceCreatePage resourceCreatePage = resourcesPage.clickAddResourceBtn();

		//create a resource
		resourcesPage = resourceCreatePage.clickResourceIcon()
			.selectResourceIcon(iconTitle)
			.setResourceName(resourceName)
			.setResourceDisplayName(resourceDisplayName)
			.setResourceDescription(resourceDescription)
			.clickSaveResourceBtn();
		RoomsPage conferenceRoomPage = resourcesPage.clickConferenceRoomsLink();
		RoomInfoPage infoPage = conferenceRoomPage.doubleClickOverRoomName(roomName);
		RoomResourceAssociationsPage crresourceAssociationsPage = infoPage.clickResourceAssociationsLink();
		
		//Associate resource to room
		crresourceAssociationsPage.clickAddResourceToARoom(resourceDisplayName)
			.clickSaveBtn();
	}

	@Test(groups = "FUNCTIONAL")
	public void testResourcesAssociatedCanBeRemovedFromDisabledRoom() {
		
		//reading to excel to create variables
		ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
		List<Map<String, String>> testData = excelReader.getMapValues("RoomInfo");
		String roomDisplayName = roomName;
		String resourceName = testData.get(0).get("AssociatedResource");
		
		//navigate to home admin page
		HomeAdminPage homeAdminPage = new HomeAdminPage();
		RoomsPage roomsPage = homeAdminPage.clickConferenceRoomsLink();
		roomsPage.enableDisableIcon(roomDisplayName);
		RoomInfoPage roomInfoPage = roomsPage.doubleClickOverRoomName(roomDisplayName);
		RoomResourceAssociationsPage resourceAssociation = roomInfoPage.clickResourceAssociationsLink();
		resourceAssociation.removeResourceFromAssociatedList(resourceName)
			.clickSaveBtn();
		
		//Assertion for TC10
		Assert.assertTrue(resourceAssociation.searchResource(resourceName));
	}

	@AfterClass(groups = "FUNCTIONAL")
	public void cleanRoom() throws InterruptedException, BiffException, IOException {
		RoomsPage roomsPage = new RoomsPage();
		roomsPage.enableDisableIcon(roomName);
			
		//Delete resource
		RoomManagerRestMethods.deleteResource(resourceName);
	}
}
