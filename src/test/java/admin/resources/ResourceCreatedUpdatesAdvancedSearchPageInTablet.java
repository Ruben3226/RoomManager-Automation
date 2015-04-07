package test.java.admin.resources;

import static main.java.utils.AppConfigConstants.EXCEL_INPUT_DATA;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import main.java.pages.admin.HomeAdminPage;
import main.java.pages.admin.conferencerooms.RoomInfoPage;
import main.java.pages.admin.conferencerooms.RoomResourceAssociationsPage;
import main.java.pages.admin.conferencerooms.RoomsPage;
import main.java.pages.admin.resources.ResourceCreatePage;
import main.java.pages.admin.resources.ResourcesPage;
import main.java.pages.tablet.HomeTabletPage;
import main.java.pages.tablet.SearchPage;
import main.java.rest.RoomManagerRestMethods;
import main.java.utils.readers.ExcelReader;

/**
 * TC31: Verify that all resources added are listed on {Filter by Resource:} section  in {Advanced > Search} page
 * @author Marco Llano
 */
public class ResourceCreatedUpdatesAdvancedSearchPageInTablet {
	
	//ExcelReader is used to read rooms data
	private ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	private List<Map<String, String>> resourcesDataList = excelReader.getMapValues("Resources");
	private String resourceName = resourcesDataList.get(0).get("ResourceName");

	@Test(groups = "FUNCTIONAL")
	public void testResourceCreatedUpdatesAdvancedSearchPageInTablet() {
		HomeAdminPage homeAdminPage = new HomeAdminPage();
		ResourcesPage resourcesPage = homeAdminPage.clickResourcesLink();

		//Variables declaration and initialize		
		String resourceDisplayName = resourcesDataList.get(0).get("ResourceDisplayName");
		String quantity = resourcesDataList.get(0).get("Value");
		String roomDisplayName = resourcesDataList.get(0).get("Room Name");

		//Create a resource
		ResourceCreatePage resourceCreatePage  = resourcesPage.clickAddResourceBtn();		
		resourcesPage = resourceCreatePage.setResourceName(resourceName)
				.setResourceDisplayName(resourceDisplayName)
				.clickSaveResourceBtn();

		//Associate a resource to a room by resource display name
		RoomsPage roomsPage = homeAdminPage.clickConferenceRoomsLink();
		RoomInfoPage roomInfoPage = roomsPage.doubleClickOverRoomName(roomDisplayName);
		RoomResourceAssociationsPage roomResourceAssociationsPage = roomInfoPage.clickResourceAssociationsLink();
		roomsPage = roomResourceAssociationsPage.clickAddResourceToARoom(resourceDisplayName)
				.changeValueForResourceFromAssociatedList(resourceDisplayName, quantity)
				.clickSaveBtn();

		//Open Advanced search from tablet
		HomeTabletPage homeTabletPage =  new HomeTabletPage();
		SearchPage searchPage = homeTabletPage.clickSearchBtn();
		searchPage.clickCollapseAdvancedBtn();

		//Assertion for TC31
		Assert.assertTrue(searchPage.isResourceInAdvancedSearch(resourceDisplayName));
	}

	@AfterMethod(groups = "FUNCTIONAL")
	public void afterMethod() throws MalformedURLException, IOException {			
		RoomManagerRestMethods.deleteResource(resourceName);
	}
}