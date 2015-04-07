package tests.admin.resources;

import static framework.common.AppConfigConstants.EXCEL_INPUT_DATA;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import framework.pages.admin.HomeAdminPage;
import framework.pages.admin.conferencerooms.RoomInfoPage;
import framework.pages.admin.conferencerooms.RoomResourceAssociationsPage;
import framework.pages.admin.conferencerooms.RoomsPage;
import framework.pages.admin.resources.ResourceAssociationsPage;
import framework.pages.admin.resources.ResourceCreatePage;
import framework.pages.admin.resources.ResourceInfoPage;
import framework.pages.admin.resources.ResourcesPage;
import framework.rest.RootRestMethods;
import framework.utils.readers.ExcelReader;

/**
 * TC33: Verify that rooms name associated to a resource are displayed on {Name} column in 
 * {ResourceInfo>Resource Associations} form when it is selected
 * @author Marco Llano
 */
public class AssociatedRoomIsDisplayedInResourceAssociationPage {
	private ResourceAssociationsPage resourceAssociationsPage;
	private ResourcesPage resourcesPage;
	private HomeAdminPage homeAdminPage;

	//ExcelReader is used to read rooms data
	private ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	private List<Map<String, String>> testData = excelReader.getMapValues("Resources");
	private String resourceName = testData.get(0).get("ResourceName");
	private String resourceDisplayName = testData.get(0).get("ResourceDisplayName");

	@BeforeMethod(groups = "FUNCTIONAL")
	public void beforeMethod() {		
		homeAdminPage = new HomeAdminPage();
		resourcesPage = homeAdminPage.clickResourcesLink();		

		//Variables declaration and initialize		
		String quantity = testData.get(0).get("Value");
		String roomDisplayName = testData.get(0).get("Room Name");
		String roomDisplayName1 = testData.get(1).get("Room Name");
		String roomDisplayName2 = testData.get(2).get("Room Name");

		//create a resource					
		ResourceCreatePage resourceCreatePage = resourcesPage.clickAddResourceBtn();		
		resourcesPage = resourceCreatePage.setResourceName(resourceName)
				.setResourceDisplayName(resourceDisplayName)
				.clickSaveResourceBtn();

		//Associate a resource to the room01
		RoomsPage roomsPage = homeAdminPage.clickConferenceRoomsLink();
		RoomInfoPage roomInfoPage = roomsPage.doubleClickOverRoomName(roomDisplayName);
		RoomResourceAssociationsPage roomResourceAssociation = roomInfoPage.clickResourceAssociationsLink();
		roomsPage = roomResourceAssociation.clickAddResourceToARoom(resourceDisplayName)
				.changeValueForResourceFromAssociatedList(resourceDisplayName, quantity)
				.clickSaveBtn();

		//Associate a resource to the room02
		roomInfoPage = roomsPage.doubleClickOverRoomName(roomDisplayName1);
		roomResourceAssociation = roomInfoPage.clickResourceAssociationsLink();
		roomsPage = roomResourceAssociation.clickAddResourceToARoom(resourceDisplayName)
				.changeValueForResourceFromAssociatedList(resourceDisplayName, quantity)
				.clickSaveBtn();

		//Associate a resource to the room03
		roomInfoPage = roomsPage.doubleClickOverRoomName(roomDisplayName2);
		roomResourceAssociation = roomInfoPage.clickResourceAssociationsLink();
		roomsPage = roomResourceAssociation.clickAddResourceToARoom(resourceDisplayName)
				.changeValueForResourceFromAssociatedList(resourceDisplayName, quantity)
				.clickSaveBtn();
	}

	@Test(groups = "FUNCTIONAL")
	public void testAssociatedRoomIsDisplayedInResourceAssociationPage() throws InterruptedException, JSONException, MalformedURLException, IOException {
		ResourceInfoPage resourceInfoPage;

		//Open ResourceAssociationsPage
		resourcesPage = homeAdminPage.clickResourcesLink();
		resourceInfoPage = resourcesPage.openResourceInfoPage(resourceDisplayName);
		resourceAssociationsPage = resourceInfoPage.clickResourceAssociationLink();

		//Recover all rooms associated to a resource in a LinkedList
		LinkedList<String> linkedList = RootRestMethods.getRoomNamesByResource(resourceName);

		//Assertion for TC33
		for (String roomList : linkedList) {
			Assert.assertTrue(resourceAssociationsPage.getRoomDisplayNameFromResourceAssociationPage(roomList));
		}
	}

	@AfterMethod(groups = "FUNCTIONAL")
	public void afterMethod() throws MalformedURLException, IOException {	
		RootRestMethods.deleteResource(resourceName);
	}
}