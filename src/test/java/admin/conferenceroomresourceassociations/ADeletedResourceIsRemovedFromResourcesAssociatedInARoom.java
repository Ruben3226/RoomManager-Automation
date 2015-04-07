package test.java.admin.conferenceroomresourceassociations;

import static main.java.utils.AppConfigConstants.EXCEL_INPUT_DATA;
import static main.java.utils.AppConfigConstants.RESOURCE1_PATH;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import main.java.pages.admin.HomeAdminPage;
import main.java.pages.admin.conferencerooms.RoomInfoPage;
import main.java.pages.admin.conferencerooms.RoomResourceAssociationsPage;
import main.java.pages.admin.conferencerooms.RoomsPage;
import main.java.pages.admin.resources.ResourcesPage;
import main.java.rest.RoomManagerRestMethods;
import main.java.selenium.UIMethods;
import main.java.utils.readers.ExcelReader;
import main.java.utils.readers.JsonReader;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * TC01: Verify that a resource deleted is removed from resources associated in a room
 * @author Juan Carlos Guevara 
 */
public class ADeletedResourceIsRemovedFromResourcesAssociatedInARoom {
	
	//Reading resource data from an .xls file
	private ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	private List<Map<String, String>> testData = excelReader.getMapValues("Resources");
	private String roomName = testData.get(0).get("Room Name");

	//Reading json resource information
	private JsonReader jsonValue = new JsonReader();
	private String filePath = System.getProperty("user.dir") + RESOURCE1_PATH;
	private String resourceName = jsonValue.readJsonFile("name" , RESOURCE1_PATH);
	private String resourceDisplayName = jsonValue.readJsonFile("customName" , RESOURCE1_PATH);

	@BeforeClass(groups = "FUNCTIONAL")
	public void associateAResourceCreated() throws MalformedURLException, IOException {

		//Creating resource by Rest
		RoomManagerRestMethods.createResource(filePath, "");
		HomeAdminPage homeAdminPage = new HomeAdminPage();				
		ResourcesPage resourcesPage = homeAdminPage.clickResourcesLink();
		
		//Associating resource to a room
		RoomsPage roomsPage = resourcesPage.clickConferenceRoomsLink();
		RoomInfoPage roomInfoPage = roomsPage.doubleClickOverRoomName(roomName);
		RoomResourceAssociationsPage roomsResourceAssociationsPage = roomInfoPage
				.clickResourceAssociationsLink();
		roomsResourceAssociationsPage.clickAddResourceToARoom(resourceDisplayName);
		roomsPage = roomsResourceAssociationsPage.clickSaveBtn();
	}

	@Test(groups = "FUNCTIONAL")
	public void testADeletedResourceIsRemovedFromResourcesAssociatedInARoom() 
			throws MalformedURLException, IOException {
		ResourcesPage resourcesPage = new ResourcesPage();
		RoomsPage roomsPage = resourcesPage.clickConferenceRoomsLink();
		resourcesPage = roomsPage.clickResourcesLink();

		//Deleting resource
		RoomManagerRestMethods.deleteResource(resourceName);
		UIMethods.refresh();
		roomsPage = resourcesPage.clickConferenceRoomsLink();
		RoomInfoPage roomInfoPage = roomsPage.doubleClickOverRoomName(roomName);
		RoomResourceAssociationsPage roomResourceAssociationsPage = roomInfoPage.
				clickResourceAssociationsLink();

		//Assertion for TC01  
		Assert.assertFalse(roomResourceAssociationsPage.searchResource(resourceDisplayName));		
	}	
}
