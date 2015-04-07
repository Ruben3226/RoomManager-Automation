package tests.admin.conferenceRoomResourceAssociations;

import static framework.common.AppConfigConstants.EXCEL_INPUT_DATA;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import framework.common.UIMethods;
import framework.pages.admin.HomeAdminPage;
import framework.pages.admin.conferencerooms.RoomInfoPage;
import framework.pages.admin.conferencerooms.RoomResourceAssociationsPage;
import framework.pages.admin.conferencerooms.RoomsPage;
import framework.pages.admin.resources.ResourcesPage;
import framework.rest.RootRestMethods;
import framework.utils.readers.ExcelReader;
import framework.utils.readers.JsonReader;

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
	private String resourceFileJSON = "\\src\\tests\\Resource1.json";
	private String filePath = System.getProperty("user.dir") + resourceFileJSON;
	private String resourceName = jsonValue.readJsonFile("name" , resourceFileJSON);
	private String resourceDisplayName = jsonValue.readJsonFile("customName" , resourceFileJSON);

	@BeforeClass(groups = "FUNCTIONAL")
	public void associateAResourceCreated() throws MalformedURLException, IOException {

		//Creating resource by Rest
		RootRestMethods.createResource(filePath, "");
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
		RootRestMethods.deleteResource(resourceName);
		UIMethods.refresh();
		roomsPage = resourcesPage.clickConferenceRoomsLink();
		RoomInfoPage roomInfoPage = roomsPage.doubleClickOverRoomName(roomName);
		RoomResourceAssociationsPage roomResourceAssociationsPage = roomInfoPage.
				clickResourceAssociationsLink();

		//Assertion for TC01  
		Assert.assertFalse(roomResourceAssociationsPage.searchResource(resourceDisplayName));		
	}	
}
