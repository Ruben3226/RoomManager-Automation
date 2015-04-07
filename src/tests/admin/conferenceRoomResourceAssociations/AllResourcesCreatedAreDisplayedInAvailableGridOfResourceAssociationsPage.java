package tests.admin.conferenceRoomResourceAssociations;

import static framework.common.AppConfigConstants.EXCEL_INPUT_DATA;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import framework.pages.admin.HomeAdminPage;
import framework.pages.admin.conferencerooms.RoomInfoPage;
import framework.pages.admin.conferencerooms.RoomResourceAssociationsPage;
import framework.pages.admin.conferencerooms.RoomsPage;
import framework.pages.admin.resources.ResourceCreatePage;
import framework.pages.admin.resources.ResourcesPage;
import framework.rest.RootRestMethods;
import framework.utils.readers.ExcelReader;

/**
 * TC05: Verify that all resources created are displayed in Available grid of Resource 
 * Associations page
 * @author Juan Carlos Guevara
 */
public class AllResourcesCreatedAreDisplayedInAvailableGridOfResourceAssociationsPage {
	
	//Reading resource data from an .xls file
	private ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	private List<Map<String, String>> testData = excelReader.getMapValues("Resources");
	private String roomName = testData.get(0).get("Room Name");

	@Test(groups = "FUNCTIONAL")
	public void testAllResourcesCreatedAreDisplayedInAvailableGridOfResourceAssociationsPage() 
			throws MalformedURLException, IOException {
		
		for(Map<String, String> resource : testData){
			HomeAdminPage homeAdminPage = new HomeAdminPage();
			ResourcesPage resourcesPage = homeAdminPage.clickResourcesLink();

			//Creating a resource	
			ResourceCreatePage resourceCreatePage = resourcesPage.clickAddResourceBtn();		
			resourceCreatePage.clickResourceIcon()
			.selectResourceIcon(resource.get("Icon"))
			.setResourceName(resource.get("ResourceName"))
			.setResourceDisplayName(resource.get("ResourceDisplayName"))
			.setResourceDescription(resource.get("Description"))
			.clickSaveResourceBtn();			

			//Associate the resource to a room
			RoomsPage roomsPage = resourcesPage.clickConferenceRoomsLink();
			RoomInfoPage roomInfoPage = roomsPage.doubleClickOverRoomName(roomName);
			RoomResourceAssociationsPage roomResourceAssociationsPage = roomInfoPage.
					clickResourceAssociationsLink();

			//Assertion for TC05 
			Assert.assertTrue(roomResourceAssociationsPage.searchResource(resource
					.get("ResourceDisplayName")));
		}
	}

	@AfterClass(groups = "FUNCTIONAL")
	public void deleteResource() throws MalformedURLException, IOException {	
		
		//Deleting resource with API rest method
		for(Map<String, String> resource : testData){					
			RootRestMethods.deleteResource(resource.get("ResourceName"));
		}
	}
}
